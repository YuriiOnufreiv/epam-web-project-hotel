package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.ParamNamesConfig;
import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entity.PasswordHash;
import ua.onufreiv.hotel.entity.User;
import ua.onufreiv.hotel.service.IRegisterService;
import ua.onufreiv.hotel.service.impl.RegisterService;
import ua.onufreiv.hotel.util.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ua.onufreiv.hotel.controller.manager.ParamNamesConfig.*;

/**
 * Created by yurii on 12/29/16.
 */
public class CommandRegister implements Command {
    private final ParamNamesConfig names;
    private final IRegisterService registerService;

    public CommandRegister() {
        registerService = new RegisterService();
        names = ParamNamesConfig.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter(names.get(USER_FIRST_NAME_NAME));
        String lastName = request.getParameter(names.get(USER_LAST_NAME_NAME));
        String email = request.getParameter(names.get(USER_EMAIL_NAME));
        String telephone = request.getParameter(names.get(USER_TELEPHONE_NUMBER_NAME));
        String password = request.getParameter(names.get(USER_PASSWORD_NAME));

        boolean validEmail = registerService.isUniqueEmail(email);
        boolean validPhoneNumber = registerService.isValidPhoneNumber(telephone);
        boolean validPassword = registerService.isValidPassword(password);

        if (!(validEmail && validPassword && validPhoneNumber)) {
            request.setAttribute(names.get(USER_FIRST_NAME_NAME), firstName);
            request.setAttribute(names.get(USER_LAST_NAME_NAME), lastName);

            StringBuilder errorsBuilder = new StringBuilder();

            if (validEmail) {
                request.setAttribute(names.get(USER_EMAIL_NAME), email);
            } else {
                errorsBuilder.append(names.get(EMAIL_EXISTS_ERROR_KEY_NAME)).append(names.get(ERROR_DELIMITER));
            }

            if (validPhoneNumber) {
                request.setAttribute(names.get(USER_TELEPHONE_NUMBER_NAME), telephone);
            } else {
                errorsBuilder.append(names.get(INVALID_NUMBER_ERROR_KEY_NAME)).append(names.get(ERROR_DELIMITER));
            }

            if (!validPassword) {
                errorsBuilder.append(names.get(INVALID_PASSWORD_ERROR_KEY_NAME)).append(names.get(ERROR_DELIMITER));
            }

            request.setAttribute(names.get(SIGN_UP_ERRORS_NAME), errorsBuilder.toString());
            return PathConfig.getInstance().getProperty(PathConfig.CREATE_ACCOUNT_PAGE_PATH);
        }

        User user = new User();
        user.setName(firstName);
        user.setSurname(lastName);
        user.setEmail(email);
        user.setPhoneNum(telephone);

        String page;
        String passwordHashString = PasswordEncoder.getInstance().encode(password);
        if (passwordHashString != null) {
            PasswordHash passwordHash = new PasswordHash();
            passwordHash.setPwdHash(passwordHashString);

            registerService.registerNewUser(user, passwordHash);

            request.setAttribute(names.get(SIGN_UP_SUCCESS_NAME), true);
            page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.ERROR_PAGE_PATH);
        }

        return page;
    }
}
