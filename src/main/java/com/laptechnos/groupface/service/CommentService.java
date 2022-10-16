package com.laptechnos.groupface.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.laptechnos.groupface.domain.Comment;
import com.laptechnos.groupface.repository.CommentRepository;
import com.laptechnos.groupface.repository.search.CommentSearchRepository;
import com.laptechnos.groupface.service.dto.CommentDTO;
import com.laptechnos.groupface.service.mapper.CommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comment}.
 */
@Service
@Transactional
public class CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final CommentSearchRepository commentSearchRepository;

    public CommentService(
        CommentRepository commentRepository,
        CommentMapper commentMapper,
        CommentSearchRepository commentSearchRepository
    ) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.commentSearchRepository = commentSearchRepository;
    }

    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentDTO save(CommentDTO commentDTO) {
        log.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        CommentDTO result = commentMapper.toDto(comment);
        commentSearchRepository.index(comment);
        return result;
    }

    /**
     * Update a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentDTO update(CommentDTO commentDTO) {
        log.debug("Request to update Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        CommentDTO result = commentMapper.toDto(comment);
        commentSearchRepository.index(comment);
        return result;
    }

    /**
     * Partially update a comment.
     *
     * @param commentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommentDTO> partialUpdate(CommentDTO commentDTO) {
        log.debug("Request to partially update Comment : {}", commentDTO);

        return commentRepository
            .findById(commentDTO.getId())
            .map(existingComment -> {
                commentMapper.partialUpdate(existingComment, commentDTO);

                return existingComment;
            })
            .map(commentRepository::save)
            .map(savedComment -> {
                commentSearchRepository.save(savedComment);

                return savedComment;
            })
            .map(commentMapper::toDto);
    }

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        return commentRepository.findAll(pageable).map(commentMapper::toDto);
    }

    /**
     * Get all the comments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CommentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return commentRepository.findAllWithEagerRelationships(pageable).map(commentMapper::toDto);
    }

    /**
     * Get one comment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        return commentRepository.findOneWithEagerRelationships(id).map(commentMapper::toDto);
    }

    /**
     * Delete the comment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
        commentSearchRepository.deleteById(id);
    }

    /**
     * Search for the comment corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Comments for query {}", query);
        return commentSearchRepository.search(query, pageable).map(commentMapper::toDto);
    }
}
