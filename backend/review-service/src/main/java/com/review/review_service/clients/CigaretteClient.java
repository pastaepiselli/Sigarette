package com.review.review_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cigarette", url = "http://localhost:8080", path = "/api/v1/cigarette")
public interface CigaretteClient {
    @PatchMapping(path = "/{id}")
    void updateAvgRating(@PathVariable Long id, @RequestParam Float rating);
}
