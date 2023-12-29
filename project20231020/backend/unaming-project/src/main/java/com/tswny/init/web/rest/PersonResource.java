package com.tswny.init.web.rest;

import com.tswny.init.service.PersonService;
import com.tswny.init.service.dto.PersonDTO;
import com.tswny.init.web.rest.vm.PersonVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonResource {
    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }


    @PostMapping
    public PersonDTO create(@RequestBody(required = true) PersonVM personVM) {
        log.debug("REST request to getAll person");
        return personService.createPerson(personVM);
    }

    @PutMapping
    public PersonDTO update(@RequestBody(required = true) PersonVM personVM) {
        log.debug("REST request to getAll person");
        return personService.updatePerson(personVM);
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable Long id) {
        log.debug("REST request to getAll person");
        return personService.findById(id);
    }

    @GetMapping
    public Page<PersonDTO> getAll(@RequestParam(required = false) String keyword,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("REST request to getAll person");
        return personService.findAllByQueryParams(keyword, pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }
}
