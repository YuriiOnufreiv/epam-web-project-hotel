package ua.onufreiv.hotel.controller.manager;

import java.util.ResourceBundle;

/**
 * Helper class for dealing with the 'jsp.properties' bundle
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/27/16.
 */
public class JspConfig {
    private static final String BUNDLE_NAME = "jsp";

    // common names
    public static final String CHECK_IN_DATE_NAME = "check.in.date";
    public static final String CHECK_OUT_DATE_NAME = "check.out.date";

    // 'user' names
    public static final String USER_NAME = "user.name";
    public static final String USER_FIRST_NAME_NAME = "user.first.name.name";
    public static final String USER_LAST_NAME_NAME = "user.last.name.name";
    public static final String USER_EMAIL_NAME = "user.email.name";
    public static final String USER_TELEPHONE_NUMBER_NAME = "user.telephone.number.name";
    public static final String USER_PASSWORD_NAME = "user.password.name";

    // 'room type' names
    public static final String ROOM_TYPE_NAME = "room.type.name";
    public static final String ID_ROOM_TYPE_MAP_NAME = "id.room.type.map.name";

    // 'room' names
    public static final String ROOM_ID_NAME = "room.id.name";
    public static final String ROOM_NUMBER_NAME = "room.number.name";
    public static final String ROOM_PRICE_NAME = "room.price.name";
    public static final String ROOM_PERSONS_TOTAL_NAME = "room.persons.total.name";
    public static final String ROOM_DESCRIPTION_NAME = "room.description.name";
    public static final String EXACT_TYPE_ROOM_NAME = "exact.type.room.name";
    public static final String FAVOURITE_TYPE_ROOM_NAME = "favourite.type.room.name";
    public static final String ANY_TYPE_ROOM_NAME = "any.type.room.name";

    // 'book request' names
    public static final String BOOK_REQUEST_NAME = "book.request.name";
    public static final String BOOK_REQUEST_ID_NAME = "book.request.id.name";
    public static final String BOOK_REQUEST_CREATION_DATE_NAME = "book.request.creation.date.name";
    public static final String BOOK_REQUEST_PERSONS_NAME = "book.request.persons.name";
    public static final String BOOK_REQUEST_LIST_NAME = "book.request.list.name";
    public static final String NOT_PROCESSED_BOOK_REQUEST_LIST_NAME = "not.processed.book.request.list.name";

    // 'bill' names
    public static final String BILL_TOTAL_PRICE_NAME = "bill.total.price.name";
    public static final String BILL_CREATION_DATE_NAME = "bill.creation.date.name";

    // MESSAGES names
    public static final String NEW_ROOMS_AVAILABLE_NAME = "new.rooms.available.name";

    // 'success' messages names
    public static final String SIGN_UP_SUCCESS_NAME = "sign.up.success.name";
    public static final String ADD_ROOM_SUCCESS_NAME = "add.room.success.name";
    public static final String ADD_ROOM_TYPE_SUCCESS_NAME = "add.room.type.success.name";
    public static final String CREATE_BOOK_REQUEST_SUCCESS_NAME = "create.book.request.success.name";
    public static final String CREATE_BILL_SUCCESS_NAME = "create.bill.success.name";

    // 'error' messages names
    public static final String SIGN_UP_ERRORS_NAME = "sign.up.errors.name";
    public static final String ADD_ROOM_ERROR_NAME = "add.room.error.name";
    public static final String ADD_ROOM_TYPE_ERROR_NAME = "add.room.type.error.name";
    public static final String CREATE_BILL_ERROR_NAME = "create.bill.error.name";
    public static final String CREATE_BOOK_REQUEST_ERROR_NAME = "create.book.request.error.name";
    public static final String INVALID_ROOM_NUMBER_ERROR_NAME = "invalid.room.number.error.name";
    public static final String INVALID_ROOM_TYPE_ERROR_NAME = "invalid.room.type.error.name";
    public static final String INVALID_BOOK_REQUEST_DATES_ERROR_NAME = "invalid.book.request.error.name";
    public static final String INVALID_LOGIN_ERROR_NAME = "invalid.login.error.name";

    // 'error' bundle keys
    public static final String ERRORS_KEY_DELIMITER = "errors.key.delimiter";
    public static final String ACCOUNT_WAS_NOT_CREATED_ERROR_KEY_NAME = "account.was.not.created.error.key.name";
    public static final String EMAIL_EXISTS_ERROR_KEY_NAME = "email.exists.error.key.name";
    public static final String INVALID_PASSWORD_ERROR_KEY_NAME = "invalid.password.error.key.name";
    public static final String INVALID_NUMBER_ERROR_KEY_NAME = "invalid.number.error.key.name";

    // date format in date pickers
    public static final String DATE_PICKER_DATE_FORMAT = "date.picker.date.format";

    private static JspConfig instance;

    private ResourceBundle resourceBundle;

    private JspConfig() {
    }

    public static synchronized JspConfig getInstance() {
        if (instance == null) {
            instance = new JspConfig();
            instance.resourceBundle =
                    ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String get(String key) {
        return (String) resourceBundle.getObject(key);
    }
}
