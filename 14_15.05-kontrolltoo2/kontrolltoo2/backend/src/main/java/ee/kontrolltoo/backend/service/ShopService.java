package ee.kontrolltoo.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShopService {

    public String getTodos() {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(
                "https://jsonplaceholder.typicode.com/todos",
                String.class
        );
    }
}
