package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.PostPics;
import com.laptechnos.groupface.repository.PostPicsRepository;
import com.laptechnos.groupface.repository.search.PostPicsSearchRepository;
import com.laptechnos.groupface.service.dto.PostPicsDTO;
import com.laptechnos.groupface.service.mapper.PostPicsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PostPics}.
 */
@Service
@Transactional
public class PostPicsService {

    private final Logger log = LoggerFactory.getLogger(PostPicsService.class);

    private final PostPicsRepository postPicsRepository;

    private final PostPicsMapper postPicsMapper;

    private final PostPicsSearchRepository postPicsSearchRepository;

    public PostPicsService(
        PostPicsRepository postPicsRepository,
        PostPicsMapper postPicsMapper,
        PostPicsSearchRepository postPicsSearchRepository
    ) {
        this.postPicsRepository = postPicsRepository;
        this.postPicsMapper = postPicsMapper;
        this.postPicsSearchRepository = postPicsSearchRepository;
    }

    /**
     * Save a postPics.
     *
     * @param postPicsDTO the entity to save.
     * @return the persisted entity.
     */
    public PostPicsDTO save(PostPicsDTO postPicsDTO) {
        log.debug("Request to save PostPics : {}", postPicsDTO);
        PostPics postPics = postPicsMapper.toEntity(postPicsDTO);
        postPics = postPicsRepository.save(postPics);
        PostPicsDTO result = postPicsMapper.toDto(postPics);
        postPicsSearchRepository.index(postPics);
        return result;
    }

    /**
     * Update a postPics.
     *
     * @param postPicsDTO the entity to save.
     * @return the persisted entity.
     */
    public PostPicsDTO update(PostPicsDTO postPicsDTO) {
        log.debug("Request to update PostPics : {}", postPicsDTO);
        PostPics postPics = postPicsMapper.toEntity(postPicsDTO);
        postPics = postPicsRepository.save(postPics);
        PostPicsDTO result = postPicsMapper.toDto(postPics);
        postPicsSearchRepository.index(postPics);
        return result;
    }

    /**
     * Partially update a postPics.
     *
     * @param postPicsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PostPicsDTO> partialUpdate(PostPicsDTO postPicsDTO) {
        log.debug("Request to partially update PostPics : {}", postPicsDTO);

        return postPicsRepository
            .findById(postPicsDTO.getId())
            .map(existingPostPics -> {
                postPicsMapper.partialUpdate(existingPostPics, postPicsDTO);

                return existingPostPics;
            })
            .map(postPicsRepository::save)
            .map(savedPostPics -> {
                postPicsSearchRepository.save(savedPostPics);

                return savedPostPics;
            })
            .map(postPicsMapper::toDto);
    }

    /**
     * Get all the postPics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostPicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostPics");
        return postPicsRepository.findAll(pageable).map(postPicsMapper::toDto);
    }

    /**
     * Get all the postPics with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PostPicsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return postPicsRepository.findAllWithEagerRelationships(pageable).map(postPicsMapper::toDto);
    }

    /**
     * Get one postPics by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PostPicsDTO> findOne(Long id) {
        log.debug("Request to get PostPics : {}", id);
        return postPicsRepository.findOneWithEagerRelationships(id).map(postPicsMapper::toDto);
    }

    /**
     * Delete the postPics by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PostPics : {}", id);
        postPicsRepository.deleteById(id);
        postPicsSearchRepository.deleteById(id);
    }

    /**
     * Search for the postPics corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostPicsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PostPics for query {}", query);
        return postPicsSearchRepository.search(query, pageable).map(postPicsMapper::toDto);
    }
}
