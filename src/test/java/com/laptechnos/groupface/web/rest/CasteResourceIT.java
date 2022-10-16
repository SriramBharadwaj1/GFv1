package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Caste;
import com.laptechnos.groupface.repository.CasteRepository;
import com.laptechnos.groupface.repository.search.CasteSearchRepository;
import com.laptechnos.groupface.service.CasteService;
import com.laptechnos.groupface.service.dto.CasteDTO;
import com.laptechnos.groupface.service.mapper.CasteMapper;
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
 * Integration tests for the {@link CasteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CasteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Integer DEFAULT_MODERATOR_ID = 1;
    private static final Integer UPDATED_MODERATOR_ID = 2;

    private static final Integer DEFAULT_PARENT_TABLE_KY = 1;
    private static final Integer UPDATED_PARENT_TABLE_KY = 2;

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

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

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

    private static final String ENTITY_API_URL = "/api/castes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/castes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CasteRepository casteRepository;

    @Mock
    private CasteRepository casteRepositoryMock;

    @Autowired
    private CasteMapper casteMapper;

    @Mock
    private CasteService casteServiceMock;

    @Autowired
    private CasteSearchRepository casteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCasteMockMvc;

    private Caste caste;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caste createEntity(EntityManager em) {
        Caste caste = new Caste()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .parentId(DEFAULT_PARENT_ID)
            .moderatorId(DEFAULT_MODERATOR_ID)
            .parentTableKy(DEFAULT_PARENT_TABLE_KY)
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
        return caste;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caste createUpdatedEntity(EntityManager em) {
        Caste caste = new Caste()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .parentId(UPDATED_PARENT_ID)
            .moderatorId(UPDATED_MODERATOR_ID)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
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
        return caste;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        casteSearchRepository.deleteAll();
        assertThat(casteSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        caste = createEntity(em);
    }

    @Test
    @Transactional
    void createCaste() throws Exception {
        int databaseSizeBeforeCreate = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());
        // Create the Caste
        CasteDTO casteDTO = casteMapper.toDto(caste);
        restCasteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(casteDTO)))
            .andExpect(status().isCreated());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Caste testCaste = casteList.get(casteList.size() - 1);
        assertThat(testCaste.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCaste.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCaste.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testCaste.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testCaste.getModeratorId()).isEqualTo(DEFAULT_MODERATOR_ID);
        assertThat(testCaste.getParentTableKy()).isEqualTo(DEFAULT_PARENT_TABLE_KY);
        assertThat(testCaste.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCaste.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testCaste.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testCaste.getHist()).isEqualTo(DEFAULT_HIST);
        assertThat(testCaste.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testCaste.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testCaste.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCaste.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCaste.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testCaste.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testCaste.getOtherinfo()).isEqualTo(DEFAULT_OTHERINFO);
        assertThat(testCaste.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testCaste.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    void createCasteWithExistingId() throws Exception {
        // Create the Caste with an existing ID
        caste.setId(1L);
        CasteDTO casteDTO = casteMapper.toDto(caste);

        int databaseSizeBeforeCreate = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restCasteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(casteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllCastes() throws Exception {
        // Initialize the database
        casteRepository.saveAndFlush(caste);

        // Get all the casteList
        restCasteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caste.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].moderatorId").value(hasItem(DEFAULT_MODERATOR_ID)))
            .andExpect(jsonPath("$.[*].parentTableKy").value(hasItem(DEFAULT_PARENT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].hist").value(hasItem(DEFAULT_HIST)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].otherinfo").value(hasItem(DEFAULT_OTHERINFO)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCastesWithEagerRelationshipsIsEnabled() throws Exception {
        when(casteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCasteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(casteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCastesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(casteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCasteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(casteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCaste() throws Exception {
        // Initialize the database
        casteRepository.saveAndFlush(caste);

        // Get the caste
        restCasteMockMvc
            .perform(get(ENTITY_API_URL_ID, caste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caste.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.moderatorId").value(DEFAULT_MODERATOR_ID))
            .andExpect(jsonPath("$.parentTableKy").value(DEFAULT_PARENT_TABLE_KY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE.intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.hist").value(DEFAULT_HIST))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()))
            .andExpect(jsonPath("$.otherinfo").value(DEFAULT_OTHERINFO))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }

    @Test
    @Transactional
    void getNonExistingCaste() throws Exception {
        // Get the caste
        restCasteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCaste() throws Exception {
        // Initialize the database
        casteRepository.saveAndFlush(caste);

        int databaseSizeBeforeUpdate = casteRepository.findAll().size();
        casteSearchRepository.save(caste);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());

        // Update the caste
        Caste updatedCaste = casteRepository.findById(caste.getId()).get();
        // Disconnect from session so that the updates on updatedCaste are not directly saved in db
        em.detach(updatedCaste);
        updatedCaste
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .parentId(UPDATED_PARENT_ID)
            .moderatorId(UPDATED_MODERATOR_ID)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
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
        CasteDTO casteDTO = casteMapper.toDto(updatedCaste);

        restCasteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, casteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(casteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        Caste testCaste = casteList.get(casteList.size() - 1);
        assertThat(testCaste.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCaste.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCaste.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testCaste.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCaste.getModeratorId()).isEqualTo(UPDATED_MODERATOR_ID);
        assertThat(testCaste.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
        assertThat(testCaste.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCaste.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testCaste.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testCaste.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testCaste.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testCaste.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testCaste.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCaste.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCaste.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testCaste.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testCaste.getOtherinfo()).isEqualTo(UPDATED_OTHERINFO);
        assertThat(testCaste.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testCaste.getRemarks()).isEqualTo(UPDATED_REMARKS);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Caste> casteSearchList = IterableUtils.toList(casteSearchRepository.findAll());
                Caste testCasteSearch = casteSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testCasteSearch.getCode()).isEqualTo(UPDATED_CODE);
                assertThat(testCasteSearch.getName()).isEqualTo(UPDATED_NAME);
                assertThat(testCasteSearch.getDesc()).isEqualTo(UPDATED_DESC);
                assertThat(testCasteSearch.getParentId()).isEqualTo(UPDATED_PARENT_ID);
                assertThat(testCasteSearch.getModeratorId()).isEqualTo(UPDATED_MODERATOR_ID);
                assertThat(testCasteSearch.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
                assertThat(testCasteSearch.getStatus()).isEqualTo(UPDATED_STATUS);
                assertThat(testCasteSearch.getZone()).isEqualTo(UPDATED_ZONE);
                assertThat(testCasteSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testCasteSearch.getHist()).isEqualTo(UPDATED_HIST);
                assertThat(testCasteSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testCasteSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testCasteSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testCasteSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testCasteSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testCasteSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testCasteSearch.getOtherinfo()).isEqualTo(UPDATED_OTHERINFO);
                assertThat(testCasteSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testCasteSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
            });
    }

    @Test
    @Transactional
    void putNonExistingCaste() throws Exception {
        int databaseSizeBeforeUpdate = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());
        caste.setId(count.incrementAndGet());

        // Create the Caste
        CasteDTO casteDTO = casteMapper.toDto(caste);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCasteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, casteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(casteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaste() throws Exception {
        int databaseSizeBeforeUpdate = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());
        caste.setId(count.incrementAndGet());

        // Create the Caste
        CasteDTO casteDTO = casteMapper.toDto(caste);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCasteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(casteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaste() throws Exception {
        int databaseSizeBeforeUpdate = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());
        caste.setId(count.incrementAndGet());

        // Create the Caste
        CasteDTO casteDTO = casteMapper.toDto(caste);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCasteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(casteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateCasteWithPatch() throws Exception {
        // Initialize the database
        casteRepository.saveAndFlush(caste);

        int databaseSizeBeforeUpdate = casteRepository.findAll().size();

        // Update the caste using partial update
        Caste partialUpdatedCaste = new Caste();
        partialUpdatedCaste.setId(caste.getId());

        partialUpdatedCaste
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .parentId(UPDATED_PARENT_ID)
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .addedOn(UPDATED_ADDED_ON)
            .approvedBy(UPDATED_APPROVED_BY);

        restCasteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaste.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaste))
            )
            .andExpect(status().isOk());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        Caste testCaste = casteList.get(casteList.size() - 1);
        assertThat(testCaste.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCaste.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCaste.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testCaste.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCaste.getModeratorId()).isEqualTo(DEFAULT_MODERATOR_ID);
        assertThat(testCaste.getParentTableKy()).isEqualTo(DEFAULT_PARENT_TABLE_KY);
        assertThat(testCaste.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCaste.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testCaste.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testCaste.getHist()).isEqualTo(DEFAULT_HIST);
        assertThat(testCaste.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testCaste.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testCaste.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCaste.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testCaste.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testCaste.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testCaste.getOtherinfo()).isEqualTo(DEFAULT_OTHERINFO);
        assertThat(testCaste.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testCaste.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    void fullUpdateCasteWithPatch() throws Exception {
        // Initialize the database
        casteRepository.saveAndFlush(caste);

        int databaseSizeBeforeUpdate = casteRepository.findAll().size();

        // Update the caste using partial update
        Caste partialUpdatedCaste = new Caste();
        partialUpdatedCaste.setId(caste.getId());

        partialUpdatedCaste
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .parentId(UPDATED_PARENT_ID)
            .moderatorId(UPDATED_MODERATOR_ID)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
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

        restCasteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaste.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaste))
            )
            .andExpect(status().isOk());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        Caste testCaste = casteList.get(casteList.size() - 1);
        assertThat(testCaste.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCaste.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCaste.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testCaste.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testCaste.getModeratorId()).isEqualTo(UPDATED_MODERATOR_ID);
        assertThat(testCaste.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
        assertThat(testCaste.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCaste.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testCaste.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testCaste.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testCaste.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testCaste.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testCaste.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCaste.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testCaste.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testCaste.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testCaste.getOtherinfo()).isEqualTo(UPDATED_OTHERINFO);
        assertThat(testCaste.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testCaste.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void patchNonExistingCaste() throws Exception {
        int databaseSizeBeforeUpdate = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());
        caste.setId(count.incrementAndGet());

        // Create the Caste
        CasteDTO casteDTO = casteMapper.toDto(caste);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCasteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, casteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(casteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaste() throws Exception {
        int databaseSizeBeforeUpdate = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());
        caste.setId(count.incrementAndGet());

        // Create the Caste
        CasteDTO casteDTO = casteMapper.toDto(caste);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCasteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(casteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaste() throws Exception {
        int databaseSizeBeforeUpdate = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());
        caste.setId(count.incrementAndGet());

        // Create the Caste
        CasteDTO casteDTO = casteMapper.toDto(caste);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCasteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(casteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caste in the database
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteCaste() throws Exception {
        // Initialize the database
        casteRepository.saveAndFlush(caste);
        casteRepository.save(caste);
        casteSearchRepository.save(caste);

        int databaseSizeBeforeDelete = casteRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the caste
        restCasteMockMvc
            .perform(delete(ENTITY_API_URL_ID, caste.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Caste> casteList = casteRepository.findAll();
        assertThat(casteList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(casteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchCaste() throws Exception {
        // Initialize the database
        caste = casteRepository.saveAndFlush(caste);
        casteSearchRepository.save(caste);

        // Search the caste
        restCasteMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + caste.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caste.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].moderatorId").value(hasItem(DEFAULT_MODERATOR_ID)))
            .andExpect(jsonPath("$.[*].parentTableKy").value(hasItem(DEFAULT_PARENT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].hist").value(hasItem(DEFAULT_HIST)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].otherinfo").value(hasItem(DEFAULT_OTHERINFO)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
}
