package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.OrgDetailsRepository;
import com.laptechnos.groupface.service.OrgDetailsService;
import com.laptechnos.groupface.service.dto.OrgDetailsDTO;
import com.laptechnos.groupface.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.laptechnos.groupface.domain.OrgDetails}.
 */
@RestController
@RequestMapping("/api")
public class OrgDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrgDetailsResource.class);

    private static final String ENTITY_NAME = "orgDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrgDetailsService orgDetailsService;

    private final OrgDetailsRepository orgDetailsRepository;

    public OrgDetailsResource(OrgDetailsService orgDetailsService, OrgDetailsRepository orgDetailsRepository) {
        this.orgDetailsService = orgDetailsService;
        this.orgDetailsRepository = orgDetailsRepository;
    }

    /**
     * {@code POST  /org-details} : Create a new orgDetails.
     *
     * @param orgDetailsDTO the orgDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orgDetailsDTO, or with status {@code 400 (Bad Request)} if the orgDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/org-details")
    public ResponseEntity<OrgDetailsDTO> createOrgDetails(@RequestBody OrgDetailsDTO orgDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save OrgDetails : {}", orgDetailsDTO);
        if (orgDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new orgDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrgDetailsDTO result = orgDetailsService.save(orgDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/org-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /org-details/:id} : Updates an existing orgDetails.
     *
     * @param id the id of the orgDetailsDTO to save.
     * @param orgDetailsDTO the orgDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orgDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orgDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/org-details/{id}")
    public ResponseEntity<OrgDetailsDTO> updateOrgDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrgDetailsDTO orgDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrgDetails : {}, {}", id, orgDetailsDTO);
        if (orgDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrgDetailsDTO result = orgDetailsService.update(orgDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /org-details/:id} : Partial updates given fields of an existing orgDetails, field will ignore if it is null
     *
     * @param id the id of the orgDetailsDTO to save.
     * @param orgDetailsDTO the orgDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orgDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orgDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orgDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orgDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/org-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrgDetailsDTO> partialUpdateOrgDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrgDetailsDTO orgDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrgDetails partially : {}, {}", id, orgDetailsDTO);
        if (orgDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orgDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orgDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrgDetailsDTO> result = orgDetailsService.partialUpdate(orgDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orgDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /org-details} : get all the orgDetails.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orgDetails in body.
     */
    @GetMapping("/org-details")
    public ResponseEntity<List<OrgDetailsDTO>> getAllOrgDetails(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of OrgDetails");
        Page<OrgDetailsDTO> page;
        if (eagerload) {
            page = orgDetailsService.findAllWithEagerRelationships(pageable);
        } else {
            page = orgDetailsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /org-details/:id} : get the "id" orgDetails.
     *
     * @param id the id of the orgDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orgDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/org-details/{id}")
    public ResponseEntity<OrgDetailsDTO> getOrgDetails(@PathVariable Long id) {
        log.debug("REST request to get OrgDetails : {}", id);
        Optional<OrgDetailsDTO> orgDetailsDTO = orgDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orgDetailsDTO);
    }

    /**
     * {@code DELETE  /org-details/:id} : delete the "id" orgDetails.
     *
     * @param id the id of the orgDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/org-details/{id}")
    public ResponseEntity<Void> deleteOrgDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrgDetails : {}", id);
        orgDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/org-details?query=:query} : search for the orgDetails corresponding
     * to the query.
     *
     * @param query the query of the orgDetails search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/org-details")
    public ResponseEntity<List<OrgDetailsDTO>> searchOrgDetails(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of OrgDetails for query {}", query);
        Page<OrgDetailsDTO> page = orgDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
