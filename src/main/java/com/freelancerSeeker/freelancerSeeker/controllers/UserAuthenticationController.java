package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Models.FreeLancer;
import com.freelancerSeeker.freelancerSeeker.Models.NormalUser;
import com.freelancerSeeker.freelancerSeeker.Repository.FreeLancerRepo;
import com.freelancerSeeker.freelancerSeeker.Repository.NormalUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserAuthenticationController {
    // This controller to handle both freelancer and user authentication.

}
