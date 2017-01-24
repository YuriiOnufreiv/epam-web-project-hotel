package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.IRegisterService;
import ua.onufreiv.hotel.service.impl.RegisterService;
import ua.onufreiv.hotel.util.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yurii on 12/29/16.
 */
public class CommandRegister implements Command {
    private static final String FIRST_NAME_NAME = "first_name";
    private static final String LAST_NAME_NAME = "last_name";
    private static final String EMAIL_NAME = "email";
    private static final String TELEPHONE_NAME = "phoneNum";
    private static final String PASSWORD_NAME = "password";
    private static final String SUCCESSFUL_SIGN_UP_NAME = "successfulSignUp";
    private static final String ERRORS_NAME = "errors";

    private static final String EMAIL_EXISTS_ERROR_KEY = "errors.email_exists";
    private static final String INVALID_NUMBER_ERROR_KEY = "errors.invalid.number";
    private static final String INVALID_PASSWORD_ERROR_KEY = "errors.invalid.password";

    private static final String ERRORS_DELIMITER = "|";
    private final IRegisterService registerService;

    public CommandRegister() {
        registerService = new RegisterService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter(FIRST_NAME_NAME);
        String lastName = request.getParameter(LAST_NAME_NAME);
        String email = request.getParameter(EMAIL_NAME);
        String telephone = request.getParameter(TELEPHONE_NAME);
        String password = request.getParameter(PASSWORD_NAME);

        boolean validEmail = registerService.isUniqueEmail(email);
        boolean validPhoneNumber = registerService.isValidPhoneNumber(telephone);
        boolean validPassword = registerService.isValidPassword(password);

        if (!(validEmail && validPassword && validPhoneNumber)) {
            request.setAttribute(FIRST_NAME_NAME, firstName);
            request.setAttribute(LAST_NAME_NAME, lastName);

            StringBuilder errorsBuilder = new StringBuilder();

            if (validEmail) {
                request.setAttribute(EMAIL_NAME, email);
            } else {
                errorsBuilder.append(EMAIL_EXISTS_ERROR_KEY).append(ERRORS_DELIMITER);
            }

            if (validPhoneNumber) {
                request.setAttribute(TELEPHONE_NAME, telephone);
            } else {
                errorsBuilder.append(INVALID_NUMBER_ERROR_KEY).append(ERRORS_DELIMITER);
            }

            if (!validPassword) {
                errorsBuilder.append(INVALID_PASSWORD_ERROR_KEY).append(ERRORS_DELIMITER);
            }

            request.setAttribute(ERRORS_NAME, errorsBuilder.toString());
            return PathConfig.getInstance().getProperty(PathConfig.CREATE_ACCOUNT_PAGE_PATH);
        }

        User user = new User();
        user.setName(firstName);
        user.setSurname(lastName);
        user.setEmail(email);
        user.setPhoneNum(telephone);

        String page;
        String passwordHashString = PasswordEncoder.encode(password);
        if (passwordHashString != null) {
            PasswordHash passwordHash = new PasswordHash();
            passwordHash.setPwdHash(passwordHashString);

            registerService.registerNewUser(user, passwordHash);

            request.setAttribute(SUCCESSFUL_SIGN_UP_NAME, true);
            page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.ERROR_PAGE_PATH);
        }

        return page;
    }
}
