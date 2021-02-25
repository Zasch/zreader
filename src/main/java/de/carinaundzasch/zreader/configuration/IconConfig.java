package de.carinaundzasch.zreader.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "de.carinaundzasch.data.icons", ignoreInvalidFields = true)
public class IconConfig {

    @Getter
    @Setter
    private String path;
}
