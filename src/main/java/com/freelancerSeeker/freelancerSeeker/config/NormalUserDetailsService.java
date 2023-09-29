package com.freelancerSeeker.freelancerSeeker.config;

import com.freelancerSeeker.freelancerSeeker.Models.NormalUser;
import com.freelancerSeeker.freelancerSeeker.Repository.NormalUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NormalUserDetailsService implements UserDetailsService {
    @Autowired
    NormalUserRepo normalUserRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        NormalUser user = normalUserRepo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("user" + username + "is not found");
        return user;
    }
}
