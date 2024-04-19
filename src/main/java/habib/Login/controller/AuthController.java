package habib.Login.controller;

import habib.Login.config.JWTProvider;
import habib.Login.model.User;
import habib.Login.repository.UserRepository;
import habib.Login.request.LoginRequest;
import habib.Login.response.AuthResponse;
import habib.Login.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception {

        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();

        User isExistEamil = userRepository.findByEmail(email);

        if (isExistEamil != null) {
            throw new Exception("Email is already exist!!!");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();

        response.setJwt(token);
        response.setMessage("Signup Success..!!!");


        return response;

    }

    @PostMapping("/signing")
    public AuthResponse signupHandler(@RequestBody LoginRequest loginRequest) {

        String userEmail = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(userEmail, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        assert authentication != null;
        String token = jwtProvider.generateToken(authentication);


        AuthResponse response = new AuthResponse();

        response.setJwt(token);
        response.setMessage("Signing Success..!!!");

        return response;
    }

    private Authentication authenticate(String userEmail, String password) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

        if (userDetails == null) {

            throw new BadCredentialsException("User not found with email::: " + userEmail);
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {

            throw new BadCredentialsException("Invalid Password..!!! ");

        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
