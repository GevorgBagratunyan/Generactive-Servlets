package com.generactive.security;

import com.generactive.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class GeneractiveUserDetails implements UserDetails {

    private User user;

    public GeneractiveUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //Extract authorities and add them into a set
        Set<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(a -> new SimpleGrantedAuthority(a.name()))
                .collect(Collectors.toSet());

        //Get role and add it into a set
        GrantedAuthority role = new SimpleGrantedAuthority(this.user.getRole().name());
        authorities.add(role);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
