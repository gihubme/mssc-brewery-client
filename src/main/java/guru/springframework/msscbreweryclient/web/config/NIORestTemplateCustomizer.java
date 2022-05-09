package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//@Component
public class NIORestTemplateCustomizer implements RestTemplateCustomizer {

    private final Integer connectionRequestTimeout;
    private final Integer soTimeout;
    private final Integer ioThreadCount;

    private final Integer maxTotal;
    private final Integer defaultMaxPerRoute;

    public NIORestTemplateCustomizer(
            @Value("${sfg.nioclient.connectionrequesttimeout}") Integer connectionRequestTimeout,
            @Value("${sfg.nioclient.sotimeout}") Integer soTimeout,
            @Value("${sfg.nioclient.iothreadcount}") Integer ioThreadCount,
            @Value("${sfg.nioclient.maxtotal}") Integer maxTotal,
            @Value("${sfg.nioclient.defaultmaxperroute}") Integer defaultMaxPerRoute) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.soTimeout = soTimeout;
        this.ioThreadCount = ioThreadCount;
        this.maxTotal = maxTotal;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public ClientHttpRequestFactory clientHttpRequestFactory() throws IOReactorException {
        final DefaultConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(
                IOReactorConfig.custom().
                        setConnectTimeout(this.connectionRequestTimeout).
                        setIoThreadCount(this.ioThreadCount).
                        setSoTimeout(this.soTimeout).
                        build());

        final PoolingNHttpClientConnectionManager connectionManager =
                new PoolingNHttpClientConnectionManager(ioreactor);
        connectionManager.setDefaultMaxPerRoute(this.defaultMaxPerRoute);
        connectionManager.setMaxTotal(this.maxTotal);

        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        return new HttpComponentsAsyncClientHttpRequestFactory(httpAsyncClient);

    }

    @Override
    public void customize(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(clientHttpRequestFactory());
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
    }
}
