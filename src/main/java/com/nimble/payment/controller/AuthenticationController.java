package com.nimble.payment.controller;

import com.nimble.payment.domain.AuthenticationDTO;
import com.nimble.payment.domain.LoginResponseDTO;
import com.nimble.payment.domain.RegisterDTO;
import com.nimble.payment.domain.User;
import com.nimble.payment.infra.security.TokenService;
import com.nimble.payment.repositories.UserRepository;
import com.nimble.payment.utils.ValidateCpf;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

       return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody @Valid RegisterDTO data) {
        String replacedCpf = data.cpf().replaceAll("[^0-9]", "");
        if(this.userRepository.findByEmailOrCpf(replacedCpf) != null || this.userRepository.findByEmailOrCpf(data.email()) != null)
            return ResponseEntity.badRequest().build();

        if(!ValidateCpf.isCPF(replacedCpf))
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.name(), replacedCpf, data.email(), encryptedPassword);

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
