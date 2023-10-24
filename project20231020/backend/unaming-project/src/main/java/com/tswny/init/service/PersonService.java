package com.tswny.init.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.Person;
import com.tswny.init.domain.QPerson;
import com.tswny.init.repository.PersonRepository;
import com.tswny.init.service.dto.PersonDTO;
import com.tswny.init.web.rest.vm.PersonVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDTO createPerson(PersonVM personVM) {
        Person person = new Person();
        person.setFirstName(personVM.getFirstName());
        person.setLastName(personVM.getLastName());
        personRepository.save(person);
        return new PersonDTO(person);
    }

    public PersonDTO updatePerson(PersonVM personVM) {
        Long id = personVM.getId();
        if (id == null) {
            log.error("id is null");
            return null;
        }
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) {
            log.error("can not find person by id: {}", id);
            return null;
        }

        Person person = personOptional.get();
        person.setFirstName(personVM.getFirstName());
        person.setLastName(personVM.getLastName());

        personRepository.save(person);

        return new PersonDTO(person);
    }

    public PersonDTO findById(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) return null;
        Person person = personOptional.get();
        return  new PersonDTO(person);
    }

    public Page<PersonDTO> findAllByQueryParams(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QPerson qPerson = QPerson.person;
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qPerson.firstName.like("%" + keyword + "%"),
                    qPerson.lastName.like("%" + keyword + "%"));
        }

        return personRepository.findAll(booleanBuilder, pageable).map(PersonDTO::new);
    }

    public void delete(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) {
            log.warn("can not find person by id: {}", id);
            return;
        }
        personRepository.delete(personOptional.get());
    }
}
