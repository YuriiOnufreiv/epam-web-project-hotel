package ua.onufreiv.hotel.entities;

import java.io.Serializable;

/**
 * Created by yurii on 12/23/16.
 */
public abstract class AbstractEntity implements Serializable {
    private Integer id;

    public AbstractEntity() {
    }

    public AbstractEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "id=" + id +
                '}';
    }
}
