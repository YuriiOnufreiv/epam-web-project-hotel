package ua.onufreiv.hotel.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yurii on 12/21/16.
 */
public class Form extends AbstractEntity implements Serializable {
    private Integer userId;
    private Integer personAmount;
    private Integer roomTypeId;
    private Date startDate;
    private Date endDate;
    private Boolean processed;

    public Form() {
    }

    public Form(Integer id, Integer userId, Integer personAmount, Integer roomTypeId, Date startDate, Date endDate, Boolean processed) {
        super(id);
        this.userId = userId;
        this.personAmount = personAmount;
        this.roomTypeId = roomTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.processed = processed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPersonAmount() {
        return personAmount;
    }

    public void setPersonAmount(Integer personAmount) {
        this.personAmount = personAmount;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "Form{" +
                ", userId=" + userId +
                ", personAmount=" + personAmount +
                ", roomTypeId=" + roomTypeId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", processed=" + processed +
                '}';
    }
}
