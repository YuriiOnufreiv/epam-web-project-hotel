package ua.onufreiv.hotel.entities;

/**
 * Created by yurii on 12/23/16.
 */
public class PasswordHash extends AbstractEntity {
    private String pwdHash;

    public PasswordHash() {
    }

    public PasswordHash(Integer id, String pwdHash) {
        super(id);
        this.pwdHash = pwdHash;
    }

    public String getPwdHash() {
        return pwdHash;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

    @Override
    public String toString() {
        return "PasswordHash{" +
                "pwdHash='" + pwdHash + '\'' +
                '}';
    }
}
