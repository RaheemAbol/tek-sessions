package org.abol.springstarter.controllers;

import org.abol.springstarter.models.BaseUser;
import org.abol.springstarter.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registerForm")
    public String showForm(Model model) {
        log.info("Displaying registration form");
        model.addAttribute("baseUser", new BaseUser());
        return "register-form";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("baseUser") BaseUser baseUser, Model model) {
        userService.saveUser(baseUser);
        log.info("User saved: {}", baseUser);
        model.addAttribute("baseUser", baseUser);
        return "userDetails";
    }

    @GetMapping("/userDetails")
    public String baseUserDetails(@RequestParam(name="index", required=false) Integer index, Model model) {
        BaseUser user = userService.getUserByIndex(index);
        model.addAttribute("baseUser", user != null ? user : new BaseUser());
        log.info("Displaying details for user at index {}", index);
        return "userDetails";
    }

    @GetMapping("/allUsers")
    public String displayAll(Model model) {
        List<BaseUser> users = userService.getAllUsers();
        log.info("Displaying all users");
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/editUser")
    public String editUserForm(@RequestParam("index") int index, Model model) {
        BaseUser user = userService.getUserByIndex(index);
        if (user != null) {
            model.addAttribute("baseUser", user);
            model.addAttribute("index", index);
            return "edit-user-form";
        } else {
            return "redirect:/allUsers";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("baseUser") BaseUser baseUser, @RequestParam("index") int index, Model model) {
        userService.updateUser(index, baseUser);
        log.info("User updated: {}", baseUser);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/allUsers";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("index") int index, Model model) {
        userService.deleteUser(index);
        log.info("Deleting user at index {}", index);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/allUsers";
    }

    @GetMapping("/getUserByEmail")
    public String getUserByEmail(@RequestParam("email") String email, Model model) {
        BaseUser user = userService.getUserByEmail(email);
        if (user != null) {
            model.addAttribute("baseUser", user);
            log.info("Displaying details for user with email {}", email);
        } else {
            model.addAttribute("baseUser", new BaseUser());
            log.warn("No user found with email {}", email);
        }
        return "userDetails";
    }

    @GetMapping("/getUserById/{id}")
    public String getUserByIdPath(@PathVariable("id") int id, Model model) {
        BaseUser user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("baseUser", user);
            log.info("Displaying details for user with ID {}", id);
        } else {
            model.addAttribute("baseUser", new BaseUser());
            log.warn("No user found with ID {}", id);
        }
        return "userDetails";
    }
}
