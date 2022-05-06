package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;

    @Test
    void getBeerById() {
        BeerDto dto = client.getBeerById(UUID.randomUUID());

        assertNotNull(dto);

    }

    @Test
    void saveNewBeer() {
        //given
        BeerDto dto = BeerDto.builder().beerName("Some new Beer").build();
        URI uri = client.saveNewBeer(dto);

        assertNotNull(uri);
        log.debug("Uri: " + uri.toString());
    }

    @Test
    void updateBeerById() {
        //given
        BeerDto dto = BeerDto.builder().beerName("Some new Beer").build();

        client.updateBeerById(UUID.randomUUID(), dto);
    }

    @Test
    void deleteById() {
        client.deleteById(UUID.randomUUID());
    }
}
