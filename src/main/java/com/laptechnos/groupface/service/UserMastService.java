package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.repository.UserMastRepository;
import com.laptechnos.groupface.repository.search.UserMastSearchRepository;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import com.laptechnos.groupface.service.mapper.UserMastMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserMast}.
 */
@Service
@Transactional
public class UserMastService {

    private final Logger log = LoggerFactory.getLogger(UserMastService.class);

    private final UserMastRepository userMastRepository;

    private final UserMastMapper userMastMapper;

    private final UserMastSearchRepository userMastSearchRepository;

    public UserMastService(
        UserMastRepository userMastRepository,
        UserMastMapper userMastMapper,
        UserMastSearchRepository userMastSearchRepository
    ) {
        this.userMastRepository = userMastRepository;
        this.userMastMapper = userMastMapper;
        this.userMastSearchRepository = userMastSearchRepository;
    }

    /**
     * Save a userMast.
     *
     * @param userMastDTO the entity to save.
     * @return the persisted entity.
     */
    public UserMastDTO save(UserMastDTO userMastDTO) {
        log.debug("Request to save UserMast : {}", userMastDTO);
        UserMast userMast = userMastMapper.toEntity(userMastDTO);
        userMast = userMastRepository.save(userMast);
        UserMastDTO result = userMastMapper.toDto(userMast);
        userMastSearchRepository.index(userMast);
        return result;
    }

    /**
     * Update a userMast.
     *
     * @param userMastDTO the entity to save.
     * @return the persisted entity.
     */
    public UserMastDTO update(UserMastDTO userMastDTO) {
        log.debug("Request to update UserMast : {}", userMastDTO);
        UserMast userMast = userMastMapper.toEntity(userMastDTO);
        userMast = userMastRepository.save(userMast);
        UserMastDTO result = userMastMapper.toDto(userMast);
        userMastSearchRepository.index(userMast);
        return result;
    }

    /**
     * Partially update a userMast.
     *
     * @param userMastDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserMastDTO> partialUpdate(UserMastDTO userMastDTO) {
        log.debug("Request to partially update UserMast : {}", userMastDTO);

        return userMastRepository
            .findById(userMastDTO.getId())
            .map(existingUserMast -> {
                userMastMapper.partialUpdate(existingUserMast, userMastDTO);

                return existingUserMast;
            })
            .map(userMastRepository::save)
            .map(savedUserMast -> {
                userMastSearchRepository.save(savedUserMast);

                return savedUserMast;
            })
            .map(userMastMapper::toDto);
    }

    /**
     * Get all the userMasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserMastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserMasts");
        return userMastRepository.findAll(pageable).map(userMastMapper::toDto);
    }

    /**
     * Get all the userMasts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserMastDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userMastRepository.findAllWithEagerRelationships(pageable).map(userMastMapper::toDto);
    }

    /**
     * Get one userMast by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserMastDTO> findOne(Long id) {
        log.debug("Request to get UserMast : {}", id);
        return userMastRepository.findOneWithEagerRelationships(id).map(userMastMapper::toDto);
    }

    /**
     * Delete the userMast by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserMast : {}", id);
        userMastRepository.deleteById(id);
        userMastSearchRepository.deleteById(id);
    }

    /**
     * Search for the userMast corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserMastDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserMasts for query {}", query);
        return userMastSearchRepository.search(query, pageable).map(userMastMapper::toDto);
    }
}
