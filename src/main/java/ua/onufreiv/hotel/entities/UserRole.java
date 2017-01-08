package ua.onufreiv.hotel.entities;

/**
 * Created by yurii on 1/8/17.
 */
public class UserRole extends AbstractEntity {
    private String role;

    public UserRole() {
    }

    public UserRole(Integer id, String role) {
        super(id);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
