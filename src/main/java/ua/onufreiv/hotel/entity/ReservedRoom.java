package ua.onufreiv.hotel.entity;

import java.util.Date;

/**
 * Created by yurii on 1/5/17.
 */
public class ReservedRoom extends AbstractEntity {
    private Integer roomId;
    private Date checkInDate;
    private Date checkOutDate;

    public ReservedRoom() {
    }

    public ReservedRoom(Integer id, Integer roomId, Date checkInDate, Date checkOutDate) {
        super(id);
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return "ReservedRoom{" +
                "roomId=" + roomId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}
