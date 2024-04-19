package habib.Login.service;

import habib.Login.config.JWTProvider;
import habib.Login.model.User;
import habib.Login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTProvider jwtProvider;
    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> opt = userRepository.findById(userId);

        if (opt.isPresent()) {

            return opt.get();
        }

        throw new Exception("User not found!!! with ID:  "+ userId);
    }

    @Override
    public User findUserByJwt(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);

        if(email == null){
            throw new Exception("Provide a valid jwt Token.!!!");
        }

        User user =userRepository.findByEmail(email);

        if (user == null){
            throw new Exception("Usr not find with the email::  "+email);
        }

        return user;
    }
}
