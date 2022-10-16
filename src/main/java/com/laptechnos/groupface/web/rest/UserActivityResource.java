package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.UserActivityRepository;
import com.laptechnos.groupface.service.UserActivityService;
import com.laptechnos.groupface.service.dto.UserActivityDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.UserActivity}.
 */
@RestController
@RequestMapping("/api")
public class UserActivityResource {

    private final Logger log = LoggerFactory.getLogger(UserActivityResource.class);

    private static final String ENTITY_NAME = "userActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserActivityService userActivityService;

    private final UserActivityRepository userActivityRepository;

    public UserActivityResource(UserActivityService userActivityService, UserActivityRepository userActivityRepository) {
        this.userActivityService = userActivityService;
        this.userActivityRepository = userActivityRepository;
    }

    /**
     * {@code POST  /user-activities} : Create a new userActivity.
     *
     * @param userActivityDTO the userActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userActivityDTO, or with status {@code 400 (Bad Request)} if the userActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-activities")
    public ResponseEntity<UserActivityDTO> createUserActivity(@RequestBody UserActivityDTO userActivityDTO) throws URISyntaxException {
        log.debug("REST request to save UserActivity : {}", userActivityDTO);
        if (userActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new userActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserActivityDTO result = userActivityService.save(userActivityDTO);
        return ResponseEntity
            .created(new URI("/api/user-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-activities/:id} : Updates an existing userActivity.
     *
     * @param id the id of the userActivityDTO to save.
     * @param userActivityDTO the userActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userActivityDTO,
     * or with status {@code 400 (Bad Request)} if the userActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-activities/{id}")
    public ResponseEntity<UserActivityDTO> updateUserActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserActivityDTO userActivityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserActivity : {}, {}", id, userActivityDTO);
        if (userActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userActivityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserActivityDTO result = userActivityService.update(userActivityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-activities/:id} : Partial updates given fields of an existing userActivity, field will ignore if it is null
     *
     * @param id the id of the userActivityDTO to save.
     * @param userActivityDTO the userActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userActivityDTO,
     * or with status {@code 400 (Bad Request)} if the userActivityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userActivityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-activities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserActivityDTO> partialUpdateUserActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserActivityDTO userActivityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserActivity partially : {}, {}", id, userActivityDTO);
        if (userActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userActivityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserActivityDTO> result = userActivityService.partialUpdate(userActivityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userActivityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-activities} : get all the userActivities.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userActivities in body.
     */
    @GetMapping("/user-activities")
    public ResponseEntity<List<UserActivityDTO>> getAllUserActivities(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of UserActivities");
        Page<UserActivityDTO> page;
        if (eagerload) {
            page = userActivityService.findAllWithEagerRelationships(pageable);
        } else {
            page = userActivityService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-activities/:id} : get the "id" userActivity.
     *
     * @param id the id of the userActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-activities/{id}")
    public ResponseEntity<UserActivityDTO> getUserActivity(@PathVariable Long id) {
        log.debug("REST request to get UserActivity : {}", id);
        Optional<UserActivityDTO> userActivityDTO = userActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userActivityDTO);
    }

    /**
     * {@code DELETE  /user-activities/:id} : delete the "id" userActivity.
     *
     * @param id the id of the userActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-activities/{id}")
    public ResponseEntity<Void> deleteUserActivity(@PathVariable Long id) {
        log.debug("REST request to delete UserActivity : {}", id);
        userActivityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/user-activities?query=:query} : search for the userActivity corresponding
     * to the query.
     *
     * @param query the query of the userActivity search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-activities")
    public ResponseEntity<List<UserActivityDTO>> searchUserActivities(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of UserActivities for query {}", query);
        Page<UserActivityDTO> page = userActivityService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
