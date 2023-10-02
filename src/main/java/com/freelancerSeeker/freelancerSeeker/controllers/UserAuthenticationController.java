package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class UserAuthenticationController {
    // This controller to handle both freelancer and user authentication.

    @Autowired
    UserSiteRepository userSiteRepo;
    @Autowired
    HttpServletRequest request;
    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String login() {
        return "signup.html";
    }


    @GetMapping("/")
    public String getHome(Principal p, Model homeModel) {
        if (p != null) {
            String username = p.getName();
            homeModel.addAttribute("username", username);
            return "home";
        }
        return "home";
    }


    @GetMapping("/reviews")
    public String getReviews() {
        return "reviews";
    }

    @GetMapping("/about")
    public String getAbout(Principal p, Model aboutModel) {
        if (p != null) {
            String username = p.getName();
            aboutModel.addAttribute("username", username);
            return "about";
        }
        return "about";
    }


    @PostMapping("/signup")
    public ModelAndView signupNormalUser(@RequestParam String username, @RequestParam String password, @RequestParam String description, @RequestParam String email, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String role, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if (userSiteRepo.findByUsername(username) != null) {
            modelAndView.addObject("usernameError","Username already exists. Please choose a different username.");
            modelAndView.setViewName("redirect:/login");
        } else {
            String encryptedPassword = passwordEncoder.encode(password);
            UserSiteEntity usersite = new UserSiteEntity();
            usersite.setUsername(username);
            usersite.setPassword(encryptedPassword);
            usersite.setDescription(description);
            usersite.setEmail(email);
            usersite.setFirstname(firstname);
            usersite.setLastname(lastname);
            usersite.setRoles(Role.valueOf(role));
            userSiteRepo.save(usersite);
            System.out.println(usersite.getUsername());
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

//    @PostMapping("/signup/check-username")
//    @ResponseBody
//    public ResponseEntity<String> checkUsernameAvailability(@RequestParam String username) {
//        if (userSiteRepo.findByUsername(username) != null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("Username already exists. Please choose a different username.");
//        }
//
//        return ResponseEntity.ok("Username is available");
//    }


    public RedirectView authWithHttpServletRequest(String username, String password) {

        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return new RedirectView("/signup");
    }


}
