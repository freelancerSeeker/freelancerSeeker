package com.freelancerSeeker.freelancerSeeker.controllers;


import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class ReviewsController {
    @Autowired
    UserSiteRepository userSiteRepo;


}
