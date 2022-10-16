package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.AutoPostAprvlUsrsRepository;
import com.laptechnos.groupface.service.AutoPostAprvlUsrsService;
import com.laptechnos.groupface.service.dto.AutoPostAprvlUsrsDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.AutoPostAprvlUsrs}.
 */
@RestController
@RequestMapping("/api")
public class AutoPostAprvlUsrsResource {

    private final Logger log = LoggerFactory.getLogger(AutoPostAprvlUsrsResource.class);

    private static final String ENTITY_NAME = "autoPostAprvlUsrs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutoPostAprvlUsrsService autoPostAprvlUsrsService;

    private final AutoPostAprvlUsrsRepository autoPostAprvlUsrsRepository;

    public AutoPostAprvlUsrsResource(
        AutoPostAprvlUsrsService autoPostAprvlUsrsService,
        AutoPostAprvlUsrsRepository autoPostAprvlUsrsRepository
    ) {
        this.autoPostAprvlUsrsService = autoPostAprvlUsrsService;
        this.autoPostAprvlUsrsRepository = autoPostAprvlUsrsRepository;
    }

    /**
     * {@code POST  /auto-post-aprvl-usrs} : Create a new autoPostAprvlUsrs.
     *
     * @param autoPostAprvlUsrsDTO the autoPostAprvlUsrsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autoPostAprvlUsrsDTO, or with status {@code 400 (Bad Request)} if the autoPostAprvlUsrs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auto-post-aprvl-usrs")
    public ResponseEntity<AutoPostAprvlUsrsDTO> createAutoPostAprvlUsrs(@RequestBody AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO)
        throws URISyntaxException {
        log.debug("REST request to save AutoPostAprvlUsrs : {}", autoPostAprvlUsrsDTO);
        if (autoPostAprvlUsrsDTO.getId() != null) {
            throw new BadRequestAlertException("A new autoPostAprvlUsrs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutoPostAprvlUsrsDTO result = autoPostAprvlUsrsService.save(autoPostAprvlUsrsDTO);
        return ResponseEntity
            .created(new URI("/api/auto-post-aprvl-usrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auto-post-aprvl-usrs/:id} : Updates an existing autoPostAprvlUsrs.
     *
     * @param id the id of the autoPostAprvlUsrsDTO to save.
     * @param autoPostAprvlUsrsDTO the autoPostAprvlUsrsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoPostAprvlUsrsDTO,
     * or with status {@code 400 (Bad Request)} if the autoPostAprvlUsrsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autoPostAprvlUsrsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auto-post-aprvl-usrs/{id}")
    public ResponseEntity<AutoPostAprvlUsrsDTO> updateAutoPostAprvlUsrs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AutoPostAprvlUsrs : {}, {}", id, autoPostAprvlUsrsDTO);
        if (autoPostAprvlUsrsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoPostAprvlUsrsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoPostAprvlUsrsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AutoPostAprvlUsrsDTO result = autoPostAprvlUsrsService.update(autoPostAprvlUsrsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autoPostAprvlUsrsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /auto-post-aprvl-usrs/:id} : Partial updates given fields of an existing autoPostAprvlUsrs, field will ignore if it is null
     *
     * @param id the id of the autoPostAprvlUsrsDTO to save.
     * @param autoPostAprvlUsrsDTO the autoPostAprvlUsrsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoPostAprvlUsrsDTO,
     * or with status {@code 400 (Bad Request)} if the autoPostAprvlUsrsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the autoPostAprvlUsrsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the autoPostAprvlUsrsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/auto-post-aprvl-usrs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AutoPostAprvlUsrsDTO> partialUpdateAutoPostAprvlUsrs(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AutoPostAprvlUsrs partially : {}, {}", id, autoPostAprvlUsrsDTO);
        if (autoPostAprvlUsrsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoPostAprvlUsrsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoPostAprvlUsrsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AutoPostAprvlUsrsDTO> result = autoPostAprvlUsrsService.partialUpdate(autoPostAprvlUsrsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autoPostAprvlUsrsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /auto-post-aprvl-usrs} : get all the autoPostAprvlUsrs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autoPostAprvlUsrs in body.
     */
    @GetMapping("/auto-post-aprvl-usrs")
    public ResponseEntity<List<AutoPostAprvlUsrsDTO>> getAllAutoPostAprvlUsrs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of AutoPostAprvlUsrs");
        Page<AutoPostAprvlUsrsDTO> page;
        if (eagerload) {
            page = autoPostAprvlUsrsService.findAllWithEagerRelationships(pageable);
        } else {
            page = autoPostAprvlUsrsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /auto-post-aprvl-usrs/:id} : get the "id" autoPostAprvlUsrs.
     *
     * @param id the id of the autoPostAprvlUsrsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autoPostAprvlUsrsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auto-post-aprvl-usrs/{id}")
    public ResponseEntity<AutoPostAprvlUsrsDTO> getAutoPostAprvlUsrs(@PathVariable Long id) {
        log.debug("REST request to get AutoPostAprvlUsrs : {}", id);
        Optional<AutoPostAprvlUsrsDTO> autoPostAprvlUsrsDTO = autoPostAprvlUsrsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autoPostAprvlUsrsDTO);
    }

    /**
     * {@code DELETE  /auto-post-aprvl-usrs/:id} : delete the "id" autoPostAprvlUsrs.
     *
     * @param id the id of the autoPostAprvlUsrsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auto-post-aprvl-usrs/{id}")
    public ResponseEntity<Void> deleteAutoPostAprvlUsrs(@PathVariable Long id) {
        log.debug("REST request to delete AutoPostAprvlUsrs : {}", id);
        autoPostAprvlUsrsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/auto-post-aprvl-usrs?query=:query} : search for the autoPostAprvlUsrs corresponding
     * to the query.
     *
     * @param query the query of the autoPostAprvlUsrs search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/auto-post-aprvl-usrs")
    public ResponseEntity<List<AutoPostAprvlUsrsDTO>> searchAutoPostAprvlUsrs(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of AutoPostAprvlUsrs for query {}", query);
        Page<AutoPostAprvlUsrsDTO> page = autoPostAprvlUsrsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
