package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.RemarksRepository;
import com.laptechnos.groupface.service.RemarksService;
import com.laptechnos.groupface.service.dto.RemarksDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.Remarks}.
 */
@RestController
@RequestMapping("/api")
public class RemarksResource {

    private final Logger log = LoggerFactory.getLogger(RemarksResource.class);

    private static final String ENTITY_NAME = "remarks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemarksService remarksService;

    private final RemarksRepository remarksRepository;

    public RemarksResource(RemarksService remarksService, RemarksRepository remarksRepository) {
        this.remarksService = remarksService;
        this.remarksRepository = remarksRepository;
    }

    /**
     * {@code POST  /remarks} : Create a new remarks.
     *
     * @param remarksDTO the remarksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remarksDTO, or with status {@code 400 (Bad Request)} if the remarks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remarks")
    public ResponseEntity<RemarksDTO> createRemarks(@RequestBody RemarksDTO remarksDTO) throws URISyntaxException {
        log.debug("REST request to save Remarks : {}", remarksDTO);
        if (remarksDTO.getId() != null) {
            throw new BadRequestAlertException("A new remarks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RemarksDTO result = remarksService.save(remarksDTO);
        return ResponseEntity
            .created(new URI("/api/remarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remarks/:id} : Updates an existing remarks.
     *
     * @param id the id of the remarksDTO to save.
     * @param remarksDTO the remarksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remarksDTO,
     * or with status {@code 400 (Bad Request)} if the remarksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remarksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remarks/{id}")
    public ResponseEntity<RemarksDTO> updateRemarks(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RemarksDTO remarksDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Remarks : {}, {}", id, remarksDTO);
        if (remarksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remarksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remarksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RemarksDTO result = remarksService.update(remarksDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remarksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /remarks/:id} : Partial updates given fields of an existing remarks, field will ignore if it is null
     *
     * @param id the id of the remarksDTO to save.
     * @param remarksDTO the remarksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remarksDTO,
     * or with status {@code 400 (Bad Request)} if the remarksDTO is not valid,
     * or with status {@code 404 (Not Found)} if the remarksDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the remarksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/remarks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RemarksDTO> partialUpdateRemarks(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RemarksDTO remarksDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Remarks partially : {}, {}", id, remarksDTO);
        if (remarksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, remarksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!remarksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RemarksDTO> result = remarksService.partialUpdate(remarksDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, remarksDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /remarks} : get all the remarks.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remarks in body.
     */
    @GetMapping("/remarks")
    public ResponseEntity<List<RemarksDTO>> getAllRemarks(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Remarks");
        Page<RemarksDTO> page;
        if (eagerload) {
            page = remarksService.findAllWithEagerRelationships(pageable);
        } else {
            page = remarksService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /remarks/:id} : get the "id" remarks.
     *
     * @param id the id of the remarksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remarksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remarks/{id}")
    public ResponseEntity<RemarksDTO> getRemarks(@PathVariable Long id) {
        log.debug("REST request to get Remarks : {}", id);
        Optional<RemarksDTO> remarksDTO = remarksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(remarksDTO);
    }

    /**
     * {@code DELETE  /remarks/:id} : delete the "id" remarks.
     *
     * @param id the id of the remarksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remarks/{id}")
    public ResponseEntity<Void> deleteRemarks(@PathVariable Long id) {
        log.debug("REST request to delete Remarks : {}", id);
        remarksService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/remarks?query=:query} : search for the remarks corresponding
     * to the query.
     *
     * @param query the query of the remarks search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/remarks")
    public ResponseEntity<List<RemarksDTO>> searchRemarks(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of Remarks for query {}", query);
        Page<RemarksDTO> page = remarksService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
