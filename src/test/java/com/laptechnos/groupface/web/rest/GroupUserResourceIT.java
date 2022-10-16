package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.GroupUser;
import com.laptechnos.groupface.repository.GroupUserRepository;
import com.laptechnos.groupface.repository.search.GroupUserSearchRepository;
import com.laptechnos.groupface.service.GroupUserService;
import com.laptechnos.groupface.service.dto.GroupUserDTO;
import com.laptechnos.groupface.service.mapper.GroupUserMapper;
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
 * Integration tests for the {@link GroupUserResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GroupUserResourceIT {

    private static final Long DEFAULT_GROUP_ID = 1L;
    private static final Long UPDATED_GROUP_ID = 2L;

    private static final Long DEFAULT_GRP_USER = 1L;
    private static final Long UPDATED_GRP_USER = 2L;

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

    private static final Long DEFAULT_USER_TYPE = 1L;
    private static final Long UPDATED_USER_TYPE = 2L;

    private static final String ENTITY_API_URL = "/api/group-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/group-users";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Mock
    private GroupUserRepository groupUserRepositoryMock;

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Mock
    private GroupUserService groupUserServiceMock;

    @Autowired
    private GroupUserSearchRepository groupUserSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupUserMockMvc;

    private GroupUser groupUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupUser createEntity(EntityManager em) {
        GroupUser groupUser = new GroupUser()
            .groupId(DEFAULT_GROUP_ID)
            .grpUser(DEFAULT_GRP_USER)
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
            .userType(DEFAULT_USER_TYPE);
        return groupUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupUser createUpdatedEntity(EntityManager em) {
        GroupUser groupUser = new GroupUser()
            .groupId(UPDATED_GROUP_ID)
            .grpUser(UPDATED_GRP_USER)
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
            .userType(UPDATED_USER_TYPE);
        return groupUser;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        groupUserSearchRepository.deleteAll();
        assertThat(groupUserSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        groupUser = createEntity(em);
    }

    @Test
    @Transactional
    void createGroupUser() throws Exception {
        int databaseSizeBeforeCreate = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        // Create the GroupUser
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(groupUser);
        restGroupUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupUserDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        GroupUser testGroupUser = groupUserList.get(groupUserList.size() - 1);
        assertThat(testGroupUser.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupUser.getGrpUser()).isEqualTo(DEFAULT_GRP_USER);
        assertThat(testGroupUser.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testGroupUser.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testGroupUser.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testGroupUser.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testGroupUser.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testGroupUser.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testGroupUser.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testGroupUser.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testGroupUser.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testGroupUser.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testGroupUser.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testGroupUser.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
    }

    @Test
    @Transactional
    void createGroupUserWithExistingId() throws Exception {
        // Create the GroupUser with an existing ID
        groupUser.setId(1L);
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(groupUser);

        int databaseSizeBeforeCreate = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllGroupUsers() throws Exception {
        // Initialize the database
        groupUserRepository.saveAndFlush(groupUser);

        // Get all the groupUserList
        restGroupUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].grpUser").value(hasItem(DEFAULT_GRP_USER.intValue())))
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
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGroupUsersWithEagerRelationshipsIsEnabled() throws Exception {
        when(groupUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupUserMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(groupUserServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGroupUsersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(groupUserServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGroupUserMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(groupUserRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGroupUser() throws Exception {
        // Initialize the database
        groupUserRepository.saveAndFlush(groupUser);

        // Get the groupUser
        restGroupUserMockMvc
            .perform(get(ENTITY_API_URL_ID, groupUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupUser.getId().intValue()))
            .andExpect(jsonPath("$.groupId").value(DEFAULT_GROUP_ID.intValue()))
            .andExpect(jsonPath("$.grpUser").value(DEFAULT_GRP_USER.intValue()))
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
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingGroupUser() throws Exception {
        // Get the groupUser
        restGroupUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGroupUser() throws Exception {
        // Initialize the database
        groupUserRepository.saveAndFlush(groupUser);

        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();
        groupUserSearchRepository.save(groupUser);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());

        // Update the groupUser
        GroupUser updatedGroupUser = groupUserRepository.findById(groupUser.getId()).get();
        // Disconnect from session so that the updates on updatedGroupUser are not directly saved in db
        em.detach(updatedGroupUser);
        updatedGroupUser
            .groupId(UPDATED_GROUP_ID)
            .grpUser(UPDATED_GRP_USER)
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
            .userType(UPDATED_USER_TYPE);
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(updatedGroupUser);

        restGroupUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        GroupUser testGroupUser = groupUserList.get(groupUserList.size() - 1);
        assertThat(testGroupUser.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupUser.getGrpUser()).isEqualTo(UPDATED_GRP_USER);
        assertThat(testGroupUser.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGroupUser.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testGroupUser.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testGroupUser.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testGroupUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testGroupUser.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testGroupUser.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testGroupUser.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testGroupUser.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testGroupUser.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testGroupUser.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testGroupUser.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<GroupUser> groupUserSearchList = IterableUtils.toList(groupUserSearchRepository.findAll());
                GroupUser testGroupUserSearch = groupUserSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testGroupUserSearch.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
                assertThat(testGroupUserSearch.getGrpUser()).isEqualTo(UPDATED_GRP_USER);
                assertThat(testGroupUserSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testGroupUserSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testGroupUserSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testGroupUserSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testGroupUserSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testGroupUserSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testGroupUserSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testGroupUserSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testGroupUserSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testGroupUserSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testGroupUserSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testGroupUserSearch.getUserType()).isEqualTo(UPDATED_USER_TYPE);
            });
    }

    @Test
    @Transactional
    void putNonExistingGroupUser() throws Exception {
        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        groupUser.setId(count.incrementAndGet());

        // Create the GroupUser
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(groupUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroupUser() throws Exception {
        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        groupUser.setId(count.incrementAndGet());

        // Create the GroupUser
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(groupUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroupUser() throws Exception {
        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        groupUser.setId(count.incrementAndGet());

        // Create the GroupUser
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(groupUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateGroupUserWithPatch() throws Exception {
        // Initialize the database
        groupUserRepository.saveAndFlush(groupUser);

        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();

        // Update the groupUser using partial update
        GroupUser partialUpdatedGroupUser = new GroupUser();
        partialUpdatedGroupUser.setId(groupUser.getId());

        partialUpdatedGroupUser
            .grpUser(UPDATED_GRP_USER)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .comments(UPDATED_COMMENTS)
            .orgId(UPDATED_ORG_ID);

        restGroupUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupUser))
            )
            .andExpect(status().isOk());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        GroupUser testGroupUser = groupUserList.get(groupUserList.size() - 1);
        assertThat(testGroupUser.getGroupId()).isEqualTo(DEFAULT_GROUP_ID);
        assertThat(testGroupUser.getGrpUser()).isEqualTo(UPDATED_GRP_USER);
        assertThat(testGroupUser.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testGroupUser.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testGroupUser.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testGroupUser.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testGroupUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testGroupUser.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testGroupUser.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testGroupUser.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testGroupUser.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testGroupUser.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testGroupUser.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testGroupUser.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateGroupUserWithPatch() throws Exception {
        // Initialize the database
        groupUserRepository.saveAndFlush(groupUser);

        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();

        // Update the groupUser using partial update
        GroupUser partialUpdatedGroupUser = new GroupUser();
        partialUpdatedGroupUser.setId(groupUser.getId());

        partialUpdatedGroupUser
            .groupId(UPDATED_GROUP_ID)
            .grpUser(UPDATED_GRP_USER)
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
            .userType(UPDATED_USER_TYPE);

        restGroupUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupUser))
            )
            .andExpect(status().isOk());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        GroupUser testGroupUser = groupUserList.get(groupUserList.size() - 1);
        assertThat(testGroupUser.getGroupId()).isEqualTo(UPDATED_GROUP_ID);
        assertThat(testGroupUser.getGrpUser()).isEqualTo(UPDATED_GRP_USER);
        assertThat(testGroupUser.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGroupUser.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testGroupUser.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testGroupUser.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testGroupUser.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testGroupUser.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testGroupUser.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testGroupUser.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testGroupUser.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testGroupUser.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testGroupUser.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testGroupUser.getUserType()).isEqualTo(UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingGroupUser() throws Exception {
        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        groupUser.setId(count.incrementAndGet());

        // Create the GroupUser
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(groupUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroupUser() throws Exception {
        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        groupUser.setId(count.incrementAndGet());

        // Create the GroupUser
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(groupUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroupUser() throws Exception {
        int databaseSizeBeforeUpdate = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        groupUser.setId(count.incrementAndGet());

        // Create the GroupUser
        GroupUserDTO groupUserDTO = groupUserMapper.toDto(groupUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(groupUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroupUser in the database
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteGroupUser() throws Exception {
        // Initialize the database
        groupUserRepository.saveAndFlush(groupUser);
        groupUserRepository.save(groupUser);
        groupUserSearchRepository.save(groupUser);

        int databaseSizeBeforeDelete = groupUserRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the groupUser
        restGroupUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, groupUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupUser> groupUserList = groupUserRepository.findAll();
        assertThat(groupUserList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groupUserSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchGroupUser() throws Exception {
        // Initialize the database
        groupUser = groupUserRepository.saveAndFlush(groupUser);
        groupUserSearchRepository.save(groupUser);

        // Search the groupUser
        restGroupUserMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + groupUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupId").value(hasItem(DEFAULT_GROUP_ID.intValue())))
            .andExpect(jsonPath("$.[*].grpUser").value(hasItem(DEFAULT_GRP_USER.intValue())))
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
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.intValue())));
    }
}
