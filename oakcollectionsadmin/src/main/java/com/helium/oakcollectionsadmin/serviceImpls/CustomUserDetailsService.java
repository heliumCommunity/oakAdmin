package com.helium.oakcollectionsadmin.serviceImpls;

import com.helium.oakcollectionsadmin.entity.UserInfo;
import com.helium.oakcollectionsadmin.repository.UserInfoRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInfoRepo userRepo;

    public CustomUserDetailsService(UserInfoRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> isUserPresent = userRepo.findByEmail(email);
        if (isUserPresent.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        UserInfo user = isUserPresent.get();

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority()))
                        .toList()
        );
    }
}

