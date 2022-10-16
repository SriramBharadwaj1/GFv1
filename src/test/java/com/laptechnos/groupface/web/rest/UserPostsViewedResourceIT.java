package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.UserPostsViewed;
import com.laptechnos.groupface.repository.UserPostsViewedRepository;
import com.laptechnos.groupface.repository.search.UserPostsViewedSearchRepository;
import com.laptechnos.groupface.service.UserPostsViewedService;
import com.laptechnos.groupface.service.dto.UserPostsViewedDTO;
import com.laptechnos.groupface.service.mapper.UserPostsViewedMapper;
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
 * Integration tests for the {@link UserPostsViewedResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserPostsViewedResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_POSTID = 1L;
    private static final Long UPDATED_POSTID = 2L;

    private static final LocalDate DEFAULT_VIEWDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VIEWDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final String ENTITY_API_URL = "/api/user-posts-vieweds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/user-posts-vieweds";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserPostsViewedRepository userPostsViewedRepository;

    @Mock
    private UserPostsViewedRepository userPostsViewedRepositoryMock;

    @Autowired
    private UserPostsViewedMapper userPostsViewedMapper;

    @Mock
    private UserPostsViewedService userPostsViewedServiceMock;

    @Autowired
    private UserPostsViewedSearchRepository userPostsViewedSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserPostsViewedMockMvc;

    private UserPostsViewed userPostsViewed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPostsViewed createEntity(EntityManager em) {
        UserPostsViewed userPostsViewed = new UserPostsViewed()
            .userId(DEFAULT_USER_ID)
            .postid(DEFAULT_POSTID)
            .viewdate(DEFAULT_VIEWDATE)
            .status(DEFAULT_STATUS)
            .orgId(DEFAULT_ORG_ID);
        return userPostsViewed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPostsViewed createUpdatedEntity(EntityManager em) {
        UserPostsViewed userPostsViewed = new UserPostsViewed()
            .userId(UPDATED_USER_ID)
            .postid(UPDATED_POSTID)
            .viewdate(UPDATED_VIEWDATE)
            .status(UPDATED_STATUS)
            .orgId(UPDATED_ORG_ID);
        return userPostsViewed;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        userPostsViewedSearchRepository.deleteAll();
        assertThat(userPostsViewedSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        userPostsViewed = createEntity(em);
    }

    @Test
    @Transactional
    void createUserPostsViewed() throws Exception {
        int databaseSizeBeforeCreate = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        // Create the UserPostsViewed
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(userPostsViewed);
        restUserPostsViewedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        UserPostsViewed testUserPostsViewed = userPostsViewedList.get(userPostsViewedList.size() - 1);
        assertThat(testUserPostsViewed.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserPostsViewed.getPostid()).isEqualTo(DEFAULT_POSTID);
        assertThat(testUserPostsViewed.getViewdate()).isEqualTo(DEFAULT_VIEWDATE);
        assertThat(testUserPostsViewed.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserPostsViewed.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
    }

    @Test
    @Transactional
    void createUserPostsViewedWithExistingId() throws Exception {
        // Create the UserPostsViewed with an existing ID
        userPostsViewed.setId(1L);
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(userPostsViewed);

        int databaseSizeBeforeCreate = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPostsViewedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllUserPostsVieweds() throws Exception {
        // Initialize the database
        userPostsViewedRepository.saveAndFlush(userPostsViewed);

        // Get all the userPostsViewedList
        restUserPostsViewedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPostsViewed.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].postid").value(hasItem(DEFAULT_POSTID.intValue())))
            .andExpect(jsonPath("$.[*].viewdate").value(hasItem(DEFAULT_VIEWDATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserPostsViewedsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userPostsViewedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserPostsViewedMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userPostsViewedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserPostsViewedsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userPostsViewedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserPostsViewedMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(userPostsViewedRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUserPostsViewed() throws Exception {
        // Initialize the database
        userPostsViewedRepository.saveAndFlush(userPostsViewed);

        // Get the userPostsViewed
        restUserPostsViewedMockMvc
            .perform(get(ENTITY_API_URL_ID, userPostsViewed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userPostsViewed.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.postid").value(DEFAULT_POSTID.intValue()))
            .andExpect(jsonPath("$.viewdate").value(DEFAULT_VIEWDATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserPostsViewed() throws Exception {
        // Get the userPostsViewed
        restUserPostsViewedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserPostsViewed() throws Exception {
        // Initialize the database
        userPostsViewedRepository.saveAndFlush(userPostsViewed);

        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();
        userPostsViewedSearchRepository.save(userPostsViewed);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());

        // Update the userPostsViewed
        UserPostsViewed updatedUserPostsViewed = userPostsViewedRepository.findById(userPostsViewed.getId()).get();
        // Disconnect from session so that the updates on updatedUserPostsViewed are not directly saved in db
        em.detach(updatedUserPostsViewed);
        updatedUserPostsViewed
            .userId(UPDATED_USER_ID)
            .postid(UPDATED_POSTID)
            .viewdate(UPDATED_VIEWDATE)
            .status(UPDATED_STATUS)
            .orgId(UPDATED_ORG_ID);
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(updatedUserPostsViewed);

        restUserPostsViewedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userPostsViewedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        UserPostsViewed testUserPostsViewed = userPostsViewedList.get(userPostsViewedList.size() - 1);
        assertThat(testUserPostsViewed.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserPostsViewed.getPostid()).isEqualTo(UPDATED_POSTID);
        assertThat(testUserPostsViewed.getViewdate()).isEqualTo(UPDATED_VIEWDATE);
        assertThat(testUserPostsViewed.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserPostsViewed.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<UserPostsViewed> userPostsViewedSearchList = IterableUtils.toList(userPostsViewedSearchRepository.findAll());
                UserPostsViewed testUserPostsViewedSearch = userPostsViewedSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testUserPostsViewedSearch.getUserId()).isEqualTo(UPDATED_USER_ID);
                assertThat(testUserPostsViewedSearch.getPostid()).isEqualTo(UPDATED_POSTID);
                assertThat(testUserPostsViewedSearch.getViewdate()).isEqualTo(UPDATED_VIEWDATE);
                assertThat(testUserPostsViewedSearch.getStatus()).isEqualTo(UPDATED_STATUS);
                assertThat(testUserPostsViewedSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
            });
    }

    @Test
    @Transactional
    void putNonExistingUserPostsViewed() throws Exception {
        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        userPostsViewed.setId(count.incrementAndGet());

        // Create the UserPostsViewed
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(userPostsViewed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPostsViewedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userPostsViewedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserPostsViewed() throws Exception {
        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        userPostsViewed.setId(count.incrementAndGet());

        // Create the UserPostsViewed
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(userPostsViewed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPostsViewedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserPostsViewed() throws Exception {
        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        userPostsViewed.setId(count.incrementAndGet());

        // Create the UserPostsViewed
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(userPostsViewed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPostsViewedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateUserPostsViewedWithPatch() throws Exception {
        // Initialize the database
        userPostsViewedRepository.saveAndFlush(userPostsViewed);

        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();

        // Update the userPostsViewed using partial update
        UserPostsViewed partialUpdatedUserPostsViewed = new UserPostsViewed();
        partialUpdatedUserPostsViewed.setId(userPostsViewed.getId());

        partialUpdatedUserPostsViewed
            .userId(UPDATED_USER_ID)
            .postid(UPDATED_POSTID)
            .viewdate(UPDATED_VIEWDATE)
            .status(UPDATED_STATUS)
            .orgId(UPDATED_ORG_ID);

        restUserPostsViewedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserPostsViewed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserPostsViewed))
            )
            .andExpect(status().isOk());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        UserPostsViewed testUserPostsViewed = userPostsViewedList.get(userPostsViewedList.size() - 1);
        assertThat(testUserPostsViewed.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserPostsViewed.getPostid()).isEqualTo(UPDATED_POSTID);
        assertThat(testUserPostsViewed.getViewdate()).isEqualTo(UPDATED_VIEWDATE);
        assertThat(testUserPostsViewed.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserPostsViewed.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    void fullUpdateUserPostsViewedWithPatch() throws Exception {
        // Initialize the database
        userPostsViewedRepository.saveAndFlush(userPostsViewed);

        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();

        // Update the userPostsViewed using partial update
        UserPostsViewed partialUpdatedUserPostsViewed = new UserPostsViewed();
        partialUpdatedUserPostsViewed.setId(userPostsViewed.getId());

        partialUpdatedUserPostsViewed
            .userId(UPDATED_USER_ID)
            .postid(UPDATED_POSTID)
            .viewdate(UPDATED_VIEWDATE)
            .status(UPDATED_STATUS)
            .orgId(UPDATED_ORG_ID);

        restUserPostsViewedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserPostsViewed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserPostsViewed))
            )
            .andExpect(status().isOk());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        UserPostsViewed testUserPostsViewed = userPostsViewedList.get(userPostsViewedList.size() - 1);
        assertThat(testUserPostsViewed.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserPostsViewed.getPostid()).isEqualTo(UPDATED_POSTID);
        assertThat(testUserPostsViewed.getViewdate()).isEqualTo(UPDATED_VIEWDATE);
        assertThat(testUserPostsViewed.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserPostsViewed.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    void patchNonExistingUserPostsViewed() throws Exception {
        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        userPostsViewed.setId(count.incrementAndGet());

        // Create the UserPostsViewed
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(userPostsViewed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserPostsViewedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userPostsViewedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserPostsViewed() throws Exception {
        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        userPostsViewed.setId(count.incrementAndGet());

        // Create the UserPostsViewed
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(userPostsViewed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPostsViewedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserPostsViewed() throws Exception {
        int databaseSizeBeforeUpdate = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        userPostsViewed.setId(count.incrementAndGet());

        // Create the UserPostsViewed
        UserPostsViewedDTO userPostsViewedDTO = userPostsViewedMapper.toDto(userPostsViewed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserPostsViewedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userPostsViewedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserPostsViewed in the database
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteUserPostsViewed() throws Exception {
        // Initialize the database
        userPostsViewedRepository.saveAndFlush(userPostsViewed);
        userPostsViewedRepository.save(userPostsViewed);
        userPostsViewedSearchRepository.save(userPostsViewed);

        int databaseSizeBeforeDelete = userPostsViewedRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the userPostsViewed
        restUserPostsViewedMockMvc
            .perform(delete(ENTITY_API_URL_ID, userPostsViewed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserPostsViewed> userPostsViewedList = userPostsViewedRepository.findAll();
        assertThat(userPostsViewedList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userPostsViewedSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchUserPostsViewed() throws Exception {
        // Initialize the database
        userPostsViewed = userPostsViewedRepository.saveAndFlush(userPostsViewed);
        userPostsViewedSearchRepository.save(userPostsViewed);

        // Search the userPostsViewed
        restUserPostsViewedMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + userPostsViewed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPostsViewed.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].postid").value(hasItem(DEFAULT_POSTID.intValue())))
            .andExpect(jsonPath("$.[*].viewdate").value(hasItem(DEFAULT_VIEWDATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }
}
