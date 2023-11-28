package com.example.web_lab2.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Results implements Serializable {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private String hit;
    private long executionTime;
    private String formattedTime;

    public Results(BigDecimal x, BigDecimal y, BigDecimal r, String hit, long executionTime, String formattedTime){
        this.x=x;
        this.y=y;
        this.r=r;
        this.hit=hit;
        this.executionTime=executionTime;
        this.formattedTime=formattedTime;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Results)) return false;
        Results results = (Results) o;
        return getExecutionTime() == results.getExecutionTime() && Objects.equals(getX(), results.getX()) && Objects.equals(getY(), results.getY()) && Objects.equals(getR(), results.getR()) && Objects.equals(getHit(), results.getHit()) && Objects.equals(getFormattedTime(), results.getFormattedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getR(), getHit(), getExecutionTime(), getFormattedTime());
    }

    @Override
    public String toString() {
        return "Results{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", hit='" + hit + '\'' +
                ", executionTime=" + executionTime +
                ", formattedTime='" + formattedTime + '\'' +
                '}';
    }
}
