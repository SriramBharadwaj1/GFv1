package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.UserPostsViewed;
import com.laptechnos.groupface.repository.UserPostsViewedRepository;
import com.laptechnos.groupface.repository.search.UserPostsViewedSearchRepository;
import com.laptechnos.groupface.service.dto.UserPostsViewedDTO;
import com.laptechnos.groupface.service.mapper.UserPostsViewedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserPostsViewed}.
 */
@Service
@Transactional
public class UserPostsViewedService {

    private final Logger log = LoggerFactory.getLogger(UserPostsViewedService.class);

    private final UserPostsViewedRepository userPostsViewedRepository;

    private final UserPostsViewedMapper userPostsViewedMapper;

    private final UserPostsViewedSearchRepository userPostsViewedSearchRepository;

    public UserPostsViewedService(
        UserPostsViewedRepository userPostsViewedRepository,
        UserPostsViewedMapper userPostsViewedMapper,
        UserPostsViewedSearchRepository userPostsViewedSearchRepository
    ) {
        this.userPostsViewedRepository = userPostsViewedRepository;
        this.userPostsViewedMapper = userPostsViewedMapper;
        this.userPostsViewedSearchRepository = userPostsViewedSearchRepository;
    }

    /**
     * Save a userPostsViewed.
     *
     * @param userPostsViewedDTO the entity to save.
     * @return the persisted entity.
     */
    public UserPostsViewedDTO save(UserPostsViewedDTO userPostsViewedDTO) {
        log.debug("Request to save UserPostsViewed : {}", userPostsViewedDTO);
        UserPostsViewed userPostsViewed = userPostsViewedMapper.toEntity(userPostsViewedDTO);
        userPostsViewed = userPostsViewedRepository.save(userPostsViewed);
        UserPostsViewedDTO result = userPostsViewedMapper.toDto(userPostsViewed);
        userPostsViewedSearchRepository.index(userPostsViewed);
        return result;
    }

    /**
     * Update a userPostsViewed.
     *
     * @param userPostsViewedDTO the entity to save.
     * @return the persisted entity.
     */
    public UserPostsViewedDTO update(UserPostsViewedDTO userPostsViewedDTO) {
        log.debug("Request to update UserPostsViewed : {}", userPostsViewedDTO);
        UserPostsViewed userPostsViewed = userPostsViewedMapper.toEntity(userPostsViewedDTO);
        userPostsViewed = userPostsViewedRepository.save(userPostsViewed);
        UserPostsViewedDTO result = userPostsViewedMapper.toDto(userPostsViewed);
        userPostsViewedSearchRepository.index(userPostsViewed);
        return result;
    }

    /**
     * Partially update a userPostsViewed.
     *
     * @param userPostsViewedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserPostsViewedDTO> partialUpdate(UserPostsViewedDTO userPostsViewedDTO) {
        log.debug("Request to partially update UserPostsViewed : {}", userPostsViewedDTO);

        return userPostsViewedRepository
            .findById(userPostsViewedDTO.getId())
            .map(existingUserPostsViewed -> {
                userPostsViewedMapper.partialUpdate(existingUserPostsViewed, userPostsViewedDTO);

                return existingUserPostsViewed;
            })
            .map(userPostsViewedRepository::save)
            .map(savedUserPostsViewed -> {
                userPostsViewedSearchRepository.save(savedUserPostsViewed);

                return savedUserPostsViewed;
            })
            .map(userPostsViewedMapper::toDto);
    }

    /**
     * Get all the userPostsVieweds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserPostsViewedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPostsVieweds");
        return userPostsViewedRepository.findAll(pageable).map(userPostsViewedMapper::toDto);
    }

    /**
     * Get all the userPostsVieweds with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserPostsViewedDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userPostsViewedRepository.findAllWithEagerRelationships(pageable).map(userPostsViewedMapper::toDto);
    }

    /**
     * Get one userPostsViewed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserPostsViewedDTO> findOne(Long id) {
        log.debug("Request to get UserPostsViewed : {}", id);
        return userPostsViewedRepository.findOneWithEagerRelationships(id).map(userPostsViewedMapper::toDto);
    }

    /**
     * Delete the userPostsViewed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPostsViewed : {}", id);
        userPostsViewedRepository.deleteById(id);
        userPostsViewedSearchRepository.deleteById(id);
    }

    /**
     * Search for the userPostsViewed corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserPostsViewedDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserPostsVieweds for query {}", query);
        return userPostsViewedSearchRepository.search(query, pageable).map(userPostsViewedMapper::toDto);
    }
}
