package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Comment;
import com.laptechnos.groupface.repository.CommentRepository;
import com.laptechnos.groupface.repository.search.CommentSearchRepository;
import com.laptechnos.groupface.service.CommentService;
import com.laptechnos.groupface.service.dto.CommentDTO;
import com.laptechnos.groupface.service.mapper.CommentMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CommentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CommentResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CATEGORY = 1;
    private static final Integer UPDATED_CATEGORY = 2;

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_META = "AAAAAAAAAA";
    private static final String UPDATED_META = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_ID = 1L;
    private static final Long UPDATED_POST_ID = 2L;

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    private static final Integer DEFAULT_APPR_REJ_REASON = 1;
    private static final Integer UPDATED_APPR_REJ_REASON = 2;

    private static final Integer DEFAULT_IS_ENABLE = 1;
    private static final Integer UPDATED_IS_ENABLE = 2;

    private static final Long DEFAULT_ADDED_BY = 1L;
    private static final Long UPDATED_ADDED_BY = 2L;

    private static final LocalDate DEFAULT_ADDED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADDED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_UPDATED_BY = 1;
    private static final Integer UPDATED_UPDATED_BY = 2;

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_APPROVED_BY = 1L;
    private static final Long UPDATED_APPROVED_BY = 2L;

    private static final LocalDate DEFAULT_APPROVED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final String DEFAULT_OTHER_INFO = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/comments";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommentRepository commentRepository;

    @Mock
    private CommentRepository commentRepositoryMock;

    @Autowired
    private CommentMapper commentMapper;

    @Mock
    private CommentService commentServiceMock;

    @Autowired
    private CommentSearchRepository commentSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentMockMvc;

    private Comment comment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
            .message(DEFAULT_MESSAGE)
            .category(DEFAULT_CATEGORY)
            .categoryName(DEFAULT_CATEGORY_NAME)
            .meta(DEFAULT_META)
            .postId(DEFAULT_POST_ID)
            .isActive(DEFAULT_IS_ACTIVE)
            .apprRejReason(DEFAULT_APPR_REJ_REASON)
            .isEnable(DEFAULT_IS_ENABLE)
            .addedBy(DEFAULT_ADDED_BY)
            .addedOn(DEFAULT_ADDED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .approvedBy(DEFAULT_APPROVED_BY)
            .approvedOn(DEFAULT_APPROVED_ON)
            .comments(DEFAULT_COMMENTS)
            .orgId(DEFAULT_ORG_ID)
            .otherInfo(DEFAULT_OTHER_INFO);
        return comment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createUpdatedEntity(EntityManager em) {
        Comment comment = new Comment()
            .message(UPDATED_MESSAGE)
            .category(UPDATED_CATEGORY)
            .categoryName(UPDATED_CATEGORY_NAME)
            .meta(UPDATED_META)
            .postId(UPDATED_POST_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .comments(UPDATED_COMMENTS)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);
        return comment;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        commentSearchRepository.deleteAll();
        assertThat(commentSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        comment = createEntity(em);
    }

    @Test
    @Transactional
    void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());
        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);
        restCommentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testComment.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testComment.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testComment.getMeta()).isEqualTo(DEFAULT_META);
        assertThat(testComment.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testComment.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testComment.getApprRejReason()).isEqualTo(DEFAULT_APPR_REJ_REASON);
        assertThat(testComment.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testComment.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testComment.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testComment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testComment.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testComment.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testComment.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testComment.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testComment.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testComment.getOtherInfo()).isEqualTo(DEFAULT_OTHER_INFO);
    }

    @Test
    @Transactional
    void createCommentWithExistingId() throws Exception {
        // Create the Comment with an existing ID
        comment.setId(1L);
        CommentDTO commentDTO = commentMapper.toDto(comment);

        int databaseSizeBeforeCreate = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].meta").value(hasItem(DEFAULT_META)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].apprRejReason").value(hasItem(DEFAULT_APPR_REJ_REASON)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].otherInfo").value(hasItem(DEFAULT_OTHER_INFO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(commentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCommentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(commentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(commentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCommentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(commentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
            .andExpect(jsonPath("$.meta").value(DEFAULT_META))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.apprRejReason").value(DEFAULT_APPR_REJ_REASON))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.otherInfo").value(DEFAULT_OTHER_INFO));
    }

    @Test
    @Transactional
    void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        commentSearchRepository.save(comment);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());

        // Update the comment
        Comment updatedComment = commentRepository.findById(comment.getId()).get();
        // Disconnect from session so that the updates on updatedComment are not directly saved in db
        em.detach(updatedComment);
        updatedComment
            .message(UPDATED_MESSAGE)
            .category(UPDATED_CATEGORY)
            .categoryName(UPDATED_CATEGORY_NAME)
            .meta(UPDATED_META)
            .postId(UPDATED_POST_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .comments(UPDATED_COMMENTS)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);
        CommentDTO commentDTO = commentMapper.toDto(updatedComment);

        restCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testComment.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testComment.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testComment.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testComment.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testComment.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testComment.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testComment.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testComment.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testComment.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testComment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testComment.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testComment.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testComment.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testComment.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testComment.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testComment.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Comment> commentSearchList = IterableUtils.toList(commentSearchRepository.findAll());
                Comment testCommentSearch = commentSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testCommentSearch.getMessage()).isEqualTo(UPDATED_MESSAGE);
                assertThat(testCommentSearch.getCategory()).isEqualTo(UPDATED_CATEGORY);
                assertThat(testCommentSearch.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
                assertThat(testCommentSearch.getMeta()).isEqualTo(UPDATED_META);
                assertThat(testCommentSearch.getPostId()).isEqualTo(UPDATED_POST_ID);
                assertThat(testCommentSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testCommentSearch.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
                assertThat(testCommentSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testCommentSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testCommentSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testCommentSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testCommentSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testCommentSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testCommentSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testCommentSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testCommentSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testCommentSearch.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
            });
    }

    @Test
    @Transactional
    void putNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateCommentWithPatch() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment using partial update
        Comment partialUpdatedComment = new Comment();
        partialUpdatedComment.setId(comment.getId());

        partialUpdatedComment
            .message(UPDATED_MESSAGE)
            .categoryName(UPDATED_CATEGORY_NAME)
            .meta(UPDATED_META)
            .postId(UPDATED_POST_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .approvedBy(UPDATED_APPROVED_BY)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);

        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComment))
            )
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testComment.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testComment.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testComment.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testComment.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testComment.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testComment.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testComment.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testComment.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testComment.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testComment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testComment.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testComment.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testComment.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testComment.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testComment.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testComment.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
    }

    @Test
    @Transactional
    void fullUpdateCommentWithPatch() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment using partial update
        Comment partialUpdatedComment = new Comment();
        partialUpdatedComment.setId(comment.getId());

        partialUpdatedComment
            .message(UPDATED_MESSAGE)
            .category(UPDATED_CATEGORY)
            .categoryName(UPDATED_CATEGORY_NAME)
            .meta(UPDATED_META)
            .postId(UPDATED_POST_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .comments(UPDATED_COMMENTS)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);

        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComment))
            )
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testComment.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testComment.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testComment.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testComment.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testComment.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testComment.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testComment.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testComment.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testComment.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testComment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testComment.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testComment.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testComment.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testComment.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testComment.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testComment.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());
        comment.setId(count.incrementAndGet());

        // Create the Comment
        CommentDTO commentDTO = commentMapper.toDto(comment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);
        commentRepository.save(comment);
        commentSearchRepository.save(comment);

        int databaseSizeBeforeDelete = commentRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the comment
        restCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, comment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(commentSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchComment() throws Exception {
        // Initialize the database
        comment = commentRepository.saveAndFlush(comment);
        commentSearchRepository.save(comment);

        // Search the comment
        restCommentMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].meta").value(hasItem(DEFAULT_META)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].apprRejReason").value(hasItem(DEFAULT_APPR_REJ_REASON)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].otherInfo").value(hasItem(DEFAULT_OTHER_INFO)));
    }
}
