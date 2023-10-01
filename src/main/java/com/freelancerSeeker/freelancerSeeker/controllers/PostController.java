package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Exceptions.ResourceNotFoundException;
import com.freelancerSeeker.freelancerSeeker.Models.Posts;
import com.freelancerSeeker.freelancerSeeker.Models.UserSite;
import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepo;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostsRepo postsRepo;
    @Autowired
    UserSiteRepo userSiteRepo;

    @PostMapping("/create-post")
    public RedirectView createPost(Principal principal, String subject, String body, Date startDate, Date  endDate){
        if(principal!=null){
            String username=principal.getName();
            UserSite userSite=userSiteRepo.findByUsername(username);
            if(userSite!=null){
                Posts post=new Posts(subject,body,startDate,endDate,userSite);
                post.setCreatedAt(Date.from(Instant.from(LocalDate.now())));
                postsRepo.save(post);
                return new RedirectView("/profile");
            }
        }
        return new RedirectView("/profile");
    }

    @GetMapping("/Posts/{postId}")
    public String getPostById(@PathVariable Long postId,Model model){
        Posts post=postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException());
        model.addAttribute("postDetails",post);
        return "post.html";
    }


    @GetMapping("/home")
    public String getAllPosts( Model model){
        List<Posts> posts=postsRepo.findAll();
        model.addAttribute("posts",posts);
        return "home.html";
    }

    @PutMapping("/Posts/{postId}")
    public RedirectView updatePost(@PathVariable Long postId,String subject, String body, Date startDate, Date  endDate){
        Posts post=postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException());
        post.setSubject(subject);
        post.setBody(body);
        post.setStartDate(startDate);
        post.setEndDate(endDate);
        postsRepo.save(post);
        return new RedirectView("/Posts/"+postId);
    }

    @DeleteMapping("/posts/{postId}")
    public RedirectView deletePost(@PathVariable Long postId){
        postsRepo.deleteById(postId);
        return new RedirectView("/home");
    }






}
