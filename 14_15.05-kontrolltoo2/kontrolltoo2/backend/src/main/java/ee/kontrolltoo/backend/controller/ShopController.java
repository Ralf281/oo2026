package ee.kontrolltoo.backend.controller;

import ee.kontrolltoo.backend.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/todos")
    public String getTodos() {
        return shopService.getTodos();
    }
}