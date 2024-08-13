package com.my.bookingsystem.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
@NoArgsConstructor
public class ResponseFormat {
    @Getter @JsonProperty("success") private Boolean success;
    @Getter @Setter @JsonProperty("data") private Optional<Object> data;
    @Getter @Setter @JsonProperty("message") private Optional<String> message;
    @Getter @Setter @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Optional<ZonedDateTime> timestamp;
    @Getter @Setter @JsonProperty("error") private Optional<String> error;

    public void setSuccess(boolean isSuccess) {
        this.success = isSuccess;
        if (this.success) {
            timestamp = Optional.empty();
            error = Optional.empty();
            message = Optional.empty();
        } else {
            timestamp = Optional.of(ZonedDateTime.now());
        }
    }


    public void appendError(String err) {
        if (error != null && error.isPresent()) {
            this.error = Optional.of(this.error.get() +  System.lineSeparator() + " " + err);
        } else {
            this.error = Optional.of(err);
        }
    }

    @Builder(builderMethodName = "failedResponse")
    public static ResponseFormat newFailedResponse(@NonNull String message, String error, Object data) {
        ResponseFormat response = new ResponseFormat();
        response.setSuccess(false);
        response.setTimestamp(Optional.of(ZonedDateTime.now()));
        response.setMessage(Optional.of(message));

        if (error != null && !error.isEmpty()) {
            response.setError(Optional.of(error));
        }
        if (data != null) {
            response.setData(Optional.of(data));
        }
        return response;
    }
}
