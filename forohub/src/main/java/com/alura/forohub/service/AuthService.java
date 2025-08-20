
package com.alura.forohub.service;

import com.alura.forohub.domain.user.AppUser;
import com.alura.forohub.domain.user.Role;
import com.alura.forohub.dto.auth.LoginRequest;
import com.alura.forohub.dto.auth.RegisterRequest;
import com.alura.forohub.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository users,
                       PasswordEncoder encoder,
                       JwtService jwt,
                       AuthenticationManager authManager) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
        this.authManager = authManager;
    }

    public String register(RegisterRequest req) {
        // Si el email ya existe -> 409 Conflict con mensaje claro
        if (users.findByEmail(req.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email ya registrado");
        }

        AppUser u = new AppUser(req.email(), encoder.encode(req.password()), Role.USER);
        users.save(u);
        return jwt.generateToken(u.getEmail(), u.getRole().name());
    }

    public String login(LoginRequest req) {
        // Autentica credenciales y genera el token
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        AppUser u = users.findByEmail(req.email()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas")
        );
        return jwt.generateToken(u.getEmail(), u.getRole().name());
    }
}
