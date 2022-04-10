package com.womakerscode.microservicemeetup.controller;


import com.womakerscode.microservicemeetup.model.RegistrationDTO;
import com.womakerscode.microservicemeetup.model.entity.Registration;
import com.womakerscode.microservicemeetup.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private RegistrationService registrationService;
    private ModelMapper modelMapper;


    public RegistrationController(RegistrationService registrationService, ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //aqui é o status que quer que retorne com esse método
    public RegistrationDTO create(@RequestBody @Valid RegistrationDTO dto){
        Registration entity = modelMapper.map(dto, Registration.class);
        entity = registrationService.save(entity);

        return modelMapper.map(entity, RegistrationDTO.class);
    }


}
