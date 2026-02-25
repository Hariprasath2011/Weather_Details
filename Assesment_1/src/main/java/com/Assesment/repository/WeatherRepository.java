package com.Assesment.repository;

import com.Assesment.entity.*;
import com.Assesment.dto.TemperatureStatsDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    List<Weather> findByDate(LocalDate date);

    @Query("SELECT w FROM Weather w WHERE MONTH(w.date) = :month")
    List<Weather> findByMonth(@Param("month") int month);

    @Query(value = """
        SELECT EXTRACT(MONTH FROM date) AS month,
               MAX(temperature) AS maxTemp,
               MIN(temperature) AS minTemp,
               PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY temperature) AS medianTemp
        FROM weather
        WHERE EXTRACT(YEAR FROM date) = :year
        GROUP BY month
        ORDER BY month
    """, nativeQuery = true)
    List<Object[]> getMonthlyTemperatureStats(@Param("year") int year);
    
}