package com.womakerscode.microservicemeetup.service;

import com.womakerscode.microservicemeetup.model.entity.Meetup;
import com.womakerscode.microservicemeetup.model.entity.Registration;
import com.womakerscode.microservicemeetup.repository.MeetupRepository;
import com.womakerscode.microservicemeetup.service.impl.MeetupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class MeetupServiceTest {

    MeetupService meetupService;

    @MockBean
    MeetupRepository meetupRepository;

    @BeforeEach
    public void setUp(){
        this.meetupService = new MeetupServiceImpl(meetupRepository);
    }

    @Test
    @DisplayName("Should save an meetup")
    public void saveMeetup(){
        Meetup meetup = createValidMeetup();
        PageRequest pageRequest = PageRequest.of(0,10);

        List<Meetup> listMeetup = Arrays.asList(meetup);
        Page<Meetup> page = new PageImpl<Meetup>(Arrays.asList(meetup),
                PageRequest.of(0,10), 1);

        Mockito.when(meetupRepository.findByRegistrationOnMeetup(Mockito.anyString(),Mockito.anyString(),Mockito.any(PageRequest.class))).thenReturn(page);
        Mockito.when(meetupRepository.save(meetup)).thenReturn(createValidMeetup());

        Meetup savedMeetup = meetupService.save(meetup);

        assertThat(savedMeetup.getId()).isEqualTo(101);
        assertThat(savedMeetup.getEvent()).isEqualTo("Aniversario");
        assertThat(savedMeetup.getRegistration()).isEqualTo(createValidRegistration());
        assertThat(savedMeetup.getMeetupDate()).isEqualTo("10/10/2022");
        assertThat(savedMeetup.getRegistered()).isEqualTo(true);
    }


    @Test
    @DisplayName("Should get an meetup by Id")
    public void getByIdTest(){

        Integer id = 11;
        Meetup meetup = createValidMeetup();
        meetup.setId(id);
        Mockito.when(meetupRepository.findById(id)).thenReturn(Optional.of(meetup));

        Optional<Meetup> foundRegistration = meetupService.getById(id);

        assertThat(foundRegistration.isPresent()).isTrue();
        assertThat(foundRegistration.get().getId()).isEqualTo(id);
        assertThat(foundRegistration.get().getEvent()).isEqualTo(meetup.getEvent());
        assertThat(foundRegistration.get().getRegistration()).isEqualTo(meetup.getRegistration());
        assertThat(foundRegistration.get().getRegistered()).isEqualTo(meetup.getRegistered());
    }

    @Test
    @DisplayName("Should return empty when get an meetup by id that doesn't exist")
    public void meetupNotFoundByIdTest() {

        Integer id = 11;
        Mockito.when(meetupRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Meetup> meetup  = meetupService.getById(id);

        assertThat(meetup.isPresent()).isFalse();
    }

    private Meetup createValidMeetup(){
        return Meetup.builder()
                .id(101)
                .event("Aniversario")
                .registration(createValidRegistration())
                .meetupDate("10/10/2022")
                .registered(true)
                .build();
    }

    private Registration createValidRegistration() {
        return Registration.builder()
                .id(101)
                .name("Ana Carolina")
                .dateOfRegistration("01/04/2022")
                .registration("001")
                .build();
    }


}
