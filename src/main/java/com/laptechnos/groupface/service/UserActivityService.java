package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.UserActivity;
import com.laptechnos.groupface.repository.UserActivityRepository;
import com.laptechnos.groupface.repository.search.UserActivitySearchRepository;
import com.laptechnos.groupface.service.dto.UserActivityDTO;
import com.laptechnos.groupface.service.mapper.UserActivityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserActivity}.
 */
@Service
@Transactional
public class UserActivityService {

    private final Logger log = LoggerFactory.getLogger(UserActivityService.class);

    private final UserActivityRepository userActivityRepository;

    private final UserActivityMapper userActivityMapper;

    private final UserActivitySearchRepository userActivitySearchRepository;

    public UserActivityService(
        UserActivityRepository userActivityRepository,
        UserActivityMapper userActivityMapper,
        UserActivitySearchRepository userActivitySearchRepository
    ) {
        this.userActivityRepository = userActivityRepository;
        this.userActivityMapper = userActivityMapper;
        this.userActivitySearchRepository = userActivitySearchRepository;
    }

    /**
     * Save a userActivity.
     *
     * @param userActivityDTO the entity to save.
     * @return the persisted entity.
     */
    public UserActivityDTO save(UserActivityDTO userActivityDTO) {
        log.debug("Request to save UserActivity : {}", userActivityDTO);
        UserActivity userActivity = userActivityMapper.toEntity(userActivityDTO);
        userActivity = userActivityRepository.save(userActivity);
        UserActivityDTO result = userActivityMapper.toDto(userActivity);
        userActivitySearchRepository.index(userActivity);
        return result;
    }

    /**
     * Update a userActivity.
     *
     * @param userActivityDTO the entity to save.
     * @return the persisted entity.
     */
    public UserActivityDTO update(UserActivityDTO userActivityDTO) {
        log.debug("Request to update UserActivity : {}", userActivityDTO);
        UserActivity userActivity = userActivityMapper.toEntity(userActivityDTO);
        userActivity = userActivityRepository.save(userActivity);
        UserActivityDTO result = userActivityMapper.toDto(userActivity);
        userActivitySearchRepository.index(userActivity);
        return result;
    }

    /**
     * Partially update a userActivity.
     *
     * @param userActivityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserActivityDTO> partialUpdate(UserActivityDTO userActivityDTO) {
        log.debug("Request to partially update UserActivity : {}", userActivityDTO);

        return userActivityRepository
            .findById(userActivityDTO.getId())
            .map(existingUserActivity -> {
                userActivityMapper.partialUpdate(existingUserActivity, userActivityDTO);

                return existingUserActivity;
            })
            .map(userActivityRepository::save)
            .map(savedUserActivity -> {
                userActivitySearchRepository.save(savedUserActivity);

                return savedUserActivity;
            })
            .map(userActivityMapper::toDto);
    }

    /**
     * Get all the userActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserActivities");
        return userActivityRepository.findAll(pageable).map(userActivityMapper::toDto);
    }

    /**
     * Get all the userActivities with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserActivityDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userActivityRepository.findAllWithEagerRelationships(pageable).map(userActivityMapper::toDto);
    }

    /**
     * Get one userActivity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserActivityDTO> findOne(Long id) {
        log.debug("Request to get UserActivity : {}", id);
        return userActivityRepository.findOneWithEagerRelationships(id).map(userActivityMapper::toDto);
    }

    /**
     * Delete the userActivity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserActivity : {}", id);
        userActivityRepository.deleteById(id);
        userActivitySearchRepository.deleteById(id);
    }

    /**
     * Search for the userActivity corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserActivityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserActivities for query {}", query);
        return userActivitySearchRepository.search(query, pageable).map(userActivityMapper::toDto);
    }
}
