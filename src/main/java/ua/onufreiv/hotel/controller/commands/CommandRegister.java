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
    private static final String PARAM_NAME_FIRST_NAME = "first_name";
    private static final String PARAM_NAME_LAST_NAME = "last_name";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_TELEPHONE = "phoneNum";
    private static final String PARAM_NAME_PASSWORD = "password";

    private static final String EMAIL_EXISTS_ERROR_KEY = "errors.email_exists";
    private static final String INVALID_NUMBER_ERROR_KEY = "errors.invalid.number";
    private static final String INVALID_PASSWORD_ERROR_KEY = "errors.invalid.password";
    private static final String ERRORS_DELIMITER = "|";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //IUserService userService = new UserService();
        IRegisterService registerService = new RegisterService();

        String firstName = request.getParameter(PARAM_NAME_FIRST_NAME);
        String lastName = request.getParameter(PARAM_NAME_LAST_NAME);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String telephone = request.getParameter(PARAM_NAME_TELEPHONE);
        String password = request.getParameter(PARAM_NAME_PASSWORD);

        boolean validEmail = registerService.isUniqueEmail(email);
        boolean validPhoneNumber = registerService.isValidPhoneNumber(telephone);
        boolean validPassword = registerService.isValidPassword(password);

        if (!(validEmail && validPassword && validPhoneNumber)) {
            request.setAttribute(PARAM_NAME_FIRST_NAME, firstName);
            request.setAttribute(PARAM_NAME_LAST_NAME, lastName);

            StringBuilder errorsBuilder = new StringBuilder();

            if (validEmail) {
                request.setAttribute(PARAM_NAME_EMAIL, email);
            } else {
                errorsBuilder.append(EMAIL_EXISTS_ERROR_KEY).append(ERRORS_DELIMITER);
            }

            if (validPhoneNumber) {
                request.setAttribute(PARAM_NAME_TELEPHONE, telephone);
            } else {
                errorsBuilder.append(INVALID_NUMBER_ERROR_KEY).append(ERRORS_DELIMITER);
            }

            if (!validPassword) {
                errorsBuilder.append(INVALID_PASSWORD_ERROR_KEY).append(ERRORS_DELIMITER);
            }
            request.setAttribute("errors", errorsBuilder.toString());

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
            request.setAttribute("successfulSignUp", true);
            page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.ERROR_PAGE_PATH);
        }
        return page;
    }
}
