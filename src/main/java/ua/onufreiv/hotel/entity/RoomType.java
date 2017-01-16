package ua.onufreiv.hotel.entity;

/**
 * Created by yurii on 1/4/17.
 */
public class RoomType extends AbstractEntity {
    private String type;
    private String description;
    private Integer price;
    private Integer maxPerson;

    public RoomType() {
    }

    public RoomType(Integer id, String type, String description, Integer price, Integer maxPerson) {
        super(id);
        this.type = type;
        this.description = description;
        this.price = price;
        this.maxPerson = maxPerson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "RoomType{" +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", maxPerson=" + maxPerson +
                '}';
    }
}
