package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.UserMast;
import com.laptechnos.groupface.repository.UserMastRepository;
import com.laptechnos.groupface.repository.search.UserMastSearchRepository;
import com.laptechnos.groupface.service.UserMastService;
import com.laptechnos.groupface.service.dto.UserMastDTO;
import com.laptechnos.groupface.service.mapper.UserMastMapper;
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
 * Integration tests for the {@link UserMastResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserMastResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER_TYPE = 1;
    private static final Integer UPDATED_USER_TYPE = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_IS_ACTIVE = "AAAAAAAAAA";
    private static final String UPDATED_IS_ACTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ACTIVATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACTIVATED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_AREA = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_PHNO = "AAAAAAAAAA";
    private static final String UPDATED_PHNO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REQUEST_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DT = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CASTE = 1L;
    private static final Long UPDATED_CASTE = 2L;

    private static final Long DEFAULT_SUB_CASTE = 1L;
    private static final Long UPDATED_SUB_CASTE = 2L;

    private static final String DEFAULT_VALID_ID = "AAAAAAAAAA";
    private static final String UPDATED_VALID_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALID_ID_TYPE = 1;
    private static final Integer UPDATED_VALID_ID_TYPE = 2;

    private static final String DEFAULT_VALID_ID_NO = "AAAAAAAAAA";
    private static final String UPDATED_VALID_ID_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_VALID_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_VALID_TILL = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_REFFERED_BY = 1;
    private static final Integer UPDATED_REFFERED_BY = 2;

    private static final Integer DEFAULT_RELATIONWITH = 1;
    private static final Integer UPDATED_RELATIONWITH = 2;

    private static final Integer DEFAULT_RELATION_TYPE = 1;
    private static final Integer UPDATED_RELATION_TYPE = 2;

    private static final Integer DEFAULT_ISSUING_COUNTRY = 1;
    private static final Integer UPDATED_ISSUING_COUNTRY = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_INFO = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_ALLERGIC_DRUG_1 = "AAAAAAAAAA";
    private static final String UPDATED_ALLERGIC_DRUG_1 = "BBBBBBBBBB";

    private static final Integer DEFAULT_MODULE_KY = 1;
    private static final Integer UPDATED_MODULE_KY = 2;

    private static final Integer DEFAULT_TABLE_KY = 1;
    private static final Integer UPDATED_TABLE_KY = 2;

    private static final String DEFAULT_ALLERGIC_DRUG_2 = "AAAAAAAAAA";
    private static final String UPDATED_ALLERGIC_DRUG_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_FIELDS = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMN_1 = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_1 = "BBBBBBBBBB";

    private static final Integer DEFAULT_COLUMN_2 = 1;
    private static final Integer UPDATED_COLUMN_2 = 2;

    private static final String DEFAULT_COLUMN_3 = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_3 = "BBBBBBBBBB";

    private static final String DEFAULT_HOBBIES = "AAAAAAAAAA";
    private static final String UPDATED_HOBBIES = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_ACTIVITIES = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_ACTIVITIES = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PATH = 1;
    private static final Integer UPDATED_PATH = 2;

    private static final String DEFAULT_ROLE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_TYPE = "BBBBBBBBBB";

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

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_HIST = "AAAAAAAAAA";
    private static final String UPDATED_HIST = "BBBBBBBBBB";

    private static final String DEFAULT_LAYOUT = "AAAAAAAAAA";
    private static final String UPDATED_LAYOUT = "BBBBBBBBBB";

    private static final String DEFAULT_SECURITY_KEY_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_SECURITY_KEY_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_HASH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HASH_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ENCRYPTION_STATUS = 1;
    private static final Integer UPDATED_ENCRYPTION_STATUS = 2;

    private static final String DEFAULT_ENCRYPTED_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_ENCRYPTED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_ZONE = "BBBBBBBBBB";

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final String DEFAULT_JOB = "AAAAAAAAAA";
    private static final String UPDATED_JOB = "BBBBBBBBBB";

    private static final Long DEFAULT_PRESENT_ADDRESS = 1L;
    private static final Long UPDATED_PRESENT_ADDRESS = 2L;

    private static final Long DEFAULT_PERMANENT_ADDRESS = 1L;
    private static final Long UPDATED_PERMANENT_ADDRESS = 2L;

    private static final String DEFAULT_WORKCOMPANY = "AAAAAAAAAA";
    private static final String UPDATED_WORKCOMPANY = "BBBBBBBBBB";

    private static final Long DEFAULT_WORK_ADDRESS = 1L;
    private static final Long UPDATED_WORK_ADDRESS = 2L;

    private static final String ENTITY_API_URL = "/api/user-masts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/user-masts";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserMastRepository userMastRepository;

    @Mock
    private UserMastRepository userMastRepositoryMock;

    @Autowired
    private UserMastMapper userMastMapper;

    @Mock
    private UserMastService userMastServiceMock;

    @Autowired
    private UserMastSearchRepository userMastSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserMastMockMvc;

    private UserMast userMast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMast createEntity(EntityManager em) {
        UserMast userMast = new UserMast()
            .userId(DEFAULT_USER_ID)
            .userType(DEFAULT_USER_TYPE)
            .name(DEFAULT_NAME)
            .lastname(DEFAULT_LASTNAME)
            .isActive(DEFAULT_IS_ACTIVE)
            .activatedBy(DEFAULT_ACTIVATED_BY)
            .activatedOn(DEFAULT_ACTIVATED_ON)
            .dob(DEFAULT_DOB)
            .gender(DEFAULT_GENDER)
            .phoneArea(DEFAULT_PHONE_AREA)
            .phno(DEFAULT_PHNO)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2)
            .requestDt(DEFAULT_REQUEST_DT)
            .caste(DEFAULT_CASTE)
            .subCaste(DEFAULT_SUB_CASTE)
            .validID(DEFAULT_VALID_ID)
            .validIDType(DEFAULT_VALID_ID_TYPE)
            .validIDNo(DEFAULT_VALID_ID_NO)
            .validValidTill(DEFAULT_VALID_VALID_TILL)
            .refferedBy(DEFAULT_REFFERED_BY)
            .relationwith(DEFAULT_RELATIONWITH)
            .relationType(DEFAULT_RELATION_TYPE)
            .issuingCountry(DEFAULT_ISSUING_COUNTRY)
            .status(DEFAULT_STATUS)
            .comment(DEFAULT_COMMENT)
            .otherInfo(DEFAULT_OTHER_INFO)
            .allergicDrug1(DEFAULT_ALLERGIC_DRUG_1)
            .moduleKy(DEFAULT_MODULE_KY)
            .tableKy(DEFAULT_TABLE_KY)
            .allergicDrug2(DEFAULT_ALLERGIC_DRUG_2)
            .comments(DEFAULT_COMMENTS)
            .remarks(DEFAULT_REMARKS)
            .extraFields(DEFAULT_EXTRA_FIELDS)
            .column1(DEFAULT_COLUMN_1)
            .column2(DEFAULT_COLUMN_2)
            .column3(DEFAULT_COLUMN_3)
            .hobbies(DEFAULT_HOBBIES)
            .otherActivities(DEFAULT_OTHER_ACTIVITIES)
            .password(DEFAULT_PASSWORD)
            .role(DEFAULT_ROLE)
            .path(DEFAULT_PATH)
            .roleType(DEFAULT_ROLE_TYPE)
            .addedBy(DEFAULT_ADDED_BY)
            .addedOn(DEFAULT_ADDED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .approvedBy(DEFAULT_APPROVED_BY)
            .approvedOn(DEFAULT_APPROVED_ON)
            .language(DEFAULT_LANGUAGE)
            .hist(DEFAULT_HIST)
            .layout(DEFAULT_LAYOUT)
            .securityKeyStatus(DEFAULT_SECURITY_KEY_STATUS)
            .hashCode(DEFAULT_HASH_CODE)
            .encryptionStatus(DEFAULT_ENCRYPTION_STATUS)
            .encryptedPassword(DEFAULT_ENCRYPTED_PASSWORD)
            .zone(DEFAULT_ZONE)
            .orgId(DEFAULT_ORG_ID)
            .job(DEFAULT_JOB)
            .presentAddress(DEFAULT_PRESENT_ADDRESS)
            .permanentAddress(DEFAULT_PERMANENT_ADDRESS)
            .workcompany(DEFAULT_WORKCOMPANY)
            .workAddress(DEFAULT_WORK_ADDRESS);
        return userMast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserMast createUpdatedEntity(EntityManager em) {
        UserMast userMast = new UserMast()
            .userId(UPDATED_USER_ID)
            .userType(UPDATED_USER_TYPE)
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
            .isActive(UPDATED_IS_ACTIVE)
            .activatedBy(UPDATED_ACTIVATED_BY)
            .activatedOn(UPDATED_ACTIVATED_ON)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .phoneArea(UPDATED_PHONE_AREA)
            .phno(UPDATED_PHNO)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .requestDt(UPDATED_REQUEST_DT)
            .caste(UPDATED_CASTE)
            .subCaste(UPDATED_SUB_CASTE)
            .validID(UPDATED_VALID_ID)
            .validIDType(UPDATED_VALID_ID_TYPE)
            .validIDNo(UPDATED_VALID_ID_NO)
            .validValidTill(UPDATED_VALID_VALID_TILL)
            .refferedBy(UPDATED_REFFERED_BY)
            .relationwith(UPDATED_RELATIONWITH)
            .relationType(UPDATED_RELATION_TYPE)
            .issuingCountry(UPDATED_ISSUING_COUNTRY)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .otherInfo(UPDATED_OTHER_INFO)
            .allergicDrug1(UPDATED_ALLERGIC_DRUG_1)
            .moduleKy(UPDATED_MODULE_KY)
            .tableKy(UPDATED_TABLE_KY)
            .allergicDrug2(UPDATED_ALLERGIC_DRUG_2)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS)
            .extraFields(UPDATED_EXTRA_FIELDS)
            .column1(UPDATED_COLUMN_1)
            .column2(UPDATED_COLUMN_2)
            .column3(UPDATED_COLUMN_3)
            .hobbies(UPDATED_HOBBIES)
            .otherActivities(UPDATED_OTHER_ACTIVITIES)
            .password(UPDATED_PASSWORD)
            .role(UPDATED_ROLE)
            .path(UPDATED_PATH)
            .roleType(UPDATED_ROLE_TYPE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .language(UPDATED_LANGUAGE)
            .hist(UPDATED_HIST)
            .layout(UPDATED_LAYOUT)
            .securityKeyStatus(UPDATED_SECURITY_KEY_STATUS)
            .hashCode(UPDATED_HASH_CODE)
            .encryptionStatus(UPDATED_ENCRYPTION_STATUS)
            .encryptedPassword(UPDATED_ENCRYPTED_PASSWORD)
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .job(UPDATED_JOB)
            .presentAddress(UPDATED_PRESENT_ADDRESS)
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .workcompany(UPDATED_WORKCOMPANY)
            .workAddress(UPDATED_WORK_ADDRESS);
        return userMast;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        userMastSearchRepository.deleteAll();
        assertThat(userMastSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        userMast = createEntity(em);
    }

    @Test
    @Transactional
    void createUserMast() throws Exception {
        int databaseSizeBeforeCreate = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        // Create the UserMast
        UserMastDTO userMastDTO = userMastMapper.toDto(userMast);
        restUserMastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMastDTO)))
            .andExpect(status().isCreated());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        UserMast testUserMast = userMastList.get(userMastList.size() - 1);
        assertThat(testUserMast.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserMast.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testUserMast.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserMast.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testUserMast.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testUserMast.getActivatedBy()).isEqualTo(DEFAULT_ACTIVATED_BY);
        assertThat(testUserMast.getActivatedOn()).isEqualTo(DEFAULT_ACTIVATED_ON);
        assertThat(testUserMast.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testUserMast.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserMast.getPhoneArea()).isEqualTo(DEFAULT_PHONE_AREA);
        assertThat(testUserMast.getPhno()).isEqualTo(DEFAULT_PHNO);
        assertThat(testUserMast.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testUserMast.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testUserMast.getRequestDt()).isEqualTo(DEFAULT_REQUEST_DT);
        assertThat(testUserMast.getCaste()).isEqualTo(DEFAULT_CASTE);
        assertThat(testUserMast.getSubCaste()).isEqualTo(DEFAULT_SUB_CASTE);
        assertThat(testUserMast.getValidID()).isEqualTo(DEFAULT_VALID_ID);
        assertThat(testUserMast.getValidIDType()).isEqualTo(DEFAULT_VALID_ID_TYPE);
        assertThat(testUserMast.getValidIDNo()).isEqualTo(DEFAULT_VALID_ID_NO);
        assertThat(testUserMast.getValidValidTill()).isEqualTo(DEFAULT_VALID_VALID_TILL);
        assertThat(testUserMast.getRefferedBy()).isEqualTo(DEFAULT_REFFERED_BY);
        assertThat(testUserMast.getRelationwith()).isEqualTo(DEFAULT_RELATIONWITH);
        assertThat(testUserMast.getRelationType()).isEqualTo(DEFAULT_RELATION_TYPE);
        assertThat(testUserMast.getIssuingCountry()).isEqualTo(DEFAULT_ISSUING_COUNTRY);
        assertThat(testUserMast.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserMast.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testUserMast.getOtherInfo()).isEqualTo(DEFAULT_OTHER_INFO);
        assertThat(testUserMast.getAllergicDrug1()).isEqualTo(DEFAULT_ALLERGIC_DRUG_1);
        assertThat(testUserMast.getModuleKy()).isEqualTo(DEFAULT_MODULE_KY);
        assertThat(testUserMast.getTableKy()).isEqualTo(DEFAULT_TABLE_KY);
        assertThat(testUserMast.getAllergicDrug2()).isEqualTo(DEFAULT_ALLERGIC_DRUG_2);
        assertThat(testUserMast.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testUserMast.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testUserMast.getExtraFields()).isEqualTo(DEFAULT_EXTRA_FIELDS);
        assertThat(testUserMast.getColumn1()).isEqualTo(DEFAULT_COLUMN_1);
        assertThat(testUserMast.getColumn2()).isEqualTo(DEFAULT_COLUMN_2);
        assertThat(testUserMast.getColumn3()).isEqualTo(DEFAULT_COLUMN_3);
        assertThat(testUserMast.getHobbies()).isEqualTo(DEFAULT_HOBBIES);
        assertThat(testUserMast.getOtherActivities()).isEqualTo(DEFAULT_OTHER_ACTIVITIES);
        assertThat(testUserMast.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserMast.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testUserMast.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testUserMast.getRoleType()).isEqualTo(DEFAULT_ROLE_TYPE);
        assertThat(testUserMast.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testUserMast.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testUserMast.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testUserMast.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testUserMast.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testUserMast.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testUserMast.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testUserMast.getHist()).isEqualTo(DEFAULT_HIST);
        assertThat(testUserMast.getLayout()).isEqualTo(DEFAULT_LAYOUT);
        assertThat(testUserMast.getSecurityKeyStatus()).isEqualTo(DEFAULT_SECURITY_KEY_STATUS);
        assertThat(testUserMast.getHashCode()).isEqualTo(DEFAULT_HASH_CODE);
        assertThat(testUserMast.getEncryptionStatus()).isEqualTo(DEFAULT_ENCRYPTION_STATUS);
        assertThat(testUserMast.getEncryptedPassword()).isEqualTo(DEFAULT_ENCRYPTED_PASSWORD);
        assertThat(testUserMast.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testUserMast.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testUserMast.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testUserMast.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testUserMast.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testUserMast.getWorkcompany()).isEqualTo(DEFAULT_WORKCOMPANY);
        assertThat(testUserMast.getWorkAddress()).isEqualTo(DEFAULT_WORK_ADDRESS);
    }

    @Test
    @Transactional
    void createUserMastWithExistingId() throws Exception {
        // Create the UserMast with an existing ID
        userMast.setId(1L);
        UserMastDTO userMastDTO = userMastMapper.toDto(userMast);

        int databaseSizeBeforeCreate = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMastDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllUserMasts() throws Exception {
        // Initialize the database
        userMastRepository.saveAndFlush(userMast);

        // Get all the userMastList
        restUserMastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMast.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].activatedBy").value(hasItem(DEFAULT_ACTIVATED_BY)))
            .andExpect(jsonPath("$.[*].activatedOn").value(hasItem(DEFAULT_ACTIVATED_ON.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].phoneArea").value(hasItem(DEFAULT_PHONE_AREA)))
            .andExpect(jsonPath("$.[*].phno").value(hasItem(DEFAULT_PHNO)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].requestDt").value(hasItem(DEFAULT_REQUEST_DT.toString())))
            .andExpect(jsonPath("$.[*].caste").value(hasItem(DEFAULT_CASTE.intValue())))
            .andExpect(jsonPath("$.[*].subCaste").value(hasItem(DEFAULT_SUB_CASTE.intValue())))
            .andExpect(jsonPath("$.[*].validID").value(hasItem(DEFAULT_VALID_ID)))
            .andExpect(jsonPath("$.[*].validIDType").value(hasItem(DEFAULT_VALID_ID_TYPE)))
            .andExpect(jsonPath("$.[*].validIDNo").value(hasItem(DEFAULT_VALID_ID_NO)))
            .andExpect(jsonPath("$.[*].validValidTill").value(hasItem(DEFAULT_VALID_VALID_TILL.toString())))
            .andExpect(jsonPath("$.[*].refferedBy").value(hasItem(DEFAULT_REFFERED_BY)))
            .andExpect(jsonPath("$.[*].relationwith").value(hasItem(DEFAULT_RELATIONWITH)))
            .andExpect(jsonPath("$.[*].relationType").value(hasItem(DEFAULT_RELATION_TYPE)))
            .andExpect(jsonPath("$.[*].issuingCountry").value(hasItem(DEFAULT_ISSUING_COUNTRY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].otherInfo").value(hasItem(DEFAULT_OTHER_INFO)))
            .andExpect(jsonPath("$.[*].allergicDrug1").value(hasItem(DEFAULT_ALLERGIC_DRUG_1)))
            .andExpect(jsonPath("$.[*].moduleKy").value(hasItem(DEFAULT_MODULE_KY)))
            .andExpect(jsonPath("$.[*].tableKy").value(hasItem(DEFAULT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].allergicDrug2").value(hasItem(DEFAULT_ALLERGIC_DRUG_2)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)))
            .andExpect(jsonPath("$.[*].column1").value(hasItem(DEFAULT_COLUMN_1)))
            .andExpect(jsonPath("$.[*].column2").value(hasItem(DEFAULT_COLUMN_2)))
            .andExpect(jsonPath("$.[*].column3").value(hasItem(DEFAULT_COLUMN_3)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].otherActivities").value(hasItem(DEFAULT_OTHER_ACTIVITIES)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].roleType").value(hasItem(DEFAULT_ROLE_TYPE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].hist").value(hasItem(DEFAULT_HIST)))
            .andExpect(jsonPath("$.[*].layout").value(hasItem(DEFAULT_LAYOUT)))
            .andExpect(jsonPath("$.[*].securityKeyStatus").value(hasItem(DEFAULT_SECURITY_KEY_STATUS)))
            .andExpect(jsonPath("$.[*].hashCode").value(hasItem(DEFAULT_HASH_CODE)))
            .andExpect(jsonPath("$.[*].encryptionStatus").value(hasItem(DEFAULT_ENCRYPTION_STATUS)))
            .andExpect(jsonPath("$.[*].encryptedPassword").value(hasItem(DEFAULT_ENCRYPTED_PASSWORD)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].job").value(hasItem(DEFAULT_JOB)))
            .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.intValue())))
            .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.intValue())))
            .andExpect(jsonPath("$.[*].workcompany").value(hasItem(DEFAULT_WORKCOMPANY)))
            .andExpect(jsonPath("$.[*].workAddress").value(hasItem(DEFAULT_WORK_ADDRESS.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserMastsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userMastServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserMastMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userMastServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserMastsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userMastServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserMastMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(userMastRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUserMast() throws Exception {
        // Initialize the database
        userMastRepository.saveAndFlush(userMast);

        // Get the userMast
        restUserMastMockMvc
            .perform(get(ENTITY_API_URL_ID, userMast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userMast.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.activatedBy").value(DEFAULT_ACTIVATED_BY))
            .andExpect(jsonPath("$.activatedOn").value(DEFAULT_ACTIVATED_ON.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.phoneArea").value(DEFAULT_PHONE_AREA))
            .andExpect(jsonPath("$.phno").value(DEFAULT_PHNO))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2))
            .andExpect(jsonPath("$.requestDt").value(DEFAULT_REQUEST_DT.toString()))
            .andExpect(jsonPath("$.caste").value(DEFAULT_CASTE.intValue()))
            .andExpect(jsonPath("$.subCaste").value(DEFAULT_SUB_CASTE.intValue()))
            .andExpect(jsonPath("$.validID").value(DEFAULT_VALID_ID))
            .andExpect(jsonPath("$.validIDType").value(DEFAULT_VALID_ID_TYPE))
            .andExpect(jsonPath("$.validIDNo").value(DEFAULT_VALID_ID_NO))
            .andExpect(jsonPath("$.validValidTill").value(DEFAULT_VALID_VALID_TILL.toString()))
            .andExpect(jsonPath("$.refferedBy").value(DEFAULT_REFFERED_BY))
            .andExpect(jsonPath("$.relationwith").value(DEFAULT_RELATIONWITH))
            .andExpect(jsonPath("$.relationType").value(DEFAULT_RELATION_TYPE))
            .andExpect(jsonPath("$.issuingCountry").value(DEFAULT_ISSUING_COUNTRY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.otherInfo").value(DEFAULT_OTHER_INFO))
            .andExpect(jsonPath("$.allergicDrug1").value(DEFAULT_ALLERGIC_DRUG_1))
            .andExpect(jsonPath("$.moduleKy").value(DEFAULT_MODULE_KY))
            .andExpect(jsonPath("$.tableKy").value(DEFAULT_TABLE_KY))
            .andExpect(jsonPath("$.allergicDrug2").value(DEFAULT_ALLERGIC_DRUG_2))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.extraFields").value(DEFAULT_EXTRA_FIELDS))
            .andExpect(jsonPath("$.column1").value(DEFAULT_COLUMN_1))
            .andExpect(jsonPath("$.column2").value(DEFAULT_COLUMN_2))
            .andExpect(jsonPath("$.column3").value(DEFAULT_COLUMN_3))
            .andExpect(jsonPath("$.hobbies").value(DEFAULT_HOBBIES))
            .andExpect(jsonPath("$.otherActivities").value(DEFAULT_OTHER_ACTIVITIES))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.roleType").value(DEFAULT_ROLE_TYPE))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.hist").value(DEFAULT_HIST))
            .andExpect(jsonPath("$.layout").value(DEFAULT_LAYOUT))
            .andExpect(jsonPath("$.securityKeyStatus").value(DEFAULT_SECURITY_KEY_STATUS))
            .andExpect(jsonPath("$.hashCode").value(DEFAULT_HASH_CODE))
            .andExpect(jsonPath("$.encryptionStatus").value(DEFAULT_ENCRYPTION_STATUS))
            .andExpect(jsonPath("$.encryptedPassword").value(DEFAULT_ENCRYPTED_PASSWORD))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.job").value(DEFAULT_JOB))
            .andExpect(jsonPath("$.presentAddress").value(DEFAULT_PRESENT_ADDRESS.intValue()))
            .andExpect(jsonPath("$.permanentAddress").value(DEFAULT_PERMANENT_ADDRESS.intValue()))
            .andExpect(jsonPath("$.workcompany").value(DEFAULT_WORKCOMPANY))
            .andExpect(jsonPath("$.workAddress").value(DEFAULT_WORK_ADDRESS.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserMast() throws Exception {
        // Get the userMast
        restUserMastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserMast() throws Exception {
        // Initialize the database
        userMastRepository.saveAndFlush(userMast);

        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();
        userMastSearchRepository.save(userMast);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());

        // Update the userMast
        UserMast updatedUserMast = userMastRepository.findById(userMast.getId()).get();
        // Disconnect from session so that the updates on updatedUserMast are not directly saved in db
        em.detach(updatedUserMast);
        updatedUserMast
            .userId(UPDATED_USER_ID)
            .userType(UPDATED_USER_TYPE)
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
            .isActive(UPDATED_IS_ACTIVE)
            .activatedBy(UPDATED_ACTIVATED_BY)
            .activatedOn(UPDATED_ACTIVATED_ON)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .phoneArea(UPDATED_PHONE_AREA)
            .phno(UPDATED_PHNO)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .requestDt(UPDATED_REQUEST_DT)
            .caste(UPDATED_CASTE)
            .subCaste(UPDATED_SUB_CASTE)
            .validID(UPDATED_VALID_ID)
            .validIDType(UPDATED_VALID_ID_TYPE)
            .validIDNo(UPDATED_VALID_ID_NO)
            .validValidTill(UPDATED_VALID_VALID_TILL)
            .refferedBy(UPDATED_REFFERED_BY)
            .relationwith(UPDATED_RELATIONWITH)
            .relationType(UPDATED_RELATION_TYPE)
            .issuingCountry(UPDATED_ISSUING_COUNTRY)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .otherInfo(UPDATED_OTHER_INFO)
            .allergicDrug1(UPDATED_ALLERGIC_DRUG_1)
            .moduleKy(UPDATED_MODULE_KY)
            .tableKy(UPDATED_TABLE_KY)
            .allergicDrug2(UPDATED_ALLERGIC_DRUG_2)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS)
            .extraFields(UPDATED_EXTRA_FIELDS)
            .column1(UPDATED_COLUMN_1)
            .column2(UPDATED_COLUMN_2)
            .column3(UPDATED_COLUMN_3)
            .hobbies(UPDATED_HOBBIES)
            .otherActivities(UPDATED_OTHER_ACTIVITIES)
            .password(UPDATED_PASSWORD)
            .role(UPDATED_ROLE)
            .path(UPDATED_PATH)
            .roleType(UPDATED_ROLE_TYPE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .language(UPDATED_LANGUAGE)
            .hist(UPDATED_HIST)
            .layout(UPDATED_LAYOUT)
            .securityKeyStatus(UPDATED_SECURITY_KEY_STATUS)
            .hashCode(UPDATED_HASH_CODE)
            .encryptionStatus(UPDATED_ENCRYPTION_STATUS)
            .encryptedPassword(UPDATED_ENCRYPTED_PASSWORD)
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .job(UPDATED_JOB)
            .presentAddress(UPDATED_PRESENT_ADDRESS)
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .workcompany(UPDATED_WORKCOMPANY)
            .workAddress(UPDATED_WORK_ADDRESS);
        UserMastDTO userMastDTO = userMastMapper.toDto(updatedUserMast);

        restUserMastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userMastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userMastDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        UserMast testUserMast = userMastList.get(userMastList.size() - 1);
        assertThat(testUserMast.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserMast.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testUserMast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserMast.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testUserMast.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testUserMast.getActivatedBy()).isEqualTo(UPDATED_ACTIVATED_BY);
        assertThat(testUserMast.getActivatedOn()).isEqualTo(UPDATED_ACTIVATED_ON);
        assertThat(testUserMast.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserMast.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserMast.getPhoneArea()).isEqualTo(UPDATED_PHONE_AREA);
        assertThat(testUserMast.getPhno()).isEqualTo(UPDATED_PHNO);
        assertThat(testUserMast.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testUserMast.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testUserMast.getRequestDt()).isEqualTo(UPDATED_REQUEST_DT);
        assertThat(testUserMast.getCaste()).isEqualTo(UPDATED_CASTE);
        assertThat(testUserMast.getSubCaste()).isEqualTo(UPDATED_SUB_CASTE);
        assertThat(testUserMast.getValidID()).isEqualTo(UPDATED_VALID_ID);
        assertThat(testUserMast.getValidIDType()).isEqualTo(UPDATED_VALID_ID_TYPE);
        assertThat(testUserMast.getValidIDNo()).isEqualTo(UPDATED_VALID_ID_NO);
        assertThat(testUserMast.getValidValidTill()).isEqualTo(UPDATED_VALID_VALID_TILL);
        assertThat(testUserMast.getRefferedBy()).isEqualTo(UPDATED_REFFERED_BY);
        assertThat(testUserMast.getRelationwith()).isEqualTo(UPDATED_RELATIONWITH);
        assertThat(testUserMast.getRelationType()).isEqualTo(UPDATED_RELATION_TYPE);
        assertThat(testUserMast.getIssuingCountry()).isEqualTo(UPDATED_ISSUING_COUNTRY);
        assertThat(testUserMast.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserMast.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserMast.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
        assertThat(testUserMast.getAllergicDrug1()).isEqualTo(UPDATED_ALLERGIC_DRUG_1);
        assertThat(testUserMast.getModuleKy()).isEqualTo(UPDATED_MODULE_KY);
        assertThat(testUserMast.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testUserMast.getAllergicDrug2()).isEqualTo(UPDATED_ALLERGIC_DRUG_2);
        assertThat(testUserMast.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testUserMast.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testUserMast.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        assertThat(testUserMast.getColumn1()).isEqualTo(UPDATED_COLUMN_1);
        assertThat(testUserMast.getColumn2()).isEqualTo(UPDATED_COLUMN_2);
        assertThat(testUserMast.getColumn3()).isEqualTo(UPDATED_COLUMN_3);
        assertThat(testUserMast.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testUserMast.getOtherActivities()).isEqualTo(UPDATED_OTHER_ACTIVITIES);
        assertThat(testUserMast.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserMast.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testUserMast.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testUserMast.getRoleType()).isEqualTo(UPDATED_ROLE_TYPE);
        assertThat(testUserMast.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testUserMast.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testUserMast.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUserMast.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testUserMast.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testUserMast.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testUserMast.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testUserMast.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testUserMast.getLayout()).isEqualTo(UPDATED_LAYOUT);
        assertThat(testUserMast.getSecurityKeyStatus()).isEqualTo(UPDATED_SECURITY_KEY_STATUS);
        assertThat(testUserMast.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testUserMast.getEncryptionStatus()).isEqualTo(UPDATED_ENCRYPTION_STATUS);
        assertThat(testUserMast.getEncryptedPassword()).isEqualTo(UPDATED_ENCRYPTED_PASSWORD);
        assertThat(testUserMast.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testUserMast.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testUserMast.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testUserMast.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testUserMast.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testUserMast.getWorkcompany()).isEqualTo(UPDATED_WORKCOMPANY);
        assertThat(testUserMast.getWorkAddress()).isEqualTo(UPDATED_WORK_ADDRESS);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<UserMast> userMastSearchList = IterableUtils.toList(userMastSearchRepository.findAll());
                UserMast testUserMastSearch = userMastSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testUserMastSearch.getUserId()).isEqualTo(UPDATED_USER_ID);
                assertThat(testUserMastSearch.getUserType()).isEqualTo(UPDATED_USER_TYPE);
                assertThat(testUserMastSearch.getName()).isEqualTo(UPDATED_NAME);
                assertThat(testUserMastSearch.getLastname()).isEqualTo(UPDATED_LASTNAME);
                assertThat(testUserMastSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testUserMastSearch.getActivatedBy()).isEqualTo(UPDATED_ACTIVATED_BY);
                assertThat(testUserMastSearch.getActivatedOn()).isEqualTo(UPDATED_ACTIVATED_ON);
                assertThat(testUserMastSearch.getDob()).isEqualTo(UPDATED_DOB);
                assertThat(testUserMastSearch.getGender()).isEqualTo(UPDATED_GENDER);
                assertThat(testUserMastSearch.getPhoneArea()).isEqualTo(UPDATED_PHONE_AREA);
                assertThat(testUserMastSearch.getPhno()).isEqualTo(UPDATED_PHNO);
                assertThat(testUserMastSearch.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
                assertThat(testUserMastSearch.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
                assertThat(testUserMastSearch.getRequestDt()).isEqualTo(UPDATED_REQUEST_DT);
                assertThat(testUserMastSearch.getCaste()).isEqualTo(UPDATED_CASTE);
                assertThat(testUserMastSearch.getSubCaste()).isEqualTo(UPDATED_SUB_CASTE);
                assertThat(testUserMastSearch.getValidID()).isEqualTo(UPDATED_VALID_ID);
                assertThat(testUserMastSearch.getValidIDType()).isEqualTo(UPDATED_VALID_ID_TYPE);
                assertThat(testUserMastSearch.getValidIDNo()).isEqualTo(UPDATED_VALID_ID_NO);
                assertThat(testUserMastSearch.getValidValidTill()).isEqualTo(UPDATED_VALID_VALID_TILL);
                assertThat(testUserMastSearch.getRefferedBy()).isEqualTo(UPDATED_REFFERED_BY);
                assertThat(testUserMastSearch.getRelationwith()).isEqualTo(UPDATED_RELATIONWITH);
                assertThat(testUserMastSearch.getRelationType()).isEqualTo(UPDATED_RELATION_TYPE);
                assertThat(testUserMastSearch.getIssuingCountry()).isEqualTo(UPDATED_ISSUING_COUNTRY);
                assertThat(testUserMastSearch.getStatus()).isEqualTo(UPDATED_STATUS);
                assertThat(testUserMastSearch.getComment()).isEqualTo(UPDATED_COMMENT);
                assertThat(testUserMastSearch.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
                assertThat(testUserMastSearch.getAllergicDrug1()).isEqualTo(UPDATED_ALLERGIC_DRUG_1);
                assertThat(testUserMastSearch.getModuleKy()).isEqualTo(UPDATED_MODULE_KY);
                assertThat(testUserMastSearch.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
                assertThat(testUserMastSearch.getAllergicDrug2()).isEqualTo(UPDATED_ALLERGIC_DRUG_2);
                assertThat(testUserMastSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testUserMastSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testUserMastSearch.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
                assertThat(testUserMastSearch.getColumn1()).isEqualTo(UPDATED_COLUMN_1);
                assertThat(testUserMastSearch.getColumn2()).isEqualTo(UPDATED_COLUMN_2);
                assertThat(testUserMastSearch.getColumn3()).isEqualTo(UPDATED_COLUMN_3);
                assertThat(testUserMastSearch.getHobbies()).isEqualTo(UPDATED_HOBBIES);
                assertThat(testUserMastSearch.getOtherActivities()).isEqualTo(UPDATED_OTHER_ACTIVITIES);
                assertThat(testUserMastSearch.getPassword()).isEqualTo(UPDATED_PASSWORD);
                assertThat(testUserMastSearch.getRole()).isEqualTo(UPDATED_ROLE);
                assertThat(testUserMastSearch.getPath()).isEqualTo(UPDATED_PATH);
                assertThat(testUserMastSearch.getRoleType()).isEqualTo(UPDATED_ROLE_TYPE);
                assertThat(testUserMastSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testUserMastSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testUserMastSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testUserMastSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testUserMastSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testUserMastSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testUserMastSearch.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
                assertThat(testUserMastSearch.getHist()).isEqualTo(UPDATED_HIST);
                assertThat(testUserMastSearch.getLayout()).isEqualTo(UPDATED_LAYOUT);
                assertThat(testUserMastSearch.getSecurityKeyStatus()).isEqualTo(UPDATED_SECURITY_KEY_STATUS);
                assertThat(testUserMastSearch.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
                assertThat(testUserMastSearch.getEncryptionStatus()).isEqualTo(UPDATED_ENCRYPTION_STATUS);
                assertThat(testUserMastSearch.getEncryptedPassword()).isEqualTo(UPDATED_ENCRYPTED_PASSWORD);
                assertThat(testUserMastSearch.getZone()).isEqualTo(UPDATED_ZONE);
                assertThat(testUserMastSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testUserMastSearch.getJob()).isEqualTo(UPDATED_JOB);
                assertThat(testUserMastSearch.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
                assertThat(testUserMastSearch.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
                assertThat(testUserMastSearch.getWorkcompany()).isEqualTo(UPDATED_WORKCOMPANY);
                assertThat(testUserMastSearch.getWorkAddress()).isEqualTo(UPDATED_WORK_ADDRESS);
            });
    }

    @Test
    @Transactional
    void putNonExistingUserMast() throws Exception {
        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        userMast.setId(count.incrementAndGet());

        // Create the UserMast
        UserMastDTO userMastDTO = userMastMapper.toDto(userMast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userMastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userMastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserMast() throws Exception {
        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        userMast.setId(count.incrementAndGet());

        // Create the UserMast
        UserMastDTO userMastDTO = userMastMapper.toDto(userMast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userMastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserMast() throws Exception {
        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        userMast.setId(count.incrementAndGet());

        // Create the UserMast
        UserMastDTO userMastDTO = userMastMapper.toDto(userMast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userMastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateUserMastWithPatch() throws Exception {
        // Initialize the database
        userMastRepository.saveAndFlush(userMast);

        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();

        // Update the userMast using partial update
        UserMast partialUpdatedUserMast = new UserMast();
        partialUpdatedUserMast.setId(userMast.getId());

        partialUpdatedUserMast
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .phoneArea(UPDATED_PHONE_AREA)
            .phno(UPDATED_PHNO)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .subCaste(UPDATED_SUB_CASTE)
            .validIDType(UPDATED_VALID_ID_TYPE)
            .validIDNo(UPDATED_VALID_ID_NO)
            .validValidTill(UPDATED_VALID_VALID_TILL)
            .relationwith(UPDATED_RELATIONWITH)
            .relationType(UPDATED_RELATION_TYPE)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .allergicDrug1(UPDATED_ALLERGIC_DRUG_1)
            .tableKy(UPDATED_TABLE_KY)
            .remarks(UPDATED_REMARKS)
            .column2(UPDATED_COLUMN_2)
            .column3(UPDATED_COLUMN_3)
            .hobbies(UPDATED_HOBBIES)
            .path(UPDATED_PATH)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .layout(UPDATED_LAYOUT)
            .securityKeyStatus(UPDATED_SECURITY_KEY_STATUS)
            .hashCode(UPDATED_HASH_CODE)
            .encryptionStatus(UPDATED_ENCRYPTION_STATUS)
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .job(UPDATED_JOB)
            .workcompany(UPDATED_WORKCOMPANY);

        restUserMastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserMast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserMast))
            )
            .andExpect(status().isOk());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        UserMast testUserMast = userMastList.get(userMastList.size() - 1);
        assertThat(testUserMast.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserMast.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testUserMast.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserMast.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testUserMast.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testUserMast.getActivatedBy()).isEqualTo(DEFAULT_ACTIVATED_BY);
        assertThat(testUserMast.getActivatedOn()).isEqualTo(DEFAULT_ACTIVATED_ON);
        assertThat(testUserMast.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserMast.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserMast.getPhoneArea()).isEqualTo(UPDATED_PHONE_AREA);
        assertThat(testUserMast.getPhno()).isEqualTo(UPDATED_PHNO);
        assertThat(testUserMast.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testUserMast.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testUserMast.getRequestDt()).isEqualTo(DEFAULT_REQUEST_DT);
        assertThat(testUserMast.getCaste()).isEqualTo(DEFAULT_CASTE);
        assertThat(testUserMast.getSubCaste()).isEqualTo(UPDATED_SUB_CASTE);
        assertThat(testUserMast.getValidID()).isEqualTo(DEFAULT_VALID_ID);
        assertThat(testUserMast.getValidIDType()).isEqualTo(UPDATED_VALID_ID_TYPE);
        assertThat(testUserMast.getValidIDNo()).isEqualTo(UPDATED_VALID_ID_NO);
        assertThat(testUserMast.getValidValidTill()).isEqualTo(UPDATED_VALID_VALID_TILL);
        assertThat(testUserMast.getRefferedBy()).isEqualTo(DEFAULT_REFFERED_BY);
        assertThat(testUserMast.getRelationwith()).isEqualTo(UPDATED_RELATIONWITH);
        assertThat(testUserMast.getRelationType()).isEqualTo(UPDATED_RELATION_TYPE);
        assertThat(testUserMast.getIssuingCountry()).isEqualTo(DEFAULT_ISSUING_COUNTRY);
        assertThat(testUserMast.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserMast.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserMast.getOtherInfo()).isEqualTo(DEFAULT_OTHER_INFO);
        assertThat(testUserMast.getAllergicDrug1()).isEqualTo(UPDATED_ALLERGIC_DRUG_1);
        assertThat(testUserMast.getModuleKy()).isEqualTo(DEFAULT_MODULE_KY);
        assertThat(testUserMast.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testUserMast.getAllergicDrug2()).isEqualTo(DEFAULT_ALLERGIC_DRUG_2);
        assertThat(testUserMast.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testUserMast.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testUserMast.getExtraFields()).isEqualTo(DEFAULT_EXTRA_FIELDS);
        assertThat(testUserMast.getColumn1()).isEqualTo(DEFAULT_COLUMN_1);
        assertThat(testUserMast.getColumn2()).isEqualTo(UPDATED_COLUMN_2);
        assertThat(testUserMast.getColumn3()).isEqualTo(UPDATED_COLUMN_3);
        assertThat(testUserMast.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testUserMast.getOtherActivities()).isEqualTo(DEFAULT_OTHER_ACTIVITIES);
        assertThat(testUserMast.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserMast.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testUserMast.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testUserMast.getRoleType()).isEqualTo(DEFAULT_ROLE_TYPE);
        assertThat(testUserMast.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testUserMast.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testUserMast.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUserMast.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testUserMast.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testUserMast.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testUserMast.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testUserMast.getHist()).isEqualTo(DEFAULT_HIST);
        assertThat(testUserMast.getLayout()).isEqualTo(UPDATED_LAYOUT);
        assertThat(testUserMast.getSecurityKeyStatus()).isEqualTo(UPDATED_SECURITY_KEY_STATUS);
        assertThat(testUserMast.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testUserMast.getEncryptionStatus()).isEqualTo(UPDATED_ENCRYPTION_STATUS);
        assertThat(testUserMast.getEncryptedPassword()).isEqualTo(DEFAULT_ENCRYPTED_PASSWORD);
        assertThat(testUserMast.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testUserMast.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testUserMast.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testUserMast.getPresentAddress()).isEqualTo(DEFAULT_PRESENT_ADDRESS);
        assertThat(testUserMast.getPermanentAddress()).isEqualTo(DEFAULT_PERMANENT_ADDRESS);
        assertThat(testUserMast.getWorkcompany()).isEqualTo(UPDATED_WORKCOMPANY);
        assertThat(testUserMast.getWorkAddress()).isEqualTo(DEFAULT_WORK_ADDRESS);
    }

    @Test
    @Transactional
    void fullUpdateUserMastWithPatch() throws Exception {
        // Initialize the database
        userMastRepository.saveAndFlush(userMast);

        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();

        // Update the userMast using partial update
        UserMast partialUpdatedUserMast = new UserMast();
        partialUpdatedUserMast.setId(userMast.getId());

        partialUpdatedUserMast
            .userId(UPDATED_USER_ID)
            .userType(UPDATED_USER_TYPE)
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
            .isActive(UPDATED_IS_ACTIVE)
            .activatedBy(UPDATED_ACTIVATED_BY)
            .activatedOn(UPDATED_ACTIVATED_ON)
            .dob(UPDATED_DOB)
            .gender(UPDATED_GENDER)
            .phoneArea(UPDATED_PHONE_AREA)
            .phno(UPDATED_PHNO)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .requestDt(UPDATED_REQUEST_DT)
            .caste(UPDATED_CASTE)
            .subCaste(UPDATED_SUB_CASTE)
            .validID(UPDATED_VALID_ID)
            .validIDType(UPDATED_VALID_ID_TYPE)
            .validIDNo(UPDATED_VALID_ID_NO)
            .validValidTill(UPDATED_VALID_VALID_TILL)
            .refferedBy(UPDATED_REFFERED_BY)
            .relationwith(UPDATED_RELATIONWITH)
            .relationType(UPDATED_RELATION_TYPE)
            .issuingCountry(UPDATED_ISSUING_COUNTRY)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .otherInfo(UPDATED_OTHER_INFO)
            .allergicDrug1(UPDATED_ALLERGIC_DRUG_1)
            .moduleKy(UPDATED_MODULE_KY)
            .tableKy(UPDATED_TABLE_KY)
            .allergicDrug2(UPDATED_ALLERGIC_DRUG_2)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS)
            .extraFields(UPDATED_EXTRA_FIELDS)
            .column1(UPDATED_COLUMN_1)
            .column2(UPDATED_COLUMN_2)
            .column3(UPDATED_COLUMN_3)
            .hobbies(UPDATED_HOBBIES)
            .otherActivities(UPDATED_OTHER_ACTIVITIES)
            .password(UPDATED_PASSWORD)
            .role(UPDATED_ROLE)
            .path(UPDATED_PATH)
            .roleType(UPDATED_ROLE_TYPE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .language(UPDATED_LANGUAGE)
            .hist(UPDATED_HIST)
            .layout(UPDATED_LAYOUT)
            .securityKeyStatus(UPDATED_SECURITY_KEY_STATUS)
            .hashCode(UPDATED_HASH_CODE)
            .encryptionStatus(UPDATED_ENCRYPTION_STATUS)
            .encryptedPassword(UPDATED_ENCRYPTED_PASSWORD)
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .job(UPDATED_JOB)
            .presentAddress(UPDATED_PRESENT_ADDRESS)
            .permanentAddress(UPDATED_PERMANENT_ADDRESS)
            .workcompany(UPDATED_WORKCOMPANY)
            .workAddress(UPDATED_WORK_ADDRESS);

        restUserMastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserMast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserMast))
            )
            .andExpect(status().isOk());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        UserMast testUserMast = userMastList.get(userMastList.size() - 1);
        assertThat(testUserMast.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserMast.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testUserMast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserMast.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testUserMast.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testUserMast.getActivatedBy()).isEqualTo(UPDATED_ACTIVATED_BY);
        assertThat(testUserMast.getActivatedOn()).isEqualTo(UPDATED_ACTIVATED_ON);
        assertThat(testUserMast.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserMast.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserMast.getPhoneArea()).isEqualTo(UPDATED_PHONE_AREA);
        assertThat(testUserMast.getPhno()).isEqualTo(UPDATED_PHNO);
        assertThat(testUserMast.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testUserMast.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testUserMast.getRequestDt()).isEqualTo(UPDATED_REQUEST_DT);
        assertThat(testUserMast.getCaste()).isEqualTo(UPDATED_CASTE);
        assertThat(testUserMast.getSubCaste()).isEqualTo(UPDATED_SUB_CASTE);
        assertThat(testUserMast.getValidID()).isEqualTo(UPDATED_VALID_ID);
        assertThat(testUserMast.getValidIDType()).isEqualTo(UPDATED_VALID_ID_TYPE);
        assertThat(testUserMast.getValidIDNo()).isEqualTo(UPDATED_VALID_ID_NO);
        assertThat(testUserMast.getValidValidTill()).isEqualTo(UPDATED_VALID_VALID_TILL);
        assertThat(testUserMast.getRefferedBy()).isEqualTo(UPDATED_REFFERED_BY);
        assertThat(testUserMast.getRelationwith()).isEqualTo(UPDATED_RELATIONWITH);
        assertThat(testUserMast.getRelationType()).isEqualTo(UPDATED_RELATION_TYPE);
        assertThat(testUserMast.getIssuingCountry()).isEqualTo(UPDATED_ISSUING_COUNTRY);
        assertThat(testUserMast.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserMast.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserMast.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
        assertThat(testUserMast.getAllergicDrug1()).isEqualTo(UPDATED_ALLERGIC_DRUG_1);
        assertThat(testUserMast.getModuleKy()).isEqualTo(UPDATED_MODULE_KY);
        assertThat(testUserMast.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testUserMast.getAllergicDrug2()).isEqualTo(UPDATED_ALLERGIC_DRUG_2);
        assertThat(testUserMast.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testUserMast.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testUserMast.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        assertThat(testUserMast.getColumn1()).isEqualTo(UPDATED_COLUMN_1);
        assertThat(testUserMast.getColumn2()).isEqualTo(UPDATED_COLUMN_2);
        assertThat(testUserMast.getColumn3()).isEqualTo(UPDATED_COLUMN_3);
        assertThat(testUserMast.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testUserMast.getOtherActivities()).isEqualTo(UPDATED_OTHER_ACTIVITIES);
        assertThat(testUserMast.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserMast.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testUserMast.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testUserMast.getRoleType()).isEqualTo(UPDATED_ROLE_TYPE);
        assertThat(testUserMast.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testUserMast.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testUserMast.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testUserMast.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testUserMast.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testUserMast.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testUserMast.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testUserMast.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testUserMast.getLayout()).isEqualTo(UPDATED_LAYOUT);
        assertThat(testUserMast.getSecurityKeyStatus()).isEqualTo(UPDATED_SECURITY_KEY_STATUS);
        assertThat(testUserMast.getHashCode()).isEqualTo(UPDATED_HASH_CODE);
        assertThat(testUserMast.getEncryptionStatus()).isEqualTo(UPDATED_ENCRYPTION_STATUS);
        assertThat(testUserMast.getEncryptedPassword()).isEqualTo(UPDATED_ENCRYPTED_PASSWORD);
        assertThat(testUserMast.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testUserMast.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testUserMast.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testUserMast.getPresentAddress()).isEqualTo(UPDATED_PRESENT_ADDRESS);
        assertThat(testUserMast.getPermanentAddress()).isEqualTo(UPDATED_PERMANENT_ADDRESS);
        assertThat(testUserMast.getWorkcompany()).isEqualTo(UPDATED_WORKCOMPANY);
        assertThat(testUserMast.getWorkAddress()).isEqualTo(UPDATED_WORK_ADDRESS);
    }

    @Test
    @Transactional
    void patchNonExistingUserMast() throws Exception {
        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        userMast.setId(count.incrementAndGet());

        // Create the UserMast
        UserMastDTO userMastDTO = userMastMapper.toDto(userMast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserMastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userMastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userMastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserMast() throws Exception {
        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        userMast.setId(count.incrementAndGet());

        // Create the UserMast
        UserMastDTO userMastDTO = userMastMapper.toDto(userMast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userMastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserMast() throws Exception {
        int databaseSizeBeforeUpdate = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        userMast.setId(count.incrementAndGet());

        // Create the UserMast
        UserMastDTO userMastDTO = userMastMapper.toDto(userMast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserMastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userMastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserMast in the database
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteUserMast() throws Exception {
        // Initialize the database
        userMastRepository.saveAndFlush(userMast);
        userMastRepository.save(userMast);
        userMastSearchRepository.save(userMast);

        int databaseSizeBeforeDelete = userMastRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the userMast
        restUserMastMockMvc
            .perform(delete(ENTITY_API_URL_ID, userMast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserMast> userMastList = userMastRepository.findAll();
        assertThat(userMastList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(userMastSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchUserMast() throws Exception {
        // Initialize the database
        userMast = userMastRepository.saveAndFlush(userMast);
        userMastSearchRepository.save(userMast);

        // Search the userMast
        restUserMastMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + userMast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMast.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].activatedBy").value(hasItem(DEFAULT_ACTIVATED_BY)))
            .andExpect(jsonPath("$.[*].activatedOn").value(hasItem(DEFAULT_ACTIVATED_ON.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].phoneArea").value(hasItem(DEFAULT_PHONE_AREA)))
            .andExpect(jsonPath("$.[*].phno").value(hasItem(DEFAULT_PHNO)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)))
            .andExpect(jsonPath("$.[*].requestDt").value(hasItem(DEFAULT_REQUEST_DT.toString())))
            .andExpect(jsonPath("$.[*].caste").value(hasItem(DEFAULT_CASTE.intValue())))
            .andExpect(jsonPath("$.[*].subCaste").value(hasItem(DEFAULT_SUB_CASTE.intValue())))
            .andExpect(jsonPath("$.[*].validID").value(hasItem(DEFAULT_VALID_ID)))
            .andExpect(jsonPath("$.[*].validIDType").value(hasItem(DEFAULT_VALID_ID_TYPE)))
            .andExpect(jsonPath("$.[*].validIDNo").value(hasItem(DEFAULT_VALID_ID_NO)))
            .andExpect(jsonPath("$.[*].validValidTill").value(hasItem(DEFAULT_VALID_VALID_TILL.toString())))
            .andExpect(jsonPath("$.[*].refferedBy").value(hasItem(DEFAULT_REFFERED_BY)))
            .andExpect(jsonPath("$.[*].relationwith").value(hasItem(DEFAULT_RELATIONWITH)))
            .andExpect(jsonPath("$.[*].relationType").value(hasItem(DEFAULT_RELATION_TYPE)))
            .andExpect(jsonPath("$.[*].issuingCountry").value(hasItem(DEFAULT_ISSUING_COUNTRY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].otherInfo").value(hasItem(DEFAULT_OTHER_INFO)))
            .andExpect(jsonPath("$.[*].allergicDrug1").value(hasItem(DEFAULT_ALLERGIC_DRUG_1)))
            .andExpect(jsonPath("$.[*].moduleKy").value(hasItem(DEFAULT_MODULE_KY)))
            .andExpect(jsonPath("$.[*].tableKy").value(hasItem(DEFAULT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].allergicDrug2").value(hasItem(DEFAULT_ALLERGIC_DRUG_2)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)))
            .andExpect(jsonPath("$.[*].column1").value(hasItem(DEFAULT_COLUMN_1)))
            .andExpect(jsonPath("$.[*].column2").value(hasItem(DEFAULT_COLUMN_2)))
            .andExpect(jsonPath("$.[*].column3").value(hasItem(DEFAULT_COLUMN_3)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].otherActivities").value(hasItem(DEFAULT_OTHER_ACTIVITIES)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].roleType").value(hasItem(DEFAULT_ROLE_TYPE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].hist").value(hasItem(DEFAULT_HIST)))
            .andExpect(jsonPath("$.[*].layout").value(hasItem(DEFAULT_LAYOUT)))
            .andExpect(jsonPath("$.[*].securityKeyStatus").value(hasItem(DEFAULT_SECURITY_KEY_STATUS)))
            .andExpect(jsonPath("$.[*].hashCode").value(hasItem(DEFAULT_HASH_CODE)))
            .andExpect(jsonPath("$.[*].encryptionStatus").value(hasItem(DEFAULT_ENCRYPTION_STATUS)))
            .andExpect(jsonPath("$.[*].encryptedPassword").value(hasItem(DEFAULT_ENCRYPTED_PASSWORD)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].job").value(hasItem(DEFAULT_JOB)))
            .andExpect(jsonPath("$.[*].presentAddress").value(hasItem(DEFAULT_PRESENT_ADDRESS.intValue())))
            .andExpect(jsonPath("$.[*].permanentAddress").value(hasItem(DEFAULT_PERMANENT_ADDRESS.intValue())))
            .andExpect(jsonPath("$.[*].workcompany").value(hasItem(DEFAULT_WORKCOMPANY)))
            .andExpect(jsonPath("$.[*].workAddress").value(hasItem(DEFAULT_WORK_ADDRESS.intValue())));
    }
}
