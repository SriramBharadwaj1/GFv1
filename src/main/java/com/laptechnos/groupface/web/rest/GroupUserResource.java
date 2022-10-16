package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.GroupUserRepository;
import com.laptechnos.groupface.service.GroupUserService;
import com.laptechnos.groupface.service.dto.GroupUserDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.GroupUser}.
 */
@RestController
@RequestMapping("/api")
public class GroupUserResource {

    private final Logger log = LoggerFactory.getLogger(GroupUserResource.class);

    private static final String ENTITY_NAME = "groupUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupUserService groupUserService;

    private final GroupUserRepository groupUserRepository;

    public GroupUserResource(GroupUserService groupUserService, GroupUserRepository groupUserRepository) {
        this.groupUserService = groupUserService;
        this.groupUserRepository = groupUserRepository;
    }

    /**
     * {@code POST  /group-users} : Create a new groupUser.
     *
     * @param groupUserDTO the groupUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupUserDTO, or with status {@code 400 (Bad Request)} if the groupUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-users")
    public ResponseEntity<GroupUserDTO> createGroupUser(@RequestBody GroupUserDTO groupUserDTO) throws URISyntaxException {
        log.debug("REST request to save GroupUser : {}", groupUserDTO);
        if (groupUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupUserDTO result = groupUserService.save(groupUserDTO);
        return ResponseEntity
            .created(new URI("/api/group-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-users/:id} : Updates an existing groupUser.
     *
     * @param id the id of the groupUserDTO to save.
     * @param groupUserDTO the groupUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupUserDTO,
     * or with status {@code 400 (Bad Request)} if the groupUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-users/{id}")
    public ResponseEntity<GroupUserDTO> updateGroupUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupUserDTO groupUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GroupUser : {}, {}", id, groupUserDTO);
        if (groupUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GroupUserDTO result = groupUserService.update(groupUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /group-users/:id} : Partial updates given fields of an existing groupUser, field will ignore if it is null
     *
     * @param id the id of the groupUserDTO to save.
     * @param groupUserDTO the groupUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupUserDTO,
     * or with status {@code 400 (Bad Request)} if the groupUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the groupUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the groupUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/group-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroupUserDTO> partialUpdateGroupUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GroupUserDTO groupUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GroupUser partially : {}, {}", id, groupUserDTO);
        if (groupUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groupUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groupUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroupUserDTO> result = groupUserService.partialUpdate(groupUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /group-users} : get all the groupUsers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupUsers in body.
     */
    @GetMapping("/group-users")
    public ResponseEntity<List<GroupUserDTO>> getAllGroupUsers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of GroupUsers");
        Page<GroupUserDTO> page;
        if (eagerload) {
            page = groupUserService.findAllWithEagerRelationships(pageable);
        } else {
            page = groupUserService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-users/:id} : get the "id" groupUser.
     *
     * @param id the id of the groupUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-users/{id}")
    public ResponseEntity<GroupUserDTO> getGroupUser(@PathVariable Long id) {
        log.debug("REST request to get GroupUser : {}", id);
        Optional<GroupUserDTO> groupUserDTO = groupUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupUserDTO);
    }

    /**
     * {@code DELETE  /group-users/:id} : delete the "id" groupUser.
     *
     * @param id the id of the groupUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-users/{id}")
    public ResponseEntity<Void> deleteGroupUser(@PathVariable Long id) {
        log.debug("REST request to delete GroupUser : {}", id);
        groupUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/group-users?query=:query} : search for the groupUser corresponding
     * to the query.
     *
     * @param query the query of the groupUser search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/group-users")
    public ResponseEntity<List<GroupUserDTO>> searchGroupUsers(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of GroupUsers for query {}", query);
        Page<GroupUserDTO> page = groupUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
