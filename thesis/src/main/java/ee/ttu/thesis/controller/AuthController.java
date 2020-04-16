package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.ERole;
import ee.ttu.thesis.domain.Role;
import ee.ttu.thesis.domain.User;
import ee.ttu.thesis.repository.RoleRepository;
import ee.ttu.thesis.repository.UserRepository;
import ee.ttu.thesis.security.UserDetailsImpl;
import ee.ttu.thesis.security.UserDetailsServiceImpl;
import ee.ttu.thesis.security.jwt.JwtResponse;
import ee.ttu.thesis.security.jwt.JwtUtils;
import ee.ttu.thesis.security.jwt.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl i;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateLoggedInUser(@Valid @RequestBody User loggedInUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loggedInUser.getUsername(), loggedInUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId().toString(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity registerNewUser(@Valid @RequestBody User registerUser) {
        if (userRepository.existsByUsername(registerUser.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerUser.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User newUser = User.builder().
                username(registerUser.getUsername()).
                email(registerUser.getEmail()).
                password(encoder.encode(registerUser.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();
        Role newRole = Role.builder().name(ERole.USER).build();
        roles.add(newRole);

        newUser.setRoles(roles);
        roleRepository.save(newRole); //add roles before
        userRepository.save(newUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
