package ua.onufreiv.hotel.controller.commands;

import ua.onufreiv.hotel.controller.manager.PathConfig;
import ua.onufreiv.hotel.entities.PasswordHash;
import ua.onufreiv.hotel.entities.User;
import ua.onufreiv.hotel.service.IRegisterService;
import ua.onufreiv.hotel.service.impl.RegisterService;
import ua.onufreiv.hotel.util.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yurii on 12/29/16.
 */
public class CommandRegister implements ICommand {
    private static final String PARAM_NAME_FIRST_NAME = "first_name";
    private static final String PARAM_NAME_LAST_NAME = "last_name";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_TELEPHONE = "telephone";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            if (validEmail) {
                request.setAttribute(PARAM_NAME_EMAIL, email);
            } else {
                request.setAttribute("emailError", "User with such email already exists");
            }

            if (validPhoneNumber) {
                request.setAttribute(PARAM_NAME_EMAIL, telephone);
            } else {
                request.setAttribute("phoneError", "Invalid phone number");
            }

            if (!validPassword) {
                request.setAttribute("passwordError", "Invalid password");
            }

            return PathConfig.getInstance().getProperty(PathConfig.REGISTER_PAGE_PATH);
        }

        User user = new User();
        user.setName(firstName);
        user.setSurname(lastName);
        user.setEmail(email);
        user.setTelephone(telephone);

        String page;
        String passwordHashString = PasswordEncoder.encode(password);
        if (passwordHashString != null) {
            PasswordHash passwordHash = new PasswordHash();
            passwordHash.setPwdHash(passwordHashString);

            registerService.registerNewUser(user, passwordHash);
            page = PathConfig.getInstance().getProperty(PathConfig.LOGIN_PAGE_PATH);
        } else {
            page = PathConfig.getInstance().getProperty(PathConfig.ERROR_PAGE_PATH);
        }
        return page;
    }
}
