package com.freelancerSeeker.freelancerSeeker.config;

import com.freelancerSeeker.freelancerSeeker.Models.FreeLancer;
import com.freelancerSeeker.freelancerSeeker.Repository.FreeLancerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FreeLancerUserDetailsService implements UserDetailsService {
    @Autowired
    FreeLancerRepo freeLancerRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FreeLancer user = freeLancerRepo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("user" + username + "is not found");
        return user;
    }
}
