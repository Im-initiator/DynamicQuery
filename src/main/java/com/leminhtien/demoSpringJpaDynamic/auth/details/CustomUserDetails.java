package com.leminhtien.demoSpringJpaDynamic.auth.details;

import com.leminhtien.demoSpringJpaDynamic.entity.AccountEntity;
import com.leminhtien.demoSpringJpaDynamic.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private AccountEntity account;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getRoles().stream().map(role-> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !(account.getStatus()== UserStatus.EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(account.getStatus()==UserStatus.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getStatus()==UserStatus.ACTIVE;
    }
}
