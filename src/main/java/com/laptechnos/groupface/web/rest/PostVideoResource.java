package com.laptechnos.groupface.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.repository.PostVideoRepository;
import com.laptechnos.groupface.service.PostVideoService;
import com.laptechnos.groupface.service.dto.PostVideoDTO;
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
 * REST controller for managing {@link com.laptechnos.groupface.domain.PostVideo}.
 */
@RestController
@RequestMapping("/api")
public class PostVideoResource {

    private final Logger log = LoggerFactory.getLogger(PostVideoResource.class);

    private static final String ENTITY_NAME = "postVideo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostVideoService postVideoService;

    private final PostVideoRepository postVideoRepository;

    public PostVideoResource(PostVideoService postVideoService, PostVideoRepository postVideoRepository) {
        this.postVideoService = postVideoService;
        this.postVideoRepository = postVideoRepository;
    }

    /**
     * {@code POST  /post-videos} : Create a new postVideo.
     *
     * @param postVideoDTO the postVideoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postVideoDTO, or with status {@code 400 (Bad Request)} if the postVideo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-videos")
    public ResponseEntity<PostVideoDTO> createPostVideo(@RequestBody PostVideoDTO postVideoDTO) throws URISyntaxException {
        log.debug("REST request to save PostVideo : {}", postVideoDTO);
        if (postVideoDTO.getId() != null) {
            throw new BadRequestAlertException("A new postVideo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostVideoDTO result = postVideoService.save(postVideoDTO);
        return ResponseEntity
            .created(new URI("/api/post-videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-videos/:id} : Updates an existing postVideo.
     *
     * @param id the id of the postVideoDTO to save.
     * @param postVideoDTO the postVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postVideoDTO,
     * or with status {@code 400 (Bad Request)} if the postVideoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-videos/{id}")
    public ResponseEntity<PostVideoDTO> updatePostVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostVideoDTO postVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PostVideo : {}, {}", id, postVideoDTO);
        if (postVideoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postVideoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PostVideoDTO result = postVideoService.update(postVideoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postVideoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /post-videos/:id} : Partial updates given fields of an existing postVideo, field will ignore if it is null
     *
     * @param id the id of the postVideoDTO to save.
     * @param postVideoDTO the postVideoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postVideoDTO,
     * or with status {@code 400 (Bad Request)} if the postVideoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the postVideoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the postVideoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/post-videos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostVideoDTO> partialUpdatePostVideo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostVideoDTO postVideoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PostVideo partially : {}, {}", id, postVideoDTO);
        if (postVideoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postVideoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postVideoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostVideoDTO> result = postVideoService.partialUpdate(postVideoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postVideoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /post-videos} : get all the postVideos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postVideos in body.
     */
    @GetMapping("/post-videos")
    public ResponseEntity<List<PostVideoDTO>> getAllPostVideos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of PostVideos");
        Page<PostVideoDTO> page;
        if (eagerload) {
            page = postVideoService.findAllWithEagerRelationships(pageable);
        } else {
            page = postVideoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-videos/:id} : get the "id" postVideo.
     *
     * @param id the id of the postVideoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postVideoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-videos/{id}")
    public ResponseEntity<PostVideoDTO> getPostVideo(@PathVariable Long id) {
        log.debug("REST request to get PostVideo : {}", id);
        Optional<PostVideoDTO> postVideoDTO = postVideoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postVideoDTO);
    }

    /**
     * {@code DELETE  /post-videos/:id} : delete the "id" postVideo.
     *
     * @param id the id of the postVideoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-videos/{id}")
    public ResponseEntity<Void> deletePostVideo(@PathVariable Long id) {
        log.debug("REST request to delete PostVideo : {}", id);
        postVideoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /_search/post-videos?query=:query} : search for the postVideo corresponding
     * to the query.
     *
     * @param query the query of the postVideo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/post-videos")
    public ResponseEntity<List<PostVideoDTO>> searchPostVideos(
        @RequestParam String query,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of PostVideos for query {}", query);
        Page<PostVideoDTO> page = postVideoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
