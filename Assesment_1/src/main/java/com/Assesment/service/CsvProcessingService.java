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

/**
 * Service responsible for reading weather data from a CSV file
 * and persisting it to the database in efficient batches.
 *
 * Expected CSV column order:
 * date, temperature, humidity, pressure, heatIndex, weatherCondition
 */
@Service
@RequiredArgsConstructor
public class CsvProcessingService {

    private static final int BATCH_SIZE = 1000;

    private final WeatherRepository repository;

    /**
     * Loads weather records from the specified CSV file path.
     * Skips the header row and saves records in batches of {@value BATCH_SIZE}.
     *
     * @param filePath absolute path to the CSV file
     * @throws Exception if the file cannot be read or parsed
     */
    public void loadCsv(String filePath) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            reader.readNext(); // skip header row

            List<Weather> batch = new ArrayList<>();
            String[] line;

            while ((line = reader.readNext()) != null) {
                Weather weather = new Weather();
                weather.setDate(LocalDate.parse(line[0].trim()));
                weather.setTemperature(Double.parseDouble(line[1].trim()));
                weather.setHumidity(Double.parseDouble(line[2].trim()));
                weather.setPressure(Double.parseDouble(line[3].trim()));
                weather.setHeatIndex(Double.parseDouble(line[4].trim()));
                weather.setWeatherCondition(line[5].trim());

                batch.add(weather);

                if (batch.size() == BATCH_SIZE) {
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