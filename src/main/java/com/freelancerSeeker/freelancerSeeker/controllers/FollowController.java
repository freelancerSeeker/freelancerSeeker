package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class FollowController {
    @Autowired
    UserSiteRepository userSiteRepository;

    @PostMapping("/follow/{username}")
    public RedirectView followUser(@PathVariable String username, Principal p, Model followModel) {
        if (p != null) {
            UserSiteEntity userToFollow = userSiteRepository.findByUsername(username);
            UserSiteEntity currentUser = userSiteRepository.findByUsername(p.getName());
            if (currentUser.getFollowing().contains(userToFollow)) {
                return new RedirectView("/profile/" + userToFollow.getUsername() + "?user=Already followed");
            }
            currentUser.addFollowing(userToFollow);
            userToFollow.addFollower(currentUser);
            userSiteRepository.save(userToFollow);
            userSiteRepository.save(currentUser);
            return new RedirectView("/profile/" + userToFollow.getUsername());
        }
        return new RedirectView("/login");
    }

    @PostMapping("/unfollow/{username}")
    public RedirectView unfollowUser(@PathVariable String username, Principal p, Model followModel) {
        if (p != null) {
            UserSiteEntity userToUnfollow = userSiteRepository.findByUsername(username);
            UserSiteEntity currentUser = userSiteRepository.findByUsername(p.getName());
            if (!currentUser.getFollowing().contains(userToUnfollow)) {
                return new RedirectView("/profile/" + userToUnfollow.getUsername() + "?user=Already followed");
            }
            currentUser.getFollowing().remove(userToUnfollow);
            userToUnfollow.getFollowers().remove(currentUser);
            userSiteRepository.save(currentUser);
            userSiteRepository.save(userToUnfollow);
            return new RedirectView("/profile/" + userToUnfollow.getUsername());
        }
        return new RedirectView("/login");
    }

}
