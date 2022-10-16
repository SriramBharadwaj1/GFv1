package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Groups;
import com.laptechnos.groupface.repository.GroupsRepository;
import com.laptechnos.groupface.repository.search.GroupsSearchRepository;
import com.laptechnos.groupface.service.dto.GroupsDTO;
import com.laptechnos.groupface.service.mapper.GroupsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Groups}.
 */
@Service
@Transactional
public class GroupsService {

    private final Logger log = LoggerFactory.getLogger(GroupsService.class);

    private final GroupsRepository groupsRepository;

    private final GroupsMapper groupsMapper;

    private final GroupsSearchRepository groupsSearchRepository;

    public GroupsService(GroupsRepository groupsRepository, GroupsMapper groupsMapper, GroupsSearchRepository groupsSearchRepository) {
        this.groupsRepository = groupsRepository;
        this.groupsMapper = groupsMapper;
        this.groupsSearchRepository = groupsSearchRepository;
    }

    /**
     * Save a groups.
     *
     * @param groupsDTO the entity to save.
     * @return the persisted entity.
     */
    public GroupsDTO save(GroupsDTO groupsDTO) {
        log.debug("Request to save Groups : {}", groupsDTO);
        Groups groups = groupsMapper.toEntity(groupsDTO);
        groups = groupsRepository.save(groups);
        GroupsDTO result = groupsMapper.toDto(groups);
        groupsSearchRepository.index(groups);
        return result;
    }

    /**
     * Update a groups.
     *
     * @param groupsDTO the entity to save.
     * @return the persisted entity.
     */
    public GroupsDTO update(GroupsDTO groupsDTO) {
        log.debug("Request to update Groups : {}", groupsDTO);
        Groups groups = groupsMapper.toEntity(groupsDTO);
        groups = groupsRepository.save(groups);
        GroupsDTO result = groupsMapper.toDto(groups);
        groupsSearchRepository.index(groups);
        return result;
    }

    /**
     * Partially update a groups.
     *
     * @param groupsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GroupsDTO> partialUpdate(GroupsDTO groupsDTO) {
        log.debug("Request to partially update Groups : {}", groupsDTO);

        return groupsRepository
            .findById(groupsDTO.getId())
            .map(existingGroups -> {
                groupsMapper.partialUpdate(existingGroups, groupsDTO);

                return existingGroups;
            })
            .map(groupsRepository::save)
            .map(savedGroups -> {
                groupsSearchRepository.save(savedGroups);

                return savedGroups;
            })
            .map(groupsMapper::toDto);
    }

    /**
     * Get all the groups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Groups");
        return groupsRepository.findAll(pageable).map(groupsMapper::toDto);
    }

    /**
     * Get all the groups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GroupsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return groupsRepository.findAllWithEagerRelationships(pageable).map(groupsMapper::toDto);
    }

    /**
     * Get one groups by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GroupsDTO> findOne(Long id) {
        log.debug("Request to get Groups : {}", id);
        return groupsRepository.findOneWithEagerRelationships(id).map(groupsMapper::toDto);
    }

    /**
     * Delete the groups by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Groups : {}", id);
        groupsRepository.deleteById(id);
        groupsSearchRepository.deleteById(id);
    }

    /**
     * Search for the groups corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Groups for query {}", query);
        return groupsSearchRepository.search(query, pageable).map(groupsMapper::toDto);
    }
}
