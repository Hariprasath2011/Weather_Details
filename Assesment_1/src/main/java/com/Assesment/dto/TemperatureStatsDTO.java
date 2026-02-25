package com.Assesment.dto;


public class TemperatureStatsDTO {

    private Integer month;
    private Double maxTemp;
    private Double minTemp;
    private Double medianTemp;

    public TemperatureStatsDTO(Integer month,
                               Double maxTemp,
                               Double minTemp,
                               Double medianTemp) {
        this.setMonth(month);
        this.setMaxTemp(maxTemp);
        this.setMinTemp(minTemp);
        this.setMedianTemp(medianTemp);
    }

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(Double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public Double getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(Double minTemp) {
		this.minTemp = minTemp;
	}

	public Double getMedianTemp() {
		return medianTemp;
	}

	public void setMedianTemp(Double medianTemp) {
		this.medianTemp = medianTemp;
	}
}