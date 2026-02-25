package com.Assesment.controller;

import com.Assesment.dto.TemperatureStatsDTO;
import com.Assesment.entity.Weather;
import com.Assesment.service.CsvProcessingService;
import com.Assesment.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller exposing weather data APIs.
 *
 * Endpoints:
 * GET /api/weather/by-date?date=YYYY-MM-DD → weather for a specific date
 * GET /api/weather/by-month?month=N → weather for a given month (all years)
 * GET /api/weather/temperature-stats?year=YYYY → monthly max/min/median temps
 * for a year
 * POST /api/weather/load-csv?filePath=<path> → trigger CSV data ingestion
 */
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

    /**
     * Get weather details (condition, temperature, humidity, pressure) for a
     * specific date.
     * Example: GET /api/weather/by-date?date=2013-01-15
     */
    @GetMapping("/by-date")
    public ResponseEntity<List<Weather>> getByDate(@RequestParam LocalDate date) {
        List<Weather> result = weatherService.getByDate(date);
        return ResponseEntity.ok(result);
    }

    /**
     * Get weather details for a specific month across all years.
     * Example: GET /api/weather/by-month?month=6
     */
    @GetMapping("/by-month")
    public ResponseEntity<List<Weather>> getByMonth(@RequestParam int month) {
        List<Weather> result = weatherService.getByMonth(month);
        return ResponseEntity.ok(result);
    }

    /**
     * Get high, median, and minimum temperature for each month of the given year.
     * Example: GET /api/weather/temperature-stats?year=2015
     */
    @GetMapping("/temperature-stats")
    public ResponseEntity<List<TemperatureStatsDTO>> getTemperatureStats(@RequestParam int year) {
        List<TemperatureStatsDTO> stats = weatherService.getTemperatureStats(year);
        return ResponseEntity.ok(stats);
    }

    /**
     * Trigger CSV data ingestion from the given file path.
     * Example: POST /api/weather/load-csv?filePath=E:/data/delhi_weather.csv
     */
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