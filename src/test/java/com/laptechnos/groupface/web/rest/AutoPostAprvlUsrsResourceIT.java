package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.AutoPostAprvlUsrs;
import com.laptechnos.groupface.repository.AutoPostAprvlUsrsRepository;
import com.laptechnos.groupface.repository.search.AutoPostAprvlUsrsSearchRepository;
import com.laptechnos.groupface.service.AutoPostAprvlUsrsService;
import com.laptechnos.groupface.service.dto.AutoPostAprvlUsrsDTO;
import com.laptechnos.groupface.service.mapper.AutoPostAprvlUsrsMapper;
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
 * Integration tests for the {@link AutoPostAprvlUsrsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AutoPostAprvlUsrsResourceIT {

    private static final Long DEFAULT_AP_USER_ID = 1L;
    private static final Long UPDATED_AP_USER_ID = 2L;

    private static final Integer DEFAULT_TABLE_KY = 1;
    private static final Integer UPDATED_TABLE_KY = 2;

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

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

    private static final String DEFAULT_EXTRA_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_FIELDS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/auto-post-aprvl-usrs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/auto-post-aprvl-usrs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AutoPostAprvlUsrsRepository autoPostAprvlUsrsRepository;

    @Mock
    private AutoPostAprvlUsrsRepository autoPostAprvlUsrsRepositoryMock;

    @Autowired
    private AutoPostAprvlUsrsMapper autoPostAprvlUsrsMapper;

    @Mock
    private AutoPostAprvlUsrsService autoPostAprvlUsrsServiceMock;

    @Autowired
    private AutoPostAprvlUsrsSearchRepository autoPostAprvlUsrsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutoPostAprvlUsrsMockMvc;

    private AutoPostAprvlUsrs autoPostAprvlUsrs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutoPostAprvlUsrs createEntity(EntityManager em) {
        AutoPostAprvlUsrs autoPostAprvlUsrs = new AutoPostAprvlUsrs()
            .apUserId(DEFAULT_AP_USER_ID)
            .tableKy(DEFAULT_TABLE_KY)
            .isActive(DEFAULT_IS_ACTIVE)
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
            .extraFields(DEFAULT_EXTRA_FIELDS);
        return autoPostAprvlUsrs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutoPostAprvlUsrs createUpdatedEntity(EntityManager em) {
        AutoPostAprvlUsrs autoPostAprvlUsrs = new AutoPostAprvlUsrs()
            .apUserId(UPDATED_AP_USER_ID)
            .tableKy(UPDATED_TABLE_KY)
            .isActive(UPDATED_IS_ACTIVE)
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
            .extraFields(UPDATED_EXTRA_FIELDS);
        return autoPostAprvlUsrs;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        autoPostAprvlUsrsSearchRepository.deleteAll();
        assertThat(autoPostAprvlUsrsSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        autoPostAprvlUsrs = createEntity(em);
    }

    @Test
    @Transactional
    void createAutoPostAprvlUsrs() throws Exception {
        int databaseSizeBeforeCreate = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        // Create the AutoPostAprvlUsrs
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);
        restAutoPostAprvlUsrsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        AutoPostAprvlUsrs testAutoPostAprvlUsrs = autoPostAprvlUsrsList.get(autoPostAprvlUsrsList.size() - 1);
        assertThat(testAutoPostAprvlUsrs.getApUserId()).isEqualTo(DEFAULT_AP_USER_ID);
        assertThat(testAutoPostAprvlUsrs.getTableKy()).isEqualTo(DEFAULT_TABLE_KY);
        assertThat(testAutoPostAprvlUsrs.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testAutoPostAprvlUsrs.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testAutoPostAprvlUsrs.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testAutoPostAprvlUsrs.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testAutoPostAprvlUsrs.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAutoPostAprvlUsrs.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testAutoPostAprvlUsrs.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testAutoPostAprvlUsrs.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testAutoPostAprvlUsrs.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testAutoPostAprvlUsrs.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAutoPostAprvlUsrs.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testAutoPostAprvlUsrs.getExtraFields()).isEqualTo(DEFAULT_EXTRA_FIELDS);
    }

    @Test
    @Transactional
    void createAutoPostAprvlUsrsWithExistingId() throws Exception {
        // Create the AutoPostAprvlUsrs with an existing ID
        autoPostAprvlUsrs.setId(1L);
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);

        int databaseSizeBeforeCreate = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoPostAprvlUsrsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllAutoPostAprvlUsrs() throws Exception {
        // Initialize the database
        autoPostAprvlUsrsRepository.saveAndFlush(autoPostAprvlUsrs);

        // Get all the autoPostAprvlUsrsList
        restAutoPostAprvlUsrsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoPostAprvlUsrs.getId().intValue())))
            .andExpect(jsonPath("$.[*].apUserId").value(hasItem(DEFAULT_AP_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].tableKy").value(hasItem(DEFAULT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
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
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAutoPostAprvlUsrsWithEagerRelationshipsIsEnabled() throws Exception {
        when(autoPostAprvlUsrsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAutoPostAprvlUsrsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(autoPostAprvlUsrsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAutoPostAprvlUsrsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(autoPostAprvlUsrsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAutoPostAprvlUsrsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(autoPostAprvlUsrsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAutoPostAprvlUsrs() throws Exception {
        // Initialize the database
        autoPostAprvlUsrsRepository.saveAndFlush(autoPostAprvlUsrs);

        // Get the autoPostAprvlUsrs
        restAutoPostAprvlUsrsMockMvc
            .perform(get(ENTITY_API_URL_ID, autoPostAprvlUsrs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autoPostAprvlUsrs.getId().intValue()))
            .andExpect(jsonPath("$.apUserId").value(DEFAULT_AP_USER_ID.intValue()))
            .andExpect(jsonPath("$.tableKy").value(DEFAULT_TABLE_KY))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
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
            .andExpect(jsonPath("$.extraFields").value(DEFAULT_EXTRA_FIELDS));
    }

    @Test
    @Transactional
    void getNonExistingAutoPostAprvlUsrs() throws Exception {
        // Get the autoPostAprvlUsrs
        restAutoPostAprvlUsrsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutoPostAprvlUsrs() throws Exception {
        // Initialize the database
        autoPostAprvlUsrsRepository.saveAndFlush(autoPostAprvlUsrs);

        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();
        autoPostAprvlUsrsSearchRepository.save(autoPostAprvlUsrs);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());

        // Update the autoPostAprvlUsrs
        AutoPostAprvlUsrs updatedAutoPostAprvlUsrs = autoPostAprvlUsrsRepository.findById(autoPostAprvlUsrs.getId()).get();
        // Disconnect from session so that the updates on updatedAutoPostAprvlUsrs are not directly saved in db
        em.detach(updatedAutoPostAprvlUsrs);
        updatedAutoPostAprvlUsrs
            .apUserId(UPDATED_AP_USER_ID)
            .tableKy(UPDATED_TABLE_KY)
            .isActive(UPDATED_IS_ACTIVE)
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
            .extraFields(UPDATED_EXTRA_FIELDS);
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(updatedAutoPostAprvlUsrs);

        restAutoPostAprvlUsrsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autoPostAprvlUsrsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        AutoPostAprvlUsrs testAutoPostAprvlUsrs = autoPostAprvlUsrsList.get(autoPostAprvlUsrsList.size() - 1);
        assertThat(testAutoPostAprvlUsrs.getApUserId()).isEqualTo(UPDATED_AP_USER_ID);
        assertThat(testAutoPostAprvlUsrs.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testAutoPostAprvlUsrs.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAutoPostAprvlUsrs.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testAutoPostAprvlUsrs.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testAutoPostAprvlUsrs.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testAutoPostAprvlUsrs.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAutoPostAprvlUsrs.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAutoPostAprvlUsrs.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testAutoPostAprvlUsrs.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testAutoPostAprvlUsrs.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAutoPostAprvlUsrs.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAutoPostAprvlUsrs.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testAutoPostAprvlUsrs.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<AutoPostAprvlUsrs> autoPostAprvlUsrsSearchList = IterableUtils.toList(autoPostAprvlUsrsSearchRepository.findAll());
                AutoPostAprvlUsrs testAutoPostAprvlUsrsSearch = autoPostAprvlUsrsSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testAutoPostAprvlUsrsSearch.getApUserId()).isEqualTo(UPDATED_AP_USER_ID);
                assertThat(testAutoPostAprvlUsrsSearch.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
                assertThat(testAutoPostAprvlUsrsSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testAutoPostAprvlUsrsSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testAutoPostAprvlUsrsSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testAutoPostAprvlUsrsSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testAutoPostAprvlUsrsSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testAutoPostAprvlUsrsSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testAutoPostAprvlUsrsSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testAutoPostAprvlUsrsSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testAutoPostAprvlUsrsSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testAutoPostAprvlUsrsSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testAutoPostAprvlUsrsSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testAutoPostAprvlUsrsSearch.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
            });
    }

    @Test
    @Transactional
    void putNonExistingAutoPostAprvlUsrs() throws Exception {
        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        autoPostAprvlUsrs.setId(count.incrementAndGet());

        // Create the AutoPostAprvlUsrs
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoPostAprvlUsrsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autoPostAprvlUsrsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutoPostAprvlUsrs() throws Exception {
        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        autoPostAprvlUsrs.setId(count.incrementAndGet());

        // Create the AutoPostAprvlUsrs
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPostAprvlUsrsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutoPostAprvlUsrs() throws Exception {
        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        autoPostAprvlUsrs.setId(count.incrementAndGet());

        // Create the AutoPostAprvlUsrs
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPostAprvlUsrsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateAutoPostAprvlUsrsWithPatch() throws Exception {
        // Initialize the database
        autoPostAprvlUsrsRepository.saveAndFlush(autoPostAprvlUsrs);

        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();

        // Update the autoPostAprvlUsrs using partial update
        AutoPostAprvlUsrs partialUpdatedAutoPostAprvlUsrs = new AutoPostAprvlUsrs();
        partialUpdatedAutoPostAprvlUsrs.setId(autoPostAprvlUsrs.getId());

        partialUpdatedAutoPostAprvlUsrs
            .apUserId(UPDATED_AP_USER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .addedOn(UPDATED_ADDED_ON)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .comments(UPDATED_COMMENTS);

        restAutoPostAprvlUsrsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoPostAprvlUsrs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutoPostAprvlUsrs))
            )
            .andExpect(status().isOk());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        AutoPostAprvlUsrs testAutoPostAprvlUsrs = autoPostAprvlUsrsList.get(autoPostAprvlUsrsList.size() - 1);
        assertThat(testAutoPostAprvlUsrs.getApUserId()).isEqualTo(UPDATED_AP_USER_ID);
        assertThat(testAutoPostAprvlUsrs.getTableKy()).isEqualTo(DEFAULT_TABLE_KY);
        assertThat(testAutoPostAprvlUsrs.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAutoPostAprvlUsrs.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testAutoPostAprvlUsrs.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testAutoPostAprvlUsrs.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testAutoPostAprvlUsrs.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAutoPostAprvlUsrs.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAutoPostAprvlUsrs.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testAutoPostAprvlUsrs.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testAutoPostAprvlUsrs.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAutoPostAprvlUsrs.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAutoPostAprvlUsrs.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testAutoPostAprvlUsrs.getExtraFields()).isEqualTo(DEFAULT_EXTRA_FIELDS);
    }

    @Test
    @Transactional
    void fullUpdateAutoPostAprvlUsrsWithPatch() throws Exception {
        // Initialize the database
        autoPostAprvlUsrsRepository.saveAndFlush(autoPostAprvlUsrs);

        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();

        // Update the autoPostAprvlUsrs using partial update
        AutoPostAprvlUsrs partialUpdatedAutoPostAprvlUsrs = new AutoPostAprvlUsrs();
        partialUpdatedAutoPostAprvlUsrs.setId(autoPostAprvlUsrs.getId());

        partialUpdatedAutoPostAprvlUsrs
            .apUserId(UPDATED_AP_USER_ID)
            .tableKy(UPDATED_TABLE_KY)
            .isActive(UPDATED_IS_ACTIVE)
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
            .extraFields(UPDATED_EXTRA_FIELDS);

        restAutoPostAprvlUsrsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoPostAprvlUsrs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutoPostAprvlUsrs))
            )
            .andExpect(status().isOk());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        AutoPostAprvlUsrs testAutoPostAprvlUsrs = autoPostAprvlUsrsList.get(autoPostAprvlUsrsList.size() - 1);
        assertThat(testAutoPostAprvlUsrs.getApUserId()).isEqualTo(UPDATED_AP_USER_ID);
        assertThat(testAutoPostAprvlUsrs.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testAutoPostAprvlUsrs.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAutoPostAprvlUsrs.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testAutoPostAprvlUsrs.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testAutoPostAprvlUsrs.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testAutoPostAprvlUsrs.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAutoPostAprvlUsrs.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAutoPostAprvlUsrs.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testAutoPostAprvlUsrs.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testAutoPostAprvlUsrs.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAutoPostAprvlUsrs.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAutoPostAprvlUsrs.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testAutoPostAprvlUsrs.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
    }

    @Test
    @Transactional
    void patchNonExistingAutoPostAprvlUsrs() throws Exception {
        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        autoPostAprvlUsrs.setId(count.incrementAndGet());

        // Create the AutoPostAprvlUsrs
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoPostAprvlUsrsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autoPostAprvlUsrsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutoPostAprvlUsrs() throws Exception {
        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        autoPostAprvlUsrs.setId(count.incrementAndGet());

        // Create the AutoPostAprvlUsrs
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPostAprvlUsrsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutoPostAprvlUsrs() throws Exception {
        int databaseSizeBeforeUpdate = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        autoPostAprvlUsrs.setId(count.incrementAndGet());

        // Create the AutoPostAprvlUsrs
        AutoPostAprvlUsrsDTO autoPostAprvlUsrsDTO = autoPostAprvlUsrsMapper.toDto(autoPostAprvlUsrs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPostAprvlUsrsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autoPostAprvlUsrsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AutoPostAprvlUsrs in the database
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteAutoPostAprvlUsrs() throws Exception {
        // Initialize the database
        autoPostAprvlUsrsRepository.saveAndFlush(autoPostAprvlUsrs);
        autoPostAprvlUsrsRepository.save(autoPostAprvlUsrs);
        autoPostAprvlUsrsSearchRepository.save(autoPostAprvlUsrs);

        int databaseSizeBeforeDelete = autoPostAprvlUsrsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the autoPostAprvlUsrs
        restAutoPostAprvlUsrsMockMvc
            .perform(delete(ENTITY_API_URL_ID, autoPostAprvlUsrs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutoPostAprvlUsrs> autoPostAprvlUsrsList = autoPostAprvlUsrsRepository.findAll();
        assertThat(autoPostAprvlUsrsList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(autoPostAprvlUsrsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchAutoPostAprvlUsrs() throws Exception {
        // Initialize the database
        autoPostAprvlUsrs = autoPostAprvlUsrsRepository.saveAndFlush(autoPostAprvlUsrs);
        autoPostAprvlUsrsSearchRepository.save(autoPostAprvlUsrs);

        // Search the autoPostAprvlUsrs
        restAutoPostAprvlUsrsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + autoPostAprvlUsrs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoPostAprvlUsrs.getId().intValue())))
            .andExpect(jsonPath("$.[*].apUserId").value(hasItem(DEFAULT_AP_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].tableKy").value(hasItem(DEFAULT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
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
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)));
    }
}
