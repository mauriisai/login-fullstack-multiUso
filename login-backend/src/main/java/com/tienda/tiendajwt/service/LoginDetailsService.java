package com.tienda.tiendajwt.service;

import com.tienda.tiendajwt.dao.UserDao;
import com.tienda.tiendajwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class LoginDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();

        if( user != null ) {
            return new org.springframework.security.core.userdetails.User( user.getUserName(),
                    user.getUserPassword(), getAuthorities(user) );
        } else {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username is not valid");
        }
    }


    private Set getAuthorities(User user) {
        Set authorities = new HashSet();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return authorities;
    }
}
