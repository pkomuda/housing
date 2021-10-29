package pl.dmcs.pkomuda.housing.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.dmcs.pkomuda.housing.model.CaptchaResponse;

@Component
@RequiredArgsConstructor
public class CaptchaValidator {

    private static final String API_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${captcha.key.secret}")
    private String captchaSecretKey;

    private final RestTemplate restTemplate;

    public boolean validate(String captchaResponse) {
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", captchaSecretKey);
        requestMap.add("response", captchaResponse);
        CaptchaResponse apiResponse = restTemplate.postForObject(API_URL, requestMap, CaptchaResponse.class);
        return apiResponse != null && apiResponse.isSuccess();
    }
}
