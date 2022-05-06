package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.config.ConfigProps;
import guru.springframework.msscbreweryclient.web.model.BeerDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;


@Component
public class BreweryClient {
    public final String BEER_PATH_V1 = "/api/v1/beer/";
    private final String apihost;

    private final RestTemplate restTemplate;

    public BreweryClient(ConfigProps props, RestTemplateBuilder restTemplateBuilder) {
        this.apihost = props.getApihost();
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID id) {
        return restTemplate.getForObject(apihost + BEER_PATH_V1 + id.toString(), BeerDto.class);
    }

    public URI saveNewBeer(BeerDto dto) {
        return restTemplate.postForLocation(apihost + BEER_PATH_V1, dto);

    }

    public void updateBeerById(UUID id, BeerDto dto) {
        restTemplate.put(apihost + BEER_PATH_V1 + id.toString(), dto);
    }
}
