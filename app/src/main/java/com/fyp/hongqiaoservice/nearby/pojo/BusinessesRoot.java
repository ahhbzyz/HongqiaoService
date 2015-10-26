package com.fyp.hongqiaoservice.nearby.pojo;

import java.util.List;

/**
 * Created by Administrator on 2015/2/17.
 */
public class BusinessesRoot {
    private String status;

    private String total_count;

    private String count;

    private List<businesses> businesses;

    public BusinessesRoot() {
    }

    public BusinessesRoot(String status, String total_count, String count, List<businesses> businesses) {
        this.status = status;
        this.total_count = total_count;
        this.count = count;
        this.businesses = businesses;
    }

    @Override
    public String toString() {
        return "BusinessesRoot{" +
                "status='" + status + '\'' +
                ", total_count='" + total_count + '\'' +
                ", count='" + count + '\'' +
                ", businesses=" + businesses +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<businesses> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<businesses> businesses) {
        this.businesses = businesses;
    }
}
