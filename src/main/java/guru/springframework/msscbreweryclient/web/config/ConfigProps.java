package guru.springframework.msscbreweryclient.web.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
public class ConfigProps {

    private final String apihost;
    private final Integer port;

    //    settings for apache CloseableHttpClient
    private final Integer connectionrequesttimeout;
    private final Integer sockettimeout;
    private final Integer maxtotal;
    private final Integer defaultmaxperroute;

}
