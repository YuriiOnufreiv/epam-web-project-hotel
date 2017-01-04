package ua.onufreiv.hotel.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yurii on 12/21/16.
 */
public class BookRequest extends AbstractEntity implements Serializable {
    private Integer userId;
    private Integer persons;
    private Integer roomTypeId;
    private Date checkIn;
    private Date endDate;
    private Boolean processed;

    public BookRequest() {
    }

    public BookRequest(Integer id, Integer userId, Integer persons, Integer roomTypeId, Date checkIn, Date endDate, Boolean processed) {
        super(id);
        this.userId = userId;
        this.persons = persons;
        this.roomTypeId = roomTypeId;
        this.checkIn = checkIn;
        this.endDate = endDate;
        this.processed = processed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
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
        return "BookRequest{" +
                ", userId=" + userId +
                ", persons=" + persons +
                ", roomTypeId=" + roomTypeId +
                ", checkIn=" + checkIn +
                ", endDate=" + endDate +
                ", processed=" + processed +
                '}';
    }
}
