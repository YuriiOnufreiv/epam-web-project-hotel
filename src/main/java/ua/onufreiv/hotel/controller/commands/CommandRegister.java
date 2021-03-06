package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.config.JspConfig;
import ua.onufreiv.hotel.controller.config.PathConfig;
import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.UserService;
import ua.onufreiv.hotel.service.impl.UserServiceImpl;
import ua.onufreiv.hotel.util.PasswordEncoder;
import ua.onufreiv.hotel.util.RegisterFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.config.JspConfig.*;

/**
 * Command for registering new user
 *
 * @author Yurii Onufreiv
 * @version 1.0
 * @since 12/29/16.
 */
public class CommandRegister implements Command {
    private final UserService userService;
    private final JspConfig jspConfig;

    public CommandRegister() {
        userService = UserServiceImpl.getInstance();
        jspConfig = JspConfig.getInstance();
    }

    /**
     * Handles request to register new user; validates the correctness of
     * inputted date, particularly - email, phone number and password
     *
     * @param request  request with the required parameters and attributes
     * @param response response that will be formed as a result
     * @return path to the same page in case of error, path to the sign in page in case of success
     * @see PathConfig
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter(jspConfig.get(USER_FIRST_NAME_NAME));
        String lastName = request.getParameter(jspConfig.get(USER_LAST_NAME_NAME));
        String email = request.getParameter(jspConfig.get(USER_EMAIL_NAME));
        String telephone = request.getParameter(jspConfig.get(USER_TELEPHONE_NUMBER_NAME));
        String password = request.getParameter(jspConfig.get(USER_PASSWORD_NAME));

        boolean validEmail = userService.isUniqueEmail(email);
        boolean validPhoneNumber = RegisterFormValidator.isValidPhoneNumber(telephone);
        boolean validPassword = RegisterFormValidator.isValidPassword(password);

        if (!(validEmail && validPassword && validPhoneNumber)) {
            request.setAttribute(jspConfig.get(USER_FIRST_NAME_NAME), firstName);
            request.setAttribute(jspConfig.get(USER_LAST_NAME_NAME), lastName);

            StringBuilder errorsBuilder = new StringBuilder();

            if (validEmail) {
                request.setAttribute(jspConfig.get(USER_EMAIL_NAME), email);
            } else {
                errorsBuilder.append(jspConfig.get(EMAIL_EXISTS_ERROR_KEY_NAME)).append(jspConfig.get(ERRORS_KEY_DELIMITER));
            }

            if (validPhoneNumber) {
                request.setAttribute(jspConfig.get(USER_TELEPHONE_NUMBER_NAME), telephone);
            } else {
                errorsBuilder.append(jspConfig.get(INVALID_NUMBER_ERROR_KEY_NAME)).append(jspConfig.get(ERRORS_KEY_DELIMITER));
            }

            if (!validPassword) {
                errorsBuilder.append(jspConfig.get(INVALID_PASSWORD_ERROR_KEY_NAME)).append(jspConfig.get(ERRORS_KEY_DELIMITER));
            }

            request.setAttribute(jspConfig.get(SIGN_UP_ERRORS_NAME), errorsBuilder.toString());
            return PathConfig.getInstance().getProperty(PathConfig.CREATE_ACCOUNT_PAGE_PATH);
        }

        User user = new User();
        user.setName(firstName);
        user.setSurname(lastName);
        user.setEmail(email);
        user.setPhoneNum(telephone);

        PasswordHash passwordHash = new PasswordHash();
        passwordHash.setPwdHash(PasswordEncoder.getInstance().encode(password));

        String page;
        if (passwordHash.getPwdHash() != null && userService.insertUser(user, passwordHash)) {
            request.setAttribute(jspConfig.get(SIGN_UP_SUCCESS_NAME), true);
            page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        } else {
            request.setAttribute(jspConfig.get(SIGN_UP_ERRORS_NAME), jspConfig.get(ACCOUNT_WAS_NOT_CREATED_ERROR_KEY_NAME));
            page = PathConfig.getInstance().getProperty(PathConfig.CREATE_ACCOUNT_PAGE_PATH);
        }

        return page;
    }
}
