package com.freelancerSeeker.freelancerSeeker.config;

import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSiteDetailsService implements UserDetailsService {
    @Autowired
    UserSiteRepository userSiteRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSiteEntity user = userSiteRepo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("user" + username + "is not found");
        return user;
    }



}
