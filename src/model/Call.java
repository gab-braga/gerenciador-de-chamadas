package model;

import java.util.Date;

public class Call {

    private String name;
    private String phone;
    private String status;
    private String description;
    private Date lastUpdate;

    public Call() {
    }

    public Call(String name, String phone, String status, String description) {
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.description = description;
    }

    public Call(String name, String phone, String status, String description, Date lastUpdate) {
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.description = description;
        this.lastUpdate = lastUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
