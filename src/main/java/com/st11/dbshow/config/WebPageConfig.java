package com.st11.dbshow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="layout.sidebar")
public class WebPageConfig {
    private String MenuA_A;
    private String MenuA_B;
    private String MenuA_C;
    private String MenuB_A;
    private String MenuB_B;
}
