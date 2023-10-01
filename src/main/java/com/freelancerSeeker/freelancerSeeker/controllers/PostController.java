package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Exceptions.ResourceNotFoundException;

import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;


@Controller
public class PostController {

    @Autowired
    PostsRepository postsRepo;
    @Autowired
    UserSiteRepository userSiteRepo;

    @PostMapping("/create-post")
    public RedirectView createPost(Principal principal, @RequestParam ("subject")String subject,  @RequestParam ("body") String body, @RequestParam ("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date  startDate, @RequestParam (value = "endDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")Date  endDate){
        if(principal!=null){
            String username=principal.getName();
            UserSiteEntity userSite=userSiteRepo.findByUsername(username);
            if(userSite!=null){
                PostsEntity post=new PostsEntity();
                post.setSubject(subject);
                post.setBody(body);
                post.setStartDate(startDate);
                post.setEndDate(endDate);
                post.setUser(userSite);
                post.setCreatedAt(new Date());
                postsRepo.save(post);
                System.out.println(endDate);
                return new RedirectView("profile/"+principal.getName() );

            }
        }
        return new RedirectView("profile");
    }



    @GetMapping("/Posts/{postId}")
    public String getPostById(@PathVariable Long postId,Model model){
        PostsEntity post=postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException());
        model.addAttribute("postDetails",post);
        return "home";
    }
//    @PutMapping("/Posts/{postId}")
//    public RedirectView updatePost(@PathVariable Long postId,String subject, String body, Date startDate, Date  endDate){
//        PostsEntity post=postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException());
//        post.setSubject(subject);
//        post.setBody(body);
//        post.setStartDate(startDate);
//        post.setEndDate(endDate);
//        postsRepo.save(post);
//        return new RedirectView("/Posts/"+postId);
//    }

//    @GetMapping("/home")
//    public String getAllPosts( Model model){
//        List<PostsEntity> posts=postsRepo.findAll();
//        model.addAttribute("posts",posts);
//        return "home.html";
//    }

    @PutMapping("/Posts/{postId}")
    public RedirectView updatePost(@PathVariable Long postId,String subject, String body, Date startDate, Date  endDate){
        PostsEntity post=postsRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException());
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
