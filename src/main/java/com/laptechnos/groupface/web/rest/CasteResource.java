package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.CasteRepository;
import com.laptechnos.groupface.service.CasteService;
import com.laptechnos.groupface.service.dto.CasteDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.Caste}.
 */
@RestController
@RequestMapping("/api")
public class CasteResource {

    private final Logger log = LoggerFactory.getLogger(CasteResource.class);

    private static final String ENTITY_NAME = "caste";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CasteService casteService;

    private final CasteRepository casteRepository;

    public CasteResource(CasteService casteService, CasteRepository casteRepository) {
        this.casteService = casteService;
        this.casteRepository = casteRepository;
    }

    /**
     * {@code POST  /castes} : Create a new caste.
     *
     * @param casteDTO the casteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new casteDTO, or with status {@code 400 (Bad Request)} if the caste has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/castes")
    public ResponseEntity<CasteDTO> createCaste(@RequestBody CasteDTO casteDTO) throws URISyntaxException {
        log.debug("REST request to save Caste : {}", casteDTO);
        if (casteDTO.getId() != null) {
            throw new BadRequestAlertException("A new caste cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CasteDTO result = casteService.save(casteDTO);
        return ResponseEntity
            .created(new URI("/api/castes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /castes/:id} : Updates an existing caste.
     *
     * @param id the id of the casteDTO to save.
     * @param casteDTO the casteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated casteDTO,
     * or with status {@code 400 (Bad Request)} if the casteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the casteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/castes/{id}")
    public ResponseEntity<CasteDTO> updateCaste(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CasteDTO casteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Caste : {}, {}", id, casteDTO);
        if (casteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, casteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!casteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CasteDTO result = casteService.update(casteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, casteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /castes/:id} : Partial updates given fields of an existing caste, field will ignore if it is null
     *
     * @param id the id of the casteDTO to save.
     * @param casteDTO the casteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated casteDTO,
     * or with status {@code 400 (Bad Request)} if the casteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the casteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the casteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/castes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CasteDTO> partialUpdateCaste(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CasteDTO casteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Caste partially : {}, {}", id, casteDTO);
        if (casteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, casteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!casteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CasteDTO> result = casteService.partialUpdate(casteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, casteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /castes} : get all the castes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of castes in body.
     */
    @GetMapping("/castes")
    public ResponseEntity<List<CasteDTO>> getAllCastes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Castes");
        Page<CasteDTO> page;
        if (eagerload) {
            page = casteService.findAllWithEagerRelationships(pageable);
        } else {
            page = casteService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /castes/:id} : get the "id" caste.
     *
     * @param id the id of the casteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the casteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/castes/{id}")
    public ResponseEntity<CasteDTO> getCaste(@PathVariable Long id) {
        log.debug("REST request to get Caste : {}", id);
        Optional<CasteDTO> casteDTO = casteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(casteDTO);
    }

    /**
     * {@code DELETE  /castes/:id} : delete the "id" caste.
     *
     * @param id the id of the casteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/castes/{id}")
    public ResponseEntity<Void> deleteCaste(@PathVariable Long id) {
        log.debug("REST request to delete Caste : {}", id);
        casteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/castes?query=:query} : search for the caste corresponding
     * to the query.
     *
     * @param query the query of the caste search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/castes")
    public ResponseEntity<List<CasteDTO>> searchCastes(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of Castes for query {}", query);
        Page<CasteDTO> page = casteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
