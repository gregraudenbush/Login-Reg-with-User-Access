package com.gregory.LoginReg.controllers;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gregory.LoginReg.models.User;
import com.gregory.LoginReg.services.UserService;
import com.gregory.LoginReg.validator.UserValidator;

@Controller
public class Users {
    
	private UserService userService;
	private UserValidator userValidator;
	
	public Users(UserService userService, UserValidator userValidator) {
	    this.userService = userService;
	    this.userValidator = userValidator;
	    }
	
	@RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
		String username = principal.getName();
		Date now = new Date();
        // 1
		List<User> users = userService.allUsers();
		model.addAttribute("users", users);
		User user = userService.findByUsername(username);
				
		
        model.addAttribute("currentUser", userService.findByUsername(username));
       
        model.addAttribute("date", now);
        if( user.getRoles().toString().equals(userService.findRoleByName("ROLE_USER").toString())) {
        		return "homePage.jsp";
        }else {
        
        		return "redirect:/admin";
        }
    }
	
//    @RequestMapping("/registration")
//    public String registerForm(@Valid @ModelAttribute("user") User user) {
//        return "registrationPage.jsp";
//    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
	    
    		userValidator.validate(user, result);
    		
    		if (result.hasErrors()) {
	        return "loginPage.jsp";
	    }
	    userService.saveWithUserRole(user);
//	    userService.saveUserWithAdminRole(user);
	    return "redirect:/login";
    }
    
    @RequestMapping("/login")
    public String login(@Valid @ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
        }
        
        
        return "loginPage.jsp";
    }
    
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUserName(username));
        model.addAttribute("allusers", userService.allUsers());
        System.out.println("in controller");
        System.out.println(userService.allUsers());
        model.addAttribute("roleadmin", userService.findRoleByName("ROLE_ADMIN"));
        model.addAttribute("roleuser", userService.findRoleByName("ROLE_USER"));
        
        return "adminPage.jsp";
    }
    @RequestMapping("/makeadmin/{id}")
    public String makeAdmin(@PathVariable("id") Long id) {
    		User user = userService.findById(id);
    		System.out.println(id);
    		System.out.println(user.getFirstName());
    		System.out.println("in make admin");
//	    userService.saveWithUserRole(user);
	    userService.saveUserWithAdminRole(user);
	    return "redirect:/admin";
    }
    
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
    		User user = userService.findById(id);
    		userService.deleteByUser(user);
//	    userService.saveWithUserRole(user);
//	    userService.saveUserWithAdminRole(user);
	    return "redirect:/admin";
    }
    
}
