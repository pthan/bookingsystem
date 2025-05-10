package com.my.bookingsystem.schedule.service.serviceimpl;

import com.my.bookingsystem.config.Constants;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.mapper.ScheduleMapper;
import com.my.bookingsystem.purchasepackage.dto.response.PackageListResponse;
import com.my.bookingsystem.purchasepackage.entity.Country;
import com.my.bookingsystem.purchasepackage.repository.CountryRepository;
import com.my.bookingsystem.schedule.dto.request.ClassScheduleCreateRequest;
import com.my.bookingsystem.schedule.dto.request.ClassScheduleSearchRequest;
import com.my.bookingsystem.schedule.dto.response.ClassScheduleDetailItem;
import com.my.bookingsystem.schedule.dto.response.ClassScheduleItem;
import com.my.bookingsystem.schedule.dto.response.ClassScheduleListResponse;
import com.my.bookingsystem.schedule.entity.ClassInfo;
import com.my.bookingsystem.schedule.entity.ClassSchedule;
import com.my.bookingsystem.schedule.entity.ClassScheduleDetail;
import com.my.bookingsystem.schedule.repository.ClassInfoRepository;
import com.my.bookingsystem.schedule.repository.ClassScheduleDetailRepository;
import com.my.bookingsystem.schedule.repository.ClassScheduleRepository;
import com.my.bookingsystem.schedule.service.ClassScheduleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.my.bookingsystem.config.Constants.STATUS_ACTIVE;
import static com.my.bookingsystem.config.Constants.STATUS_PROGRESS;

@Slf4j
@Service
@AllArgsConstructor
public class ClassScheduleServiceImpl implements ClassScheduleService {

    private final ClassScheduleDetailRepository classScheduleDetailRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final ClassInfoRepository classInfoRepository;
    private final CountryRepository countryRepository;

    @Override
    public ResponseFormat listSchedules(ClassScheduleSearchRequest request) {
        ResponseFormat responseFormat;
        try {
            ClassScheduleListResponse response = ClassScheduleListResponse.builder()
                    .items(new ArrayList<>())
                    .totalRecords(0)
                    .build();

            int page = request.getFirst() == null ? 0 : request.getFirst();
            int size = request.getMax() == null ? Integer.MAX_VALUE : request.getMax();
            String orderBy = request.getOrderBy() == null ? "startDate" : request.getOrderBy();
            boolean asc = Boolean.TRUE.equals(request.getAsc());

            Pageable pageable = PageRequest.of(page, size, asc
                    ? Sort.by(orderBy).ascending()
                    : Sort.by(orderBy).descending());

            Specification<ClassSchedule> spec = Specification.where(null);

            // Always filter: startDate >= today
            ZonedDateTime todayStart = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startDate"), todayStart));

            boolean hasKeyword = request.getKeyword() != null && !request.getKeyword().isBlank();
            boolean hasCountry = request.getCountryId() != null;

            boolean hasDateRange = request.getFromDate() != null && request.getToDate() != null;

            // Apply additional filters only if provided
            if (hasKeyword) {
                String pattern = "%" + request.getKeyword().toLowerCase() + "%";
                spec = spec.and((root, query, cb) ->{
                      query.distinct(true);
                      return  cb.like(cb.lower(root.get("classInfo").get("className")), pattern);
                });
            }
            if (hasCountry) {
                spec = spec.and((root, query, cb) ->
                        cb.equal(root.get("classInfo").get("country").get("id"),
                                request.getCountryId()));
            }
            if (hasDateRange) {
                ZonedDateTime from = request.getFromDate().atStartOfDay(ZoneId.systemDefault());
                ZonedDateTime to = request.getToDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());

                spec = spec.and((root, query, cb) ->
                        cb.and(
                                cb.greaterThanOrEqualTo(root.get("startDate"), from),
                                cb.lessThanOrEqualTo(root.get("startDate"), to)
                        )
                );
            }

            Page<ClassSchedule> pageResult = classScheduleRepository.findAll(spec, pageable);
            List<ClassScheduleItem> items = scheduleMapper.toClassScheduleItems(pageResult.getContent());

            response.setItems(items);
            response.setTotalRecords(pageResult.getTotalElements());

            responseFormat = new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage(Optional.of("Class schedule list successful"));
            responseFormat.setData(Optional.of(response));

            log.info("Successfully fetched schedule list, total records: {}", pageResult.getTotalElements());
        } catch (Exception e) {
            log.error("Error at fetching records", e);
            return ResponseFormat.failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }

        return responseFormat;
    }

    public ResponseFormat createClassSchedule(ClassScheduleCreateRequest request, Long userId) {
        ResponseFormat responseFormat = null;

        try {
            ClassInfo classInfo = classInfoRepository.findById(request.getClassInfoId())
                    .orElseThrow(() -> new EntityNotFoundException("ClassInfo not found"));
            Country country = countryRepository.findById(classInfo.getCountry().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Country not found"));
            ClassSchedule schedule = new ClassSchedule();
            schedule.setClassInfo(classInfo);
            schedule.setGuid(UUID.randomUUID().toString());
//            ZonedDateTime startDate = request.getStartDate().atStartOfDay(ZoneId.systemDefault());
//            ZonedDateTime endDate = request.getEndDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());
            schedule.setStartDate(request.getStartDate());
            schedule.setEndDate(request.getEndDate());
            schedule.setAvailableSlots(request.getAvailableSlots());

            schedule.setBookingCount(0);
            schedule.setWaitingCount(0);
            schedule.setStatus(STATUS_ACTIVE);
            schedule.setScheduleClassStatus(STATUS_PROGRESS);
            schedule.setCreatedBy(userId);
            schedule.setCreatedOn(ZonedDateTime.now());

            List<ClassScheduleDetail> detailList = new ArrayList<>();
            for (ClassScheduleCreateRequest.ScheduleDetailRequest d : request.getDetails()) {
                ClassScheduleDetail detail = new ClassScheduleDetail();
                detail.setClassSchedule(schedule);  // set parent
                detail.setGuid(UUID.randomUUID().toString());
                detail.setSessionDay(d.getSessionDay());
                detail.setSessionStartTime(d.getStartTime());
                detail.setSessionEndTime(d.getEndTime());
                detail.setStatus(STATUS_ACTIVE);
                detail.setCreatedBy(userId);
                detail.setCreatedOn(ZonedDateTime.now());
                detailList.add(detail);
            }

            schedule.setDetails(detailList); // cascade must be set

            classScheduleRepository.save(schedule);

            responseFormat = new ResponseFormat();
            responseFormat.setSuccess(true);
            responseFormat.setMessage(Optional.of("Class schedule created successfully"));
             ClassScheduleItem responseItems= scheduleMapper.toClassScheduleItem(schedule);
            responseFormat.setData(Optional.of(responseItems));


            log.info("Successfully created class schedule with id={} for classInfoId={}", schedule.getId(), classInfo.getId());

        } catch (Exception e) {
            log.error("Error occurred during class schedule creation", e);
            return ResponseFormat.failedResponse()
                    .message("Processing failed, please try again later!")
                    .data("Processing failed, please try again later!")
                    .build();
        }

        return responseFormat;
    }

}
