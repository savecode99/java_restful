package com.vung.restful.Service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService {

    private UserService userService;
    public UserDetailsCustom(UserService userService)
    {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        com.vung.restful.domain.Entity.User user = this.userService.getUerByEmail(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("sai username hoặc password");
        }

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER" )));
    }
    
}
