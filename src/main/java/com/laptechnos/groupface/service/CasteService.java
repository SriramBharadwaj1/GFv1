package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Caste;
import com.laptechnos.groupface.repository.CasteRepository;
import com.laptechnos.groupface.repository.search.CasteSearchRepository;
import com.laptechnos.groupface.service.dto.CasteDTO;
import com.laptechnos.groupface.service.mapper.CasteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Caste}.
 */
@Service
@Transactional
public class CasteService {

    private final Logger log = LoggerFactory.getLogger(CasteService.class);

    private final CasteRepository casteRepository;

    private final CasteMapper casteMapper;

    private final CasteSearchRepository casteSearchRepository;

    public CasteService(CasteRepository casteRepository, CasteMapper casteMapper, CasteSearchRepository casteSearchRepository) {
        this.casteRepository = casteRepository;
        this.casteMapper = casteMapper;
        this.casteSearchRepository = casteSearchRepository;
    }

    /**
     * Save a caste.
     *
     * @param casteDTO the entity to save.
     * @return the persisted entity.
     */
    public CasteDTO save(CasteDTO casteDTO) {
        log.debug("Request to save Caste : {}", casteDTO);
        Caste caste = casteMapper.toEntity(casteDTO);
        caste = casteRepository.save(caste);
        CasteDTO result = casteMapper.toDto(caste);
        casteSearchRepository.index(caste);
        return result;
    }

    /**
     * Update a caste.
     *
     * @param casteDTO the entity to save.
     * @return the persisted entity.
     */
    public CasteDTO update(CasteDTO casteDTO) {
        log.debug("Request to update Caste : {}", casteDTO);
        Caste caste = casteMapper.toEntity(casteDTO);
        caste = casteRepository.save(caste);
        CasteDTO result = casteMapper.toDto(caste);
        casteSearchRepository.index(caste);
        return result;
    }

    /**
     * Partially update a caste.
     *
     * @param casteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CasteDTO> partialUpdate(CasteDTO casteDTO) {
        log.debug("Request to partially update Caste : {}", casteDTO);

        return casteRepository
            .findById(casteDTO.getId())
            .map(existingCaste -> {
                casteMapper.partialUpdate(existingCaste, casteDTO);

                return existingCaste;
            })
            .map(casteRepository::save)
            .map(savedCaste -> {
                casteSearchRepository.save(savedCaste);

                return savedCaste;
            })
            .map(casteMapper::toDto);
    }

    /**
     * Get all the castes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CasteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Castes");
        return casteRepository.findAll(pageable).map(casteMapper::toDto);
    }

    /**
     * Get all the castes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CasteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return casteRepository.findAllWithEagerRelationships(pageable).map(casteMapper::toDto);
    }

    /**
     * Get one caste by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CasteDTO> findOne(Long id) {
        log.debug("Request to get Caste : {}", id);
        return casteRepository.findOneWithEagerRelationships(id).map(casteMapper::toDto);
    }

    /**
     * Delete the caste by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Caste : {}", id);
        casteRepository.deleteById(id);
        casteSearchRepository.deleteById(id);
    }

    /**
     * Search for the caste corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CasteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Castes for query {}", query);
        return casteSearchRepository.search(query, pageable).map(casteMapper::toDto);
    }
}
