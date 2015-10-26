package com.fyp.hongqiaoservice.query.pojo;

import java.io.Serializable;
import java.util.Date;


public class RRdt implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private Date run_date; 
	private int train_no;
	private String arrive_train_no;
	private String depart_train_no;
	private Date estimated_arrive_time;
	private Date estimated_depart_time;
	private Date arrive_time;
	private Date depart_time;
	private Date check_time;
	private Date stop_time;
	private String train_status;

	private String abnormalstatus;
	private String abnormalcause;
	private int abnormaltime;
	private int groupno;
	
	private RSta startstation;
	private RSta terminalstation;

	
	

	public RSta getStartstation() {
		return startstation;
	}
	public void setStartstation(RSta startstation) {
		this.startstation = startstation;
	}
	public RSta getTerminalstation() {
		return terminalstation;
	}
	public void setTerminalstation(RSta terminalstation) {
		this.terminalstation = terminalstation;
	}
	public RRdt() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getRun_date() {
		return run_date;
	}
	public void setRun_date(Date run_date) {
		this.run_date = run_date;
	}
	public int getTrain_no() {
		return train_no;
	}
	public void setTrain_no(int train_no) {
		this.train_no = train_no;
	}
	public String getArrive_train_no() {
		return arrive_train_no;
	}
	public void setArrive_train_no(String arrive_train_no) {
		this.arrive_train_no = arrive_train_no;
	}
	public String getDepart_train_no() {
		return depart_train_no;
	}
	public void setDepart_train_no(String depart_train_no) {
		this.depart_train_no = depart_train_no;
	}
	public Date getEstimated_arrive_time() {
		return estimated_arrive_time;
	}
	public void setEstimated_arrive_time(Date estimated_arrive_time) {
		this.estimated_arrive_time = estimated_arrive_time;
	}
	public Date getEstimated_depart_time() {
		return estimated_depart_time;
	}
	public void setEstimated_depart_time(Date estimated_depart_time) {
		this.estimated_depart_time = estimated_depart_time;
	}
	public Date getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(Date arrive_time) {
		this.arrive_time = arrive_time;
	}
	public Date getDepart_time() {
		return depart_time;
	}
	public void setDepart_time(Date depart_time) {
		this.depart_time = depart_time;
	}
	public Date getCheck_time() {
		return check_time;
	}
	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}
	public Date getStop_time() {
		return stop_time;
	}
	public void setStop_time(Date stop_time) {
		this.stop_time = stop_time;
	}
	public String getTrain_status() {
		return train_status;
	}
	public void setTrain_status(String train_status) {
		this.train_status = train_status;
	}

	public String getAbnormalstatus() {
		return abnormalstatus;
	}
	public void setAbnormalstatus(String abnormalstatus) {
		this.abnormalstatus = abnormalstatus;
	}
	public String getAbnormalcause() {
		return abnormalcause;
	}
	public void setAbnormalcause(String abnormalcause) {
		this.abnormalcause = abnormalcause;
	}
	public int getAbnormaltime() {
		return abnormaltime;
	}
	public void setAbnormaltime(int abnormaltime) {
		this.abnormaltime = abnormaltime;
	}
	public int getGroupno() {
		return groupno;
	}
	public void setGroupno(int groupno) {
		this.groupno = groupno;
	}
	
	
	
	

}
