package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.repository.OrganisationRepository;
import com.laptechnos.groupface.repository.search.OrganisationSearchRepository;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.mapper.OrganisationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organisation}.
 */
@Service
@Transactional
public class OrganisationService {

    private final Logger log = LoggerFactory.getLogger(OrganisationService.class);

    private final OrganisationRepository organisationRepository;

    private final OrganisationMapper organisationMapper;

    private final OrganisationSearchRepository organisationSearchRepository;

    public OrganisationService(
        OrganisationRepository organisationRepository,
        OrganisationMapper organisationMapper,
        OrganisationSearchRepository organisationSearchRepository
    ) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
        this.organisationSearchRepository = organisationSearchRepository;
    }

    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganisationDTO save(OrganisationDTO organisationDTO) {
        log.debug("Request to save Organisation : {}", organisationDTO);
        Organisation organisation = organisationMapper.toEntity(organisationDTO);
        organisation = organisationRepository.save(organisation);
        OrganisationDTO result = organisationMapper.toDto(organisation);
        organisationSearchRepository.index(organisation);
        return result;
    }

    /**
     * Update a organisation.
     *
     * @param organisationDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganisationDTO update(OrganisationDTO organisationDTO) {
        log.debug("Request to update Organisation : {}", organisationDTO);
        Organisation organisation = organisationMapper.toEntity(organisationDTO);
        organisation = organisationRepository.save(organisation);
        OrganisationDTO result = organisationMapper.toDto(organisation);
        organisationSearchRepository.index(organisation);
        return result;
    }

    /**
     * Partially update a organisation.
     *
     * @param organisationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrganisationDTO> partialUpdate(OrganisationDTO organisationDTO) {
        log.debug("Request to partially update Organisation : {}", organisationDTO);

        return organisationRepository
            .findById(organisationDTO.getId())
            .map(existingOrganisation -> {
                organisationMapper.partialUpdate(existingOrganisation, organisationDTO);

                return existingOrganisation;
            })
            .map(organisationRepository::save)
            .map(savedOrganisation -> {
                organisationSearchRepository.save(savedOrganisation);

                return savedOrganisation;
            })
            .map(organisationMapper::toDto);
    }

    /**
     * Get all the organisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organisations");
        return organisationRepository.findAll(pageable).map(organisationMapper::toDto);
    }

    /**
     * Get one organisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganisationDTO> findOne(Long id) {
        log.debug("Request to get Organisation : {}", id);
        return organisationRepository.findById(id).map(organisationMapper::toDto);
    }

    /**
     * Delete the organisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Organisation : {}", id);
        organisationRepository.deleteById(id);
        organisationSearchRepository.deleteById(id);
    }

    /**
     * Search for the organisation corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganisationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Organisations for query {}", query);
        return organisationSearchRepository.search(query, pageable).map(organisationMapper::toDto);
    }
}
