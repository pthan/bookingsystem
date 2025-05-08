    package com.my.bookingsystem.schedule.dto.request;

    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import lombok.Data;

    @Data
    public class ClassInfoRequest {
        @NotBlank
        private String className;

        @NotNull
        @Min(1)
        private Integer requiredCredit;

        @NotNull @Min(1)
        private Integer duration;

        private String description;

        @NotNull
        private Long countryId;
    }
