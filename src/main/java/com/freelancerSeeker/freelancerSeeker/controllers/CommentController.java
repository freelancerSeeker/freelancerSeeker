package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.CommentEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Exceptions.ResourceNotFoundException;
import com.freelancerSeeker.freelancerSeeker.Repository.CommentRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserSiteRepository userSiteRepository;
    @Autowired
    PostsRepository postsRepository;

    @PostMapping("/create-Comment")
    public RedirectView createComment(Principal principal, @RequestParam("body") String body, @RequestParam("postId") Long postId){
        if(principal!=null){
            String username=principal.getName();
            UserSiteEntity userSite=userSiteRepository.findByUsername(username);
            if (userSite!=null){
                PostsEntity posts=postsRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException());

            if(posts!=null) {
                CommentEntity commentEntity = new CommentEntity();
                commentEntity.setBody(body);
                commentEntity.setUser(userSite);
                commentEntity.setPosts(posts);
                commentRepository.save(commentEntity);

                return new RedirectView("/Posts/" + posts.getId());
                }
            }
        }
        return new RedirectView("/Posts/" + postId);
    }

    @DeleteMapping("/comment/delete/{commentID}")
    public RedirectView deleteComment(@PathVariable Long commentID,Principal principal){
        if(principal!=null){
            String username=principal.getName();
            UserSiteEntity userSite=userSiteRepository.findByUsername(username);
            if (userSite!=null){
                CommentEntity commentEntity=commentRepository.findById(commentID).orElseThrow(()->new ResourceNotFoundException());
            if(userSite.getId()==commentEntity.getUser().getId()){
                commentRepository.deleteById(commentID);
                return new RedirectView("/Posts/" + commentEntity.getPosts().getId());
            }
            }
        }
        return new RedirectView("/");
    }




}
