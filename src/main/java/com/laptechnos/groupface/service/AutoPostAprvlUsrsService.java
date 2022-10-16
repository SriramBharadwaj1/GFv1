package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.AutoPostAprvlUsrs;
import com.laptechnos.groupface.repository.AutoPostAprvlUsrsRepository;
import com.laptechnos.groupface.repository.search.AutoPostAprvlUsrsSearchRepository;
import com.laptechnos.groupface.service.dto.AutoPostAprvlUsrsDTO;
import com.laptechnos.groupface.service.mapper.AutoPostAprvlUsrsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AutoPostAprvlUsrs}.
 */
@Service
@Transactional
public class AutoPostAprvlUsrsService {

    private final Logger log = LoggerFactory.getLogger(AutoPostAprvlUsrsService.class);

    private final AutoPostAprvlUsrsRepository autoPostAprvlUsrsRepository;

    private final AutoPostAprvlUsrsMapper autoPostAprvlUsrsMapper;

    private final AutoPostAprvlUsrsSearchRepository autoPostAprvlUsrsSearchRepository;

    public AutoPostAprvlUsrsService(
        AutoPostAprvlUsrsRepository autoPostAprvlUsrsRepository,
        AutoPostAprvlUsrsMapper autoPostAprvlUsrsMapper,
        AutoPostAprvlUsrsSearchRepository autoPostAprvlUsrsSearchRepository
    ) {
        this.autoPostAprvlUsrsRepository = autoPostAprvlUsrsRepository;
        this.autoPostAprvlUsrsMapper = autoPostAprvlUsrsMapper;
        this.autoPostAprvlUsrsSearchRepository = autoPostAprvlUsrsSearchRepository;
    }

    /**
     * Save a autoPostAprvlUsrs.
     *
     * @param autoPostAprvlUsrsDTO the entity to save.
     * @return the persisted entity.
     */
    public AutoPostAprvlUsrsDTO save(AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO) {
        log.debug("Request to save AutoPostAprvlUsrs : {}", autoPostAprvlUsrsDTO);
        AutoPostAprvlUsrs autoPostAprvlUsrs = autoPostAprvlUsrsMapper.toEntity(autoPostAprvlUsrsDTO);
        autoPostAprvlUsrs = autoPostAprvlUsrsRepository.save(autoPostAprvlUsrs);
        AutoPostAprvlUsrsDTO result = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);
        autoPostAprvlUsrsSearchRepository.index(autoPostAprvlUsrs);
        return result;
    }

    /**
     * Update a autoPostAprvlUsrs.
     *
     * @param autoPostAprvlUsrsDTO the entity to save.
     * @return the persisted entity.
     */
    public AutoPostAprvlUsrsDTO update(AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO) {
        log.debug("Request to update AutoPostAprvlUsrs : {}", autoPostAprvlUsrsDTO);
        AutoPostAprvlUsrs autoPostAprvlUsrs = autoPostAprvlUsrsMapper.toEntity(autoPostAprvlUsrsDTO);
        autoPostAprvlUsrs = autoPostAprvlUsrsRepository.save(autoPostAprvlUsrs);
        AutoPostAprvlUsrsDTO result = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);
        autoPostAprvlUsrsSearchRepository.index(autoPostAprvlUsrs);
        return result;
    }

    /**
     * Partially update a autoPostAprvlUsrs.
     *
     * @param autoPostAprvlUsrsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AutoPostAprvlUsrsDTO> partialUpdate(AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO) {
        log.debug("Request to partially update AutoPostAprvlUsrs : {}", autoPostAprvlUsrsDTO);

        return autoPostAprvlUsrsRepository
            .findById(autoPostAprvlUsrsDTO.getId())
            .map(existingAutoPostAprvlUsrs -> {
                autoPostAprvlUsrsMapper.partialUpdate(existingAutoPostAprvlUsrs, autoPostAprvlUsrsDTO);

                return existingAutoPostAprvlUsrs;
            })
            .map(autoPostAprvlUsrsRepository::save)
            .map(savedAutoPostAprvlUsrs -> {
                autoPostAprvlUsrsSearchRepository.save(savedAutoPostAprvlUsrs);

                return savedAutoPostAprvlUsrs;
            })
            .map(autoPostAprvlUsrsMapper::toDto);
    }

    /**
     * Get all the autoPostAprvlUsrs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutoPostAprvlUsrsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AutoPostAprvlUsrs");
        return autoPostAprvlUsrsRepository.findAll(pageable).map(autoPostAprvlUsrsMapper::toDto);
    }

    /**
     * Get all the autoPostAprvlUsrs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AutoPostAprvlUsrsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return autoPostAprvlUsrsRepository.findAllWithEagerRelationships(pageable).map(autoPostAprvlUsrsMapper::toDto);
    }

    /**
     * Get one autoPostAprvlUsrs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AutoPostAprvlUsrsDTO> findOne(Long id) {
        log.debug("Request to get AutoPostAprvlUsrs : {}", id);
        return autoPostAprvlUsrsRepository.findOneWithEagerRelationships(id).map(autoPostAprvlUsrsMapper::toDto);
    }

    /**
     * Delete the autoPostAprvlUsrs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AutoPostAprvlUsrs : {}", id);
        autoPostAprvlUsrsRepository.deleteById(id);
        autoPostAprvlUsrsSearchRepository.deleteById(id);
    }

    /**
     * Search for the autoPostAprvlUsrs corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AutoPostAprvlUsrsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AutoPostAprvlUsrs for query {}", query);
        return autoPostAprvlUsrsSearchRepository.search(query, pageable).map(autoPostAprvlUsrsMapper::toDto);
    }
}
