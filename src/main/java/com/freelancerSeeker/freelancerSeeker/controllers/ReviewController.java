package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.ReviewEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Repository.ReviewRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    UserSiteRepository userSiteRepo;
    @Autowired
    ReviewRepository  reviewRepository ;


    @GetMapping("/reviews")
    public String showFreelancerReviews(Model model, Principal principal) {
        String username = (principal != null) ? principal.getName() : null;
        model.addAttribute("username", username);
        List<ReviewEntity> reviews = reviewRepository.findAll();
        model.addAttribute("reviewsEntity", reviews);
        UserSiteEntity userSite = (username != null) ? userSiteRepo.findByUsername(username) : null;
        if (userSite != null && userSite.getRoles() == Role.FREELANCER) {
            model.addAttribute("allowReview", false);
        } else {
            List<UserSiteEntity> freelancers = userSiteRepo.findByRoles(Role.FREELANCER);
            model.addAttribute("freelancerName", freelancers);
            model.addAttribute("allowReview", true);
        }
        return "reviews.html";
    }
    @PostMapping("/addReview")
    public RedirectView addReview(
            @ModelAttribute ReviewEntity reviewEntity,
            @RequestParam String description,
            @RequestParam int numberOfStars,
            Principal principal,
            @RequestParam String reviewedName)
    {
        UserSiteEntity reviewer = userSiteRepo.findByUsername(principal.getName());
        UserSiteEntity reviewedFreelancer = userSiteRepo.findByUsername(reviewedName);
        reviewEntity.setUser(reviewer);
        reviewEntity.setFreelancer(reviewedFreelancer);
        reviewEntity.setDescription(description);
        reviewEntity.setNumberOfStars(numberOfStars);
        reviewRepository.save(reviewEntity);
        return new RedirectView("/reviews");
    }

    @RequestMapping("/search")
    public String searchReviews(Model model, @RequestParam("freelancerName") String freelancerName, Principal principal) {
        if (principal != null) {
            List<ReviewEntity> resultReviews = reviewRepository.findByFreelancerUsername(freelancerName.toLowerCase());
            model.addAttribute("resultReviews", resultReviews);
            model.addAttribute("freelancerName", freelancerName);
            System.out.println("Inside searchReviews method");
            return "searchResults";
        }
        return "searchResults";
    }

    @DeleteMapping("/reviews/delete/{id}")
    public RedirectView deleteReview(@PathVariable long id, Principal principal) {
        ReviewEntity review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            UserSiteEntity loggedInUser = userSiteRepo.findByUsername(principal.getName());
            if (loggedInUser != null && review.getUser().getId().equals(loggedInUser.getId())) {
                reviewRepository.deleteById(id);
            }
        }
        return new RedirectView("/reviews");
    }
    }

