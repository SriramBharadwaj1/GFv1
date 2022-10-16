package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Masters;
import com.laptechnos.groupface.repository.MastersRepository;
import com.laptechnos.groupface.repository.search.MastersSearchRepository;
import com.laptechnos.groupface.service.dto.MastersDTO;
import com.laptechnos.groupface.service.mapper.MastersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Masters}.
 */
@Service
@Transactional
public class MastersService {

    private final Logger log = LoggerFactory.getLogger(MastersService.class);

    private final MastersRepository mastersRepository;

    private final MastersMapper mastersMapper;

    private final MastersSearchRepository mastersSearchRepository;

    public MastersService(
        MastersRepository mastersRepository,
        MastersMapper mastersMapper,
        MastersSearchRepository mastersSearchRepository
    ) {
        this.mastersRepository = mastersRepository;
        this.mastersMapper = mastersMapper;
        this.mastersSearchRepository = mastersSearchRepository;
    }

    /**
     * Save a masters.
     *
     * @param mastersDTO the entity to save.
     * @return the persisted entity.
     */
    public MastersDTO save(MastersDTO mastersDTO) {
        log.debug("Request to save Masters : {}", mastersDTO);
        Masters masters = mastersMapper.toEntity(mastersDTO);
        masters = mastersRepository.save(masters);
        MastersDTO result = mastersMapper.toDto(masters);
        mastersSearchRepository.index(masters);
        return result;
    }

    /**
     * Update a masters.
     *
     * @param mastersDTO the entity to save.
     * @return the persisted entity.
     */
    public MastersDTO update(MastersDTO mastersDTO) {
        log.debug("Request to update Masters : {}", mastersDTO);
        Masters masters = mastersMapper.toEntity(mastersDTO);
        masters = mastersRepository.save(masters);
        MastersDTO result = mastersMapper.toDto(masters);
        mastersSearchRepository.index(masters);
        return result;
    }

    /**
     * Partially update a masters.
     *
     * @param mastersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MastersDTO> partialUpdate(MastersDTO mastersDTO) {
        log.debug("Request to partially update Masters : {}", mastersDTO);

        return mastersRepository
            .findById(mastersDTO.getId())
            .map(existingMasters -> {
                mastersMapper.partialUpdate(existingMasters, mastersDTO);

                return existingMasters;
            })
            .map(mastersRepository::save)
            .map(savedMasters -> {
                mastersSearchRepository.save(savedMasters);

                return savedMasters;
            })
            .map(mastersMapper::toDto);
    }

    /**
     * Get all the masters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MastersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Masters");
        return mastersRepository.findAll(pageable).map(mastersMapper::toDto);
    }

    /**
     * Get all the masters with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MastersDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mastersRepository.findAllWithEagerRelationships(pageable).map(mastersMapper::toDto);
    }

    /**
     * Get one masters by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MastersDTO> findOne(Long id) {
        log.debug("Request to get Masters : {}", id);
        return mastersRepository.findOneWithEagerRelationships(id).map(mastersMapper::toDto);
    }

    /**
     * Delete the masters by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Masters : {}", id);
        mastersRepository.deleteById(id);
        mastersSearchRepository.deleteById(id);
    }

    /**
     * Search for the masters corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MastersDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Masters for query {}", query);
        return mastersSearchRepository.search(query, pageable).map(mastersMapper::toDto);
    }
}
