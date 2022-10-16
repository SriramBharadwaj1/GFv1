package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Location;
import com.laptechnos.groupface.repository.LocationRepository;
import com.laptechnos.groupface.repository.search.LocationSearchRepository;
import com.laptechnos.groupface.service.LocationService;
import com.laptechnos.groupface.service.dto.LocationDTO;
import com.laptechnos.groupface.service.mapper.LocationMapper;
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
 * Integration tests for the {@link LocationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LocationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Long DEFAULT_MODERATOR_1_ID = 1L;
    private static final Long UPDATED_MODERATOR_1_ID = 2L;

    private static final Long DEFAULT_MODERATOR_2_ID = 1L;
    private static final Long UPDATED_MODERATOR_2_ID = 2L;

    private static final String DEFAULT_MODERATOR_1_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MODERATOR_1_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MODERATOR_2_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MODERATOR_2_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_TABLE_KY = 1L;
    private static final Long UPDATED_PARENT_TABLE_KY = 2L;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Long DEFAULT_ZONE = 1L;
    private static final Long UPDATED_ZONE = 2L;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final Integer DEFAULT_HIST = 1;
    private static final Integer UPDATED_HIST = 2;

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

    private static final String DEFAULT_OTHERINFO = "AAAAAAAAAA";
    private static final String UPDATED_OTHERINFO = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/locations";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LocationRepository locationRepository;

    @Mock
    private LocationRepository locationRepositoryMock;

    @Autowired
    private LocationMapper locationMapper;

    @Mock
    private LocationService locationServiceMock;

    @Autowired
    private LocationSearchRepository locationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocationMockMvc;

    private Location location;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .parentId(DEFAULT_PARENT_ID)
            .moderator1Id(DEFAULT_MODERATOR_1_ID)
            .moderator2Id(DEFAULT_MODERATOR_2_ID)
            .moderator1Code(DEFAULT_MODERATOR_1_CODE)
            .moderator2Code(DEFAULT_MODERATOR_2_CODE)
            .parentTableKy(DEFAULT_PARENT_TABLE_KY)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS)
            .zone(DEFAULT_ZONE)
            .orgId(DEFAULT_ORG_ID)
            .hist(DEFAULT_HIST)
            .addedBy(DEFAULT_ADDED_BY)
            .addedOn(DEFAULT_ADDED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .approvedBy(DEFAULT_APPROVED_BY)
            .approvedOn(DEFAULT_APPROVED_ON)
            .otherinfo(DEFAULT_OTHERINFO)
            .comments(DEFAULT_COMMENTS)
            .remarks(DEFAULT_REMARKS);
        return location;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity(EntityManager em) {
        Location location = new Location()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .parentId(UPDATED_PARENT_ID)
            .moderator1Id(UPDATED_MODERATOR_1_ID)
            .moderator2Id(UPDATED_MODERATOR_2_ID)
            .moderator1Code(UPDATED_MODERATOR_1_CODE)
            .moderator2Code(UPDATED_MODERATOR_2_CODE)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .hist(UPDATED_HIST)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .otherinfo(UPDATED_OTHERINFO)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS);
        return location;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        locationSearchRepository.deleteAll();
        assertThat(locationSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        location = createEntity(em);
    }

    @Test
    @Transactional
    void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());
        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);
        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLocation.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testLocation.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testLocation.getModerator1Id()).isEqualTo(DEFAULT_MODERATOR_1_ID);
        assertThat(testLocation.getModerator2Id()).isEqualTo(DEFAULT_MODERATOR_2_ID);
        assertThat(testLocation.getModerator1Code()).isEqualTo(DEFAULT_MODERATOR_1_CODE);
        assertThat(testLocation.getModerator2Code()).isEqualTo(DEFAULT_MODERATOR_2_CODE);
        assertThat(testLocation.getParentTableKy()).isEqualTo(DEFAULT_PARENT_TABLE_KY);
        assertThat(testLocation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLocation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLocation.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testLocation.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testLocation.getHist()).isEqualTo(DEFAULT_HIST);
        assertThat(testLocation.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testLocation.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testLocation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLocation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testLocation.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testLocation.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testLocation.getOtherinfo()).isEqualTo(DEFAULT_OTHERINFO);
        assertThat(testLocation.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testLocation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    void createLocationWithExistingId() throws Exception {
        // Create the Location with an existing ID
        location.setId(1L);
        LocationDTO locationDTO = locationMapper.toDto(location);

        int databaseSizeBeforeCreate = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].moderator1Id").value(hasItem(DEFAULT_MODERATOR_1_ID.intValue())))
            .andExpect(jsonPath("$.[*].moderator2Id").value(hasItem(DEFAULT_MODERATOR_2_ID.intValue())))
            .andExpect(jsonPath("$.[*].moderator1Code").value(hasItem(DEFAULT_MODERATOR_1_CODE)))
            .andExpect(jsonPath("$.[*].moderator2Code").value(hasItem(DEFAULT_MODERATOR_2_CODE)))
            .andExpect(jsonPath("$.[*].parentTableKy").value(hasItem(DEFAULT_PARENT_TABLE_KY.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].hist").value(hasItem(DEFAULT_HIST)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].otherinfo").value(hasItem(DEFAULT_OTHERINFO)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLocationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(locationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLocationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(locationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLocationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(locationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLocationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(locationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.moderator1Id").value(DEFAULT_MODERATOR_1_ID.intValue()))
            .andExpect(jsonPath("$.moderator2Id").value(DEFAULT_MODERATOR_2_ID.intValue()))
            .andExpect(jsonPath("$.moderator1Code").value(DEFAULT_MODERATOR_1_CODE))
            .andExpect(jsonPath("$.moderator2Code").value(DEFAULT_MODERATOR_2_CODE))
            .andExpect(jsonPath("$.parentTableKy").value(DEFAULT_PARENT_TABLE_KY.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE.intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.hist").value(DEFAULT_HIST))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()))
            .andExpect(jsonPath("$.otherinfo").value(DEFAULT_OTHERINFO))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }

    @Test
    @Transactional
    void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        locationSearchRepository.save(location);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).get();
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .parentId(UPDATED_PARENT_ID)
            .moderator1Id(UPDATED_MODERATOR_1_ID)
            .moderator2Id(UPDATED_MODERATOR_2_ID)
            .moderator1Code(UPDATED_MODERATOR_1_CODE)
            .moderator2Code(UPDATED_MODERATOR_2_CODE)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .hist(UPDATED_HIST)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .otherinfo(UPDATED_OTHERINFO)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS);
        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);

        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocation.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testLocation.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testLocation.getModerator1Id()).isEqualTo(UPDATED_MODERATOR_1_ID);
        assertThat(testLocation.getModerator2Id()).isEqualTo(UPDATED_MODERATOR_2_ID);
        assertThat(testLocation.getModerator1Code()).isEqualTo(UPDATED_MODERATOR_1_CODE);
        assertThat(testLocation.getModerator2Code()).isEqualTo(UPDATED_MODERATOR_2_CODE);
        assertThat(testLocation.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
        assertThat(testLocation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLocation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLocation.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testLocation.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testLocation.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testLocation.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testLocation.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testLocation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLocation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testLocation.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testLocation.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testLocation.getOtherinfo()).isEqualTo(UPDATED_OTHERINFO);
        assertThat(testLocation.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLocation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Location> locationSearchList = IterableUtils.toList(locationSearchRepository.findAll());
                Location testLocationSearch = locationSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testLocationSearch.getCode()).isEqualTo(UPDATED_CODE);
                assertThat(testLocationSearch.getName()).isEqualTo(UPDATED_NAME);
                assertThat(testLocationSearch.getDesc()).isEqualTo(UPDATED_DESC);
                assertThat(testLocationSearch.getParentId()).isEqualTo(UPDATED_PARENT_ID);
                assertThat(testLocationSearch.getModerator1Id()).isEqualTo(UPDATED_MODERATOR_1_ID);
                assertThat(testLocationSearch.getModerator2Id()).isEqualTo(UPDATED_MODERATOR_2_ID);
                assertThat(testLocationSearch.getModerator1Code()).isEqualTo(UPDATED_MODERATOR_1_CODE);
                assertThat(testLocationSearch.getModerator2Code()).isEqualTo(UPDATED_MODERATOR_2_CODE);
                assertThat(testLocationSearch.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
                assertThat(testLocationSearch.getType()).isEqualTo(UPDATED_TYPE);
                assertThat(testLocationSearch.getStatus()).isEqualTo(UPDATED_STATUS);
                assertThat(testLocationSearch.getZone()).isEqualTo(UPDATED_ZONE);
                assertThat(testLocationSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testLocationSearch.getHist()).isEqualTo(UPDATED_HIST);
                assertThat(testLocationSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testLocationSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testLocationSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testLocationSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testLocationSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testLocationSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testLocationSearch.getOtherinfo()).isEqualTo(UPDATED_OTHERINFO);
                assertThat(testLocationSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testLocationSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
            });
    }

    @Test
    @Transactional
    void putNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .code(UPDATED_CODE)
            .desc(UPDATED_DESC)
            .parentId(UPDATED_PARENT_ID)
            .moderator2Id(UPDATED_MODERATOR_2_ID)
            .moderator2Code(UPDATED_MODERATOR_2_CODE)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
            .type(UPDATED_TYPE)
            .zone(UPDATED_ZONE)
            .hist(UPDATED_HIST)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .otherinfo(UPDATED_OTHERINFO)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLocation.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testLocation.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testLocation.getModerator1Id()).isEqualTo(DEFAULT_MODERATOR_1_ID);
        assertThat(testLocation.getModerator2Id()).isEqualTo(UPDATED_MODERATOR_2_ID);
        assertThat(testLocation.getModerator1Code()).isEqualTo(DEFAULT_MODERATOR_1_CODE);
        assertThat(testLocation.getModerator2Code()).isEqualTo(UPDATED_MODERATOR_2_CODE);
        assertThat(testLocation.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
        assertThat(testLocation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLocation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLocation.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testLocation.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testLocation.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testLocation.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testLocation.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testLocation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLocation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testLocation.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testLocation.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testLocation.getOtherinfo()).isEqualTo(UPDATED_OTHERINFO);
        assertThat(testLocation.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLocation.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void fullUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .parentId(UPDATED_PARENT_ID)
            .moderator1Id(UPDATED_MODERATOR_1_ID)
            .moderator2Id(UPDATED_MODERATOR_2_ID)
            .moderator1Code(UPDATED_MODERATOR_1_CODE)
            .moderator2Code(UPDATED_MODERATOR_2_CODE)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS)
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .hist(UPDATED_HIST)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .otherinfo(UPDATED_OTHERINFO)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS);

        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLocation))
            )
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocation.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testLocation.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testLocation.getModerator1Id()).isEqualTo(UPDATED_MODERATOR_1_ID);
        assertThat(testLocation.getModerator2Id()).isEqualTo(UPDATED_MODERATOR_2_ID);
        assertThat(testLocation.getModerator1Code()).isEqualTo(UPDATED_MODERATOR_1_CODE);
        assertThat(testLocation.getModerator2Code()).isEqualTo(UPDATED_MODERATOR_2_CODE);
        assertThat(testLocation.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
        assertThat(testLocation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLocation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLocation.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testLocation.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testLocation.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testLocation.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testLocation.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testLocation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLocation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testLocation.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testLocation.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testLocation.getOtherinfo()).isEqualTo(UPDATED_OTHERINFO);
        assertThat(testLocation.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testLocation.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void patchNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, locationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());
        location.setId(count.incrementAndGet());

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLocationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(locationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        locationRepository.save(location);
        locationSearchRepository.save(location);

        int databaseSizeBeforeDelete = locationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the location
        restLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, location.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(locationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchLocation() throws Exception {
        // Initialize the database
        location = locationRepository.saveAndFlush(location);
        locationSearchRepository.save(location);

        // Search the location
        restLocationMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].moderator1Id").value(hasItem(DEFAULT_MODERATOR_1_ID.intValue())))
            .andExpect(jsonPath("$.[*].moderator2Id").value(hasItem(DEFAULT_MODERATOR_2_ID.intValue())))
            .andExpect(jsonPath("$.[*].moderator1Code").value(hasItem(DEFAULT_MODERATOR_1_CODE)))
            .andExpect(jsonPath("$.[*].moderator2Code").value(hasItem(DEFAULT_MODERATOR_2_CODE)))
            .andExpect(jsonPath("$.[*].parentTableKy").value(hasItem(DEFAULT_PARENT_TABLE_KY.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].hist").value(hasItem(DEFAULT_HIST)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].otherinfo").value(hasItem(DEFAULT_OTHERINFO)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
}
