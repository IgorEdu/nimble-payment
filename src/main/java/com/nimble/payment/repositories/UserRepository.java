package com.nimble.payment.repositories;

import com.nimble.payment.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM users u WHERE u.email = :emailOrCpf OR u.cpf = :emailOrCpf")
    User findByEmailOrCpf(@Param("emailOrCpf") String emailOrCpf);
}
