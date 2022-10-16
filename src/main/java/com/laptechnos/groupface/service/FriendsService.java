package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Friends;
import com.laptechnos.groupface.repository.FriendsRepository;
import com.laptechnos.groupface.repository.search.FriendsSearchRepository;
import com.laptechnos.groupface.service.dto.FriendsDTO;
import com.laptechnos.groupface.service.mapper.FriendsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Friends}.
 */
@Service
@Transactional
public class FriendsService {

    private final Logger log = LoggerFactory.getLogger(FriendsService.class);

    private final FriendsRepository friendsRepository;

    private final FriendsMapper friendsMapper;

    private final FriendsSearchRepository friendsSearchRepository;

    public FriendsService(
        FriendsRepository friendsRepository,
        FriendsMapper friendsMapper,
        FriendsSearchRepository friendsSearchRepository
    ) {
        this.friendsRepository = friendsRepository;
        this.friendsMapper = friendsMapper;
        this.friendsSearchRepository = friendsSearchRepository;
    }

    /**
     * Save a friends.
     *
     * @param friendsDTO the entity to save.
     * @return the persisted entity.
     */
    public FriendsDTO save(FriendsDTO friendsDTO) {
        log.debug("Request to save Friends : {}", friendsDTO);
        Friends friends = friendsMapper.toEntity(friendsDTO);
        friends = friendsRepository.save(friends);
        FriendsDTO result = friendsMapper.toDto(friends);
        friendsSearchRepository.index(friends);
        return result;
    }

    /**
     * Update a friends.
     *
     * @param friendsDTO the entity to save.
     * @return the persisted entity.
     */
    public FriendsDTO update(FriendsDTO friendsDTO) {
        log.debug("Request to update Friends : {}", friendsDTO);
        Friends friends = friendsMapper.toEntity(friendsDTO);
        friends = friendsRepository.save(friends);
        FriendsDTO result = friendsMapper.toDto(friends);
        friendsSearchRepository.index(friends);
        return result;
    }

    /**
     * Partially update a friends.
     *
     * @param friendsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FriendsDTO> partialUpdate(FriendsDTO friendsDTO) {
        log.debug("Request to partially update Friends : {}", friendsDTO);

        return friendsRepository
            .findById(friendsDTO.getId())
            .map(existingFriends -> {
                friendsMapper.partialUpdate(existingFriends, friendsDTO);

                return existingFriends;
            })
            .map(friendsRepository::save)
            .map(savedFriends -> {
                friendsSearchRepository.save(savedFriends);

                return savedFriends;
            })
            .map(friendsMapper::toDto);
    }

    /**
     * Get all the friends.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FriendsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Friends");
        return friendsRepository.findAll(pageable).map(friendsMapper::toDto);
    }

    /**
     * Get all the friends with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FriendsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return friendsRepository.findAllWithEagerRelationships(pageable).map(friendsMapper::toDto);
    }

    /**
     * Get one friends by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FriendsDTO> findOne(Long id) {
        log.debug("Request to get Friends : {}", id);
        return friendsRepository.findOneWithEagerRelationships(id).map(friendsMapper::toDto);
    }

    /**
     * Delete the friends by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Friends : {}", id);
        friendsRepository.deleteById(id);
        friendsSearchRepository.deleteById(id);
    }

    /**
     * Search for the friends corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FriendsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Friends for query {}", query);
        return friendsSearchRepository.search(query, pageable).map(friendsMapper::toDto);
    }
}
