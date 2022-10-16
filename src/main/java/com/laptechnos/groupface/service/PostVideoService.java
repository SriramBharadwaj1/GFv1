package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.PostVideo;
import com.laptechnos.groupface.repository.PostVideoRepository;
import com.laptechnos.groupface.repository.search.PostVideoSearchRepository;
import com.laptechnos.groupface.service.dto.PostVideoDTO;
import com.laptechnos.groupface.service.mapper.PostVideoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PostVideo}.
 */
@Service
@Transactional
public class PostVideoService {

    private final Logger log = LoggerFactory.getLogger(PostVideoService.class);

    private final PostVideoRepository postVideoRepository;

    private final PostVideoMapper postVideoMapper;

    private final PostVideoSearchRepository postVideoSearchRepository;

    public PostVideoService(
        PostVideoRepository postVideoRepository,
        PostVideoMapper postVideoMapper,
        PostVideoSearchRepository postVideoSearchRepository
    ) {
        this.postVideoRepository = postVideoRepository;
        this.postVideoMapper = postVideoMapper;
        this.postVideoSearchRepository = postVideoSearchRepository;
    }

    /**
     * Save a postVideo.
     *
     * @param postVideoDTO the entity to save.
     * @return the persisted entity.
     */
    public PostVideoDTO save(PostVideoDTO postVideoDTO) {
        log.debug("Request to save PostVideo : {}", postVideoDTO);
        PostVideo postVideo = postVideoMapper.toEntity(postVideoDTO);
        postVideo = postVideoRepository.save(postVideo);
        PostVideoDTO result = postVideoMapper.toDto(postVideo);
        postVideoSearchRepository.index(postVideo);
        return result;
    }

    /**
     * Update a postVideo.
     *
     * @param postVideoDTO the entity to save.
     * @return the persisted entity.
     */
    public PostVideoDTO update(PostVideoDTO postVideoDTO) {
        log.debug("Request to update PostVideo : {}", postVideoDTO);
        PostVideo postVideo = postVideoMapper.toEntity(postVideoDTO);
        postVideo = postVideoRepository.save(postVideo);
        PostVideoDTO result = postVideoMapper.toDto(postVideo);
        postVideoSearchRepository.index(postVideo);
        return result;
    }

    /**
     * Partially update a postVideo.
     *
     * @param postVideoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PostVideoDTO> partialUpdate(PostVideoDTO postVideoDTO) {
        log.debug("Request to partially update PostVideo : {}", postVideoDTO);

        return postVideoRepository
            .findById(postVideoDTO.getId())
            .map(existingPostVideo -> {
                postVideoMapper.partialUpdate(existingPostVideo, postVideoDTO);

                return existingPostVideo;
            })
            .map(postVideoRepository::save)
            .map(savedPostVideo -> {
                postVideoSearchRepository.save(savedPostVideo);

                return savedPostVideo;
            })
            .map(postVideoMapper::toDto);
    }

    /**
     * Get all the postVideos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostVideoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostVideos");
        return postVideoRepository.findAll(pageable).map(postVideoMapper::toDto);
    }

    /**
     * Get all the postVideos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PostVideoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return postVideoRepository.findAllWithEagerRelationships(pageable).map(postVideoMapper::toDto);
    }

    /**
     * Get one postVideo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PostVideoDTO> findOne(Long id) {
        log.debug("Request to get PostVideo : {}", id);
        return postVideoRepository.findOneWithEagerRelationships(id).map(postVideoMapper::toDto);
    }

    /**
     * Delete the postVideo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PostVideo : {}", id);
        postVideoRepository.deleteById(id);
        postVideoSearchRepository.deleteById(id);
    }

    /**
     * Search for the postVideo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PostVideoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PostVideos for query {}", query);
        return postVideoSearchRepository.search(query, pageable).map(postVideoMapper::toDto);
    }
}
