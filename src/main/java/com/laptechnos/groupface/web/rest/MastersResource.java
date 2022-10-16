package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.MastersRepository;
import com.laptechnos.groupface.service.MastersService;
import com.laptechnos.groupface.service.dto.MastersDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.Masters}.
 */
@RestController
@RequestMapping("/api")
public class MastersResource {

    private final Logger log = LoggerFactory.getLogger(MastersResource.class);

    private static final String ENTITY_NAME = "masters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MastersService mastersService;

    private final MastersRepository mastersRepository;

    public MastersResource(MastersService mastersService, MastersRepository mastersRepository) {
        this.mastersService = mastersService;
        this.mastersRepository = mastersRepository;
    }

    /**
     * {@code POST  /masters} : Create a new masters.
     *
     * @param mastersDTO the mastersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mastersDTO, or with status {@code 400 (Bad Request)} if the masters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/masters")
    public ResponseEntity<MastersDTO> createMasters(@RequestBody MastersDTO mastersDTO) throws URISyntaxException {
        log.debug("REST request to save Masters : {}", mastersDTO);
        if (mastersDTO.getId() != null) {
            throw new BadRequestAlertException("A new masters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MastersDTO result = mastersService.save(mastersDTO);
        return ResponseEntity
            .created(new URI("/api/masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /masters/:id} : Updates an existing masters.
     *
     * @param id the id of the mastersDTO to save.
     * @param mastersDTO the mastersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mastersDTO,
     * or with status {@code 400 (Bad Request)} if the mastersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mastersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/masters/{id}")
    public ResponseEntity<MastersDTO> updateMasters(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MastersDTO mastersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Masters : {}, {}", id, mastersDTO);
        if (mastersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mastersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mastersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MastersDTO result = mastersService.update(mastersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mastersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /masters/:id} : Partial updates given fields of an existing masters, field will ignore if it is null
     *
     * @param id the id of the mastersDTO to save.
     * @param mastersDTO the mastersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mastersDTO,
     * or with status {@code 400 (Bad Request)} if the mastersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mastersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mastersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MastersDTO> partialUpdateMasters(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MastersDTO mastersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Masters partially : {}, {}", id, mastersDTO);
        if (mastersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mastersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mastersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MastersDTO> result = mastersService.partialUpdate(mastersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mastersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /masters} : get all the masters.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of masters in body.
     */
    @GetMapping("/masters")
    public ResponseEntity<List<MastersDTO>> getAllMasters(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Masters");
        Page<MastersDTO> page;
        if (eagerload) {
            page = mastersService.findAllWithEagerRelationships(pageable);
        } else {
            page = mastersService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /masters/:id} : get the "id" masters.
     *
     * @param id the id of the mastersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mastersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/masters/{id}")
    public ResponseEntity<MastersDTO> getMasters(@PathVariable Long id) {
        log.debug("REST request to get Masters : {}", id);
        Optional<MastersDTO> mastersDTO = mastersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mastersDTO);
    }

    /**
     * {@code DELETE  /masters/:id} : delete the "id" masters.
     *
     * @param id the id of the mastersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/masters/{id}")
    public ResponseEntity<Void> deleteMasters(@PathVariable Long id) {
        log.debug("REST request to delete Masters : {}", id);
        mastersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/masters?query=:query} : search for the masters corresponding
     * to the query.
     *
     * @param query the query of the masters search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/masters")
    public ResponseEntity<List<MastersDTO>> searchMasters(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of Masters for query {}", query);
        Page<MastersDTO> page = mastersService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
