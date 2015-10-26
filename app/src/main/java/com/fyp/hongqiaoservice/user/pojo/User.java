package com.fyp.hongqiaoservice.user.pojo;



public class User {

    private long id;
    private String username;
    private String password;
    private String phone;
    private String create_date;
    private String parking_area;
    private String flight_no_follow;
    private String train_no_follow;

    public User() {
        super();
    }












    public User(String username, String password, String phone) {
        super();
        this.username = username;
        this.password = password;
        this.phone = phone;
    }












    public String getPhone() {
        return phone;
    }












    public void setPhone(String phone) {
        this.phone = phone;
    }












    public String getCreate_date() {
        return create_date;
    }


    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }


    public String getParking_area() {
        return parking_area;
    }



    public void setParking_area(String parking_area) {
        this.parking_area = parking_area;
    }



    public String getFlight_no_follow() {
        return flight_no_follow;
    }



    public void setFlight_no_follow(String flight_no_follow) {
        this.flight_no_follow = flight_no_follow;
    }



    public String getTrain_no_follow() {
        return train_no_follow;
    }



    public void setTrain_no_follow(String train_no_follow) {
        this.train_no_follow = train_no_follow;
    }



    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }







}
