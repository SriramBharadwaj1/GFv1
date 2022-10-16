package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Rating;
import com.laptechnos.groupface.repository.RatingRepository;
import com.laptechnos.groupface.repository.search.RatingSearchRepository;
import com.laptechnos.groupface.service.RatingService;
import com.laptechnos.groupface.service.dto.RatingDTO;
import com.laptechnos.groupface.service.mapper.RatingMapper;
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
 * Integration tests for the {@link RatingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RatingResourceIT {

    private static final Double DEFAULT_RATING = 1D;
    private static final Double UPDATED_RATING = 2D;

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

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final LocalDate DEFAULT_UPDATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_APPROVED_BY = 1L;
    private static final Long UPDATED_APPROVED_BY = 2L;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final LocalDate DEFAULT_APPROVED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/ratings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/ratings";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RatingRepository ratingRepository;

    @Mock
    private RatingRepository ratingRepositoryMock;

    @Autowired
    private RatingMapper ratingMapper;

    @Mock
    private RatingService ratingServiceMock;

    @Autowired
    private RatingSearchRepository ratingSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingMockMvc;

    private Rating rating;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createEntity(EntityManager em) {
        Rating rating = new Rating()
            .rating(DEFAULT_RATING)
            .postId(DEFAULT_POST_ID)
            .isActive(DEFAULT_IS_ACTIVE)
            .apprRejReason(DEFAULT_APPR_REJ_REASON)
            .isEnable(DEFAULT_IS_ENABLE)
            .addedBy(DEFAULT_ADDED_BY)
            .addedOn(DEFAULT_ADDED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .approvedBy(DEFAULT_APPROVED_BY)
            .orgId(DEFAULT_ORG_ID)
            .approvedOn(DEFAULT_APPROVED_ON);
        return rating;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createUpdatedEntity(EntityManager em) {
        Rating rating = new Rating()
            .rating(UPDATED_RATING)
            .postId(UPDATED_POST_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .orgId(UPDATED_ORG_ID)
            .approvedOn(UPDATED_APPROVED_ON);
        return rating;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        ratingSearchRepository.deleteAll();
        assertThat(ratingSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        rating = createEntity(em);
    }

    @Test
    @Transactional
    void createRating() throws Exception {
        int databaseSizeBeforeCreate = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);
        restRatingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isCreated());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testRating.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testRating.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testRating.getApprRejReason()).isEqualTo(DEFAULT_APPR_REJ_REASON);
        assertThat(testRating.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testRating.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testRating.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testRating.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRating.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testRating.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testRating.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testRating.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
    }

    @Test
    @Transactional
    void createRatingWithExistingId() throws Exception {
        // Create the Rating with an existing ID
        rating.setId(1L);
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        int databaseSizeBeforeCreate = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllRatings() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList
        restRatingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].apprRejReason").value(hasItem(DEFAULT_APPR_REJ_REASON)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRatingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(ratingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRatingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ratingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRatingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ratingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRatingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ratingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get the rating
        restRatingMockMvc
            .perform(get(ENTITY_API_URL_ID, rating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rating.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.doubleValue()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.apprRejReason").value(DEFAULT_APPR_REJ_REASON))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRating() throws Exception {
        // Get the rating
        restRatingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        ratingSearchRepository.save(rating);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());

        // Update the rating
        Rating updatedRating = ratingRepository.findById(rating.getId()).get();
        // Disconnect from session so that the updates on updatedRating are not directly saved in db
        em.detach(updatedRating);
        updatedRating
            .rating(UPDATED_RATING)
            .postId(UPDATED_POST_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .orgId(UPDATED_ORG_ID)
            .approvedOn(UPDATED_APPROVED_ON);
        RatingDTO ratingDTO = ratingMapper.toDto(updatedRating);

        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testRating.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testRating.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testRating.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testRating.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testRating.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testRating.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testRating.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRating.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testRating.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testRating.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testRating.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Rating> ratingSearchList = IterableUtils.toList(ratingSearchRepository.findAll());
                Rating testRatingSearch = ratingSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testRatingSearch.getRating()).isEqualTo(UPDATED_RATING);
                assertThat(testRatingSearch.getPostId()).isEqualTo(UPDATED_POST_ID);
                assertThat(testRatingSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testRatingSearch.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
                assertThat(testRatingSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testRatingSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testRatingSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testRatingSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testRatingSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testRatingSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testRatingSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testRatingSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
            });
    }

    @Test
    @Transactional
    void putNonExistingRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        rating.setId(count.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ratingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        rating.setId(count.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ratingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        rating.setId(count.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateRatingWithPatch() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Update the rating using partial update
        Rating partialUpdatedRating = new Rating();
        partialUpdatedRating.setId(rating.getId());

        partialUpdatedRating
            .rating(UPDATED_RATING)
            .postId(UPDATED_POST_ID)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .orgId(UPDATED_ORG_ID);

        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRating.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRating))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testRating.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testRating.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testRating.getApprRejReason()).isEqualTo(DEFAULT_APPR_REJ_REASON);
        assertThat(testRating.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testRating.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testRating.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testRating.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRating.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testRating.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testRating.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testRating.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
    }

    @Test
    @Transactional
    void fullUpdateRatingWithPatch() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Update the rating using partial update
        Rating partialUpdatedRating = new Rating();
        partialUpdatedRating.setId(rating.getId());

        partialUpdatedRating
            .rating(UPDATED_RATING)
            .postId(UPDATED_POST_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .orgId(UPDATED_ORG_ID)
            .approvedOn(UPDATED_APPROVED_ON);

        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRating.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRating))
            )
            .andExpect(status().isOk());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testRating.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testRating.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testRating.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testRating.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testRating.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testRating.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testRating.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRating.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testRating.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testRating.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testRating.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        rating.setId(count.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ratingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        rating.setId(count.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ratingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        rating.setId(count.incrementAndGet());

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRatingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ratingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);
        ratingRepository.save(rating);
        ratingSearchRepository.save(rating);

        int databaseSizeBeforeDelete = ratingRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the rating
        restRatingMockMvc
            .perform(delete(ENTITY_API_URL_ID, rating.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(ratingSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchRating() throws Exception {
        // Initialize the database
        rating = ratingRepository.saveAndFlush(rating);
        ratingSearchRepository.save(rating);

        // Search the rating
        restRatingMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + rating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].apprRejReason").value(hasItem(DEFAULT_APPR_REJ_REASON)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())));
    }
}
