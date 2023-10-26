package com.tswny.init.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.util.StringUtils;
import com.tswny.init.domain.Person;
import com.tswny.init.domain.QPerson;
import com.tswny.init.domain.QWebsite;
import com.tswny.init.domain.Website;
import com.tswny.init.repository.WebsiteRepository;
import com.tswny.init.service.dto.PersonDTO;
import com.tswny.init.service.dto.WebsiteDTO;
import com.tswny.init.util.EncryptUtil;
import com.tswny.init.web.rest.vm.WebsiteVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.QuerydslWebConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class WebsiteService {
    private final Logger log = LoggerFactory.getLogger(WebsiteService.class);

    private final WebsiteRepository websiteRepository;

    public WebsiteService(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    public WebsiteDTO create(WebsiteVM websiteVM) {
        Website website = new Website();
        String url = websiteVM.getUrl();
        if (!StringUtils.isNullOrEmpty(url)) {
            website.setUrl(EncryptUtil.base64Encrypt(url));
        }
        website.setLoginName(websiteVM.getLoginName());
        website.setPassword(websiteVM.getPassword());
        website.setDescription(websiteVM.getDescription());
        website.setDirectory(websiteVM.getDirectory());
        website.setCreatedBy(websiteVM.getCreatedBy());
        website.setCreatedDate(Instant.now());
        website.setLastModifiedBy(websiteVM.getCreatedBy());
        website.setLastModifiedDate(Instant.now());

        Website webSite = websiteRepository.save(website);

        return new WebsiteDTO(webSite);
    }


    public WebsiteDTO update(WebsiteVM websiteVM) {
        Long id = websiteVM.getId();
        Optional<Website> webSiteOptional = websiteRepository.findById(id);
        if (!webSiteOptional.isPresent()) {
            log.error("can not find website by id: {}", id);
        }
        Website website = webSiteOptional.get();

        String url = websiteVM.getUrl();
        if (!StringUtils.isNullOrEmpty(url)) {
            website.setUrl(EncryptUtil.base64Encrypt(url));
        }

        website.setLoginName(websiteVM.getLoginName());
        website.setPassword(websiteVM.getPassword());
        website.setDescription(websiteVM.getDescription());
        website.setDirectory(websiteVM.getDirectory());
        website.setCreatedBy(websiteVM.getCreatedBy());
        website.setCreatedDate(websiteVM.getCreatedDate());
        website.setLastModifiedBy(websiteVM.getLastModifiedBy());
        website.setLastModifiedDate(websiteVM.getLastModifiedDate());
        Website webSite = websiteRepository.save(website);
        return new WebsiteDTO(webSite);
    }


    public WebsiteDTO findById(Long id) {
        Optional<Website> webSiteOptional = websiteRepository.findById(id);
        if (!webSiteOptional.isPresent()) {
            log.error("can not find website by id: {}", id);
        }
        Website website = webSiteOptional.get();
        return new WebsiteDTO(website);
    }


    public Page<WebsiteDTO> findAllByQueryParams(String keyword, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QWebsite qWebsite = QWebsite.website;
        if (!StringUtils.isNullOrEmpty(keyword)) {
            booleanBuilder.andAnyOf(qWebsite.url.like("%" + keyword + "%"),
                    qWebsite.directory.like("%" + keyword + "%"),
                    qWebsite.description.like("%" + keyword + "%"));
        }

        return websiteRepository.findAll(booleanBuilder, pageable).map(WebsiteDTO::new);
    }


    public void delete(Long id) {
        Optional<Website> websiteOptional = websiteRepository.findById(id);
        if (!websiteOptional.isPresent()) {
            log.warn("can not find website by id: {}", id);
            return;
        }
        websiteRepository.delete(websiteOptional.get());
    }

}
