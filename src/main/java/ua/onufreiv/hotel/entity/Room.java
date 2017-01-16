package ua.onufreiv.hotel.entity;

/**
 * Created by yurii on 1/5/17.
 */
public class Room extends AbstractEntity {
    private Integer roomTypeId;
    private Integer number;

    public Room() {
    }

    public Room(Integer id, Integer roomTypeId, Integer number) {
        super(id);
        this.roomTypeId = roomTypeId;
        this.number = number;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Room{" +
                ", roomTypeId=" + roomTypeId +
                ", number=" + number +
                '}';
    }
}
