package com.fyp.hongqiaoservice.query.pojo;

public class TrainFollow {
	private long id;
	private String phone;
	private String train_no_follow;
	public TrainFollow(String phone, String train_no_follow) {
		super();
		this.phone = phone;
		this.train_no_follow = train_no_follow;
	}
	public TrainFollow() {
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
	public String getTrain_no_follow() {
		return train_no_follow;
	}
	public void setTrain_no_follow(String train_no_follow) {
		this.train_no_follow = train_no_follow;
	}
	
	

}
