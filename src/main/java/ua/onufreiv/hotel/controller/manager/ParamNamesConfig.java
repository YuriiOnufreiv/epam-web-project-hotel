package ua.onufreiv.hotel.controller.manager;

import java.util.ResourceBundle;

/**
 * Created by yurii on 12/27/16.
 */
public class ParamNamesConfig {
    private static final String BUNDLE_NAME = "param_names";

    public static final String ID_ROOM_TYPE_MAP_NAME = "ID_ROOM_TYPE_MAP_NAME";

    public static final String ROOM_TYPE_NAME = "ROOM_TYPE_NAME";
    public static final String ROOM_NUMBER_NAME = "ROOM_NUMBER_NAME";
    public static final String ADD_ROOM_SUCCESS_NAME = "ADD_ROOM_SUCCESS_NAME";
    public static final String INVALID_ROOM_NUMBER_ERROR_NAME = "INVALID_ROOM_NUMBER_ERROR_NAME";
    public static final String ROOM_PRICE_NAME = "ROOM_PRICE_NAME";
    public static final String ROOM_PERSONS_TOTAL_NAME = "ROOM_PERSONS_TOTAL_NAME";
    public static final String ROOM_DESCRIPTION_NAME = "ROOM_DESCRIPTION_NAME";
    public static final String INVALID_ROOM_TYPE_ERROR_NAME = "INVALID_ROOM_TYPE_ERROR_NAME";
    public static final String ADD_ROOM_TYPE_SUCCESS_NAME = "ADD_ROOM_TYPE_SUCCESS_NAME";
    public static final String BOOK_REQUEST_ID_NAME = "BOOK_REQUEST_ID_NAME";
    public static final String ROOM_ID_NAME = "ROOM_ID_NAME";
    public static final String ROOM_TYPE_ID_NAME = "ROOM_TYPE_ID_NAME";
    public static final String CREATE_BILL_ERROR_NAME = "CREATE_BILL_ERROR_NAME";
    public static final String BOOK_REQUEST_CREATION_DATE_NAME = "BOOK_REQUEST_CREATION_DATE_NAME";
    public static final String BOOK_REQUEST_PERSONS_NAME = "BOOK_REQUEST_PERSONS_NAME";
    public static final String CHECK_IN_DATE_NAME = "CHECK_IN_DATE_NAME";
    public static final String CHECK_OUT_DATE_NAME = "CHECK_OUT_DATE_NAME";
    public static final String BILL_TOTAL_PRICE_NAME = "BILL_TOTAL_PRICE_NAME";
    public static final String USER_EMAIL_NAME = "USER_EMAIL_NAME";
    public static final String USER_PASSWORD_NAME = "USER_PASSWORD_NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String INVALID_LOGIN_ERROR_NAME = "INVALID_LOGIN_ERROR_NAME";
    public static final String RESERVE_SUCCESS_NAME = "RESERVE_SUCCESS_NAME";
    public static final String INVALID_BOOK_REQUEST_DATES_ERROR_NAME = "INVALID_BOOK_REQUEST_DATES_ERROR_NAME";
    public static final String BOOK_REQUEST_NAME = "BOOK_REQUEST_NAME";
    public static final String EXACT_ROOM_NAME = "EXACT_ROOM_NAME";
    public static final String CHEAPER_ROOM_NAME = "CHEAPER_ROOM_NAME";
    public static final String EXPENSIVE_ROOM_NAME = "EXPENSIVE_ROOM_NAME";
    public static final String BOOK_REQUEST_LIST_NAME = "BOOK_REQUEST_LIST_NAME";
    public static final String NOT_PROCESSED_BOOK_REQUEST_LIST_NAME = "NOT_PROCESSED_BOOK_REQUEST_LIST_NAME";
    public static final String BILL_CREATION_DATE_NAME = "BILL_CREATION_DATE_NAME";
    public static final String USER_FIRST_NAME_NAME = "USER_FIRST_NAME_NAME";
    public static final String USER_LAST_NAME_NAME = "USER_LAST_NAME_NAME";
    public static final String USER_TELEPHONE_NUMBER_NAME = "USER_TELEPHONE_NUMBER_NAME";
    public static final String SIGN_UP_SUCCESS_NAME = "SIGN_UP_SUCCESS_NAME";
    public static final String SIGN_UP_ERRORS_NAME = "SIGN_UP_ERRORS_NAME";
    public static final String NEW_ROOMS_AVAILABLE_NAME = "NEW_ROOMS_AVAILABLE_NAME";


    public static final String EMAIL_EXISTS_ERROR_KEY_NAME = "EMAIL_EXISTS_ERROR_KEY_NAME";
    public static final String INVALID_NUMBER_ERROR_KEY_NAME = "INVALID_NUMBER_ERROR_KEY_NAME";
    public static final String INVALID_PASSWORD_ERROR_KEY_NAME = "INVALID_PASSWORD_ERROR_KEY_NAME";
    public static final String ERROR_DELIMITER = "ERROR_DELIMITER";

    private static ParamNamesConfig instance;

    private ResourceBundle resourceBundle;

    private ParamNamesConfig() {
    }

    public static ParamNamesConfig getInstance() {
        if (instance == null) {
            instance = new ParamNamesConfig();
            instance.resourceBundle =
                    ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String get(String key) {
        return (String) resourceBundle.getObject(key);
    }
}
