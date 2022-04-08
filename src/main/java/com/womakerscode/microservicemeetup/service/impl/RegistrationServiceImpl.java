package com.womakerscode.microservicemeetup.service.impl;

import com.womakerscode.microservicemeetup.exception.BusinessException;
import com.womakerscode.microservicemeetup.model.entity.Registration;
import com.womakerscode.microservicemeetup.repository.RegistrationRepository;
import com.womakerscode.microservicemeetup.service.RegistrationService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    RegistrationRepository repository;

    public RegistrationServiceImpl(RegistrationRepository repository) {
        this.repository = repository;
    }

    public Registration save(Registration registration) {
        if (repository.existsByRegistration(registration.getRegistration())){   //aqui ele valida se não é um registro duplicado
            throw new BusinessException("Registration already created");
        }

        return repository.save(registration);
    }
}
