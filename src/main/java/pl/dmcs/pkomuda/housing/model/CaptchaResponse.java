package pl.dmcs.pkomuda.housing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaptchaResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private List<String> errorCodes;
}
