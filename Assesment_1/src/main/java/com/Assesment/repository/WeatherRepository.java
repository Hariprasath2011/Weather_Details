package com.Assesment.repository;

import com.Assesment.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    // Find all records for a specific date
    List<Weather> findByDate(LocalDate date);

    // Find all records for a specific month (across all years)
    @Query("SELECT w FROM Weather w WHERE MONTH(w.date) = :month")
    List<Weather> findByMonth(@Param("month") int month);

    // Get monthly temperature stats for a given year — MySQL compatible
    // Returns: [month, maxTemp, minTemp] grouped by month
    @Query(value = """
                SELECT MONTH(date) AS month,
                       MAX(temperature) AS maxTemp,
                       MIN(temperature) AS minTemp
                FROM weather
                WHERE YEAR(date) = :year
                GROUP BY MONTH(date)
                ORDER BY MONTH(date)
            """, nativeQuery = true)
    List<Object[]> getMonthlyMaxMin(@Param("year") int year);

    // Get all temperatures for a specific month and year (for median calculation in
    // Java)
    @Query(value = """
                SELECT temperature
                FROM weather
                WHERE YEAR(date) = :year AND MONTH(date) = :month
                ORDER BY temperature
            """, nativeQuery = true)
    List<Double> getTemperaturesByMonthAndYear(@Param("year") int year, @Param("month") int month);
}