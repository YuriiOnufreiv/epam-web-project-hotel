package ua.onufreiv.hotel.entity;

import java.util.Date;

/**
 * Created by yurii on 1/10/17.
 */
public class Bill extends AbstractEntity {
    private Date creationDate;
    private Integer bookRequestId;
    private Integer roomId;
    private Long totalPrice;

    public Bill() {
    }

    public Bill(Integer id, Date creationDate, Integer bookRequestId, Integer roomId, Long totalPrice) {
        super(id);
        this.creationDate = creationDate;
        this.bookRequestId = bookRequestId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getBookRequestId() {
        return bookRequestId;
    }

    public void setBookRequestId(Integer bookRequestId) {
        this.bookRequestId = bookRequestId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Bill{" +
                ", creationDate=" + creationDate +
                ", bookRequestId=" + bookRequestId +
                ", roomId=" + roomId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
