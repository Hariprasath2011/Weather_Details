package com.Assesment.service;

import com.Assesment.dto.TemperatureStatsDTO;
import com.Assesment.entity.Weather;
import com.Assesment.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for weather data retrieval and business logic.
 * Median temperature is computed in Java since MySQL does not support
 * PERCENTILE_CONT.
 */
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository repository;

    /**
     * Get all weather records for a specific date.
     */
    public List<Weather> getByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    /**
     * Get all weather records for a specific month (across all years).
     */
    public List<Weather> getByMonth(int month) {
        return repository.findByMonth(month);
    }

    /**
     * Get monthly temperature statistics (max, min, median) for each month of the
     * given year.
     * Median is calculated in Java using the sorted list of temperatures.
     */
    public List<TemperatureStatsDTO> getTemperatureStats(int year) {
        // Get max/min per month from DB
        List<Object[]> maxMinResults = repository.getMonthlyMaxMin(year);

        List<TemperatureStatsDTO> stats = new ArrayList<>();

        for (Object[] row : maxMinResults) {
            int month = ((Number) row[0]).intValue();
            double maxTemp = ((Number) row[1]).doubleValue();
            double minTemp = ((Number) row[2]).doubleValue();

            // Fetch sorted temperatures for this month to compute median in Java
            List<Double> temps = repository.getTemperaturesByMonthAndYear(year, month);
            double medianTemp = computeMedian(temps);

            stats.add(new TemperatureStatsDTO(month, maxTemp, minTemp, medianTemp));
        }

        return stats;
    }

    /**
     * Computes the median of a sorted list of doubles.
     */
    private double computeMedian(List<Double> sortedTemps) {
        if (sortedTemps == null || sortedTemps.isEmpty())
            return 0.0;

        int size = sortedTemps.size();
        if (size % 2 == 1) {
            return sortedTemps.get(size / 2);
        } else {
            return (sortedTemps.get(size / 2 - 1) + sortedTemps.get(size / 2)) / 2.0;
        }
    }
}