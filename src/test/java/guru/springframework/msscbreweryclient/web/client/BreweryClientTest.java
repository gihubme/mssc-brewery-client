package guru.springframework.msscbreweryclient.web.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.Optional;
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
    void saveNewBeerDto2() {
        //given
        BeerDto dto = BeerDto.builder().beerName("Yupp Beer").build();
        ResponseEntity<BeerDto> response = client.saveNewBeerDto2(dto, Optional.of("1"));

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        BeerDto responseDto = response.getBody();
        Assertions.assertNotNull(responseDto);
        Assertions.assertEquals(dto.getBeerName(), responseDto.getBeerName());

    }

    @Test
    void saveNewBeerDtoViaForm() throws JsonProcessingException {
        //given
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("beerName", "tasty Beer");
        map.add("upc", Long.toString(56382L));

        ResponseEntity<BeerDto> response = client.saveNewBeerDtoViaForm(map);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);

//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(response.getBody());
//        JsonNode beerName = root.path("beerName");

        BeerDto responseDto = response.getBody();

        Assertions.assertEquals(map.getFirst("beerName"), responseDto.getBeerName());
        Assertions.assertEquals(map.getFirst("upc"), Long.toString(responseDto.getUpc()));
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
