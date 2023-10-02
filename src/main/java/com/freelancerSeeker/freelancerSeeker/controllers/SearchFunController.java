package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchFunController {
@Autowired
    PostsRepository postsRepository;
    @GetMapping("/search/{subject}")
    public String getSearchPost(Model model, @PathVariable String subject, Principal principal)
    {
        List<PostsEntity> post = postsRepository.findBySubjectContainingOrderByCreatedAtDesc(subject);
        model.addAttribute("posts",post);
        if(principal!=null)
        {
            String username = principal.getName();

            model.addAttribute("username",username);

            return "search";
        }

        return "search";
    }

    @PostMapping("/search-post")
    public String viewPost(Model model, @RequestParam("subject") String subject, Principal principal)
    {
        if(principal!=null)
        {
            String username = principal.getName();
            List<PostsEntity> post= postsRepository.findBySubjectContainingOrderByCreatedAtDesc(subject);
            model.addAttribute("posts",post);
            model.addAttribute("subject", subject);
            model.addAttribute("username",username);

            return "redirect:/search/"+subject;
        }

        return "redirect:/search/"+subject;

    }
}
