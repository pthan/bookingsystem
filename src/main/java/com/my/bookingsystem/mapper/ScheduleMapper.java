package com.my.bookingsystem.mapper;

import com.my.bookingsystem.purchasepackage.entity.Country;
import com.my.bookingsystem.schedule.dto.request.ClassInfoRequest;
import com.my.bookingsystem.schedule.dto.response.ClassInfoResponse;
import com.my.bookingsystem.schedule.dto.response.ClassScheduleDetailItem;
import com.my.bookingsystem.schedule.dto.response.ClassScheduleItem;
import com.my.bookingsystem.schedule.entity.ClassInfo;
import com.my.bookingsystem.schedule.entity.ClassSchedule;
import com.my.bookingsystem.schedule.entity.ClassScheduleDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ClassInfoResponse toClassInfoResponse(ClassInfo classInfo);

    @Mapping(target = "country", expression = "java(mapCountry(request.getCountryId()))")
    ClassInfo toClassInfoFromClassInfoRequest(ClassInfoRequest request);

    @Mapping(target = "country", expression = "java(schedule.getClassInfo().getCountry().getCountryName())")
    @Mapping(source = "classInfo.className", target = "className")
    @Mapping(source = "classInfo.requiredCredit", target = "credit")
    @Mapping(source = "details", target = "classDetailSchedule")
    @Mapping(target = "duration", expression = "java(calculateDuration(schedule))")
    ClassScheduleItem toClassScheduleItem(ClassSchedule schedule);

    List<ClassScheduleItem> toClassScheduleItems(List<ClassSchedule> schedules);

    @Mapping(source = "sessionDay", target = "sessionDay")
    @Mapping(source = "sessionStartTime", target = "sessionStartTime")
    @Mapping(source = "sessionEndTime", target = "sessionEndTime")
    ClassScheduleDetailItem toClassScheduleDetailItem(ClassScheduleDetail detail);

    List<ClassScheduleDetailItem> toDetailItemsList(List<ClassScheduleDetail> details);

    default String calculateDuration(ClassSchedule schedule) {
        if (schedule.getDetails() == null || schedule.getDetails().isEmpty()) return "";
        try {
            ClassScheduleDetail detail = schedule.getDetails().get(0);
            LocalTime start = LocalTime.parse(detail.getSessionStartTime());
            LocalTime end = LocalTime.parse(detail.getSessionEndTime());
            return Duration.between(start, end).toMinutes() + " minutes";
        } catch (Exception e) {
            return "";
        }
    }


    default Country mapCountry(Long countryId) {
        Country c = new Country();
        c.setId(countryId);
        return c;
    }
}
