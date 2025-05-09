package com.my.bookingsystem.schedule.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NamedEntityGraph(
        name = "ClassSchedule.details",
        attributeNodes = @NamedAttributeNode("details")
)
@Table(name = "class_schedule")
@Getter
@Setter
@NoArgsConstructor
public class ClassSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String guid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classinfo_id", nullable = false)
    private ClassInfo classInfo;

    @Column(name = "start_date", nullable = false)
    private ZonedDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private ZonedDateTime endDate;

    @Column(nullable = false)
    private Integer bookingCount;

    @Column(nullable = false)
    private Integer waitingCount;

    @Column(name = "available_slots", nullable = false)
    private Integer availableSlots;

    @Column(name="schedule_class_status",nullable = false)
    private String scheduleClassStatus;

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

    @OneToMany(mappedBy = "classSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClassScheduleDetail> details;
}
