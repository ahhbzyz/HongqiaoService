package com.fyp.hongqiaoservice.query.pojo;

/**
 * Created by Administrator on 2015/2/9.
 */
public class Flight {
    private String name;

    private String complany;

    private String AirModel;

    private String AirAge;

    private String start;

    private String end;

    private String DepCode;

    private String ArrCode;

    private String DepCode4;

    private String ArrCode4;

    private String DepAirport;

    private String ArrAirport;

    private String DepTerminal;

    private String ArrTerminal;

    private String status;

    private String DepTime;

    private String ArrTime;

    private String Dexpected;

    private String Aexpected;

    private String Dactual;

    private String Aactual;

    private String food;

    private String OnTimeRate;

    private String FlyTime;

    private String DepDelay;

    private String ArrDelay;

    private String DepTrafficState;

    private String DepTemperature;

    private String ArrTrafficState;

    private String ArrTemperature;

    public Flight() {
    }

    public Flight(String name, String complany, String airModel, String airAge, String start, String end, String depCode, String arrCode, String depCode4, String arrCode4, String depAirport, String arrAirport, String depTerminal, String arrTerminal, String status, String depTime, String arrTime, String dexpected, String aexpected, String dactual, String aactual, String food, String onTimeRate, String flyTime, String depDelay, String arrDelay, String depTrafficState, String depTemperature, String arrTrafficState, String arrTemperature) {
        this.name = name;
        this.complany = complany;
        AirModel = airModel;
        AirAge = airAge;
        this.start = start;
        this.end = end;
        DepCode = depCode;
        ArrCode = arrCode;
        DepCode4 = depCode4;
        ArrCode4 = arrCode4;
        DepAirport = depAirport;
        ArrAirport = arrAirport;
        DepTerminal = depTerminal;
        ArrTerminal = arrTerminal;
        this.status = status;
        DepTime = depTime;
        ArrTime = arrTime;
        Dexpected = dexpected;
        Aexpected = aexpected;
        Dactual = dactual;
        Aactual = aactual;
        this.food = food;
        OnTimeRate = onTimeRate;
        FlyTime = flyTime;
        DepDelay = depDelay;
        ArrDelay = arrDelay;
        DepTrafficState = depTrafficState;
        DepTemperature = depTemperature;
        ArrTrafficState = arrTrafficState;
        ArrTemperature = arrTemperature;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name='" + name + '\'' +
                ", complany='" + complany + '\'' +
                ", AirModel='" + AirModel + '\'' +
                ", AirAge='" + AirAge + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", DepCode='" + DepCode + '\'' +
                ", ArrCode='" + ArrCode + '\'' +
                ", DepCode4='" + DepCode4 + '\'' +
                ", ArrCode4='" + ArrCode4 + '\'' +
                ", DepAirport='" + DepAirport + '\'' +
                ", ArrAirport='" + ArrAirport + '\'' +
                ", DepTerminal='" + DepTerminal + '\'' +
                ", ArrTerminal='" + ArrTerminal + '\'' +
                ", status='" + status + '\'' +
                ", DepTime='" + DepTime + '\'' +
                ", ArrTime='" + ArrTime + '\'' +
                ", Dexpected='" + Dexpected + '\'' +
                ", Aexpected='" + Aexpected + '\'' +
                ", Dactual='" + Dactual + '\'' +
                ", Aactual='" + Aactual + '\'' +
                ", food='" + food + '\'' +
                ", OnTimeRate='" + OnTimeRate + '\'' +
                ", FlyTime='" + FlyTime + '\'' +
                ", DepDelay='" + DepDelay + '\'' +
                ", ArrDelay='" + ArrDelay + '\'' +
                ", DepTrafficState='" + DepTrafficState + '\'' +
                ", DepTemperature='" + DepTemperature + '\'' +
                ", ArrTrafficState='" + ArrTrafficState + '\'' +
                ", ArrTemperature='" + ArrTemperature + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplany() {
        return complany;
    }

    public void setComplany(String complany) {
        this.complany = complany;
    }

    public String getAirModel() {
        return AirModel;
    }

    public void setAirModel(String airModel) {
        AirModel = airModel;
    }

    public String getAirAge() {
        return AirAge;
    }

    public void setAirAge(String airAge) {
        AirAge = airAge;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDepCode() {
        return DepCode;
    }

    public void setDepCode(String depCode) {
        DepCode = depCode;
    }

    public String getArrCode() {
        return ArrCode;
    }

    public void setArrCode(String arrCode) {
        ArrCode = arrCode;
    }

    public String getDepCode4() {
        return DepCode4;
    }

    public void setDepCode4(String depCode4) {
        DepCode4 = depCode4;
    }

    public String getArrCode4() {
        return ArrCode4;
    }

    public void setArrCode4(String arrCode4) {
        ArrCode4 = arrCode4;
    }

    public String getDepAirport() {
        return DepAirport;
    }

    public void setDepAirport(String depAirport) {
        DepAirport = depAirport;
    }

    public String getArrAirport() {
        return ArrAirport;
    }

    public void setArrAirport(String arrAirport) {
        ArrAirport = arrAirport;
    }

    public String getDepTerminal() {
        return DepTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        DepTerminal = depTerminal;
    }

    public String getArrTerminal() {
        return ArrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        ArrTerminal = arrTerminal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepTime() {
        return DepTime;
    }

    public void setDepTime(String depTime) {
        DepTime = depTime;
    }

    public String getArrTime() {
        return ArrTime;
    }

    public void setArrTime(String arrTime) {
        ArrTime = arrTime;
    }

    public String getDexpected() {
        return Dexpected;
    }

    public void setDexpected(String dexpected) {
        Dexpected = dexpected;
    }

    public String getAexpected() {
        return Aexpected;
    }

    public void setAexpected(String aexpected) {
        Aexpected = aexpected;
    }

    public String getDactual() {
        return Dactual;
    }

    public void setDactual(String dactual) {
        Dactual = dactual;
    }

    public String getAactual() {
        return Aactual;
    }

    public void setAactual(String aactual) {
        Aactual = aactual;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getOnTimeRate() {
        return OnTimeRate;
    }

    public void setOnTimeRate(String onTimeRate) {
        OnTimeRate = onTimeRate;
    }

    public String getFlyTime() {
        return FlyTime;
    }

    public void setFlyTime(String flyTime) {
        FlyTime = flyTime;
    }

    public String getDepDelay() {
        return DepDelay;
    }

    public void setDepDelay(String depDelay) {
        DepDelay = depDelay;
    }

    public String getArrDelay() {
        return ArrDelay;
    }

    public void setArrDelay(String arrDelay) {
        ArrDelay = arrDelay;
    }

    public String getDepTrafficState() {
        return DepTrafficState;
    }

    public void setDepTrafficState(String depTrafficState) {
        DepTrafficState = depTrafficState;
    }

    public String getDepTemperature() {
        return DepTemperature;
    }

    public void setDepTemperature(String depTemperature) {
        DepTemperature = depTemperature;
    }

    public String getArrTrafficState() {
        return ArrTrafficState;
    }

    public void setArrTrafficState(String arrTrafficState) {
        ArrTrafficState = arrTrafficState;
    }

    public String getArrTemperature() {
        return ArrTemperature;
    }

    public void setArrTemperature(String arrTemperature) {
        ArrTemperature = arrTemperature;
    }
}
