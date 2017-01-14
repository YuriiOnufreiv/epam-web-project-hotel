package ua.onufreiv.hotel.entities;

/**
 * Created by yurii on 1/10/17.
 */
public class Bill extends AbstractEntity {
    private Integer bookRequestId;
    private Integer roomId;
    private Long totalPrice;

    public Bill() {
    }

    public Bill(Integer id, Integer bookRequestId, Integer roomId, Long totalPrice) {
        super(id);
        this.bookRequestId = bookRequestId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
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
                ", bookRequestId=" + bookRequestId +
                ", roomId=" + roomId +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
