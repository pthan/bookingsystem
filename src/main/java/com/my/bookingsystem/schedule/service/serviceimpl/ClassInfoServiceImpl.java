package com.my.bookingsystem.schedule.service.serviceimpl;

import com.my.bookingsystem.config.Constants;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.mapper.ScheduleMapper;
import com.my.bookingsystem.purchasepackage.entity.Country;
import com.my.bookingsystem.purchasepackage.repository.CountryRepository;
import com.my.bookingsystem.schedule.dto.request.ClassInfoRequest;
import com.my.bookingsystem.schedule.dto.response.ClassInfoResponse;
import com.my.bookingsystem.schedule.entity.ClassInfo;
import com.my.bookingsystem.schedule.repository.ClassInfoRepository;
import com.my.bookingsystem.schedule.service.ClassInfoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
class ClassInfoServiceImpl implements ClassInfoService {

    private final ClassInfoRepository classInfoRepository;
    private final CountryRepository countryRepository;
    private final ScheduleMapper scheduleMapper;


    public ResponseFormat createClassInfo(ClassInfoRequest request, Long userId) {
        ResponseFormat responseFormat=null;

        try {
            Country c = countryRepository.findById(request.getCountryId())
                    .orElseThrow(() -> new EntityNotFoundException("Country not found"));

           ClassInfo classInfo=scheduleMapper.toClassInfoFromClassInfoRequest(request);

            classInfo.setStatus(Constants.STATUS_ACTIVE);
            classInfo.setGuid(UUID.randomUUID().toString());
            classInfo.setCreatedOn(ZonedDateTime.now());
            classInfo.setCreatedBy(userId);
            classInfoRepository.save(classInfo);
            responseFormat=new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage(Optional.of("Class creation success!"));
            ClassInfoResponse response=scheduleMapper.toClassInfoResponse(classInfo);
            response.setCountryId(c.getId());
            responseFormat.setData(Optional.of(response));
            log.info("Successfully creating classinfo with classid:{} and  class name:{}",classInfo.getId(),classInfo.getClassName());
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

    @Override
    public ResponseFormat getById(Long id) {
        ResponseFormat responseFormat=null;
        try {

            Optional<ClassInfo> optionalClassInfo=classInfoRepository.findById(id);

            responseFormat=new ResponseFormat();
            responseFormat.setSuccess(true);
            if(optionalClassInfo.isEmpty()){
                responseFormat.setMessage(Optional.of("No Class found  for classid:"+id+" !"));
                responseFormat.setData(null);
            }
            else{
                responseFormat.setMessage(Optional.of("Class fetch by class id success!"));
                responseFormat.setData(Optional.of(scheduleMapper.toClassInfoResponse(optionalClassInfo.get())));
            }

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
