package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.User;
import ee.ttu.thesis.repository.UserRepository;
import ee.ttu.thesis.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final JwtUtils jwtUtils;

    public Optional<User> getUserByUsername(String username){
        return repository.findByUsername(username);
    }

    public Long getUserIdFromToken(String token){
        Optional<User> u = getUserByUsername(jwtUtils.getUserNameFromJwtToken(token));
        return u.map( user -> user.getId()).orElse( null);
    }

    public User getUserFromToken(String token){
        Optional<User> u = getUserByUsername(jwtUtils.getUserNameFromJwtToken(token));
        return u.map(user -> user).orElse(null);
    }
}
