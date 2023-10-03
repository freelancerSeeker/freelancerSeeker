package com.freelancerSeeker.freelancerSeeker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ApiController {
    @Value("${skill_api_url}")
    private String apiUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ApiController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/skill/search/{skillName}")
    public ResponseEntity<String> getSkillFromApi(@PathVariable String skillName) {
        final String url = apiUrl + "?q=" + skillName + "&limit=10";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Host", "iys-skill-api.p.rapidapi.com");
        headers.set("X-RapidAPI-Key", "9c073b49ebmshc35ea2f247eddbdp142277jsn59202d2ae50c");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            return ResponseEntity.status(responseEntity.getStatusCode()).build();
        }
    }
}
