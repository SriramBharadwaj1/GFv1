package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Remarks;
import com.laptechnos.groupface.repository.RemarksRepository;
import com.laptechnos.groupface.repository.search.RemarksSearchRepository;
import com.laptechnos.groupface.service.dto.RemarksDTO;
import com.laptechnos.groupface.service.mapper.RemarksMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Remarks}.
 */
@Service
@Transactional
public class RemarksService {

    private final Logger log = LoggerFactory.getLogger(RemarksService.class);

    private final RemarksRepository remarksRepository;

    private final RemarksMapper remarksMapper;

    private final RemarksSearchRepository remarksSearchRepository;

    public RemarksService(
        RemarksRepository remarksRepository,
        RemarksMapper remarksMapper,
        RemarksSearchRepository remarksSearchRepository
    ) {
        this.remarksRepository = remarksRepository;
        this.remarksMapper = remarksMapper;
        this.remarksSearchRepository = remarksSearchRepository;
    }

    /**
     * Save a remarks.
     *
     * @param remarksDTO the entity to save.
     * @return the persisted entity.
     */
    public RemarksDTO save(RemarksDTO remarksDTO) {
        log.debug("Request to save Remarks : {}", remarksDTO);
        Remarks remarks = remarksMapper.toEntity(remarksDTO);
        remarks = remarksRepository.save(remarks);
        RemarksDTO result = remarksMapper.toDto(remarks);
        remarksSearchRepository.index(remarks);
        return result;
    }

    /**
     * Update a remarks.
     *
     * @param remarksDTO the entity to save.
     * @return the persisted entity.
     */
    public RemarksDTO update(RemarksDTO remarksDTO) {
        log.debug("Request to update Remarks : {}", remarksDTO);
        Remarks remarks = remarksMapper.toEntity(remarksDTO);
        remarks = remarksRepository.save(remarks);
        RemarksDTO result = remarksMapper.toDto(remarks);
        remarksSearchRepository.index(remarks);
        return result;
    }

    /**
     * Partially update a remarks.
     *
     * @param remarksDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RemarksDTO> partialUpdate(RemarksDTO remarksDTO) {
        log.debug("Request to partially update Remarks : {}", remarksDTO);

        return remarksRepository
            .findById(remarksDTO.getId())
            .map(existingRemarks -> {
                remarksMapper.partialUpdate(existingRemarks, remarksDTO);

                return existingRemarks;
            })
            .map(remarksRepository::save)
            .map(savedRemarks -> {
                remarksSearchRepository.save(savedRemarks);

                return savedRemarks;
            })
            .map(remarksMapper::toDto);
    }

    /**
     * Get all the remarks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RemarksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Remarks");
        return remarksRepository.findAll(pageable).map(remarksMapper::toDto);
    }

    /**
     * Get all the remarks with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RemarksDTO> findAllWithEagerRelationships(Pageable pageable) {
        return remarksRepository.findAllWithEagerRelationships(pageable).map(remarksMapper::toDto);
    }

    /**
     * Get one remarks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RemarksDTO> findOne(Long id) {
        log.debug("Request to get Remarks : {}", id);
        return remarksRepository.findOneWithEagerRelationships(id).map(remarksMapper::toDto);
    }

    /**
     * Delete the remarks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Remarks : {}", id);
        remarksRepository.deleteById(id);
        remarksSearchRepository.deleteById(id);
    }

    /**
     * Search for the remarks corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RemarksDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Remarks for query {}", query);
        return remarksSearchRepository.search(query, pageable).map(remarksMapper::toDto);
    }
}
