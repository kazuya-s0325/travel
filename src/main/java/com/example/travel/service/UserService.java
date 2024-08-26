package com.example.travel.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;

import com.example.travel.entity.Role;
import com.example.travel.entity.User;
import com.example.travel.form.SignupForm;
import com.example.travel.repository.RoleRepository;
import com.example.travel.repository.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User create(SignupForm signupForm) {
    User user = new User();
    Role role = roleRepository.findByName("ROLE_GENERAL");

    user.setName(signupForm.getName());
    user.setFurigana(signupForm.getFurigana());
    user.setPostalCode(signupForm.getPostalCode());
    user.setAddress(signupForm.getAddress());
    user.setPhoneNumber(signupForm.getPhoneNumber());
    user.setEmail(signupForm.getEmail());
    user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
    user.setRole(role);
    user.setEnabled(true);
    return userRepository.save(user);
  }

  public void validateSignupForm(SignupForm signupForm, BindingResult bindingResult) {
    if (isEmailRegistered(signupForm.getEmail())) {
      FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです");
      bindingResult.addError(fieldError);
    }

    if (!isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
      FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません");
      bindingResult.addError(fieldError);
    }
  }

  public boolean isEmailRegistered(String email) {
    User user = userRepository.findByEmail(email);
    return user != null;
  }

  public boolean isSamePassword(String password, String passwordConfirmation) {
    return password.equals(passwordConfirmation);
  }
}
