package com.Assesment.service;

import com.Assesment.dto.TemperatureStatsDTO;
import com.Assesment.entity.Weather;
import com.Assesment.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository repository;

    public List<Weather> getByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public List<Weather> getByMonth(int month) {
        return repository.findByMonth(month);
    }

    public List<TemperatureStatsDTO> getTemperatureStats(int year) {
        List<Object[]> maxMinResults = repository.getMonthlyMaxMin(year);

        List<TemperatureStatsDTO> stats = new ArrayList<>();

        for (Object[] row : maxMinResults) {
            int month = ((Number) row[0]).intValue();
            double maxTemp = ((Number) row[1]).doubleValue();
            double minTemp = ((Number) row[2]).doubleValue();
            List<Double> temps = repository.getTemperaturesByMonthAndYear(year, month);
            double medianTemp = computeMedian(temps);

            stats.add(new TemperatureStatsDTO(month, maxTemp, minTemp, medianTemp));
        }

        return stats;
    }
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
