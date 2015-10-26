package com.fyp.hongqiaoservice.query.pojo;

import java.io.Serializable;


import java.util.Date;

//import oracle.sql.Date;

public class F1DynFlight implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Date create_date;
	private Date operation_date;
	private String flight_no;
	private String a_or_d;
	private int flight_repeat_count;
	private String d_or_i;
	private String task_code;
	private String aircraft_terminal_code;
	private String airline_terminal_code;
	private String passenger_terminal_code;
	private F3Airline airline_iata;
	private String aircraft_type_iata;
	private String ac_reg_no;
	private String flg_vip;
	private F3Airport origin_airport_iata;
	private Date std;
	private Date etd;
	private Date atd;
    private F3Airport dest_airport_iata;
	private Date sta;
	private Date eta;
	private Date ata;
	private String boarding_status;
	private String previous_flight;
	private String next_flight;
	private Date enter_corridor_time;
	private F3Remark recent_abnormal_status;
	private String reported_abnormal;
	private String runway;
	private String code_share1;
	private String code_share2;
	private String code_share3;
	private String code_share4;
	private String code_share5;
	private String code_share6;
	private String remark;
	private String display_finished;



	public F1DynFlight()
	{
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getOperation_date() {
		return operation_date;
	}
	public void setOperation_date(Date operation_date) {
		this.operation_date = operation_date;
	}
	public String getFlight_no() {
		return flight_no;
	}
	public void setFlight_no(String flight_no) {
		this.flight_no = flight_no;
	}
	public String getA_or_d() {
		return a_or_d;
	}
	public void setA_or_d(String a_or_d) {
		this.a_or_d = a_or_d;
	}
	public int getFlight_repeat_count() {
		return flight_repeat_count;
	}
	public void setFlight_repeat_count(int flight_repeat_count) {
		this.flight_repeat_count = flight_repeat_count;
	}
	public String getD_or_i() {
		return d_or_i;
	}
	public void setD_or_i(String d_or_i) {
		this.d_or_i = d_or_i;
	}
	public String getTask_code() {
		return task_code;
	}
	public void setTask_code(String task_code) {
		this.task_code = task_code;
	}
	public String getAircraft_terminal_code() {
		return aircraft_terminal_code;
	}
	public void setAircraft_terminal_code(String aircraft_terminal_code) {
		this.aircraft_terminal_code = aircraft_terminal_code;
	}
	public String getAirline_terminal_code() {
		return airline_terminal_code;
	}
	public void setAirline_terminal_code(String airline_terminal_code) {
		this.airline_terminal_code = airline_terminal_code;
	}
	public String getPassenger_terminal_code() {
		return passenger_terminal_code;
	}
	public void setPassenger_terminal_code(String passenger_terminal_code) {
		this.passenger_terminal_code = passenger_terminal_code;
	}

	public String getAircraft_type_iata() {
		return aircraft_type_iata;
	}
	public void setAircraft_type_iata(String aircraft_type_iata) {
		this.aircraft_type_iata = aircraft_type_iata;
	}
	public String getAc_reg_no() {
		return ac_reg_no;
	}
	public void setAc_reg_no(String ac_reg_no) {
		this.ac_reg_no = ac_reg_no;
	}
	public String getFlg_vip() {
		return flg_vip;
	}
	public void setFlg_vip(String flg_vip) {
		this.flg_vip = flg_vip;
	}

	public Date getStd() {
		return std;
	}
	public void setStd(Date std) {
		this.std = std;
	}
	public Date getEtd() {
		return etd;
	}
	public void setEtd(Date etd) {
		this.etd = etd;
	}
	public Date getAtd() {
		return atd;
	}
	public void setAtd(Date atd) {
		this.atd = atd;
	}

	public Date getSta() {
		return sta;
	}
	public void setSta(Date sta) {
		this.sta = sta;
	}
	public Date getEta() {
		return eta;
	}
	public void setEta(Date eta) {
		this.eta = eta;
	}
	public Date getAta() {
		return ata;
	}
	public void setAta(Date ata) {
		this.ata = ata;
	}
	public String getBoarding_status() {
		return boarding_status;
	}
	public void setBoarding_status(String boarding_status) {
		this.boarding_status = boarding_status;
	}
	public String getPrevious_flight() {
		return previous_flight;
	}
	public void setPrevious_flight(String previous_flight) {
		this.previous_flight = previous_flight;
	}
	public String getNext_flight() {
		return next_flight;
	}
	public void setNext_flight(String next_flight) {
		this.next_flight = next_flight;
	}
	public Date getEnter_corridor_time() {
		return enter_corridor_time;
	}
	public void setEnter_corridor_time(Date enter_corridor_time) {
		this.enter_corridor_time = enter_corridor_time;
	}

	public String getReported_abnormal() {
		return reported_abnormal;
	}
	public void setReported_abnormal(String reported_abnormal) {
		this.reported_abnormal = reported_abnormal;
	}
	public String getRunway() {
		return runway;
	}
	public void setRunway(String runway) {
		this.runway = runway;
	}
	public String getCode_share1() {
		return code_share1;
	}
	public void setCode_share1(String code_share1) {
		this.code_share1 = code_share1;
	}
	public String getCode_share2() {
		return code_share2;
	}
	public void setCode_share2(String code_share2) {
		this.code_share2 = code_share2;
	}
	public String getCode_share3() {
		return code_share3;
	}
	public void setCode_share3(String code_share3) {
		this.code_share3 = code_share3;
	}
	public String getCode_share4() {
		return code_share4;
	}
	public void setCode_share4(String code_share4) {
		this.code_share4 = code_share4;
	}
	public String getCode_share5() {
		return code_share5;
	}
	public void setCode_share5(String code_share5) {
		this.code_share5 = code_share5;
	}
	public String getCode_share6() {
		return code_share6;
	}
	public void setCode_share6(String code_share6) {
		this.code_share6 = code_share6;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDisplay_finished() {
		return display_finished;
	}
	public void setDisplay_finished(String display_finished) {
		this.display_finished = display_finished;
	}


	public F3Airline getAirline_iata() {
		return airline_iata;
	}


	public void setAirline_iata(F3Airline airline_iata) {
		this.airline_iata = airline_iata;
	}


	public F3Airport getOrigin_airport_iata() {
		return origin_airport_iata;
	}


	public void setOrigin_airport_iata(F3Airport origin_airport_iata) {
		this.origin_airport_iata = origin_airport_iata;
	}


	public F3Airport getDest_airport_iata() {
		return dest_airport_iata;
	}


	public void setDest_airport_iata(F3Airport dest_airport_iata) {
		this.dest_airport_iata = dest_airport_iata;
	}


	public F3Remark getRecent_abnormal_status() {
		return recent_abnormal_status;
	}


	public void setRecent_abnormal_status(F3Remark recent_abnormal_status) {
		this.recent_abnormal_status = recent_abnormal_status;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
