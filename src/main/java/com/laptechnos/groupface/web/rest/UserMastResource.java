package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.UserMastRepository;
import com.laptechnos.groupface.service.UserMastService;
import com.laptechnos.groupface.service.dto.UserMastDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.UserMast}.
 */
@RestController
@RequestMapping("/api")
public class UserMastResource {

    private final Logger log = LoggerFactory.getLogger(UserMastResource.class);

    private static final String ENTITY_NAME = "userMast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserMastService userMastService;

    private final UserMastRepository userMastRepository;

    public UserMastResource(UserMastService userMastService, UserMastRepository userMastRepository) {
        this.userMastService = userMastService;
        this.userMastRepository = userMastRepository;
    }

    /**
     * {@code POST  /user-masts} : Create a new userMast.
     *
     * @param userMastDTO the userMastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userMastDTO, or with status {@code 400 (Bad Request)} if the userMast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-masts")
    public ResponseEntity<UserMastDTO> createUserMast(@RequestBody UserMastDTO userMastDTO) throws URISyntaxException {
        log.debug("REST request to save UserMast : {}", userMastDTO);
        if (userMastDTO.getId() != null) {
            throw new BadRequestAlertException("A new userMast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserMastDTO result = userMastService.save(userMastDTO);
        return ResponseEntity
            .created(new URI("/api/user-masts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-masts/:id} : Updates an existing userMast.
     *
     * @param id the id of the userMastDTO to save.
     * @param userMastDTO the userMastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userMastDTO,
     * or with status {@code 400 (Bad Request)} if the userMastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userMastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-masts/{id}")
    public ResponseEntity<UserMastDTO> updateUserMast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserMastDTO userMastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserMast : {}, {}", id, userMastDTO);
        if (userMastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userMastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userMastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserMastDTO result = userMastService.update(userMastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userMastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-masts/:id} : Partial updates given fields of an existing userMast, field will ignore if it is null
     *
     * @param id the id of the userMastDTO to save.
     * @param userMastDTO the userMastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userMastDTO,
     * or with status {@code 400 (Bad Request)} if the userMastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userMastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userMastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-masts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserMastDTO> partialUpdateUserMast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserMastDTO userMastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserMast partially : {}, {}", id, userMastDTO);
        if (userMastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userMastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userMastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserMastDTO> result = userMastService.partialUpdate(userMastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userMastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-masts} : get all the userMasts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userMasts in body.
     */
    @GetMapping("/user-masts")
    public ResponseEntity<List<UserMastDTO>> getAllUserMasts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of UserMasts");
        Page<UserMastDTO> page;
        if (eagerload) {
            page = userMastService.findAllWithEagerRelationships(pageable);
        } else {
            page = userMastService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-masts/:id} : get the "id" userMast.
     *
     * @param id the id of the userMastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userMastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-masts/{id}")
    public ResponseEntity<UserMastDTO> getUserMast(@PathVariable Long id) {
        log.debug("REST request to get UserMast : {}", id);
        Optional<UserMastDTO> userMastDTO = userMastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userMastDTO);
    }

    /**
     * {@code DELETE  /user-masts/:id} : delete the "id" userMast.
     *
     * @param id the id of the userMastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-masts/{id}")
    public ResponseEntity<Void> deleteUserMast(@PathVariable Long id) {
        log.debug("REST request to delete UserMast : {}", id);
        userMastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/user-masts?query=:query} : search for the userMast corresponding
     * to the query.
     *
     * @param query the query of the userMast search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-masts")
    public ResponseEntity<List<UserMastDTO>> searchUserMasts(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of UserMasts for query {}", query);
        Page<UserMastDTO> page = userMastService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
