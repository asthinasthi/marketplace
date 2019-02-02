package org.anirudh.marketplace.service;

import org.anirudh.marketplace.dao.UserDao;
import org.anirudh.marketplace.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Component
public class IntuitUserDetailsService implements UserDetailsService {
    private EntityManagerFactory entityManagerFactory;

    public IntuitUserDetailsService() {
        entityManagerFactory = Persistence.createEntityManagerFactory("marketplacePersistence");
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDao dao = new UserDao(entityManagerFactory);
        User user = dao.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(), authorities);

        return userDetails;
    }
}
