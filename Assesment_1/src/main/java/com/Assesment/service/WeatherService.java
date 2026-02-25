package com.Assesment.service;
import com.Assesment.dto.TemperatureStatsDTO;
import com.Assesment.entity.Weather;
import com.Assesment.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
	    private final WeatherRepository repository;

	    public WeatherService(WeatherRepository repository) {
	        this.repository = repository;
	    }

    public List<Weather> getByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public List<Weather> getByMonth(int month) {
        return repository.findByMonth(month);
    }

    public List<TemperatureStatsDTO> getTemperatureStats(int year) {

        List<Object[]> results = repository.getMonthlyTemperatureStats(year);

        return results.stream()
                .map((Object[] r) -> new TemperatureStatsDTO(
                        ((Number) r[0]).intValue(),
                        ((Number) r[1]).doubleValue(),
                        ((Number) r[2]).doubleValue(),
                        ((Number) r[3]).doubleValue()
                ))
                .toList();
    }
}