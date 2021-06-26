package engine.controller;

import engine.model.User;
import engine.serviece.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody User user) {
        System.out.println(user.getEmail() + user.getPassword());
        if (!userService.save(user)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "user with this username is already registered"
            );
        }
    }
}
