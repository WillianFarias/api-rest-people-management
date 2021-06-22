package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static one.digitalinnovation.personapi.utils.PersonUtils.createFakeDTO;
import static one.digitalinnovation.personapi.utils.PersonUtils.createFakeEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void testGivenPersonDTOThenReturnSavedMessage() {
        PersonDTO personDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();

        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);

        MessageResponseDTO successMessage = personService.createPerson(personDTO);

        assertEquals("Create person with ID 1", successMessage.getMessage());
    }

    @Test
    void testGivenValidPersonIdThenReturnThisPerson() throws PersonNotFoundException {
        PersonDTO expectedPersonDTO = createFakeDTO();
        Person expectedSavedPerson = createFakeEntity();
        expectedPersonDTO.setId(expectedSavedPerson.getId());

        when(personRepository.findById(expectedSavedPerson.getId())).thenReturn(Optional.of(expectedSavedPerson));

        PersonDTO personDTO = personService.findById(expectedSavedPerson.getId());

        assertEquals(expectedSavedPerson.getId(), personDTO.getId());
        assertEquals(expectedSavedPerson.getFirstName(), personDTO.getFirstName());
    }

    @Test
    void testGivenInvalidPersonIdThenThrowException() {
        var invalidPersonId = 1L;
        when(personRepository.findById(invalidPersonId))
                .thenReturn(Optional.ofNullable(any(Person.class)));

        assertThrows(PersonNotFoundException.class, () -> personService.findById(invalidPersonId));
    }

    @Test
    void testGivenNoDataThenReturnAllPeopleRegistered() {
        List<Person> expectedRegisteredPeople = Collections.singletonList(createFakeEntity());
        PersonDTO personDTO = createFakeDTO();
        personDTO.setId(expectedRegisteredPeople.get(0).getId());

        when(personRepository.findAll()).thenReturn(expectedRegisteredPeople);

        List<PersonDTO> expectedPeopleDTOList = personService.listAll();

        assertFalse(expectedPeopleDTOList.isEmpty());
        assertEquals(expectedPeopleDTOList.get(0).getId(), personDTO.getId());
    }

    @Test
    void testGivenValidPersonIdAndUpdateInfoThenReturnSuccessOnUpdate() throws PersonNotFoundException {
        var updatePersonId = 2L;

        PersonDTO updatePersonDTORequest = createFakeDTO();
        updatePersonDTORequest.setId(updatePersonId);
        updatePersonDTORequest.setLastName("Paleias updated");

        Person expectedPersonToUpdate = createFakeEntity();
        expectedPersonToUpdate.setId(updatePersonId);

        Person expectedPersonUpdated = createFakeEntity();
        expectedPersonUpdated.setId(updatePersonId);
        expectedPersonToUpdate.setLastName(updatePersonDTORequest.getLastName());

        when(personRepository.findById(updatePersonId)).thenReturn(Optional.of(expectedPersonUpdated));
        when(personMapper.toModel(updatePersonDTORequest)).thenReturn(expectedPersonUpdated);

        MessageResponseDTO successMessage = personService.updateById(updatePersonId, updatePersonDTORequest);

        assertEquals("Update person with ID 2", successMessage.getMessage());
    }
}
