package com.womakerscode.microservicemeetup.service.impl;

import com.womakerscode.microservicemeetup.exception.BusinessException;
import com.womakerscode.microservicemeetup.model.entity.Registration;
import com.womakerscode.microservicemeetup.repository.RegistrationRepository;
import com.womakerscode.microservicemeetup.service.RegistrationService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    RegistrationRepository repository;

    public RegistrationServiceImpl(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Registration save(Registration registration) {
        if (repository.existsByRegistration(registration.getRegistration())) {   //aqui ele valida se não é um registro duplicado
            throw new BusinessException("Registration already created");
        }

        return repository.save(registration);
    }

    @Override
    public Optional<Registration> getRegistrationById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Registration registration) {
        if (registration == null || registration.getId() == null) {
            throw new IllegalArgumentException("Registration id cannot be null");
        }
        this.repository.delete(registration); //esse delete já é o método que o JPA trás

    }

    @Override
    public Registration update(Registration registration) {
        if (registration == null || registration.getId() == null) {
            throw new IllegalArgumentException("Registration id cannot be null");
        }
        return this.repository.save(registration);
    }

    @Override
    public Page<Registration> find(Registration filter, Pageable pageRequest) {
        Example<Registration> example = Example.of(filter, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return repository.findAll(example, pageRequest);
    }


    @Override
    public Optional<Registration> getRegistrationByRegistrationAttribute(String registrationAttribute) {
        return repository.findByRegistration(registrationAttribute);
    }
}
