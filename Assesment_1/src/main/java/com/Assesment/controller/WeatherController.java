package com.Assesment.controller;

import com.Assesment.dto.TemperatureStatsDTO;
import com.Assesment.entity.Weather;
import com.Assesment.service.CsvProcessingService;
import com.Assesment.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CsvProcessingService csvProcessingService;

    public WeatherController(WeatherService weatherService,
            CsvProcessingService csvProcessingService) {
        this.weatherService = weatherService;
        this.csvProcessingService = csvProcessingService;
    }
    @GetMapping("/by-date")
    public ResponseEntity<List<Weather>> getByDate(@RequestParam LocalDate date) {
        List<Weather> result = weatherService.getByDate(date);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/by-month")
    public ResponseEntity<List<Weather>> getByMonth(@RequestParam int month) {
        List<Weather> result = weatherService.getByMonth(month);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/temperature-stats")
    public ResponseEntity<List<TemperatureStatsDTO>> getTemperatureStats(@RequestParam int year) {
        List<TemperatureStatsDTO> stats = weatherService.getTemperatureStats(year);
        return ResponseEntity.ok(stats);
    }
    @PostMapping("/load-csv")
    public ResponseEntity<String> loadCsv(@RequestParam String filePath) {
        try {
            csvProcessingService.loadCsv(filePath);
            return ResponseEntity.ok("CSV data loaded successfully from: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Failed to load CSV: " + e.getMessage());
        }
    }
}
