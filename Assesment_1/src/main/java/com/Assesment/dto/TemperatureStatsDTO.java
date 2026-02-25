package com.Assesment.dto;
public class TemperatureStatsDTO {

	private Integer month;
	private Double maxTemp;
	private Double minTemp;
	private Double medianTemp;

	public TemperatureStatsDTO(Integer month, Double maxTemp, Double minTemp, Double medianTemp) {
		this.month = month;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
		this.medianTemp = medianTemp;
	}

	public Integer getMonth() {
		return month;
	}

	public Double getMaxTemp() {
		return maxTemp;
	}

	public Double getMinTemp() {
		return minTemp;
	}

	public Double getMedianTemp() {
		return medianTemp;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public void setMaxTemp(Double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public void setMinTemp(Double minTemp) {
		this.minTemp = minTemp;
	}

	public void setMedianTemp(Double medianTemp) {
		this.medianTemp = medianTemp;
	}
}
