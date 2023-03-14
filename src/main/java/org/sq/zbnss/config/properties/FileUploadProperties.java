package org.sq.zbnss.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created with IntelliJ IDEA.
 *
 */
@Data
@ConfigurationProperties(prefix = "file")
public class FileUploadProperties {

    private String accessPathPattern = "/u/**";
    private String uploadFolder;
    private String accessPrefixUrl;
}
