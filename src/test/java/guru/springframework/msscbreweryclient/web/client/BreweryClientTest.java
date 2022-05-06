package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
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
    void deleteBeerById() {
        client.deleteBeerById(UUID.randomUUID());
    }

    @Test
    void getCustomerById() {
        CustomerDto dto = client.getCustomerById(UUID.randomUUID());

        assertNotNull(dto);

    }

    @Test
    void saveNewCustomer() {
        //given
        CustomerDto dto = CustomerDto.builder().name("Some new Customer").build();
        URI uri = client.saveNewCustomer(dto);

        assertNotNull(uri);
        log.debug("Uri: " + uri.toString());
    }

    @Test
    void updateCustomerById() {
        //given
        CustomerDto dto = CustomerDto.builder().name("Some new Customer").build();

        client.updateCustomerById(UUID.randomUUID(), dto);
    }

    @Test
    void deleteCustomerById() {
        client.deleteCustomerById(UUID.randomUUID());
    }
}
