package com.Assesment.controller;

import com.Assesment.entity.Weather;
import com.Assesment.service.WeatherService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/by-date")
    public List<Weather> getByDate(@RequestParam LocalDate date) {
        return service.getByDate(date);
    }

    @GetMapping("/by-month")
    public List<Weather> getByMonth(@RequestParam int month) {
        return service.getByMonth(month);
    }
}