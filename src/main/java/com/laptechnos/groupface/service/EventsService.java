package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Events;
import com.laptechnos.groupface.repository.EventsRepository;
import com.laptechnos.groupface.repository.search.EventsSearchRepository;
import com.laptechnos.groupface.service.dto.EventsDTO;
import com.laptechnos.groupface.service.mapper.EventsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Events}.
 */
@Service
@Transactional
public class EventsService {

    private final Logger log = LoggerFactory.getLogger(EventsService.class);

    private final EventsRepository eventsRepository;

    private final EventsMapper eventsMapper;

    private final EventsSearchRepository eventsSearchRepository;

    public EventsService(EventsRepository eventsRepository, EventsMapper eventsMapper, EventsSearchRepository eventsSearchRepository) {
        this.eventsRepository = eventsRepository;
        this.eventsMapper = eventsMapper;
        this.eventsSearchRepository = eventsSearchRepository;
    }

    /**
     * Save a events.
     *
     * @param eventsDTO the entity to save.
     * @return the persisted entity.
     */
    public EventsDTO save(EventsDTO eventsDTO) {
        log.debug("Request to save Events : {}", eventsDTO);
        Events events = eventsMapper.toEntity(eventsDTO);
        events = eventsRepository.save(events);
        EventsDTO result = eventsMapper.toDto(events);
        eventsSearchRepository.index(events);
        return result;
    }

    /**
     * Update a events.
     *
     * @param eventsDTO the entity to save.
     * @return the persisted entity.
     */
    public EventsDTO update(EventsDTO eventsDTO) {
        log.debug("Request to update Events : {}", eventsDTO);
        Events events = eventsMapper.toEntity(eventsDTO);
        events = eventsRepository.save(events);
        EventsDTO result = eventsMapper.toDto(events);
        eventsSearchRepository.index(events);
        return result;
    }

    /**
     * Partially update a events.
     *
     * @param eventsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventsDTO> partialUpdate(EventsDTO eventsDTO) {
        log.debug("Request to partially update Events : {}", eventsDTO);

        return eventsRepository
            .findById(eventsDTO.getId())
            .map(existingEvents -> {
                eventsMapper.partialUpdate(existingEvents, eventsDTO);

                return existingEvents;
            })
            .map(eventsRepository::save)
            .map(savedEvents -> {
                eventsSearchRepository.save(savedEvents);

                return savedEvents;
            })
            .map(eventsMapper::toDto);
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventsRepository.findAll(pageable).map(eventsMapper::toDto);
    }

    /**
     * Get all the events with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EventsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return eventsRepository.findAllWithEagerRelationships(pageable).map(eventsMapper::toDto);
    }

    /**
     * Get one events by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventsDTO> findOne(Long id) {
        log.debug("Request to get Events : {}", id);
        return eventsRepository.findOneWithEagerRelationships(id).map(eventsMapper::toDto);
    }

    /**
     * Delete the events by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Events : {}", id);
        eventsRepository.deleteById(id);
        eventsSearchRepository.deleteById(id);
    }

    /**
     * Search for the events corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Events for query {}", query);
        return eventsSearchRepository.search(query, pageable).map(eventsMapper::toDto);
    }
}
