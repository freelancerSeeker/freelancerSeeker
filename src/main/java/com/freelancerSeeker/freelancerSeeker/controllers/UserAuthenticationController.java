package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    PostsRepository postsRepo;


    @GetMapping("/login")
    public String login(Principal p) {
        if (isAlreadyLoggedIn(p)) {
            return "redirect:/profile/" + p.getName();
        }
        return "signup";
    }

    @GetMapping("/")
    public String getHome(Principal p, Model homeModel, @RequestParam(required = false, defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 9);
        Page<PostsEntity> posts = postsRepo.findAllByOrderByCreatedAtDesc(pageable);
        homeModel.addAttribute("postList", posts.getContent());
        homeModel.addAttribute("Page", page);
        homeModel.addAttribute("totalPages", posts.getTotalPages());

        if (p != null) {
            String username = p.getName();
            homeModel.addAttribute("username", username);
            return "home";
        }

        return "home";
    }


   /* @GetMapping("/reviews")
    public String getReviews() {
        return "reviews";
    }*/

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
    public ModelAndView signupNormalUser(Principal p,
                                         @RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String description,
                                         @RequestParam String email,
                                         @RequestParam String firstname,
                                         @RequestParam String lastname,
                                         @RequestParam String role,
                                         Model model) {
        ModelAndView modelAndView = new ModelAndView();


        username = username.trim();


        if (isAlreadyLoggedIn(p)) {

            modelAndView.setViewName("redirect:/profile/" + p.getName());
        } else if (username.isEmpty() || username.isBlank()) {

            modelAndView.addObject("usernameError", "Username cannot be empty or contain only whitespace.");
            modelAndView.setViewName("redirect:/login");
        } else if (userSiteRepo.findByUsername(username) != null) {
            modelAndView.addObject("usernameError", "Username already exists. Please choose a different username.");
            modelAndView.setViewName("redirect:/login");

        } else if (userSiteRepo.findByEmail(email) != null) {
            modelAndView.addObject("usernameError", "Email already exists. Please choose a different email.");
            modelAndView.setViewName("redirect:/login");
        } else {
            if (isPasswordValidated(password)) {
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
            } else {
                modelAndView.addObject("usernameError", "Password should be 8 character including uppercase.");
                modelAndView.setViewName("redirect:/login");
            }
        }
        return modelAndView;
    }



    @PostMapping("/login")
    public RedirectView authWithHttpServletRequest(@RequestParam String username, @RequestParam String password) {



        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return new RedirectView("/signup");


    }

    private boolean isAlreadyLoggedIn(Principal p) {
        return p != null;
    }

    private boolean isPasswordValidated(String password) {
        if (password.length() < 8) {
            return false;
        } else {
            return password.matches("^(?=.*[a-z])(?=.*[A-Z]).+$");
        }
    }
}

