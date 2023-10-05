package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.ReviewEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.ReviewRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    UserSiteRepository userSiteRepo;
    @Autowired
    ReviewRepository  reviewRepository ;

   
}
