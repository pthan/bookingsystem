package com.my.bookingsystem.schedule.entity;
import com.my.bookingsystem.purchasepackage.entity.Country;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "classinfo")
@Getter
@Setter
@NoArgsConstructor
public class ClassInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String guid;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "required_credit", nullable = false)
    private Integer requiredCredit;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(nullable = false)
    private Integer duration;

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
}

