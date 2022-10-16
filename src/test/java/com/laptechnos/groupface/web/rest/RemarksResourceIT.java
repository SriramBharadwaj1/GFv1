package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Remarks;
import com.laptechnos.groupface.repository.RemarksRepository;
import com.laptechnos.groupface.repository.search.RemarksSearchRepository;
import com.laptechnos.groupface.service.RemarksService;
import com.laptechnos.groupface.service.dto.RemarksDTO;
import com.laptechnos.groupface.service.mapper.RemarksMapper;
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
 * Integration tests for the {@link RemarksResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RemarksResourceIT {

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

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final String DEFAULT_OTHER_INFO = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/remarks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/remarks";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RemarksRepository remarksRepository;

    @Mock
    private RemarksRepository remarksRepositoryMock;

    @Autowired
    private RemarksMapper remarksMapper;

    @Mock
    private RemarksService remarksServiceMock;

    @Autowired
    private RemarksSearchRepository remarksSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemarksMockMvc;

    private Remarks remarks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remarks createEntity(EntityManager em) {
        Remarks remarks = new Remarks()
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
            .remarks(DEFAULT_REMARKS)
            .orgId(DEFAULT_ORG_ID)
            .otherInfo(DEFAULT_OTHER_INFO);
        return remarks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remarks createUpdatedEntity(EntityManager em) {
        Remarks remarks = new Remarks()
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
            .remarks(UPDATED_REMARKS)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);
        return remarks;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        remarksSearchRepository.deleteAll();
        assertThat(remarksSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        remarks = createEntity(em);
    }

    @Test
    @Transactional
    void createRemarks() throws Exception {
        int databaseSizeBeforeCreate = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        // Create the Remarks
        RemarksDTO remarksDTO = remarksMapper.toDto(remarks);
        restRemarksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remarksDTO)))
            .andExpect(status().isCreated());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Remarks testRemarks = remarksList.get(remarksList.size() - 1);
        assertThat(testRemarks.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testRemarks.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testRemarks.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testRemarks.getMeta()).isEqualTo(DEFAULT_META);
        assertThat(testRemarks.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testRemarks.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testRemarks.getApprRejReason()).isEqualTo(DEFAULT_APPR_REJ_REASON);
        assertThat(testRemarks.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testRemarks.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testRemarks.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testRemarks.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRemarks.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testRemarks.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testRemarks.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testRemarks.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testRemarks.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testRemarks.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testRemarks.getOtherInfo()).isEqualTo(DEFAULT_OTHER_INFO);
    }

    @Test
    @Transactional
    void createRemarksWithExistingId() throws Exception {
        // Create the Remarks with an existing ID
        remarks.setId(1L);
        RemarksDTO remarksDTO = remarksMapper.toDto(remarks);

        int databaseSizeBeforeCreate = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemarksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remarksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllRemarks() throws Exception {
        // Initialize the database
        remarksRepository.saveAndFlush(remarks);

        // Get all the remarksList
        restRemarksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remarks.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].otherInfo").value(hasItem(DEFAULT_OTHER_INFO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRemarksWithEagerRelationshipsIsEnabled() throws Exception {
        when(remarksServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRemarksMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(remarksServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRemarksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(remarksServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRemarksMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(remarksRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRemarks() throws Exception {
        // Initialize the database
        remarksRepository.saveAndFlush(remarks);

        // Get the remarks
        restRemarksMockMvc
            .perform(get(ENTITY_API_URL_ID, remarks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remarks.getId().intValue()))
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
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.otherInfo").value(DEFAULT_OTHER_INFO));
    }

    @Test
    @Transactional
    void getNonExistingRemarks() throws Exception {
        // Get the remarks
        restRemarksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRemarks() throws Exception {
        // Initialize the database
        remarksRepository.saveAndFlush(remarks);

        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();
        remarksSearchRepository.save(remarks);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());

        // Update the remarks
        Remarks updatedRemarks = remarksRepository.findById(remarks.getId()).get();
        // Disconnect from session so that the updates on updatedRemarks are not directly saved in db
        em.detach(updatedRemarks);
        updatedRemarks
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
            .remarks(UPDATED_REMARKS)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);
        RemarksDTO remarksDTO = remarksMapper.toDto(updatedRemarks);

        restRemarksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remarksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remarksDTO))
            )
            .andExpect(status().isOk());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        Remarks testRemarks = remarksList.get(remarksList.size() - 1);
        assertThat(testRemarks.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testRemarks.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testRemarks.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testRemarks.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testRemarks.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testRemarks.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testRemarks.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testRemarks.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testRemarks.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testRemarks.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testRemarks.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRemarks.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testRemarks.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testRemarks.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testRemarks.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testRemarks.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testRemarks.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testRemarks.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Remarks> remarksSearchList = IterableUtils.toList(remarksSearchRepository.findAll());
                Remarks testRemarksSearch = remarksSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testRemarksSearch.getMessage()).isEqualTo(UPDATED_MESSAGE);
                assertThat(testRemarksSearch.getCategory()).isEqualTo(UPDATED_CATEGORY);
                assertThat(testRemarksSearch.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
                assertThat(testRemarksSearch.getMeta()).isEqualTo(UPDATED_META);
                assertThat(testRemarksSearch.getPostId()).isEqualTo(UPDATED_POST_ID);
                assertThat(testRemarksSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testRemarksSearch.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
                assertThat(testRemarksSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testRemarksSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testRemarksSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testRemarksSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testRemarksSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testRemarksSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testRemarksSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testRemarksSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testRemarksSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testRemarksSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testRemarksSearch.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
            });
    }

    @Test
    @Transactional
    void putNonExistingRemarks() throws Exception {
        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        remarks.setId(count.incrementAndGet());

        // Create the Remarks
        RemarksDTO remarksDTO = remarksMapper.toDto(remarks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemarksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remarksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remarksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemarks() throws Exception {
        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        remarks.setId(count.incrementAndGet());

        // Create the Remarks
        RemarksDTO remarksDTO = remarksMapper.toDto(remarks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemarksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remarksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemarks() throws Exception {
        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        remarks.setId(count.incrementAndGet());

        // Create the Remarks
        RemarksDTO remarksDTO = remarksMapper.toDto(remarks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemarksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remarksDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateRemarksWithPatch() throws Exception {
        // Initialize the database
        remarksRepository.saveAndFlush(remarks);

        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();

        // Update the remarks using partial update
        Remarks partialUpdatedRemarks = new Remarks();
        partialUpdatedRemarks.setId(remarks.getId());

        partialUpdatedRemarks
            .postId(UPDATED_POST_ID)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .approvedBy(UPDATED_APPROVED_BY);

        restRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemarks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemarks))
            )
            .andExpect(status().isOk());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        Remarks testRemarks = remarksList.get(remarksList.size() - 1);
        assertThat(testRemarks.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testRemarks.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testRemarks.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testRemarks.getMeta()).isEqualTo(DEFAULT_META);
        assertThat(testRemarks.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testRemarks.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testRemarks.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testRemarks.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testRemarks.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testRemarks.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testRemarks.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRemarks.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testRemarks.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testRemarks.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testRemarks.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testRemarks.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testRemarks.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testRemarks.getOtherInfo()).isEqualTo(DEFAULT_OTHER_INFO);
    }

    @Test
    @Transactional
    void fullUpdateRemarksWithPatch() throws Exception {
        // Initialize the database
        remarksRepository.saveAndFlush(remarks);

        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();

        // Update the remarks using partial update
        Remarks partialUpdatedRemarks = new Remarks();
        partialUpdatedRemarks.setId(remarks.getId());

        partialUpdatedRemarks
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
            .remarks(UPDATED_REMARKS)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);

        restRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemarks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemarks))
            )
            .andExpect(status().isOk());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        Remarks testRemarks = remarksList.get(remarksList.size() - 1);
        assertThat(testRemarks.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testRemarks.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testRemarks.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testRemarks.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testRemarks.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testRemarks.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testRemarks.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testRemarks.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testRemarks.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testRemarks.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testRemarks.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRemarks.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testRemarks.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testRemarks.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testRemarks.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testRemarks.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testRemarks.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testRemarks.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingRemarks() throws Exception {
        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        remarks.setId(count.incrementAndGet());

        // Create the Remarks
        RemarksDTO remarksDTO = remarksMapper.toDto(remarks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remarksDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remarksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemarks() throws Exception {
        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        remarks.setId(count.incrementAndGet());

        // Create the Remarks
        RemarksDTO remarksDTO = remarksMapper.toDto(remarks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remarksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemarks() throws Exception {
        int databaseSizeBeforeUpdate = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        remarks.setId(count.incrementAndGet());

        // Create the Remarks
        RemarksDTO remarksDTO = remarksMapper.toDto(remarks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemarksMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(remarksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Remarks in the database
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteRemarks() throws Exception {
        // Initialize the database
        remarksRepository.saveAndFlush(remarks);
        remarksRepository.save(remarks);
        remarksSearchRepository.save(remarks);

        int databaseSizeBeforeDelete = remarksRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the remarks
        restRemarksMockMvc
            .perform(delete(ENTITY_API_URL_ID, remarks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Remarks> remarksList = remarksRepository.findAll();
        assertThat(remarksList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(remarksSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchRemarks() throws Exception {
        // Initialize the database
        remarks = remarksRepository.saveAndFlush(remarks);
        remarksSearchRepository.save(remarks);

        // Search the remarks
        restRemarksMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + remarks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remarks.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].otherInfo").value(hasItem(DEFAULT_OTHER_INFO)));
    }
}
