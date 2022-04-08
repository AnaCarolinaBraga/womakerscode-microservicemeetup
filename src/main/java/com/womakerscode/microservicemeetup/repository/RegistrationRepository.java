package com.womakerscode.microservicemeetup.repository;

import com.womakerscode.microservicemeetup.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

    //Não precisa fazer o do save por causa do JPA

    boolean existsByRegistration(String registration);
}