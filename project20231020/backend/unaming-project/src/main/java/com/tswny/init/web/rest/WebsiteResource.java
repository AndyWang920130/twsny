package com.tswny.init.web.rest;


import com.tswny.init.service.PersonService;
import com.tswny.init.service.WebsiteService;
import com.tswny.init.service.dto.PersonDTO;
import com.tswny.init.service.dto.WebsiteDTO;
import com.tswny.init.web.rest.vm.PersonVM;
import com.tswny.init.web.rest.vm.WebsiteVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/websites")
public class WebsiteResource {
    private final Logger log = LoggerFactory.getLogger(WebsiteResource.class);

    private final WebsiteService websiteService;

    public WebsiteResource(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }


    @PostMapping
    public WebsiteDTO create(@RequestBody(required = true) WebsiteVM websiteVM) {
        return websiteService.create(websiteVM);
    }

    @PutMapping
    public WebsiteDTO update(@RequestBody(required = true) WebsiteVM websiteVM) {
        return websiteService.update(websiteVM);
    }

    @GetMapping("/{id}")
    public WebsiteDTO findById(@PathVariable Long id) {
        return websiteService.findById(id);
    }



    @GetMapping
    public Page<WebsiteDTO> getAll(@RequestParam(required = false) String keyword,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return websiteService.findAllByQueryParams(keyword, pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        websiteService.delete(id);
    }
}
