package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.GroupUser;
import com.laptechnos.groupface.repository.GroupUserRepository;
import com.laptechnos.groupface.repository.search.GroupUserSearchRepository;
import com.laptechnos.groupface.service.dto.GroupUserDTO;
import com.laptechnos.groupface.service.mapper.GroupUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GroupUser}.
 */
@Service
@Transactional
public class GroupUserService {

    private final Logger log = LoggerFactory.getLogger(GroupUserService.class);

    private final GroupUserRepository groupUserRepository;

    private final GroupUserMapper groupUserMapper;

    private final GroupUserSearchRepository groupUserSearchRepository;

    public GroupUserService(
        GroupUserRepository groupUserRepository,
        GroupUserMapper groupUserMapper,
        GroupUserSearchRepository groupUserSearchRepository
    ) {
        this.groupUserRepository = groupUserRepository;
        this.groupUserMapper = groupUserMapper;
        this.groupUserSearchRepository = groupUserSearchRepository;
    }

    /**
     * Save a groupUser.
     *
     * @param groupUserDTO the entity to save.
     * @return the persisted entity.
     */
    public GroupUserDTO save(GroupUserDTO groupUserDTO) {
        log.debug("Request to save GroupUser : {}", groupUserDTO);
        GroupUser groupUser = groupUserMapper.toEntity(groupUserDTO);
        groupUser = groupUserRepository.save(groupUser);
        GroupUserDTO result = groupUserMapper.toDto(groupUser);
        groupUserSearchRepository.index(groupUser);
        return result;
    }

    /**
     * Update a groupUser.
     *
     * @param groupUserDTO the entity to save.
     * @return the persisted entity.
     */
    public GroupUserDTO update(GroupUserDTO groupUserDTO) {
        log.debug("Request to update GroupUser : {}", groupUserDTO);
        GroupUser groupUser = groupUserMapper.toEntity(groupUserDTO);
        groupUser = groupUserRepository.save(groupUser);
        GroupUserDTO result = groupUserMapper.toDto(groupUser);
        groupUserSearchRepository.index(groupUser);
        return result;
    }

    /**
     * Partially update a groupUser.
     *
     * @param groupUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GroupUserDTO> partialUpdate(GroupUserDTO groupUserDTO) {
        log.debug("Request to partially update GroupUser : {}", groupUserDTO);

        return groupUserRepository
            .findById(groupUserDTO.getId())
            .map(existingGroupUser -> {
                groupUserMapper.partialUpdate(existingGroupUser, groupUserDTO);

                return existingGroupUser;
            })
            .map(groupUserRepository::save)
            .map(savedGroupUser -> {
                groupUserSearchRepository.save(savedGroupUser);

                return savedGroupUser;
            })
            .map(groupUserMapper::toDto);
    }

    /**
     * Get all the groupUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupUsers");
        return groupUserRepository.findAll(pageable).map(groupUserMapper::toDto);
    }

    /**
     * Get all the groupUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GroupUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return groupUserRepository.findAllWithEagerRelationships(pageable).map(groupUserMapper::toDto);
    }

    /**
     * Get one groupUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GroupUserDTO> findOne(Long id) {
        log.debug("Request to get GroupUser : {}", id);
        return groupUserRepository.findOneWithEagerRelationships(id).map(groupUserMapper::toDto);
    }

    /**
     * Delete the groupUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupUser : {}", id);
        groupUserRepository.deleteById(id);
        groupUserSearchRepository.deleteById(id);
    }

    /**
     * Search for the groupUser corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GroupUsers for query {}", query);
        return groupUserSearchRepository.search(query, pageable).map(groupUserMapper::toDto);
    }
}
