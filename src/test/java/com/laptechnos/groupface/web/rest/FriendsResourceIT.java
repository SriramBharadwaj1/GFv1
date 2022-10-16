package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Friends;
import com.laptechnos.groupface.repository.FriendsRepository;
import com.laptechnos.groupface.repository.search.FriendsSearchRepository;
import com.laptechnos.groupface.service.FriendsService;
import com.laptechnos.groupface.service.dto.FriendsDTO;
import com.laptechnos.groupface.service.mapper.FriendsMapper;
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
 * Integration tests for the {@link FriendsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FriendsResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_FRIEND_ID = 1L;
    private static final Long UPDATED_FRIEND_ID = 2L;

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

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final String ENTITY_API_URL = "/api/friends";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/friends";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FriendsRepository friendsRepository;

    @Mock
    private FriendsRepository friendsRepositoryMock;

    @Autowired
    private FriendsMapper friendsMapper;

    @Mock
    private FriendsService friendsServiceMock;

    @Autowired
    private FriendsSearchRepository friendsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFriendsMockMvc;

    private Friends friends;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friends createEntity(EntityManager em) {
        Friends friends = new Friends()
            .userId(DEFAULT_USER_ID)
            .friendId(DEFAULT_FRIEND_ID)
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
            .type(DEFAULT_TYPE);
        return friends;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Friends createUpdatedEntity(EntityManager em) {
        Friends friends = new Friends()
            .userId(UPDATED_USER_ID)
            .friendId(UPDATED_FRIEND_ID)
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
            .type(UPDATED_TYPE);
        return friends;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        friendsSearchRepository.deleteAll();
        assertThat(friendsSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        friends = createEntity(em);
    }

    @Test
    @Transactional
    void createFriends() throws Exception {
        int databaseSizeBeforeCreate = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);
        restFriendsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isCreated());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testFriends.getFriendId()).isEqualTo(DEFAULT_FRIEND_ID);
        assertThat(testFriends.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFriends.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testFriends.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testFriends.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testFriends.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFriends.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testFriends.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testFriends.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testFriends.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testFriends.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testFriends.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testFriends.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createFriendsWithExistingId() throws Exception {
        // Create the Friends with an existing ID
        friends.setId(1L);
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        int databaseSizeBeforeCreate = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restFriendsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        // Get all the friendsList
        restFriendsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friends.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].friendId").value(hasItem(DEFAULT_FRIEND_ID.intValue())))
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
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFriendsWithEagerRelationshipsIsEnabled() throws Exception {
        when(friendsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFriendsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(friendsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFriendsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(friendsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFriendsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(friendsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        // Get the friends
        restFriendsMockMvc
            .perform(get(ENTITY_API_URL_ID, friends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(friends.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.friendId").value(DEFAULT_FRIEND_ID.intValue()))
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
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingFriends() throws Exception {
        // Get the friends
        restFriendsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        friendsSearchRepository.save(friends);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());

        // Update the friends
        Friends updatedFriends = friendsRepository.findById(friends.getId()).get();
        // Disconnect from session so that the updates on updatedFriends are not directly saved in db
        em.detach(updatedFriends);
        updatedFriends
            .userId(UPDATED_USER_ID)
            .friendId(UPDATED_FRIEND_ID)
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
            .type(UPDATED_TYPE);
        FriendsDTO friendsDTO = friendsMapper.toDto(updatedFriends);

        restFriendsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, friendsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFriends.getFriendId()).isEqualTo(UPDATED_FRIEND_ID);
        assertThat(testFriends.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFriends.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testFriends.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testFriends.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testFriends.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFriends.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testFriends.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testFriends.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testFriends.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testFriends.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testFriends.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testFriends.getType()).isEqualTo(UPDATED_TYPE);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Friends> friendsSearchList = IterableUtils.toList(friendsSearchRepository.findAll());
                Friends testFriendsSearch = friendsSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testFriendsSearch.getUserId()).isEqualTo(UPDATED_USER_ID);
                assertThat(testFriendsSearch.getFriendId()).isEqualTo(UPDATED_FRIEND_ID);
                assertThat(testFriendsSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testFriendsSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testFriendsSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testFriendsSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testFriendsSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testFriendsSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testFriendsSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testFriendsSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testFriendsSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testFriendsSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testFriendsSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testFriendsSearch.getType()).isEqualTo(UPDATED_TYPE);
            });
    }

    @Test
    @Transactional
    void putNonExistingFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, friendsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(friendsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateFriendsWithPatch() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Update the friends using partial update
        Friends partialUpdatedFriends = new Friends();
        partialUpdatedFriends.setId(friends.getId());

        partialUpdatedFriends
            .userId(UPDATED_USER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .addedOn(UPDATED_ADDED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .type(UPDATED_TYPE);

        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFriends.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFriends))
            )
            .andExpect(status().isOk());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFriends.getFriendId()).isEqualTo(DEFAULT_FRIEND_ID);
        assertThat(testFriends.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFriends.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testFriends.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testFriends.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testFriends.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFriends.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testFriends.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testFriends.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testFriends.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testFriends.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testFriends.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testFriends.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFriendsWithPatch() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);

        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();

        // Update the friends using partial update
        Friends partialUpdatedFriends = new Friends();
        partialUpdatedFriends.setId(friends.getId());

        partialUpdatedFriends
            .userId(UPDATED_USER_ID)
            .friendId(UPDATED_FRIEND_ID)
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
            .type(UPDATED_TYPE);

        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFriends.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFriends))
            )
            .andExpect(status().isOk());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        Friends testFriends = friendsList.get(friendsList.size() - 1);
        assertThat(testFriends.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testFriends.getFriendId()).isEqualTo(UPDATED_FRIEND_ID);
        assertThat(testFriends.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFriends.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testFriends.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testFriends.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testFriends.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFriends.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testFriends.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testFriends.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testFriends.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testFriends.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testFriends.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testFriends.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, friendsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFriends() throws Exception {
        int databaseSizeBeforeUpdate = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        friends.setId(count.incrementAndGet());

        // Create the Friends
        FriendsDTO friendsDTO = friendsMapper.toDto(friends);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFriendsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(friendsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Friends in the database
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteFriends() throws Exception {
        // Initialize the database
        friendsRepository.saveAndFlush(friends);
        friendsRepository.save(friends);
        friendsSearchRepository.save(friends);

        int databaseSizeBeforeDelete = friendsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the friends
        restFriendsMockMvc
            .perform(delete(ENTITY_API_URL_ID, friends.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Friends> friendsList = friendsRepository.findAll();
        assertThat(friendsList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(friendsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchFriends() throws Exception {
        // Initialize the database
        friends = friendsRepository.saveAndFlush(friends);
        friendsSearchRepository.save(friends);

        // Search the friends
        restFriendsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + friends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(friends.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].friendId").value(hasItem(DEFAULT_FRIEND_ID.intValue())))
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
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
}
