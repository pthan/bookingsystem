package com.my.bookingsystem.service.serviceimpl;

import com.my.bookingsystem.model.request.PackageSearchRequest;
import com.my.bookingsystem.model.response.PackageListResponse;
import com.my.bookingsystem.model.response.ResponseFormat;
import com.my.bookingsystem.model.shared.PackageItem;
import com.my.bookingsystem.repository.PackageRepository;
import com.my.bookingsystem.service.PackageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    PackageRepository packageRepository;
    public ResponseFormat displayPackageList(PackageSearchRequest request){
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
                packageDataList  =  packageRepository.getAllPackageByKeywords( request.getKeyword(), pageable);
                totalRecords =  packageRepository.countPackageByKeywords(request.getKeyword());
            }
            packageDataList.forEach(iPackage->{

                PackageItem pd=new PackageItem();
                pd.setId(iPackage.getId());
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
            responseFormat.setMessage( Optional.of("Package list successful") );
            responseFormat.setData( Optional.of(response) );
        }catch (Exception e){
            e.printStackTrace();
            return ResponseFormat
                    .failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }
        return  responseFormat;
    }
}
