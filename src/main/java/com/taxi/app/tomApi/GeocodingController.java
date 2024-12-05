package com.taxi.app.tomApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeocodingController {

    @Autowired
    private RotaService rotaService;

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/geocode")
    public double[][] geocodeEnderecos(
            @RequestParam String endereco1,
            @RequestParam String endereco2) {
        return geocodingService.geocodeDoisEnderecos(endereco1, endereco2);
    }


}
