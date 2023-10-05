package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.TagsEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class TagController {
    @Autowired
    TagsRepository tagsRepository;

    @PostMapping("/tag/add")
    public RedirectView addTag(Principal p, @RequestParam String tag){
        if (p !=null){
            TagsEntity newTag = new TagsEntity();
            newTag.setTagName(tag);
            tagsRepository.save(newTag);
            return new RedirectView("/profile/" +p.getName());
        }
        return new RedirectView("/login");
    }
}
