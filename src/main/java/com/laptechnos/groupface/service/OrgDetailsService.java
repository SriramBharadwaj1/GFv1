package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.OrgDetails;
import com.laptechnos.groupface.repository.OrgDetailsRepository;
import com.laptechnos.groupface.repository.search.OrgDetailsSearchRepository;
import com.laptechnos.groupface.service.dto.OrgDetailsDTO;
import com.laptechnos.groupface.service.mapper.OrgDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrgDetails}.
 */
@Service
@Transactional
public class OrgDetailsService {

    private final Logger log = LoggerFactory.getLogger(OrgDetailsService.class);

    private final OrgDetailsRepository orgDetailsRepository;

    private final OrgDetailsMapper orgDetailsMapper;

    private final OrgDetailsSearchRepository orgDetailsSearchRepository;

    public OrgDetailsService(
        OrgDetailsRepository orgDetailsRepository,
        OrgDetailsMapper orgDetailsMapper,
        OrgDetailsSearchRepository orgDetailsSearchRepository
    ) {
        this.orgDetailsRepository = orgDetailsRepository;
        this.orgDetailsMapper = orgDetailsMapper;
        this.orgDetailsSearchRepository = orgDetailsSearchRepository;
    }

    /**
     * Save a orgDetails.
     *
     * @param orgDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgDetailsDTO save(OrgDetailsDTO orgDetailsDTO) {
        log.debug("Request to save OrgDetails : {}", orgDetailsDTO);
        OrgDetails orgDetails = orgDetailsMapper.toEntity(orgDetailsDTO);
        orgDetails = orgDetailsRepository.save(orgDetails);
        OrgDetailsDTO result = orgDetailsMapper.toDto(orgDetails);
        orgDetailsSearchRepository.index(orgDetails);
        return result;
    }

    /**
     * Update a orgDetails.
     *
     * @param orgDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgDetailsDTO update(OrgDetailsDTO orgDetailsDTO) {
        log.debug("Request to update OrgDetails : {}", orgDetailsDTO);
        OrgDetails orgDetails = orgDetailsMapper.toEntity(orgDetailsDTO);
        orgDetails = orgDetailsRepository.save(orgDetails);
        OrgDetailsDTO result = orgDetailsMapper.toDto(orgDetails);
        orgDetailsSearchRepository.index(orgDetails);
        return result;
    }

    /**
     * Partially update a orgDetails.
     *
     * @param orgDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrgDetailsDTO> partialUpdate(OrgDetailsDTO orgDetailsDTO) {
        log.debug("Request to partially update OrgDetails : {}", orgDetailsDTO);

        return orgDetailsRepository
            .findById(orgDetailsDTO.getId())
            .map(existingOrgDetails -> {
                orgDetailsMapper.partialUpdate(existingOrgDetails, orgDetailsDTO);

                return existingOrgDetails;
            })
            .map(orgDetailsRepository::save)
            .map(savedOrgDetails -> {
                orgDetailsSearchRepository.save(savedOrgDetails);

                return savedOrgDetails;
            })
            .map(orgDetailsMapper::toDto);
    }

    /**
     * Get all the orgDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrgDetails");
        return orgDetailsRepository.findAll(pageable).map(orgDetailsMapper::toDto);
    }

    /**
     * Get all the orgDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<OrgDetailsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return orgDetailsRepository.findAllWithEagerRelationships(pageable).map(orgDetailsMapper::toDto);
    }

    /**
     * Get one orgDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrgDetailsDTO> findOne(Long id) {
        log.debug("Request to get OrgDetails : {}", id);
        return orgDetailsRepository.findOneWithEagerRelationships(id).map(orgDetailsMapper::toDto);
    }

    /**
     * Delete the orgDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrgDetails : {}", id);
        orgDetailsRepository.deleteById(id);
        orgDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the orgDetails corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrgDetails for query {}", query);
        return orgDetailsSearchRepository.search(query, pageable).map(orgDetailsMapper::toDto);
    }
}
