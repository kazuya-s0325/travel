package com.example.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.travel.form.SignupForm;
import com.example.travel.service.UserService;

@Controller
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String login() {
    return "auth/login";
  }

  @GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute("signupForm", new SignupForm());
    return "auth/signup";
  }

  @PostMapping("/signup")
  public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    userService.validateSignupForm(signupForm, bindingResult);

    if (bindingResult.hasErrors()) {
      return "auth/signup";
    }

    userService.create(signupForm);
    redirectAttributes.addFlashAttribute("successMessage", "登録完了しました");
    return "redirect:/";
  }
}
