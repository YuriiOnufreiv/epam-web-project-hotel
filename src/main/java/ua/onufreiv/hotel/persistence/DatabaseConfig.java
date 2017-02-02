package ua.onufreiv.hotel.persistence;

import java.util.ResourceBundle;

/**
 * Helper class for dealing with the 'database.properties' bundle
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 1/17/17.
 */
public class DatabaseConfig {
    private static final String BUNDLE_NAME = "database";

    public static final String DATABASE_TYPE = "db.type";
    public static final String DATABASE_TRANSACTIONS_LEVEL = "db.transactions.level";

    private static DatabaseConfig instance;

    private ResourceBundle resourceBundle;

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
            instance.resourceBundle =
                    ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        return (String) resourceBundle.getObject(key);
    }
}
