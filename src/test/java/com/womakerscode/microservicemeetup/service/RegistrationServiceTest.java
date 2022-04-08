package com.womakerscode.microservicemeetup.service;

import com.womakerscode.microservicemeetup.exception.BusinessException;
import com.womakerscode.microservicemeetup.model.entity.Registration;
import com.womakerscode.microservicemeetup.repository.RegistrationRepository;
import com.womakerscode.microservicemeetup.service.impl.RegistrationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)  //esse é o jupiter, uma das nossas dependencias
@ActiveProfiles("test")  //vai identificar que é um profile de teste, para utilizar e rodar como um teste
public class RegistrationServiceTest {

    RegistrationService registrationService;

    @MockBean
    RegistrationRepository repository;




    @BeforeEach    //essa anotação vai dizer que antes de cada teste, vamos fazer isso, rodar essa dependencia do service
    public void setUp(){
        // vamos inserir aqui a dependencia do service e dar um new na mesma
        //Como se fosse um @builder, mas aqui a gente precisa colocar o que queremos que aconteça e que rode
        //quando estivermos fazendo esses testes.
        this.registrationService = new RegistrationServiceImpl(repository);
    }

    @Test
    @DisplayName("Should save an registration")
    public void saveStudent(){

        //cenário, o que é necessario fazer para o teste funcionar
        //Aqui a gente mocka um objeto
        Registration registration = createValidRegistration();

        //execução, aqui a gente simula o que ta dentro do serviço/controller
        //Nesse caso, vamos simular o ato de salvar um dado. para isso, precisamos chamar o repository
        //O when vai chamar o repository e o método, o retorno do método tem que ser uma string qualquer
        //Essa primeira parte ta mockando comportamento, ele nao ta validando retorno
        Mockito.when(repository.existsByRegistration(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(registration)).thenReturn(createValidRegistration()); //quero que ele retorne um objeto criado de forma válida

        //esse é para validar o retorno
        Registration savedRegistration = registrationService.save(registration);

        //assert, estamos garantindo que o retorno seja o que a gente espera
        assertThat(savedRegistration.getId()).isEqualTo(101);   //o isEqualTo tem que retornar o que ta dentro do mock
        assertThat(savedRegistration.getName()).isEqualTo("Ana Carolina");   //o isEqualTo tem que retornar o que ta dentro do mock
        assertThat(savedRegistration.getDateOfregistration()).isEqualTo(LocalDate.now());   //o isEqualTo tem que retornar o que ta dentro do mock
        assertThat(savedRegistration.getRegistration()).isEqualTo("001");   //o isEqualTo tem que retornar o que ta dentro do mock

    }

    private Registration createValidRegistration() {
        return Registration.builder()
                .id(101)
                .name("Ana Carolina")
                .dateOfregistration(LocalDate.now())
                .registration("001") //aqui vamos supor que seja quantidade de inserções de objetos na tabela.
                                    //como é a "versão 1" dessa pessoa, fica 001. Se atualizasse algo, mudaria esse numero
                .build();  //Ele construiu esse objeto
    }

    @Test
    @DisplayName("Should throw business error when try to save a new registration when this registration already exist")
    public void shouldNotSaveAsRegistrationDuplicated(){

        //cenario
        Registration registration = createValidRegistration();
        Mockito.when(repository.existsByRegistration(Mockito.any())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable( () -> registrationService.save(registration));   //não é possível mockar exceções, então se faz dessa forma
        assertThat(exception)
                .isInstanceOf(BusinessException.class)  //quer garantir que a exceção é uma exceção de uma instância
                .hasMessage("Registration already created");

        Mockito.verify(repository, Mockito.never()).save(registration); //aqui é para verificar que nunca vai salvar caso isso aconteça
    }

    @Test
    @DisplayName("Should get an registration by Id")
    public void getByRegistrationIdTest(){

        // cenario
        Integer id = 11;
        Registration registration = createValidRegistration();
        registration.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(registration));

        // execucao
        Optional<Registration> foundRegistration = registrationService.getRegistrationById(id);

        assertThat(foundRegistration.isPresent()).isTrue();
        assertThat(foundRegistration.get().getId()).isEqualTo(id);
        assertThat(foundRegistration.get().getName()).isEqualTo(registration.getName());
        assertThat(foundRegistration.get().getDateOfRegistration()).isEqualTo(registration.getDateOfRegistration());
        assertThat(foundRegistration.get().getRegistration()).isEqualTo(registration.getRegistration())
    }

}
