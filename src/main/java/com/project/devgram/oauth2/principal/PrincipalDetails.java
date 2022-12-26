package com.project.devgram.oauth2.principal;

import com.project.devgram.entity.Users;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Data
@Slf4j // Oauth2 CLient 의존성 삭제
public class PrincipalDetails implements UserDetails {


    public PrincipalDetails(Users user) {
        this.users = user;
    }

    private Users users;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
        collect.add(()->{ return String.valueOf(users.getRole());});
        return collect;

    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
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
        return true;
    }
}
