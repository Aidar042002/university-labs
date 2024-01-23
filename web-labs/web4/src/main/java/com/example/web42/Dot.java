package com.example.web42;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dots")
public class Dot implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String x;
    private String y;
    private float r;
    private boolean status;
    private long timestamp;
    private long scriptTime;
    private String owner;

    public Dot() {}

    public Dot(String x, String y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        checkStatus();
    }

    private void checkStatus() {
        boolean inArea = inArea(x, y, r);
        status = inArea ? true : false;
    }

    public static double batman_upper(float x) {
        x = Math.abs(x);
        if (x < 0.5) {
            return 2.25;
        } else if (0.5 <= x && x < 0.75) {
            return 3 * x + 0.75;
        } else if (0.75 <= x && x < 1.0) {
            return 9 - 8 * x;
        } else if (1 <= x && x < 3) {
            return (1.5 - 0.5 * x - 3 * Math.sqrt(10) / 7 * (Math.sqrt(3 - x * x + 2 * x) - 2));
        } else if (3 <= x && x <= 7) {
            return 3 * Math.sqrt(-(x / 7) * (x / 7) + 1);
        }
        return 0.0;
    }

    public static double batman_lower(float x) {
        x = Math.abs(x);
        if (0 <= x && x < 4) {
            return (Math.abs(x / 2) - (3 * Math.sqrt(33) - 7) / 112 * x * x +
                    Math.sqrt(1 - (Math.abs(x - 2) - 1) * (Math.abs(x - 2) - 1)) - 3);
        } else if (4 <= x && x <= 7) {
            return -3 * Math.sqrt(-(x / 7) * (x / 7) + 1);
        }
        return 0.0;
    }

    public static boolean inArea(String xx, String yy, float r) {
        float x=Float.parseFloat(xx);
        float y=Float.parseFloat(yy);
        double yUpper = batman_upper(x * 7 / r);
        double yLower = batman_lower(x * 7 / r);
        return y * 7 / r >= yLower && y * 7 / r <= yUpper;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float radius) {
        this.r = radius;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getScriptTime() {
        return scriptTime;
    }

    public void setScriptTime(long scriptTime) {
        this.scriptTime = scriptTime;
    }
}
