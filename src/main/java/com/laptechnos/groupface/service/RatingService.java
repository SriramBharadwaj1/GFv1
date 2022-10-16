package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Rating;
import com.laptechnos.groupface.repository.RatingRepository;
import com.laptechnos.groupface.repository.search.RatingSearchRepository;
import com.laptechnos.groupface.service.dto.RatingDTO;
import com.laptechnos.groupface.service.mapper.RatingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rating}.
 */
@Service
@Transactional
public class RatingService {

    private final Logger log = LoggerFactory.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;

    private final RatingMapper ratingMapper;

    private final RatingSearchRepository ratingSearchRepository;

    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper, RatingSearchRepository ratingSearchRepository) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
        this.ratingSearchRepository = ratingSearchRepository;
    }

    /**
     * Save a rating.
     *
     * @param ratingDTO the entity to save.
     * @return the persisted entity.
     */
    public RatingDTO save(RatingDTO ratingDTO) {
        log.debug("Request to save Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);
        rating = ratingRepository.save(rating);
        RatingDTO result = ratingMapper.toDto(rating);
        ratingSearchRepository.index(rating);
        return result;
    }

    /**
     * Update a rating.
     *
     * @param ratingDTO the entity to save.
     * @return the persisted entity.
     */
    public RatingDTO update(RatingDTO ratingDTO) {
        log.debug("Request to update Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);
        rating = ratingRepository.save(rating);
        RatingDTO result = ratingMapper.toDto(rating);
        ratingSearchRepository.index(rating);
        return result;
    }

    /**
     * Partially update a rating.
     *
     * @param ratingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RatingDTO> partialUpdate(RatingDTO ratingDTO) {
        log.debug("Request to partially update Rating : {}", ratingDTO);

        return ratingRepository
            .findById(ratingDTO.getId())
            .map(existingRating -> {
                ratingMapper.partialUpdate(existingRating, ratingDTO);

                return existingRating;
            })
            .map(ratingRepository::save)
            .map(savedRating -> {
                ratingSearchRepository.save(savedRating);

                return savedRating;
            })
            .map(ratingMapper::toDto);
    }

    /**
     * Get all the ratings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ratings");
        return ratingRepository.findAll(pageable).map(ratingMapper::toDto);
    }

    /**
     * Get all the ratings with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RatingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ratingRepository.findAllWithEagerRelationships(pageable).map(ratingMapper::toDto);
    }

    /**
     * Get one rating by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RatingDTO> findOne(Long id) {
        log.debug("Request to get Rating : {}", id);
        return ratingRepository.findOneWithEagerRelationships(id).map(ratingMapper::toDto);
    }

    /**
     * Delete the rating by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.deleteById(id);
        ratingSearchRepository.deleteById(id);
    }

    /**
     * Search for the rating corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ratings for query {}", query);
        return ratingSearchRepository.search(query, pageable).map(ratingMapper::toDto);
    }
}
