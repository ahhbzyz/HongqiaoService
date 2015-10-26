package com.fyp.hongqiaoservice.query.pojo;

import java.util.List;
/**
 * Created by Yaozhong on 2015/1/29.
 */
public class TrainRoot {
    private String reason;

    private List<Train> result ;

    private int error_code;

    public TrainRoot() {
    }

    @Override
    public String toString() {
        return "TrainRoot [reason=" + reason + ", result=" + result + ", error_code=" + error_code
                + "]";
    }

    public TrainRoot(String reason, List<Train> result, int error_code) {
        this.reason = reason;
        this.result = result;
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Train> getResult() {
        return result;
    }

    public void setResult(List<Train> result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}