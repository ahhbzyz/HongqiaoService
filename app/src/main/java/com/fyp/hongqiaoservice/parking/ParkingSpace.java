package com.fyp.hongqiaoservice.parking;

public class ParkingSpace {
	
	private long id;
	private String parking_area;
	private String parking_zone;
	private String parking_space;
	
	
	public ParkingSpace(long id, String parking_area, String parking_zone,
			String parking_space) {
		super();
		this.id = id;
		this.parking_area = parking_area;
		this.parking_zone = parking_zone;
		this.parking_space = parking_space;
	}
	public ParkingSpace() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getParking_area() {
		return parking_area;
	}
	public void setParking_area(String parking_area) {
		this.parking_area = parking_area;
	}
	public String getParking_zone() {
		return parking_zone;
	}
	public void setParking_zone(String parking_zone) {
		this.parking_zone = parking_zone;
	}
	public String getParking_space() {
		return parking_space;
	}
	public void setParking_space(String parking_space) {
		this.parking_space = parking_space;
	}
	
	

}
