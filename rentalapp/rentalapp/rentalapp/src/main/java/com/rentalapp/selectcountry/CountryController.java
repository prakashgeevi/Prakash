package com.rentalapp.selectcountry;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/country")
@CrossOrigin("*")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public Set<String> getAllCountries() {
    	CountryData allCountries = countryService.getAllCountries();
    	return allCountries.getData().stream().map(e -> e.getCountry()).collect(Collectors.toSet());
    }

    @GetMapping("/states/{country}")
    public Set<String> getStatesByCountry(@PathVariable("country") String country) {
        return countryService.getStatesByCountry(country);
    }

    @GetMapping("/cities/{country}/{state}")
    public Set<String> getCitiesByState(@PathVariable("country") String country, @PathVariable("state") String state) {
        return countryService.getCitiesByState(state, country);
    }

}
