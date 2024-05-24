package org.abol.springstarter.controllers;

import org.abol.springstarter.models.BaseUser;
import org.abol.springstarter.models.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@SessionAttributes("baseUser")
@RequestMapping("/session")
public class SessionController {

    @ModelAttribute("baseUser")
    public BaseUser baseUser() {
        return new BaseUser();
    }

    @GetMapping("/registerForm")
    public String showForm(Model model) {
        log.info("Displaying registration form");
        model.addAttribute("baseUser", new BaseUser());
        return "register-session";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("baseUser") BaseUser baseUser, Model model) {
        log.info("User saved: {}", baseUser);
        model.addAttribute("message", "User successfully saved in session!");
        return "redirect:/session/addItemForm";
    }

    @GetMapping("/addItemForm")
    public String showAddItemForm(Model model) {
        model.addAttribute("cartItem", new CartItem());
        return "add-item-form";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute("cartItem") CartItem cartItem, @SessionAttribute("baseUser") BaseUser baseUser, Model model) {
        baseUser.getCart().add(cartItem);
        log.info("Item added to cart: {}", cartItem);
        model.addAttribute("message", "Item added to cart!");
        return "redirect:/session/viewCart";
    }

    @GetMapping("/viewCart")
    public String viewCart(@SessionAttribute("baseUser") BaseUser baseUser, Model model) {
        model.addAttribute("cart", baseUser.getCart());
        model.addAttribute("userName", baseUser.getName());
        return "view-cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(@SessionAttribute("baseUser") BaseUser baseUser, Model model) {
        baseUser.getCart().clear();
        log.info("Cart cleared");
        model.addAttribute("message", "Cart cleared!");
        return "redirect:/session/viewCart";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        log.info("Session cleared");
        return "redirect:/session/registerForm";
    }

    @GetMapping("/userDetails")
    public String baseUserDetails(@SessionAttribute("baseUser") BaseUser baseUser, Model model) {
        log.info("Displaying details for user: {}", baseUser);
        model.addAttribute("baseUser", baseUser);
        return "user-details-session";
    }
}
