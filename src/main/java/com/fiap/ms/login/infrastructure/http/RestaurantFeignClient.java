package com.fiap.ms.login.infrastructure.http;

import com.fiap.ms.login.infrastructure.http.dto.RestaurantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", url = "${restaurant.service.url}", configuration = FeignClientConfig.class)
public interface RestaurantFeignClient {

    @GetMapping("/v1/restaurantes/{userId}/exists")
    RestaurantDto userHasRestaurant(@PathVariable("userId") Long userId);
}
