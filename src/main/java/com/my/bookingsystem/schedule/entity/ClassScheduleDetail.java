package com.my.bookingsystem.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "class_schedule_detail")
@Getter
@Setter
@NoArgsConstructor
public class ClassScheduleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String guid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_schedule_id", nullable = false)
    private ClassSchedule classSchedule;

    @Column(name = "session_day", nullable = false)
    private String sessionDay; // e.g., Mon, Tue

    @Column(name = "session_start_time", nullable = false)
    private String sessionStartTime; // stored as "HH:mm"

    @Column(name = "session_end_time", nullable = false)
    private String sessionEndTime; // stored as "HH:mm"

    @Column(nullable = false)
    private String status;

    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    // Optional helper methods for time parsing
    @Transient
    public LocalTime getParsedStartTime() {
        return LocalTime.parse(sessionStartTime);
    }

    @Transient
    public LocalTime getParsedEndTime() {
        return LocalTime.parse(sessionEndTime);
    }
}
