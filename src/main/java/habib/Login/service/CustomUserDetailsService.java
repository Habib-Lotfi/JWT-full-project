package habib.Login.service;

import habib.Login.model.User;
import habib.Login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Username being searched: " + username);
        User user = userRepository.findByEmail(username);
        System.out.println("User found: " + user);

        if (user == null) {

            throw new UsernameNotFoundException("User Not Found...!!!" + username);

        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
