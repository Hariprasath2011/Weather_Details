package com.Assesment.service;
import com.Assesment.entity.Weather;
import com.Assesment.repository.WeatherRepository;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvProcessingService {

    private final WeatherRepository repository;
    public CsvProcessingService(WeatherRepository repository) {
        this.repository = repository;
    }

    public void loadCsv(String filePath) throws Exception {

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

            String[] line;
            reader.readNext(); 

            List<Weather> batch = new ArrayList<>();

            while ((line = reader.readNext()) != null) {

            	Weather weather = new Weather();
            	weather.setDate(LocalDate.parse(line[0]));
            	weather.setTemperature(Double.parseDouble(line[1]));
            	weather.setHumidity(Double.parseDouble(line[2]));
            	weather.setPressure(Double.parseDouble(line[3]));
            	weather.setHeatIndex(Double.parseDouble(line[4]));
            	weather.setWeatherCondition(line[5]);

                batch.add(weather);
                if (batch.size() == 1000) {
                    repository.saveAll(batch);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                repository.saveAll(batch);
            }
        }
    }
}