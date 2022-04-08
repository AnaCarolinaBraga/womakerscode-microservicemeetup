package com.womakerscode.microservicemeetup.service;

import com.womakerscode.microservicemeetup.model.entity.Registration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)  //esse é o jupiter, uma das nossas dependencias
@ActiveProfiles("test")  //vai identificar que é um profile de teste, para utilizar e rodar como um teste
public class RegistrationServiceTest {



    @BeforeEach    //essa anotação vai dizer que antes de cada teste, vamos fazer isso, rodar essa dependencia do service
    public void setUp(){
        // vamos inserir aqui a dependencia do service e dar um new na mesma
        //Como se fosse um @builder, mas aqui a gente precisa colocar o que queremos que aconteça e que rode
        //quando estivermos fazendo esses testes.
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
        Mockito.when(repository.existsByRegistration(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(registration)).thenReturn(createValidRegistration()); //quero que ele retorne um objeto criado de forma válida

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

}
