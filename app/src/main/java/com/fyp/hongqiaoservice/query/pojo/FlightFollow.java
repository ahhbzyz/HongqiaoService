package com.fyp.hongqiaoservice.query.pojo;

public class FlightFollow {
	private long id;
	private String phone;
	private String flight_no_follow;
	
	public FlightFollow(String phone, String flight_no_follow) {
		super();
		this.phone = phone;
		this.flight_no_follow = flight_no_follow;
	}
	
	public FlightFollow() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFlight_no_follow() {
		return flight_no_follow;
	}
	public void setFlight_no_follow(String flight_no_follow) {
		this.flight_no_follow = flight_no_follow;
	}
	
	
}
