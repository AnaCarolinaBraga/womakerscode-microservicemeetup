package com.womakerscode.microservicemeetup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder    //Essas anotações criam os constructos, getters e setters
public class RegistrationDTO {     //Vamos acrescentar as regras que queremos que os atributos tenham

    //Não precisa dizer que não vai ser vazio porque cria sozinho
    private long id;

    @NotEmpty   //Vai dizer que ele não pode ser vazio
    private String name;

    @NotEmpty   //Vai dizer que ele não pode ser vazio
    private LocalDate dateOfregistration;

    @NotEmpty   //Vai dizer que ele não pode ser vazio
    private String registration;

}
