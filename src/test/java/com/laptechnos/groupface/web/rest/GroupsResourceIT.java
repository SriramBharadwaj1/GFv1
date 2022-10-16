package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Groups;
import com.laptechnos.groupface.repository.GroupsRepository;
import com.laptechnos.groupface.repository.search.GroupsSearchRepository;
import com.laptechnos.groupface.service.GroupsService;
import com.laptechnos.groupface.service.dto.GroupsDTO;
import com.laptechnos.groupface.service.mapper.GroupsMapper;
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
 * Integration tests for the {@link GroupsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GroupsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACTIVITYS_ID = 1;
    private static final Integer UPDATED_ACTIVITYS_ID = 2;

    private static final Integer DEFAULT_GROUP_TYPE = 1;
    private static final Integer UPDATED_GROUP_TYPE = 2;

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

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

    private static final LocalDate DEFAULT_APPROVED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TILL = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/groups";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GroupsRepository groupsRepository;

    @Mock
    private GroupsRepository groupsRepositoryMock;

    @Autowired
    private GroupsMapper groupsMapper;

    @Mock
    private GroupsService groupsServiceMock;

    @Autowired
    private GroupsSearchRepository groupsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupsMockMvc;

    private Groups groups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groups createEntity(EntityManager em) {
        Groups groups = new Groups()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .activitysId(DEFAULT_ACTIVITYS_ID)
            .groupType(DEFAULT_GROUP_TYPE)
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
            .validFrom(DEFAULT_VALID_FROM)
            .validTill(DEFAULT_VALID_TILL);
        return groups;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groups createUpdatedEntity(EntityManager em) {
        Groups groups = new Groups()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .activitysId(UPDATED_ACTIVITYS_ID)
            .groupType(UPDATED_GROUP_TYPE)
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
            .validFrom(UPDATED_VALID_FROM)
            .validTill(UPDATED_VALID_TILL);
        return groups;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        groupsSearchRepository.deleteAll();
        assertThat(groupsSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        groups = createEntity(em);
    }

    @Test
    @Transactional
    void createGroups() throws Exception {
        int databaseSizeBeforeCreate = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);
        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isCreated());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroups.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGroups.getActivitysId()).isEqualTo(DEFAULT_ACTIVITYS_ID);
        assertThat(testGroups.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
        assertThat(testGroups.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testGroups.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testGroups.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testGroups.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testGroups.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testGroups.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testGroups.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testGroups.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testGroups.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testGroups.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testGroups.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testGroups.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testGroups.getValidTill()).isEqualTo(DEFAULT_VALID_TILL);
    }

    @Test
    @Transactional
    void createGroupsWithExistingId() throws Exception {
        // Create the Groups with an existing ID
        groups.setId(1L);
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        int databaseSizeBeforeCreate = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        // Get all the groupsList
        restGroupsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].activitysId").value(hasItem(DEFAULT_ACTIVITYS_ID)))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        when(groupsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(groupsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(groupsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(groupsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        // Get the groups
        restGroupsMockMvc
            .perform(get(ENTITY_API_URL_ID, groups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groups.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.activitysId").value(DEFAULT_ACTIVITYS_ID))
            .andExpect(jsonPath("$.groupType").value(DEFAULT_GROUP_TYPE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTill").value(DEFAULT_VALID_TILL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGroups() throws Exception {
        // Get the groups
        restGroupsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        groupsSearchRepository.save(groups);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());

        // Update the groups
        Groups updatedGroups = groupsRepository.findById(groups.getId()).get();
        // Disconnect from session so that the updates on updatedGroups are not directly saved in db
        em.detach(updatedGroups);
        updatedGroups
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .activitysId(UPDATED_ACTIVITYS_ID)
            .groupType(UPDATED_GROUP_TYPE)
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
            .validFrom(UPDATED_VALID_FROM)
            .validTill(UPDATED_VALID_TILL);
        GroupsDTO groupsDTO = groupsMapper.toDto(updatedGroups);

        restGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroups.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGroups.getActivitysId()).isEqualTo(UPDATED_ACTIVITYS_ID);
        assertThat(testGroups.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
        assertThat(testGroups.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGroups.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testGroups.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testGroups.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testGroups.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testGroups.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testGroups.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testGroups.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testGroups.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testGroups.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testGroups.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testGroups.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testGroups.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Groups> groupsSearchList = IterableUtils.toList(groupsSearchRepository.findAll());
                Groups testGroupsSearch = groupsSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testGroupsSearch.getName()).isEqualTo(UPDATED_NAME);
                assertThat(testGroupsSearch.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
                assertThat(testGroupsSearch.getActivitysId()).isEqualTo(UPDATED_ACTIVITYS_ID);
                assertThat(testGroupsSearch.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
                assertThat(testGroupsSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testGroupsSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testGroupsSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testGroupsSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testGroupsSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testGroupsSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testGroupsSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testGroupsSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testGroupsSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testGroupsSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testGroupsSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testGroupsSearch.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
                assertThat(testGroupsSearch.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
            });
    }

    @Test
    @Transactional
    void putNonExistingGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateGroupsWithPatch() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();

        // Update the groups using partial update
        Groups partialUpdatedGroups = new Groups();
        partialUpdatedGroups.setId(groups.getId());

        partialUpdatedGroups
            .description(UPDATED_DESCRIPTION)
            .activitysId(UPDATED_ACTIVITYS_ID)
            .isEnable(UPDATED_IS_ENABLE)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .remarks(UPDATED_REMARKS);

        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroups.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroups))
            )
            .andExpect(status().isOk());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroups.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGroups.getActivitysId()).isEqualTo(UPDATED_ACTIVITYS_ID);
        assertThat(testGroups.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
        assertThat(testGroups.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testGroups.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testGroups.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testGroups.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testGroups.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testGroups.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testGroups.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testGroups.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testGroups.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testGroups.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testGroups.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testGroups.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testGroups.getValidTill()).isEqualTo(DEFAULT_VALID_TILL);
    }

    @Test
    @Transactional
    void fullUpdateGroupsWithPatch() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);

        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();

        // Update the groups using partial update
        Groups partialUpdatedGroups = new Groups();
        partialUpdatedGroups.setId(groups.getId());

        partialUpdatedGroups
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .activitysId(UPDATED_ACTIVITYS_ID)
            .groupType(UPDATED_GROUP_TYPE)
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
            .validFrom(UPDATED_VALID_FROM)
            .validTill(UPDATED_VALID_TILL);

        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroups.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroups))
            )
            .andExpect(status().isOk());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        Groups testGroups = groupsList.get(groupsList.size() - 1);
        assertThat(testGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroups.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGroups.getActivitysId()).isEqualTo(UPDATED_ACTIVITYS_ID);
        assertThat(testGroups.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
        assertThat(testGroups.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGroups.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testGroups.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testGroups.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testGroups.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testGroups.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testGroups.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testGroups.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testGroups.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testGroups.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testGroups.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testGroups.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testGroups.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
    }

    @Test
    @Transactional
    void patchNonExistingGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroups() throws Exception {
        int databaseSizeBeforeUpdate = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        groups.setId(count.incrementAndGet());

        // Create the Groups
        GroupsDTO groupsDTO = groupsMapper.toDto(groups);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(groupsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Groups in the database
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteGroups() throws Exception {
        // Initialize the database
        groupsRepository.saveAndFlush(groups);
        groupsRepository.save(groups);
        groupsSearchRepository.save(groups);

        int databaseSizeBeforeDelete = groupsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the groups
        restGroupsMockMvc
            .perform(delete(ENTITY_API_URL_ID, groups.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Groups> groupsList = groupsRepository.findAll();
        assertThat(groupsList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchGroups() throws Exception {
        // Initialize the database
        groups = groupsRepository.saveAndFlush(groups);
        groupsSearchRepository.save(groups);

        // Search the groups
        restGroupsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + groups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].activitysId").value(hasItem(DEFAULT_ACTIVITYS_ID)))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.toString())));
    }
}
