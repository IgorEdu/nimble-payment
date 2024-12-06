package com.nimble.payment.repositories;

import com.nimble.payment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    User findByCpf(String cpf);
}
