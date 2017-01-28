package ua.onufreiv.hotel.entity;

/**
 * Created by yurii on 1/4/17.
 */
public class RoomType extends AbstractEntity {
    private String type;

    public RoomType() {
    }

    public RoomType(Integer id, String type) {
        super(id);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "type='" + type + '\'' +
                '}';
    }
}
