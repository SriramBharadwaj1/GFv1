package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.OrgDetails;
import com.laptechnos.groupface.repository.OrgDetailsRepository;
import com.laptechnos.groupface.repository.search.OrgDetailsSearchRepository;
import com.laptechnos.groupface.service.OrgDetailsService;
import com.laptechnos.groupface.service.dto.OrgDetailsDTO;
import com.laptechnos.groupface.service.mapper.OrgDetailsMapper;
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
 * Integration tests for the {@link OrgDetailsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrgDetailsResourceIT {

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String DEFAULT_KEY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_KEY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_KEY_VALUE = "BBBBBBBBBB";

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

    private static final String DEFAULT_EXTRA_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_FIELDS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/org-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/org-details";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrgDetailsRepository orgDetailsRepository;

    @Mock
    private OrgDetailsRepository orgDetailsRepositoryMock;

    @Autowired
    private OrgDetailsMapper orgDetailsMapper;

    @Mock
    private OrgDetailsService orgDetailsServiceMock;

    @Autowired
    private OrgDetailsSearchRepository orgDetailsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgDetailsMockMvc;

    private OrgDetails orgDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgDetails createEntity(EntityManager em) {
        OrgDetails orgDetails = new OrgDetails()
            .orgId(DEFAULT_ORG_ID)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .keyName(DEFAULT_KEY_NAME)
            .keyValue(DEFAULT_KEY_VALUE)
            .isActive(DEFAULT_IS_ACTIVE)
            .isEnable(DEFAULT_IS_ENABLE)
            .addedBy(DEFAULT_ADDED_BY)
            .addedOn(DEFAULT_ADDED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .approvedBy(DEFAULT_APPROVED_BY)
            .approvedOn(DEFAULT_APPROVED_ON)
            .extraFields(DEFAULT_EXTRA_FIELDS);
        return orgDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgDetails createUpdatedEntity(EntityManager em) {
        OrgDetails orgDetails = new OrgDetails()
            .orgId(UPDATED_ORG_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .keyName(UPDATED_KEY_NAME)
            .keyValue(UPDATED_KEY_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .extraFields(UPDATED_EXTRA_FIELDS);
        return orgDetails;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        orgDetailsSearchRepository.deleteAll();
        assertThat(orgDetailsSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        orgDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createOrgDetails() throws Exception {
        int databaseSizeBeforeCreate = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        // Create the OrgDetails
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(orgDetails);
        restOrgDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        OrgDetails testOrgDetails = orgDetailsList.get(orgDetailsList.size() - 1);
        assertThat(testOrgDetails.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testOrgDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrgDetails.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrgDetails.getKeyName()).isEqualTo(DEFAULT_KEY_NAME);
        assertThat(testOrgDetails.getKeyValue()).isEqualTo(DEFAULT_KEY_VALUE);
        assertThat(testOrgDetails.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testOrgDetails.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testOrgDetails.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testOrgDetails.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testOrgDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOrgDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testOrgDetails.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testOrgDetails.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testOrgDetails.getExtraFields()).isEqualTo(DEFAULT_EXTRA_FIELDS);
    }

    @Test
    @Transactional
    void createOrgDetailsWithExistingId() throws Exception {
        // Create the OrgDetails with an existing ID
        orgDetails.setId(1L);
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(orgDetails);

        int databaseSizeBeforeCreate = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllOrgDetails() throws Exception {
        // Initialize the database
        orgDetailsRepository.saveAndFlush(orgDetails);

        // Get all the orgDetailsList
        restOrgDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].keyName").value(hasItem(DEFAULT_KEY_NAME)))
            .andExpect(jsonPath("$.[*].keyValue").value(hasItem(DEFAULT_KEY_VALUE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrgDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(orgDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrgDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(orgDetailsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrgDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(orgDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrgDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(orgDetailsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOrgDetails() throws Exception {
        // Initialize the database
        orgDetailsRepository.saveAndFlush(orgDetails);

        // Get the orgDetails
        restOrgDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, orgDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orgDetails.getId().intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.keyName").value(DEFAULT_KEY_NAME))
            .andExpect(jsonPath("$.keyValue").value(DEFAULT_KEY_VALUE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()))
            .andExpect(jsonPath("$.extraFields").value(DEFAULT_EXTRA_FIELDS));
    }

    @Test
    @Transactional
    void getNonExistingOrgDetails() throws Exception {
        // Get the orgDetails
        restOrgDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrgDetails() throws Exception {
        // Initialize the database
        orgDetailsRepository.saveAndFlush(orgDetails);

        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();
        orgDetailsSearchRepository.save(orgDetails);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());

        // Update the orgDetails
        OrgDetails updatedOrgDetails = orgDetailsRepository.findById(orgDetails.getId()).get();
        // Disconnect from session so that the updates on updatedOrgDetails are not directly saved in db
        em.detach(updatedOrgDetails);
        updatedOrgDetails
            .orgId(UPDATED_ORG_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .keyName(UPDATED_KEY_NAME)
            .keyValue(UPDATED_KEY_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .extraFields(UPDATED_EXTRA_FIELDS);
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(updatedOrgDetails);

        restOrgDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrgDetails testOrgDetails = orgDetailsList.get(orgDetailsList.size() - 1);
        assertThat(testOrgDetails.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testOrgDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrgDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrgDetails.getKeyName()).isEqualTo(UPDATED_KEY_NAME);
        assertThat(testOrgDetails.getKeyValue()).isEqualTo(UPDATED_KEY_VALUE);
        assertThat(testOrgDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOrgDetails.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testOrgDetails.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testOrgDetails.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testOrgDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrgDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testOrgDetails.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testOrgDetails.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testOrgDetails.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<OrgDetails> orgDetailsSearchList = IterableUtils.toList(orgDetailsSearchRepository.findAll());
                OrgDetails testOrgDetailsSearch = orgDetailsSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testOrgDetailsSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testOrgDetailsSearch.getName()).isEqualTo(UPDATED_NAME);
                assertThat(testOrgDetailsSearch.getType()).isEqualTo(UPDATED_TYPE);
                assertThat(testOrgDetailsSearch.getKeyName()).isEqualTo(UPDATED_KEY_NAME);
                assertThat(testOrgDetailsSearch.getKeyValue()).isEqualTo(UPDATED_KEY_VALUE);
                assertThat(testOrgDetailsSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testOrgDetailsSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testOrgDetailsSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testOrgDetailsSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testOrgDetailsSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testOrgDetailsSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testOrgDetailsSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testOrgDetailsSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testOrgDetailsSearch.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
            });
    }

    @Test
    @Transactional
    void putNonExistingOrgDetails() throws Exception {
        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        orgDetails.setId(count.incrementAndGet());

        // Create the OrgDetails
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(orgDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrgDetails() throws Exception {
        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        orgDetails.setId(count.incrementAndGet());

        // Create the OrgDetails
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(orgDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrgDetails() throws Exception {
        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        orgDetails.setId(count.incrementAndGet());

        // Create the OrgDetails
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(orgDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateOrgDetailsWithPatch() throws Exception {
        // Initialize the database
        orgDetailsRepository.saveAndFlush(orgDetails);

        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();

        // Update the orgDetails using partial update
        OrgDetails partialUpdatedOrgDetails = new OrgDetails();
        partialUpdatedOrgDetails.setId(orgDetails.getId());

        partialUpdatedOrgDetails
            .orgId(UPDATED_ORG_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .keyName(UPDATED_KEY_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .approvedBy(UPDATED_APPROVED_BY);

        restOrgDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgDetails))
            )
            .andExpect(status().isOk());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrgDetails testOrgDetails = orgDetailsList.get(orgDetailsList.size() - 1);
        assertThat(testOrgDetails.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testOrgDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrgDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrgDetails.getKeyName()).isEqualTo(UPDATED_KEY_NAME);
        assertThat(testOrgDetails.getKeyValue()).isEqualTo(DEFAULT_KEY_VALUE);
        assertThat(testOrgDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOrgDetails.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testOrgDetails.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testOrgDetails.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testOrgDetails.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOrgDetails.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testOrgDetails.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testOrgDetails.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testOrgDetails.getExtraFields()).isEqualTo(DEFAULT_EXTRA_FIELDS);
    }

    @Test
    @Transactional
    void fullUpdateOrgDetailsWithPatch() throws Exception {
        // Initialize the database
        orgDetailsRepository.saveAndFlush(orgDetails);

        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();

        // Update the orgDetails using partial update
        OrgDetails partialUpdatedOrgDetails = new OrgDetails();
        partialUpdatedOrgDetails.setId(orgDetails.getId());

        partialUpdatedOrgDetails
            .orgId(UPDATED_ORG_ID)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .keyName(UPDATED_KEY_NAME)
            .keyValue(UPDATED_KEY_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .extraFields(UPDATED_EXTRA_FIELDS);

        restOrgDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgDetails))
            )
            .andExpect(status().isOk());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        OrgDetails testOrgDetails = orgDetailsList.get(orgDetailsList.size() - 1);
        assertThat(testOrgDetails.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testOrgDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrgDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrgDetails.getKeyName()).isEqualTo(UPDATED_KEY_NAME);
        assertThat(testOrgDetails.getKeyValue()).isEqualTo(UPDATED_KEY_VALUE);
        assertThat(testOrgDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOrgDetails.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testOrgDetails.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testOrgDetails.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testOrgDetails.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrgDetails.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testOrgDetails.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testOrgDetails.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testOrgDetails.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
    }

    @Test
    @Transactional
    void patchNonExistingOrgDetails() throws Exception {
        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        orgDetails.setId(count.incrementAndGet());

        // Create the OrgDetails
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(orgDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orgDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrgDetails() throws Exception {
        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        orgDetails.setId(count.incrementAndGet());

        // Create the OrgDetails
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(orgDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrgDetails() throws Exception {
        int databaseSizeBeforeUpdate = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        orgDetails.setId(count.incrementAndGet());

        // Create the OrgDetails
        OrgDetailsDTO orgDetailsDTO = orgDetailsMapper.toDto(orgDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orgDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrgDetails in the database
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteOrgDetails() throws Exception {
        // Initialize the database
        orgDetailsRepository.saveAndFlush(orgDetails);
        orgDetailsRepository.save(orgDetails);
        orgDetailsSearchRepository.save(orgDetails);

        int databaseSizeBeforeDelete = orgDetailsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the orgDetails
        restOrgDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, orgDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrgDetails> orgDetailsList = orgDetailsRepository.findAll();
        assertThat(orgDetailsList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(orgDetailsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchOrgDetails() throws Exception {
        // Initialize the database
        orgDetails = orgDetailsRepository.saveAndFlush(orgDetails);
        orgDetailsSearchRepository.save(orgDetails);

        // Search the orgDetails
        restOrgDetailsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + orgDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].keyName").value(hasItem(DEFAULT_KEY_NAME)))
            .andExpect(jsonPath("$.[*].keyValue").value(hasItem(DEFAULT_KEY_VALUE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)));
    }
}
