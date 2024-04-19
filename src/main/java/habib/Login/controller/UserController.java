package habib.Login.controller;


import java.util.List;

import habib.Login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import habib.Login.model.User;
import habib.Login.repository.UserRepository;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users/profile")
    public User findUserByJwt(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwt(jwt);

        return  user;
    }

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public User createUser(@RequestBody User user) throws Exception {

        User isExist = userRepository.findByEmail(user.getEmail());
        if (isExist != null) {
            throw new Exception("Email is already used!!!  " + user.getEmail());
        }

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable Long userId) throws Exception {
        userRepository.deleteById(userId);
        return "User is DELETED successfully with id: " + userId;
    }

    @GetMapping("/users/all")
    public List<User> getAllUsers() throws Exception {
        List<User> users = userRepository.findAll();
        return users;
    }
}