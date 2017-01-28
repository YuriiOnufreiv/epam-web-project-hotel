package ua.onufreiv.hotel.entity;

/**
 * Created by yurii on 1/5/17.
 */
public class Room extends AbstractEntity {
    private Integer roomTypeId;
    private Integer number;
    private String description;
    private Integer price;
    private Integer maxPerson;

    public Room() {
    }

    public Room(Integer id, Integer roomTypeId, Integer number, String description, Integer price, Integer maxPerson) {
        super(id);
        this.roomTypeId = roomTypeId;
        this.number = number;
        this.description = description;
        this.price = price;
        this.maxPerson = maxPerson;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(Integer maxPerson) {
        this.maxPerson = maxPerson;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomTypeId=" + roomTypeId +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", maxPerson=" + maxPerson +
                '}';
    }
}
