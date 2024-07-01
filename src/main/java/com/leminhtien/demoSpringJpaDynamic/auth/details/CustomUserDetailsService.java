package com.leminhtien.demoSpringJpaDynamic.auth.details;

import com.leminhtien.demoSpringJpaDynamic.entity.AccountEntity;
import com.leminhtien.demoSpringJpaDynamic.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity account = accountRepository.findOneByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User name not found")
        );
        return new CustomUserDetails(account);
    }
}
