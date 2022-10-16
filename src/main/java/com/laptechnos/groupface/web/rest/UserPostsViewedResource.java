package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.UserPostsViewedRepository;
import com.laptechnos.groupface.service.UserPostsViewedService;
import com.laptechnos.groupface.service.dto.UserPostsViewedDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.UserPostsViewed}.
 */
@RestController
@RequestMapping("/api")
public class UserPostsViewedResource {

    private final Logger log = LoggerFactory.getLogger(UserPostsViewedResource.class);

    private static final String ENTITY_NAME = "userPostsViewed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserPostsViewedService userPostsViewedService;

    private final UserPostsViewedRepository userPostsViewedRepository;

    public UserPostsViewedResource(UserPostsViewedService userPostsViewedService, UserPostsViewedRepository userPostsViewedRepository) {
        this.userPostsViewedService = userPostsViewedService;
        this.userPostsViewedRepository = userPostsViewedRepository;
    }

    /**
     * {@code POST  /user-posts-vieweds} : Create a new userPostsViewed.
     *
     * @param userPostsViewedDTO the userPostsViewedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userPostsViewedDTO, or with status {@code 400 (Bad Request)} if the userPostsViewed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-posts-vieweds")
    public ResponseEntity<UserPostsViewedDTO> createUserPostsViewed(@RequestBody UserPostsViewedDTO userPostsViewedDTO)
        throws URISyntaxException {
        log.debug("REST request to save UserPostsViewed : {}", userPostsViewedDTO);
        if (userPostsViewedDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPostsViewed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPostsViewedDTO result = userPostsViewedService.save(userPostsViewedDTO);
        return ResponseEntity
            .created(new URI("/api/user-posts-vieweds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-posts-vieweds/:id} : Updates an existing userPostsViewed.
     *
     * @param id the id of the userPostsViewedDTO to save.
     * @param userPostsViewedDTO the userPostsViewedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPostsViewedDTO,
     * or with status {@code 400 (Bad Request)} if the userPostsViewedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userPostsViewedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-posts-vieweds/{id}")
    public ResponseEntity<UserPostsViewedDTO> updateUserPostsViewed(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserPostsViewedDTO userPostsViewedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserPostsViewed : {}, {}", id, userPostsViewedDTO);
        if (userPostsViewedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPostsViewedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPostsViewedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserPostsViewedDTO result = userPostsViewedService.update(userPostsViewedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPostsViewedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-posts-vieweds/:id} : Partial updates given fields of an existing userPostsViewed, field will ignore if it is null
     *
     * @param id the id of the userPostsViewedDTO to save.
     * @param userPostsViewedDTO the userPostsViewedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userPostsViewedDTO,
     * or with status {@code 400 (Bad Request)} if the userPostsViewedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userPostsViewedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userPostsViewedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-posts-vieweds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserPostsViewedDTO> partialUpdateUserPostsViewed(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserPostsViewedDTO userPostsViewedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserPostsViewed partially : {}, {}", id, userPostsViewedDTO);
        if (userPostsViewedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userPostsViewedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userPostsViewedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserPostsViewedDTO> result = userPostsViewedService.partialUpdate(userPostsViewedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userPostsViewedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-posts-vieweds} : get all the userPostsVieweds.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userPostsVieweds in body.
     */
    @GetMapping("/user-posts-vieweds")
    public ResponseEntity<List<UserPostsViewedDTO>> getAllUserPostsVieweds(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of UserPostsVieweds");
        Page<UserPostsViewedDTO> page;
        if (eagerload) {
            page = userPostsViewedService.findAllWithEagerRelationships(pageable);
        } else {
            page = userPostsViewedService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-posts-vieweds/:id} : get the "id" userPostsViewed.
     *
     * @param id the id of the userPostsViewedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userPostsViewedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-posts-vieweds/{id}")
    public ResponseEntity<UserPostsViewedDTO> getUserPostsViewed(@PathVariable Long id) {
        log.debug("REST request to get UserPostsViewed : {}", id);
        Optional<UserPostsViewedDTO> userPostsViewedDTO = userPostsViewedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userPostsViewedDTO);
    }

    /**
     * {@code DELETE  /user-posts-vieweds/:id} : delete the "id" userPostsViewed.
     *
     * @param id the id of the userPostsViewedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-posts-vieweds/{id}")
    public ResponseEntity<Void> deleteUserPostsViewed(@PathVariable Long id) {
        log.debug("REST request to delete UserPostsViewed : {}", id);
        userPostsViewedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/user-posts-vieweds?query=:query} : search for the userPostsViewed corresponding
     * to the query.
     *
     * @param query the query of the userPostsViewed search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-posts-vieweds")
    public ResponseEntity<List<UserPostsViewedDTO>> searchUserPostsVieweds(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of UserPostsVieweds for query {}", query);
        Page<UserPostsViewedDTO> page = userPostsViewedService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
