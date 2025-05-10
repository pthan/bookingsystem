package com.my.bookingsystem.purchasepackage.service.serviceimpl;

import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageRequest;
import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageSearchRequest;
import com.my.bookingsystem.purchasepackage.dto.response.PackageListResponse;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.domain.shared.PackageItem;
import com.my.bookingsystem.purchasepackage.entity.Country;
import com.my.bookingsystem.purchasepackage.entity.CreditPackage;
import com.my.bookingsystem.purchasepackage.repository.CountryRepository;
import com.my.bookingsystem.purchasepackage.repository.PackageRepository;
import com.my.bookingsystem.purchasepackage.service.PackageService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    CountryRepository countryRepository;
    public ResponseFormat getPackageList(CreditPackageSearchRequest request){
        log.info("Get Packkage Info with request info:{}",request);
        ResponseFormat responseFormat=null;
        PackageListResponse response = PackageListResponse.builder()
                .items(new ArrayList<>())
                .totalRecords(0)
                .build();
        try {

            if( request.getFirst() == null)
                request.setFirst(0);
            if( request.getMax() == null)
                request.setMax(Integer.MAX_VALUE);
            if( request.getOrderBy() == null)
                request.setOrderBy("id");
            if( request.getAsc() == null)
                request.setAsc(false);
            if( request.getKeyword() == null)
                request.setKeyword("");
            Sort sort = Sort.by( request.getOrderBy() ).ascending();
            Pageable pageable = PageRequest.of(  request.getFirst(), request.getMax(), sort );
            List<PackageRepository.IPackage> packageDataList;
            long totalRecords =0;
            if(request.getKeyword().equals(""))
            {
                packageDataList  =  packageRepository.getAllPackageList(pageable);
                totalRecords =  packageRepository.countPackage();
            }
            else
            {//search by keywords
                String country = request.getKeyword();
                String packageName = "%" + country + "%";
                packageDataList  =  packageRepository.getAllPackageByKeywords( request.getKeyword(), "%" + request.getKeyword() + "%", pageable);
                totalRecords =  packageRepository.countPackageByKeywords(request.getKeyword(),"%" + request.getKeyword() + "%");
            }
            packageDataList.forEach(iPackage->{

                PackageItem pd=new PackageItem();
                pd.setId(iPackage.getPackageId());
                pd.setPackageName(iPackage.getPackageName());
                pd.setCredit(iPackage.getCredit());
                pd.setGuid(iPackage.getGuid());
                pd.setPrice(iPackage.getPrice());
                pd.setExpireIn(iPackage.getExpireIn());
                pd.setCountryName(iPackage.getCountryName());
                pd.setStatus(iPackage.getStatus());
                response.getItems().add(pd);

            });
            response.setTotalRecords(totalRecords);

            responseFormat = new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage( Optional.of("CreditPackage list successful") );
            responseFormat.setData( Optional.of(response) );
            log.info("Successfully fetching package list and found total {} records",totalRecords);
        }catch (Exception e){
            log.error("Error at fetching records  ",e);
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }
        return  responseFormat;
    }
    public ResponseFormat createPackage(CreditPackageRequest request,long userId){
        ResponseFormat responseFormat=null;

        try {
            Country c = countryRepository.findById(request.getCountryId())
                    .orElseThrow(() -> new EntityNotFoundException("Country not found"));
            CreditPackage pkg = new CreditPackage();
            pkg.setPackageName(request.getPackageName());
            pkg.setCountry(c);
            pkg.setPrice(request.getPrice());
            pkg.setCredit(request.getCredit());
            pkg.setExpireIn(request.getExpireIn());
            pkg.setStatus(CreditPackage.PACKAGE_STATUS_ACTIVE);
            pkg.setGuid(UUID.randomUUID().toString());
            pkg.setCreatedOn(ZonedDateTime.now());
            pkg.setCreatedBy(userId);
            packageRepository.save(pkg);
            responseFormat=new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage(Optional.of("Package creation success!"));
            responseFormat.setData(Optional.of(pkg));
            log.info("Successfully creating package with packageid:{} and  package name:{}",pkg.getId(),pkg.getPackageName());
        }catch (Exception e){
            log.error("error at creating records  ",e);
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }
        return  responseFormat;
    }
}
