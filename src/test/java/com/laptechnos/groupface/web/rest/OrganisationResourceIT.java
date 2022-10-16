package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Organisation;
import com.laptechnos.groupface.repository.OrganisationRepository;
import com.laptechnos.groupface.repository.search.OrganisationSearchRepository;
import com.laptechnos.groupface.service.dto.OrganisationDTO;
import com.laptechnos.groupface.service.mapper.OrganisationMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link OrganisationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganisationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Long DEFAULT_PARENT_ORG_ID = 1L;
    private static final Long UPDATED_PARENT_ORG_ID = 2L;

    private static final Long DEFAULT_PRIMARY_CONTACT_ID = 1L;
    private static final Long UPDATED_PRIMARY_CONTACT_ID = 2L;

    private static final Long DEFAULT_ORG_HEAD = 1L;
    private static final Long UPDATED_ORG_HEAD = 2L;

    private static final Long DEFAULT_LOCATION_ID = 1L;
    private static final Long UPDATED_LOCATION_ID = 2L;

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_LAYOUT = "AAAAAAAAAA";
    private static final String UPDATED_LAYOUT = "BBBBBBBBBB";

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

    private static final String DEFAULT_EXTRA_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_FIELDS = "BBBBBBBBBB";

    private static final String DEFAULT_BUILD_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_BUILD_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_NO = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_MODULE_KY = 1;
    private static final Integer UPDATED_MODULE_KY = 2;

    private static final String DEFAULT_HASH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CERTIFICATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organisations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/organisations";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OrganisationMapper organisationMapper;

    @Autowired
    private OrganisationSearchRepository organisationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganisationMockMvc;

    private Organisation organisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisation createEntity(EntityManager em) {
        Organisation organisation = new Organisation()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .parentOrgId(DEFAULT_PARENT_ORG_ID)
            .primaryContactId(DEFAULT_PRIMARY_CONTACT_ID)
            .orgHead(DEFAULT_ORG_HEAD)
            .locationId(DEFAULT_LOCATION_ID)
            .website(DEFAULT_WEBSITE)
            .layout(DEFAULT_LAYOUT)
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
            .extraFields(DEFAULT_EXTRA_FIELDS)
            .buildFilePath(DEFAULT_BUILD_FILE_PATH)
            .shortName(DEFAULT_SHORT_NAME)
            .vatNo(DEFAULT_VAT_NO)
            .moduleKy(DEFAULT_MODULE_KY)
            .hashCode(DEFAULT_HASH_CODE)
            .hashCertificate(DEFAULT_HASH_CERTIFICATE);
        return organisation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisation createUpdatedEntity(EntityManager em) {
        Organisation organisation = new Organisation()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .parentOrgId(UPDATED_PARENT_ORG_ID)
            .primaryContactId(UPDATED_PRIMARY_CONTACT_ID)
            .orgHead(UPDATED_ORG_HEAD)
            .locationId(UPDATED_LOCATION_ID)
            .website(UPDATED_WEBSITE)
            .layout(UPDATED_LAYOUT)
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
            .extraFields(UPDATED_EXTRA_FIELDS)
            .buildFilePath(UPDATED_BUILD_FILE_PATH)
            .shortName(UPDATED_SHORT_NAME)
            .vatNo(UPDATED_VAT_NO)
            .moduleKy(UPDATED_MODULE_KY)
            .hashCode(UPDATED_HASH_CODE)
            .hashCertificate(UPDATED_HASH_CERTIFICATE);
        return organisation;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        organisationSearchRepository.deleteAll();
        assertThat(organisationSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        organisation = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganisation() throws Exception {
        int databaseSizeBeforeCreate = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);
        restOrganisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOrganisation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganisation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrganisation.getParentOrgId()).isEqualTo(DEFAULT_PARENT_ORG_ID);
        assertThat(testOrganisation.getPrimaryContactId()).isEqualTo(DEFAULT_PRIMARY_CONTACT_ID);
        assertThat(testOrganisation.getOrgHead()).isEqualTo(DEFAULT_ORG_HEAD);
        assertThat(testOrganisation.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testOrganisation.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrganisation.getLayout()).isEqualTo(DEFAULT_LAYOUT);
        assertThat(testOrganisation.getTableKy()).isEqualTo(DEFAULT_TABLE_KY);
        assertThat(testOrganisation.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testOrganisation.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testOrganisation.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testOrganisation.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testOrganisation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOrganisation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testOrganisation.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testOrganisation.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testOrganisation.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testOrganisation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testOrganisation.getExtraFields()).isEqualTo(DEFAULT_EXTRA_FIELDS);
        assertThat(testOrganisation.getBuildFilePath()).isEqualTo(DEFAULT_BUILD_FILE_PATH);
        assertThat(testOrganisation.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testOrganisation.getVatNo()).isEqualTo(DEFAULT_VAT_NO);
        assertThat(testOrganisation.getModuleKy()).isEqualTo(DEFAULT_MODULE_KY);
        assertThat(testOrganisation.getHashCode()).isEqualTo(DEFAULT_HASH_CODE);
        assertThat(testOrganisation.getHashCertificate()).isEqualTo(DEFAULT_HASH_CERTIFICATE);
    }

    @Test
    @Transactional
    void createOrganisationWithExistingId() throws Exception {
        // Create the Organisation with an existing ID
        organisation.setId(1L);
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        int databaseSizeBeforeCreate = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllOrganisations() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList
        restOrganisationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].parentOrgId").value(hasItem(DEFAULT_PARENT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].primaryContactId").value(hasItem(DEFAULT_PRIMARY_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].orgHead").value(hasItem(DEFAULT_ORG_HEAD.intValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].layout").value(hasItem(DEFAULT_LAYOUT)))
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
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)))
            .andExpect(jsonPath("$.[*].buildFilePath").value(hasItem(DEFAULT_BUILD_FILE_PATH)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].vatNo").value(hasItem(DEFAULT_VAT_NO)))
            .andExpect(jsonPath("$.[*].moduleKy").value(hasItem(DEFAULT_MODULE_KY)))
            .andExpect(jsonPath("$.[*].hashCode").value(hasItem(DEFAULT_HASH_CODE)))
            .andExpect(jsonPath("$.[*].hashCertificate").value(hasItem(DEFAULT_HASH_CERTIFICATE)));
    }

    @Test
    @Transactional
    void getOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get the organisation
        restOrganisationMockMvc
            .perform(get(ENTITY_API_URL_ID, organisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.parentOrgId").value(DEFAULT_PARENT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.primaryContactId").value(DEFAULT_PRIMARY_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.orgHead").value(DEFAULT_ORG_HEAD.intValue()))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID.intValue()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.layout").value(DEFAULT_LAYOUT))
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
            .andExpect(jsonPath("$.extraFields").value(DEFAULT_EXTRA_FIELDS))
            .andExpect(jsonPath("$.buildFilePath").value(DEFAULT_BUILD_FILE_PATH))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.vatNo").value(DEFAULT_VAT_NO))
            .andExpect(jsonPath("$.moduleKy").value(DEFAULT_MODULE_KY))
            .andExpect(jsonPath("$.hashCode").value(DEFAULT_HASH_CODE))
            .andExpect(jsonPath("$.hashCertificate").value(DEFAULT_HASH_CERTIFICATE));
    }

    @Test
    @Transactional
    void getNonExistingOrganisation() throws Exception {
        // Get the organisation
        restOrganisationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        organisationSearchRepository.save(organisation);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());

        // Update the organisation
        Organisation updatedOrganisation = organisationRepository.findById(organisation.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisation are not directly saved in db
        em.detach(updatedOrganisation);
        updatedOrganisation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .parentOrgId(UPDATED_PARENT_ORG_ID)
            .primaryContactId(UPDATED_PRIMARY_CONTACT_ID)
            .orgHead(UPDATED_ORG_HEAD)
            .locationId(UPDATED_LOCATION_ID)
            .website(UPDATED_WEBSITE)
            .layout(UPDATED_LAYOUT)
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
            .extraFields(UPDATED_EXTRA_FIELDS)
            .buildFilePath(UPDATED_BUILD_FILE_PATH)
            .shortName(UPDATED_SHORT_NAME)
            .vatNo(UPDATED_VAT_NO)
            .moduleKy(UPDATED_MODULE_KY)
            .hashCode(UPDATED_HASH_CODE)
            .hashCertificate(UPDATED_HASH_CERTIFICATE);
        OrganisationDTO organisationDTO = organisationMapper.toDto(updatedOrganisation);

        restOrganisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organisationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOrganisation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganisation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrganisation.getParentOrgId()).isEqualTo(UPDATED_PARENT_ORG_ID);
        assertThat(testOrganisation.getPrimaryContactId()).isEqualTo(UPDATED_PRIMARY_CONTACT_ID);
        assertThat(testOrganisation.getOrgHead()).isEqualTo(UPDATED_ORG_HEAD);
        assertThat(testOrganisation.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testOrganisation.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganisation.getLayout()).isEqualTo(UPDATED_LAYOUT);
        assertThat(testOrganisation.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testOrganisation.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOrganisation.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testOrganisation.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testOrganisation.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testOrganisation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrganisation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testOrganisation.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testOrganisation.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testOrganisation.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testOrganisation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testOrganisation.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        assertThat(testOrganisation.getBuildFilePath()).isEqualTo(UPDATED_BUILD_FILE_PATH);
        assertThat(testOrganisation.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testOrganisation.getVatNo()).isEqualTo(UPDATED_VAT_NO);
        assertThat(testOrganisation.getModuleKy()).isEqualTo(UPDATED_MODULE_KY);
        assertThat(testOrganisation.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testOrganisation.getHashCertificate()).isEqualTo(UPDATED_HASH_CERTIFICATE);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Organisation> organisationSearchList = IterableUtils.toList(organisationSearchRepository.findAll());
                Organisation testOrganisationSearch = organisationSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testOrganisationSearch.getCode()).isEqualTo(UPDATED_CODE);
                assertThat(testOrganisationSearch.getName()).isEqualTo(UPDATED_NAME);
                assertThat(testOrganisationSearch.getType()).isEqualTo(UPDATED_TYPE);
                assertThat(testOrganisationSearch.getParentOrgId()).isEqualTo(UPDATED_PARENT_ORG_ID);
                assertThat(testOrganisationSearch.getPrimaryContactId()).isEqualTo(UPDATED_PRIMARY_CONTACT_ID);
                assertThat(testOrganisationSearch.getOrgHead()).isEqualTo(UPDATED_ORG_HEAD);
                assertThat(testOrganisationSearch.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
                assertThat(testOrganisationSearch.getWebsite()).isEqualTo(UPDATED_WEBSITE);
                assertThat(testOrganisationSearch.getLayout()).isEqualTo(UPDATED_LAYOUT);
                assertThat(testOrganisationSearch.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
                assertThat(testOrganisationSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testOrganisationSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testOrganisationSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testOrganisationSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testOrganisationSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testOrganisationSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testOrganisationSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testOrganisationSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testOrganisationSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testOrganisationSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testOrganisationSearch.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
                assertThat(testOrganisationSearch.getBuildFilePath()).isEqualTo(UPDATED_BUILD_FILE_PATH);
                assertThat(testOrganisationSearch.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
                assertThat(testOrganisationSearch.getVatNo()).isEqualTo(UPDATED_VAT_NO);
                assertThat(testOrganisationSearch.getModuleKy()).isEqualTo(UPDATED_MODULE_KY);
                assertThat(testOrganisationSearch.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
                assertThat(testOrganisationSearch.getHashCertificate()).isEqualTo(UPDATED_HASH_CERTIFICATE);
            });
    }

    @Test
    @Transactional
    void putNonExistingOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organisationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateOrganisationWithPatch() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Update the organisation using partial update
        Organisation partialUpdatedOrganisation = new Organisation();
        partialUpdatedOrganisation.setId(organisation.getId());

        partialUpdatedOrganisation
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .orgHead(UPDATED_ORG_HEAD)
            .tableKy(UPDATED_TABLE_KY)
            .isActive(UPDATED_IS_ACTIVE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .comments(UPDATED_COMMENTS)
            .extraFields(UPDATED_EXTRA_FIELDS)
            .vatNo(UPDATED_VAT_NO)
            .hashCode(UPDATED_HASH_CODE);

        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganisation))
            )
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOrganisation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganisation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrganisation.getParentOrgId()).isEqualTo(DEFAULT_PARENT_ORG_ID);
        assertThat(testOrganisation.getPrimaryContactId()).isEqualTo(DEFAULT_PRIMARY_CONTACT_ID);
        assertThat(testOrganisation.getOrgHead()).isEqualTo(UPDATED_ORG_HEAD);
        assertThat(testOrganisation.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testOrganisation.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testOrganisation.getLayout()).isEqualTo(DEFAULT_LAYOUT);
        assertThat(testOrganisation.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testOrganisation.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOrganisation.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testOrganisation.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testOrganisation.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testOrganisation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrganisation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testOrganisation.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testOrganisation.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testOrganisation.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testOrganisation.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testOrganisation.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        assertThat(testOrganisation.getBuildFilePath()).isEqualTo(DEFAULT_BUILD_FILE_PATH);
        assertThat(testOrganisation.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testOrganisation.getVatNo()).isEqualTo(UPDATED_VAT_NO);
        assertThat(testOrganisation.getModuleKy()).isEqualTo(DEFAULT_MODULE_KY);
        assertThat(testOrganisation.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testOrganisation.getHashCertificate()).isEqualTo(DEFAULT_HASH_CERTIFICATE);
    }

    @Test
    @Transactional
    void fullUpdateOrganisationWithPatch() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Update the organisation using partial update
        Organisation partialUpdatedOrganisation = new Organisation();
        partialUpdatedOrganisation.setId(organisation.getId());

        partialUpdatedOrganisation
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .parentOrgId(UPDATED_PARENT_ORG_ID)
            .primaryContactId(UPDATED_PRIMARY_CONTACT_ID)
            .orgHead(UPDATED_ORG_HEAD)
            .locationId(UPDATED_LOCATION_ID)
            .website(UPDATED_WEBSITE)
            .layout(UPDATED_LAYOUT)
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
            .extraFields(UPDATED_EXTRA_FIELDS)
            .buildFilePath(UPDATED_BUILD_FILE_PATH)
            .shortName(UPDATED_SHORT_NAME)
            .vatNo(UPDATED_VAT_NO)
            .moduleKy(UPDATED_MODULE_KY)
            .hashCode(UPDATED_HASH_CODE)
            .hashCertificate(UPDATED_HASH_CERTIFICATE);

        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganisation))
            )
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOrganisation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganisation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrganisation.getParentOrgId()).isEqualTo(UPDATED_PARENT_ORG_ID);
        assertThat(testOrganisation.getPrimaryContactId()).isEqualTo(UPDATED_PRIMARY_CONTACT_ID);
        assertThat(testOrganisation.getOrgHead()).isEqualTo(UPDATED_ORG_HEAD);
        assertThat(testOrganisation.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testOrganisation.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testOrganisation.getLayout()).isEqualTo(UPDATED_LAYOUT);
        assertThat(testOrganisation.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testOrganisation.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testOrganisation.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testOrganisation.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testOrganisation.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testOrganisation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrganisation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testOrganisation.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testOrganisation.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testOrganisation.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testOrganisation.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testOrganisation.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        assertThat(testOrganisation.getBuildFilePath()).isEqualTo(UPDATED_BUILD_FILE_PATH);
        assertThat(testOrganisation.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testOrganisation.getVatNo()).isEqualTo(UPDATED_VAT_NO);
        assertThat(testOrganisation.getModuleKy()).isEqualTo(UPDATED_MODULE_KY);
        assertThat(testOrganisation.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testOrganisation.getHashCertificate()).isEqualTo(UPDATED_HASH_CERTIFICATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organisationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);
        organisationRepository.save(organisation);
        organisationSearchRepository.save(organisation);

        int databaseSizeBeforeDelete = organisationRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the organisation
        restOrganisationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organisation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(organisationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchOrganisation() throws Exception {
        // Initialize the database
        organisation = organisationRepository.saveAndFlush(organisation);
        organisationSearchRepository.save(organisation);

        // Search the organisation
        restOrganisationMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + organisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].parentOrgId").value(hasItem(DEFAULT_PARENT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].primaryContactId").value(hasItem(DEFAULT_PRIMARY_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].orgHead").value(hasItem(DEFAULT_ORG_HEAD.intValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].layout").value(hasItem(DEFAULT_LAYOUT)))
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
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)))
            .andExpect(jsonPath("$.[*].buildFilePath").value(hasItem(DEFAULT_BUILD_FILE_PATH)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].vatNo").value(hasItem(DEFAULT_VAT_NO)))
            .andExpect(jsonPath("$.[*].moduleKy").value(hasItem(DEFAULT_MODULE_KY)))
            .andExpect(jsonPath("$.[*].hashCode").value(hasItem(DEFAULT_HASH_CODE)))
            .andExpect(jsonPath("$.[*].hashCertificate").value(hasItem(DEFAULT_HASH_CERTIFICATE)));
    }
}
