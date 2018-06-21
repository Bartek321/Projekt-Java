package Pack;

public class Notify {
	private Integer stationId;
	private String type;
	private String date;
	private Double value;
	
	public Notify(Integer stationId, String type, String date, Double value) {
		this.stationId = stationId;
		this.type = type;
		this.date = date;
		this.value = value;
	}
	
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
	public Integer getStationId() {
		return stationId;
	}
	public String getType() {
		return type;
	}
	public String getDate() {
		return date;
	}
	public Double getValue() {
		return value;
	}
}
