package ua.onufreiv.hotel.entities;

/**
 * Created by yurii on 12/23/16.
 */
public class User extends AbstractEntity {
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private Integer userRoleId;
    private Integer pwdHashId;

    public User() {
    }

    public User(Integer id, String name, String surname, String email, String telephone, Integer userRoleId, Integer pwdHashId) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
        this.userRoleId = userRoleId;
        this.pwdHashId = pwdHashId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getPwdHashId() {
        return pwdHashId;
    }

    public void setPwdHashId(Integer pwdHashId) {
        this.pwdHashId = pwdHashId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", userRoleId=" + userRoleId +
                ", pwdHashId=" + pwdHashId +
                '}';
    }
}
