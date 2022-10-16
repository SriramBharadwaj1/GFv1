package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.PostPicsRepository;
import com.laptechnos.groupface.service.PostPicsService;
import com.laptechnos.groupface.service.dto.PostPicsDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.PostPics}.
 */
@RestController
@RequestMapping("/api")
public class PostPicsResource {

    private final Logger log = LoggerFactory.getLogger(PostPicsResource.class);

    private static final String ENTITY_NAME = "postPics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostPicsService postPicsService;

    private final PostPicsRepository postPicsRepository;

    public PostPicsResource(PostPicsService postPicsService, PostPicsRepository postPicsRepository) {
        this.postPicsService = postPicsService;
        this.postPicsRepository = postPicsRepository;
    }

    /**
     * {@code POST  /post-pics} : Create a new postPics.
     *
     * @param postPicsDTO the postPicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postPicsDTO, or with status {@code 400 (Bad Request)} if the postPics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-pics")
    public ResponseEntity<PostPicsDTO> createPostPics(@RequestBody PostPicsDTO postPicsDTO) throws URISyntaxException {
        log.debug("REST request to save PostPics : {}", postPicsDTO);
        if (postPicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new postPics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostPicsDTO result = postPicsService.save(postPicsDTO);
        return ResponseEntity
            .created(new URI("/api/post-pics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-pics/:id} : Updates an existing postPics.
     *
     * @param id the id of the postPicsDTO to save.
     * @param postPicsDTO the postPicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postPicsDTO,
     * or with status {@code 400 (Bad Request)} if the postPicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postPicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-pics/{id}")
    public ResponseEntity<PostPicsDTO> updatePostPics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostPicsDTO postPicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PostPics : {}, {}", id, postPicsDTO);
        if (postPicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postPicsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postPicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PostPicsDTO result = postPicsService.update(postPicsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postPicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /post-pics/:id} : Partial updates given fields of an existing postPics, field will ignore if it is null
     *
     * @param id the id of the postPicsDTO to save.
     * @param postPicsDTO the postPicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postPicsDTO,
     * or with status {@code 400 (Bad Request)} if the postPicsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the postPicsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the postPicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/post-pics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostPicsDTO> partialUpdatePostPics(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostPicsDTO postPicsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PostPics partially : {}, {}", id, postPicsDTO);
        if (postPicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postPicsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postPicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostPicsDTO> result = postPicsService.partialUpdate(postPicsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postPicsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /post-pics} : get all the postPics.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postPics in body.
     */
    @GetMapping("/post-pics")
    public ResponseEntity<List<PostPicsDTO>> getAllPostPics(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PostPics");
        Page<PostPicsDTO> page;
        if (eagerload) {
            page = postPicsService.findAllWithEagerRelationships(pageable);
        } else {
            page = postPicsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-pics/:id} : get the "id" postPics.
     *
     * @param id the id of the postPicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postPicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-pics/{id}")
    public ResponseEntity<PostPicsDTO> getPostPics(@PathVariable Long id) {
        log.debug("REST request to get PostPics : {}", id);
        Optional<PostPicsDTO> postPicsDTO = postPicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postPicsDTO);
    }

    /**
     * {@code DELETE  /post-pics/:id} : delete the "id" postPics.
     *
     * @param id the id of the postPicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-pics/{id}")
    public ResponseEntity<Void> deletePostPics(@PathVariable Long id) {
        log.debug("REST request to delete PostPics : {}", id);
        postPicsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/post-pics?query=:query} : search for the postPics corresponding
     * to the query.
     *
     * @param query the query of the postPics search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/post-pics")
    public ResponseEntity<List<PostPicsDTO>> searchPostPics(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of PostPics for query {}", query);
        Page<PostPicsDTO> page = postPicsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
