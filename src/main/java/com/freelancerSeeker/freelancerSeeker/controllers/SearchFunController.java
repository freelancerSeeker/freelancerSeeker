package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.TagsEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
public class SearchFunController {
    @Autowired
    PostsRepository postsRepository;
    @Autowired
    TagsRepository tagsRepository;

    @GetMapping("/search/{subject}")
    public String getSearchPost(Model model,
                                @PathVariable String subject,
                                Principal principal,
                                @RequestParam(name = "page", defaultValue = "1") int page) {
        Pageable pageable = calculatePostPerPage(page);
        Page<PostsEntity> postPage = postsRepository.findBySubjectContaining(subject, pageable);
        List<PostsEntity> post = postPage.getContent();
        if (post.isEmpty()) {
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            model.addAttribute("posts", post);
            model.addAttribute("noResultsMessage", "No matching posts found.");
        } else {
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", postPage.getTotalPages());
            model.addAttribute("posts", post);
        }
        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
            return "search";
        }

        return "search";
    }

    @GetMapping("/search/{subject}/filter")
    public String getPostByCreatedAt(Model model, Principal principal, @PathVariable(value = "subject") String subject,
                                     @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                     @RequestParam(name = "page", defaultValue = "1") int page) {
        int postPerpage = 9;
        Pageable pageable = PageRequest.of(page - 1, postPerpage, Sort.by("startDate").descending());
        Page<PostsEntity> postPage = postsRepository.findBySubjectContainingAndStartDate(subject, startDate, pageable);
        List<PostsEntity> post = postPage.getContent();
        if (post.isEmpty()) {
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            model.addAttribute("posts", post);
            model.addAttribute("noResultsMessage", "No matching posts found.");
        } else {
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", postPage.getTotalPages());
            model.addAttribute("posts", post);
        }

        if (principal != null) {
            String username = principal.getName();

            model.addAttribute("username", username);

            return "search";
        }
        return "search";
    }


    @PostMapping("/search-post")
    public String viewPost(Model model, @RequestParam("subject") String subject, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            List<PostsEntity> post = postsRepository.findBySubjectContainingOrderByCreatedAtDesc(subject.toLowerCase());
            model.addAttribute("posts", post);
            model.addAttribute("subject", subject);
            model.addAttribute("username", username);
            return "redirect:/search/" + subject;
        }
        return "redirect:/search/" + subject;
    }

    @PostMapping("/search/tag")
    public String searchBasedOnTag(Principal p, @RequestParam String tag, Model searchModel, @RequestParam(name = "page", defaultValue = "1") int page) {
        Pageable pageable = calculatePostPerPage(page);
        TagsEntity foundedTag = tagsRepository.findByTagNameContaining(tag);
        if (foundedTag != null) {
            Page<PostsEntity> postsPage = postsRepository.findByTags_TagNameContaining(tag, pageable);
            if (postsPage.isEmpty()) {
                searchModel.addAttribute("currentPage", 1);
                searchModel.addAttribute("totalPages", 1);
                searchModel.addAttribute("posts", postsPage);
                searchModel.addAttribute("noResultsMessage", "No matching posts found.");
            } else {
                searchModel.addAttribute("posts", postsPage);
                searchModel.addAttribute("subject", tag);
                searchModel.addAttribute("currentPage", page);
                searchModel.addAttribute("totalPages", postsPage.getTotalPages());
            }
            if (p != null) {
                searchModel.addAttribute("username", p.getName());
            }
            return "search";
        }
        return "redirect:/?tag=Not found#explore";
    }

    private Pageable calculatePostPerPage(int page) {
        int postPerPage = 9;
        return PageRequest.of(page - 1, postPerPage, Sort.by("CreatedAt").descending());
    }

    @PostMapping("/filter")
    public String getFilter(Model model, @RequestParam(name = "subject", required = false, defaultValue = "") String subject,
                            Principal principal,
                            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate) {


        List<PostsEntity> post = postsRepository.findByCreatedAt(startDate);
        System.out.println("post" + post);
        model.addAttribute("posts", post);
        model.addAttribute("subject", subject);
        model.addAttribute("formattedDate", startDate);
        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
        }


        return "redirect:/search/" + subject + "/filter" + "?startDate=" + startDate;
    }


}
