package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.UserActivity;
import com.laptechnos.groupface.repository.UserActivityRepository;
import com.laptechnos.groupface.repository.search.UserActivitySearchRepository;
import com.laptechnos.groupface.service.UserActivityService;
import com.laptechnos.groupface.service.dto.UserActivityDTO;
import com.laptechnos.groupface.service.mapper.UserActivityMapper;
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
 * Integration tests for the {@link UserActivityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserActivityResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final LocalDate DEFAULT_LOGGEDON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOGGEDON = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_ACTIVEDURATION = 1D;
    private static final Double UPDATED_ACTIVEDURATION = 2D;

    private static final String DEFAULT_IP_ADR = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADR = "BBBBBBBBBB";

    private static final String DEFAULT_USR_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_USR_LOCATION = "BBBBBBBBBB";

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LONGI = 1F;
    private static final Float UPDATED_LONGI = 2F;

    private static final String DEFAULT_PIN = "AAAAAAAAAA";
    private static final String UPDATED_PIN = "BBBBBBBBBB";

    private static final Long DEFAULT_ADDED_BY = 1L;
    private static final Long UPDATED_ADDED_BY = 2L;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final LocalDate DEFAULT_ADDED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADDED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/user-activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/user-activities";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Mock
    private UserActivityRepository userActivityRepositoryMock;

    @Autowired
    private UserActivityMapper userActivityMapper;

    @Mock
    private UserActivityService userActivityServiceMock;

    @Autowired
    private UserActivitySearchRepository userActivitySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserActivityMockMvc;

    private UserActivity userActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActivity createEntity(EntityManager em) {
        UserActivity userActivity = new UserActivity()
            .userId(DEFAULT_USER_ID)
            .loggedon(DEFAULT_LOGGEDON)
            .activeduration(DEFAULT_ACTIVEDURATION)
            .ipAdr(DEFAULT_IP_ADR)
            .usrLocation(DEFAULT_USR_LOCATION)
            .lat(DEFAULT_LAT)
            .longi(DEFAULT_LONGI)
            .pin(DEFAULT_PIN)
            .addedBy(DEFAULT_ADDED_BY)
            .orgId(DEFAULT_ORG_ID)
            .addedOn(DEFAULT_ADDED_ON);
        return userActivity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserActivity createUpdatedEntity(EntityManager em) {
        UserActivity userActivity = new UserActivity()
            .userId(UPDATED_USER_ID)
            .loggedon(UPDATED_LOGGEDON)
            .activeduration(UPDATED_ACTIVEDURATION)
            .ipAdr(UPDATED_IP_ADR)
            .usrLocation(UPDATED_USR_LOCATION)
            .lat(UPDATED_LAT)
            .longi(UPDATED_LONGI)
            .pin(UPDATED_PIN)
            .addedBy(UPDATED_ADDED_BY)
            .orgId(UPDATED_ORG_ID)
            .addedOn(UPDATED_ADDED_ON);
        return userActivity;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        userActivitySearchRepository.deleteAll();
        assertThat(userActivitySearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        userActivity = createEntity(em);
    }

    @Test
    @Transactional
    void createUserActivity() throws Exception {
        int databaseSizeBeforeCreate = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);
        restUserActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        UserActivity testUserActivity = userActivityList.get(userActivityList.size() - 1);
        assertThat(testUserActivity.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserActivity.getLoggedon()).isEqualTo(DEFAULT_LOGGEDON);
        assertThat(testUserActivity.getActiveduration()).isEqualTo(DEFAULT_ACTIVEDURATION);
        assertThat(testUserActivity.getIpAdr()).isEqualTo(DEFAULT_IP_ADR);
        assertThat(testUserActivity.getUsrLocation()).isEqualTo(DEFAULT_USR_LOCATION);
        assertThat(testUserActivity.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testUserActivity.getLongi()).isEqualTo(DEFAULT_LONGI);
        assertThat(testUserActivity.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testUserActivity.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testUserActivity.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testUserActivity.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
    }

    @Test
    @Transactional
    void createUserActivityWithExistingId() throws Exception {
        // Create the UserActivity with an existing ID
        userActivity.setId(1L);
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        int databaseSizeBeforeCreate = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserActivityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllUserActivities() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        // Get all the userActivityList
        restUserActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].loggedon").value(hasItem(DEFAULT_LOGGEDON.toString())))
            .andExpect(jsonPath("$.[*].activeduration").value(hasItem(DEFAULT_ACTIVEDURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].ipAdr").value(hasItem(DEFAULT_IP_ADR)))
            .andExpect(jsonPath("$.[*].usrLocation").value(hasItem(DEFAULT_USR_LOCATION)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].longi").value(hasItem(DEFAULT_LONGI.doubleValue())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserActivitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(userActivityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserActivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userActivityServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserActivitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userActivityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserActivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(userActivityRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUserActivity() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        // Get the userActivity
        restUserActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, userActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userActivity.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.loggedon").value(DEFAULT_LOGGEDON.toString()))
            .andExpect(jsonPath("$.activeduration").value(DEFAULT_ACTIVEDURATION.doubleValue()))
            .andExpect(jsonPath("$.ipAdr").value(DEFAULT_IP_ADR))
            .andExpect(jsonPath("$.usrLocation").value(DEFAULT_USR_LOCATION))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.longi").value(DEFAULT_LONGI.doubleValue()))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserActivity() throws Exception {
        // Get the userActivity
        restUserActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserActivity() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();
        userActivitySearchRepository.save(userActivity);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());

        // Update the userActivity
        UserActivity updatedUserActivity = userActivityRepository.findById(userActivity.getId()).get();
        // Disconnect from session so that the updates on updatedUserActivity are not directly saved in db
        em.detach(updatedUserActivity);
        updatedUserActivity
            .userId(UPDATED_USER_ID)
            .loggedon(UPDATED_LOGGEDON)
            .activeduration(UPDATED_ACTIVEDURATION)
            .ipAdr(UPDATED_IP_ADR)
            .usrLocation(UPDATED_USR_LOCATION)
            .lat(UPDATED_LAT)
            .longi(UPDATED_LONGI)
            .pin(UPDATED_PIN)
            .addedBy(UPDATED_ADDED_BY)
            .orgId(UPDATED_ORG_ID)
            .addedOn(UPDATED_ADDED_ON);
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(updatedUserActivity);

        restUserActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userActivityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        UserActivity testUserActivity = userActivityList.get(userActivityList.size() - 1);
        assertThat(testUserActivity.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserActivity.getLoggedon()).isEqualTo(UPDATED_LOGGEDON);
        assertThat(testUserActivity.getActiveduration()).isEqualTo(UPDATED_ACTIVEDURATION);
        assertThat(testUserActivity.getIpAdr()).isEqualTo(UPDATED_IP_ADR);
        assertThat(testUserActivity.getUsrLocation()).isEqualTo(UPDATED_USR_LOCATION);
        assertThat(testUserActivity.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testUserActivity.getLongi()).isEqualTo(UPDATED_LONGI);
        assertThat(testUserActivity.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testUserActivity.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testUserActivity.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testUserActivity.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<UserActivity> userActivitySearchList = IterableUtils.toList(userActivitySearchRepository.findAll());
                UserActivity testUserActivitySearch = userActivitySearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testUserActivitySearch.getUserId()).isEqualTo(UPDATED_USER_ID);
                assertThat(testUserActivitySearch.getLoggedon()).isEqualTo(UPDATED_LOGGEDON);
                assertThat(testUserActivitySearch.getActiveduration()).isEqualTo(UPDATED_ACTIVEDURATION);
                assertThat(testUserActivitySearch.getIpAdr()).isEqualTo(UPDATED_IP_ADR);
                assertThat(testUserActivitySearch.getUsrLocation()).isEqualTo(UPDATED_USR_LOCATION);
                assertThat(testUserActivitySearch.getLat()).isEqualTo(UPDATED_LAT);
                assertThat(testUserActivitySearch.getLongi()).isEqualTo(UPDATED_LONGI);
                assertThat(testUserActivitySearch.getPin()).isEqualTo(UPDATED_PIN);
                assertThat(testUserActivitySearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testUserActivitySearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testUserActivitySearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
            });
    }

    @Test
    @Transactional
    void putNonExistingUserActivity() throws Exception {
        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        userActivity.setId(count.incrementAndGet());

        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userActivityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserActivity() throws Exception {
        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        userActivity.setId(count.incrementAndGet());

        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserActivity() throws Exception {
        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        userActivity.setId(count.incrementAndGet());

        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserActivityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateUserActivityWithPatch() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();

        // Update the userActivity using partial update
        UserActivity partialUpdatedUserActivity = new UserActivity();
        partialUpdatedUserActivity.setId(userActivity.getId());

        partialUpdatedUserActivity
            .userId(UPDATED_USER_ID)
            .loggedon(UPDATED_LOGGEDON)
            .ipAdr(UPDATED_IP_ADR)
            .usrLocation(UPDATED_USR_LOCATION)
            .longi(UPDATED_LONGI)
            .addedBy(UPDATED_ADDED_BY);

        restUserActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserActivity))
            )
            .andExpect(status().isOk());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        UserActivity testUserActivity = userActivityList.get(userActivityList.size() - 1);
        assertThat(testUserActivity.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserActivity.getLoggedon()).isEqualTo(UPDATED_LOGGEDON);
        assertThat(testUserActivity.getActiveduration()).isEqualTo(DEFAULT_ACTIVEDURATION);
        assertThat(testUserActivity.getIpAdr()).isEqualTo(UPDATED_IP_ADR);
        assertThat(testUserActivity.getUsrLocation()).isEqualTo(UPDATED_USR_LOCATION);
        assertThat(testUserActivity.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testUserActivity.getLongi()).isEqualTo(UPDATED_LONGI);
        assertThat(testUserActivity.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testUserActivity.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testUserActivity.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testUserActivity.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
    }

    @Test
    @Transactional
    void fullUpdateUserActivityWithPatch() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);

        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();

        // Update the userActivity using partial update
        UserActivity partialUpdatedUserActivity = new UserActivity();
        partialUpdatedUserActivity.setId(userActivity.getId());

        partialUpdatedUserActivity
            .userId(UPDATED_USER_ID)
            .loggedon(UPDATED_LOGGEDON)
            .activeduration(UPDATED_ACTIVEDURATION)
            .ipAdr(UPDATED_IP_ADR)
            .usrLocation(UPDATED_USR_LOCATION)
            .lat(UPDATED_LAT)
            .longi(UPDATED_LONGI)
            .pin(UPDATED_PIN)
            .addedBy(UPDATED_ADDED_BY)
            .orgId(UPDATED_ORG_ID)
            .addedOn(UPDATED_ADDED_ON);

        restUserActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserActivity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserActivity))
            )
            .andExpect(status().isOk());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        UserActivity testUserActivity = userActivityList.get(userActivityList.size() - 1);
        assertThat(testUserActivity.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserActivity.getLoggedon()).isEqualTo(UPDATED_LOGGEDON);
        assertThat(testUserActivity.getActiveduration()).isEqualTo(UPDATED_ACTIVEDURATION);
        assertThat(testUserActivity.getIpAdr()).isEqualTo(UPDATED_IP_ADR);
        assertThat(testUserActivity.getUsrLocation()).isEqualTo(UPDATED_USR_LOCATION);
        assertThat(testUserActivity.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testUserActivity.getLongi()).isEqualTo(UPDATED_LONGI);
        assertThat(testUserActivity.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testUserActivity.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testUserActivity.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testUserActivity.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingUserActivity() throws Exception {
        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        userActivity.setId(count.incrementAndGet());

        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userActivityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserActivity() throws Exception {
        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        userActivity.setId(count.incrementAndGet());

        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserActivity() throws Exception {
        int databaseSizeBeforeUpdate = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        userActivity.setId(count.incrementAndGet());

        // Create the UserActivity
        UserActivityDTO userActivityDTO = userActivityMapper.toDto(userActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserActivityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userActivityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserActivity in the database
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteUserActivity() throws Exception {
        // Initialize the database
        userActivityRepository.saveAndFlush(userActivity);
        userActivityRepository.save(userActivity);
        userActivitySearchRepository.save(userActivity);

        int databaseSizeBeforeDelete = userActivityRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the userActivity
        restUserActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, userActivity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserActivity> userActivityList = userActivityRepository.findAll();
        assertThat(userActivityList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userActivitySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchUserActivity() throws Exception {
        // Initialize the database
        userActivity = userActivityRepository.saveAndFlush(userActivity);
        userActivitySearchRepository.save(userActivity);

        // Search the userActivity
        restUserActivityMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + userActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].loggedon").value(hasItem(DEFAULT_LOGGEDON.toString())))
            .andExpect(jsonPath("$.[*].activeduration").value(hasItem(DEFAULT_ACTIVEDURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].ipAdr").value(hasItem(DEFAULT_IP_ADR)))
            .andExpect(jsonPath("$.[*].usrLocation").value(hasItem(DEFAULT_USR_LOCATION)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].longi").value(hasItem(DEFAULT_LONGI.doubleValue())))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())));
    }
}
