package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Address;
import com.laptechnos.groupface.repository.AddressRepository;
import com.laptechnos.groupface.repository.search.AddressSearchRepository;
import com.laptechnos.groupface.service.AddressService;
import com.laptechnos.groupface.service.dto.AddressDTO;
import com.laptechnos.groupface.service.mapper.AddressMapper;
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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AddressResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ADDRESS_TYPE = 1;
    private static final Integer UPDATED_ADDRESS_TYPE = 2;

    private static final String DEFAULT_ADDRESS_LN_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LN_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LN_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LN_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LN_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LN_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LN_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LN_4 = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final Long DEFAULT_VILLAGE = 1L;
    private static final Long UPDATED_VILLAGE = 2L;

    private static final Long DEFAULT_CITY = 1L;
    private static final Long UPDATED_CITY = 2L;

    private static final Long DEFAULT_DISTRICT = 1L;
    private static final Long UPDATED_DISTRICT = 2L;

    private static final Long DEFAULT_STATE = 1L;
    private static final Long UPDATED_STATE = 2L;

    private static final Integer DEFAULT_PARENT_TABLE_ID = 1;
    private static final Integer UPDATED_PARENT_TABLE_ID = 2;

    private static final Integer DEFAULT_PARENT_MODULE_KY = 1;
    private static final Integer UPDATED_PARENT_MODULE_KY = 2;

    private static final Integer DEFAULT_PARENT_TABLE_KY = 1;
    private static final Integer UPDATED_PARENT_TABLE_KY = 2;

    private static final String DEFAULT_PIN = "AAAAAAAAAA";
    private static final String UPDATED_PIN = "BBBBBBBBBB";

    private static final Long DEFAULT_COUNTRY = 1L;
    private static final Long UPDATED_COUNTRY = 2L;

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

    private static final String DEFAULT_EXTRA_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_FIELDS = "BBBBBBBBBB";

    private static final Long DEFAULT_ZONE = 1L;
    private static final Long UPDATED_ZONE = 2L;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final Integer DEFAULT_HIST = 1;
    private static final Integer UPDATED_HIST = 2;

    private static final String DEFAULT_COLUMN_1 = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_1 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/addresses";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressRepository addressRepository;

    @Mock
    private AddressRepository addressRepositoryMock;

    @Autowired
    private AddressMapper addressMapper;

    @Mock
    private AddressService addressServiceMock;

    @Autowired
    private AddressSearchRepository addressSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .name(DEFAULT_NAME)
            .addressType(DEFAULT_ADDRESS_TYPE)
            .addressLn1(DEFAULT_ADDRESS_LN_1)
            .addressLn2(DEFAULT_ADDRESS_LN_2)
            .addressLn3(DEFAULT_ADDRESS_LN_3)
            .addressLn4(DEFAULT_ADDRESS_LN_4)
            .landmark(DEFAULT_LANDMARK)
            .village(DEFAULT_VILLAGE)
            .city(DEFAULT_CITY)
            .district(DEFAULT_DISTRICT)
            .state(DEFAULT_STATE)
            .parentTableId(DEFAULT_PARENT_TABLE_ID)
            .parentModuleKy(DEFAULT_PARENT_MODULE_KY)
            .parentTableKy(DEFAULT_PARENT_TABLE_KY)
            .pin(DEFAULT_PIN)
            .country(DEFAULT_COUNTRY)
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
            .zone(DEFAULT_ZONE)
            .orgId(DEFAULT_ORG_ID)
            .hist(DEFAULT_HIST)
            .column1(DEFAULT_COLUMN_1);
        return address;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .name(UPDATED_NAME)
            .addressType(UPDATED_ADDRESS_TYPE)
            .addressLn1(UPDATED_ADDRESS_LN_1)
            .addressLn2(UPDATED_ADDRESS_LN_2)
            .addressLn3(UPDATED_ADDRESS_LN_3)
            .addressLn4(UPDATED_ADDRESS_LN_4)
            .landmark(UPDATED_LANDMARK)
            .village(UPDATED_VILLAGE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .state(UPDATED_STATE)
            .parentTableId(UPDATED_PARENT_TABLE_ID)
            .parentModuleKy(UPDATED_PARENT_MODULE_KY)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
            .pin(UPDATED_PIN)
            .country(UPDATED_COUNTRY)
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
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .hist(UPDATED_HIST)
            .column1(UPDATED_COLUMN_1);
        return address;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        addressSearchRepository.deleteAll();
        assertThat(addressSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAddress.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testAddress.getAddressLn1()).isEqualTo(DEFAULT_ADDRESS_LN_1);
        assertThat(testAddress.getAddressLn2()).isEqualTo(DEFAULT_ADDRESS_LN_2);
        assertThat(testAddress.getAddressLn3()).isEqualTo(DEFAULT_ADDRESS_LN_3);
        assertThat(testAddress.getAddressLn4()).isEqualTo(DEFAULT_ADDRESS_LN_4);
        assertThat(testAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testAddress.getVillage()).isEqualTo(DEFAULT_VILLAGE);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getParentTableId()).isEqualTo(DEFAULT_PARENT_TABLE_ID);
        assertThat(testAddress.getParentModuleKy()).isEqualTo(DEFAULT_PARENT_MODULE_KY);
        assertThat(testAddress.getParentTableKy()).isEqualTo(DEFAULT_PARENT_TABLE_KY);
        assertThat(testAddress.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddress.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testAddress.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testAddress.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testAddress.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testAddress.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAddress.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testAddress.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testAddress.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testAddress.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testAddress.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAddress.getExtraFields()).isEqualTo(DEFAULT_EXTRA_FIELDS);
        assertThat(testAddress.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testAddress.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testAddress.getHist()).isEqualTo(DEFAULT_HIST);
        assertThat(testAddress.getColumn1()).isEqualTo(DEFAULT_COLUMN_1);
    }

    @Test
    @Transactional
    void createAddressWithExistingId() throws Exception {
        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE)))
            .andExpect(jsonPath("$.[*].addressLn1").value(hasItem(DEFAULT_ADDRESS_LN_1)))
            .andExpect(jsonPath("$.[*].addressLn2").value(hasItem(DEFAULT_ADDRESS_LN_2)))
            .andExpect(jsonPath("$.[*].addressLn3").value(hasItem(DEFAULT_ADDRESS_LN_3)))
            .andExpect(jsonPath("$.[*].addressLn4").value(hasItem(DEFAULT_ADDRESS_LN_4)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].village").value(hasItem(DEFAULT_VILLAGE.intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.intValue())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.intValue())))
            .andExpect(jsonPath("$.[*].parentTableId").value(hasItem(DEFAULT_PARENT_TABLE_ID)))
            .andExpect(jsonPath("$.[*].parentModuleKy").value(hasItem(DEFAULT_PARENT_MODULE_KY)))
            .andExpect(jsonPath("$.[*].parentTableKy").value(hasItem(DEFAULT_PARENT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.intValue())))
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
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].hist").value(hasItem(DEFAULT_HIST)))
            .andExpect(jsonPath("$.[*].column1").value(hasItem(DEFAULT_COLUMN_1)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAddressesWithEagerRelationshipsIsEnabled() throws Exception {
        when(addressServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAddressMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(addressServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAddressesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(addressServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAddressMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(addressRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE))
            .andExpect(jsonPath("$.addressLn1").value(DEFAULT_ADDRESS_LN_1))
            .andExpect(jsonPath("$.addressLn2").value(DEFAULT_ADDRESS_LN_2))
            .andExpect(jsonPath("$.addressLn3").value(DEFAULT_ADDRESS_LN_3))
            .andExpect(jsonPath("$.addressLn4").value(DEFAULT_ADDRESS_LN_4))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK))
            .andExpect(jsonPath("$.village").value(DEFAULT_VILLAGE.intValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.intValue()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.intValue()))
            .andExpect(jsonPath("$.parentTableId").value(DEFAULT_PARENT_TABLE_ID))
            .andExpect(jsonPath("$.parentModuleKy").value(DEFAULT_PARENT_MODULE_KY))
            .andExpect(jsonPath("$.parentTableKy").value(DEFAULT_PARENT_TABLE_KY))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.intValue()))
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
            .andExpect(jsonPath("$.extraFields").value(DEFAULT_EXTRA_FIELDS))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE.intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.hist").value(DEFAULT_HIST))
            .andExpect(jsonPath("$.column1").value(DEFAULT_COLUMN_1));
    }

    @Test
    @Transactional
    void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        addressSearchRepository.save(address);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .name(UPDATED_NAME)
            .addressType(UPDATED_ADDRESS_TYPE)
            .addressLn1(UPDATED_ADDRESS_LN_1)
            .addressLn2(UPDATED_ADDRESS_LN_2)
            .addressLn3(UPDATED_ADDRESS_LN_3)
            .addressLn4(UPDATED_ADDRESS_LN_4)
            .landmark(UPDATED_LANDMARK)
            .village(UPDATED_VILLAGE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .state(UPDATED_STATE)
            .parentTableId(UPDATED_PARENT_TABLE_ID)
            .parentModuleKy(UPDATED_PARENT_MODULE_KY)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
            .pin(UPDATED_PIN)
            .country(UPDATED_COUNTRY)
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
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .hist(UPDATED_HIST)
            .column1(UPDATED_COLUMN_1);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testAddress.getAddressLn1()).isEqualTo(UPDATED_ADDRESS_LN_1);
        assertThat(testAddress.getAddressLn2()).isEqualTo(UPDATED_ADDRESS_LN_2);
        assertThat(testAddress.getAddressLn3()).isEqualTo(UPDATED_ADDRESS_LN_3);
        assertThat(testAddress.getAddressLn4()).isEqualTo(UPDATED_ADDRESS_LN_4);
        assertThat(testAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testAddress.getVillage()).isEqualTo(UPDATED_VILLAGE);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getParentTableId()).isEqualTo(UPDATED_PARENT_TABLE_ID);
        assertThat(testAddress.getParentModuleKy()).isEqualTo(UPDATED_PARENT_MODULE_KY);
        assertThat(testAddress.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
        assertThat(testAddress.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddress.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAddress.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testAddress.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testAddress.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testAddress.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAddress.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAddress.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testAddress.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testAddress.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAddress.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAddress.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        assertThat(testAddress.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testAddress.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testAddress.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testAddress.getColumn1()).isEqualTo(UPDATED_COLUMN_1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Address> addressSearchList = IterableUtils.toList(addressSearchRepository.findAll());
                Address testAddressSearch = addressSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testAddressSearch.getName()).isEqualTo(UPDATED_NAME);
                assertThat(testAddressSearch.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
                assertThat(testAddressSearch.getAddressLn1()).isEqualTo(UPDATED_ADDRESS_LN_1);
                assertThat(testAddressSearch.getAddressLn2()).isEqualTo(UPDATED_ADDRESS_LN_2);
                assertThat(testAddressSearch.getAddressLn3()).isEqualTo(UPDATED_ADDRESS_LN_3);
                assertThat(testAddressSearch.getAddressLn4()).isEqualTo(UPDATED_ADDRESS_LN_4);
                assertThat(testAddressSearch.getLandmark()).isEqualTo(UPDATED_LANDMARK);
                assertThat(testAddressSearch.getVillage()).isEqualTo(UPDATED_VILLAGE);
                assertThat(testAddressSearch.getCity()).isEqualTo(UPDATED_CITY);
                assertThat(testAddressSearch.getDistrict()).isEqualTo(UPDATED_DISTRICT);
                assertThat(testAddressSearch.getState()).isEqualTo(UPDATED_STATE);
                assertThat(testAddressSearch.getParentTableId()).isEqualTo(UPDATED_PARENT_TABLE_ID);
                assertThat(testAddressSearch.getParentModuleKy()).isEqualTo(UPDATED_PARENT_MODULE_KY);
                assertThat(testAddressSearch.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
                assertThat(testAddressSearch.getPin()).isEqualTo(UPDATED_PIN);
                assertThat(testAddressSearch.getCountry()).isEqualTo(UPDATED_COUNTRY);
                assertThat(testAddressSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testAddressSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testAddressSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testAddressSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testAddressSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testAddressSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testAddressSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testAddressSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testAddressSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testAddressSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testAddressSearch.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
                assertThat(testAddressSearch.getZone()).isEqualTo(UPDATED_ZONE);
                assertThat(testAddressSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testAddressSearch.getHist()).isEqualTo(UPDATED_HIST);
                assertThat(testAddressSearch.getColumn1()).isEqualTo(UPDATED_COLUMN_1);
            });
    }

    @Test
    @Transactional
    void putNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .addressType(UPDATED_ADDRESS_TYPE)
            .addressLn1(UPDATED_ADDRESS_LN_1)
            .addressLn2(UPDATED_ADDRESS_LN_2)
            .addressLn3(UPDATED_ADDRESS_LN_3)
            .landmark(UPDATED_LANDMARK)
            .village(UPDATED_VILLAGE)
            .state(UPDATED_STATE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .remarks(UPDATED_REMARKS)
            .extraFields(UPDATED_EXTRA_FIELDS)
            .hist(UPDATED_HIST);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testAddress.getAddressLn1()).isEqualTo(UPDATED_ADDRESS_LN_1);
        assertThat(testAddress.getAddressLn2()).isEqualTo(UPDATED_ADDRESS_LN_2);
        assertThat(testAddress.getAddressLn3()).isEqualTo(UPDATED_ADDRESS_LN_3);
        assertThat(testAddress.getAddressLn4()).isEqualTo(DEFAULT_ADDRESS_LN_4);
        assertThat(testAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testAddress.getVillage()).isEqualTo(UPDATED_VILLAGE);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getParentTableId()).isEqualTo(DEFAULT_PARENT_TABLE_ID);
        assertThat(testAddress.getParentModuleKy()).isEqualTo(DEFAULT_PARENT_MODULE_KY);
        assertThat(testAddress.getParentTableKy()).isEqualTo(DEFAULT_PARENT_TABLE_KY);
        assertThat(testAddress.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddress.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testAddress.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testAddress.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testAddress.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testAddress.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAddress.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testAddress.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testAddress.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testAddress.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testAddress.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAddress.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        assertThat(testAddress.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testAddress.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testAddress.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testAddress.getColumn1()).isEqualTo(DEFAULT_COLUMN_1);
    }

    @Test
    @Transactional
    void fullUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .name(UPDATED_NAME)
            .addressType(UPDATED_ADDRESS_TYPE)
            .addressLn1(UPDATED_ADDRESS_LN_1)
            .addressLn2(UPDATED_ADDRESS_LN_2)
            .addressLn3(UPDATED_ADDRESS_LN_3)
            .addressLn4(UPDATED_ADDRESS_LN_4)
            .landmark(UPDATED_LANDMARK)
            .village(UPDATED_VILLAGE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .state(UPDATED_STATE)
            .parentTableId(UPDATED_PARENT_TABLE_ID)
            .parentModuleKy(UPDATED_PARENT_MODULE_KY)
            .parentTableKy(UPDATED_PARENT_TABLE_KY)
            .pin(UPDATED_PIN)
            .country(UPDATED_COUNTRY)
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
            .zone(UPDATED_ZONE)
            .orgId(UPDATED_ORG_ID)
            .hist(UPDATED_HIST)
            .column1(UPDATED_COLUMN_1);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testAddress.getAddressLn1()).isEqualTo(UPDATED_ADDRESS_LN_1);
        assertThat(testAddress.getAddressLn2()).isEqualTo(UPDATED_ADDRESS_LN_2);
        assertThat(testAddress.getAddressLn3()).isEqualTo(UPDATED_ADDRESS_LN_3);
        assertThat(testAddress.getAddressLn4()).isEqualTo(UPDATED_ADDRESS_LN_4);
        assertThat(testAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testAddress.getVillage()).isEqualTo(UPDATED_VILLAGE);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getParentTableId()).isEqualTo(UPDATED_PARENT_TABLE_ID);
        assertThat(testAddress.getParentModuleKy()).isEqualTo(UPDATED_PARENT_MODULE_KY);
        assertThat(testAddress.getParentTableKy()).isEqualTo(UPDATED_PARENT_TABLE_KY);
        assertThat(testAddress.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddress.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAddress.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testAddress.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testAddress.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testAddress.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAddress.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testAddress.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testAddress.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testAddress.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testAddress.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAddress.getExtraFields()).isEqualTo(UPDATED_EXTRA_FIELDS);
        assertThat(testAddress.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testAddress.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testAddress.getHist()).isEqualTo(UPDATED_HIST);
        assertThat(testAddress.getColumn1()).isEqualTo(UPDATED_COLUMN_1);
    }

    @Test
    @Transactional
    void patchNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
        addressRepository.save(address);
        addressSearchRepository.save(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the address
        restAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, address.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(addressSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchAddress() throws Exception {
        // Initialize the database
        address = addressRepository.saveAndFlush(address);
        addressSearchRepository.save(address);

        // Search the address
        restAddressMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE)))
            .andExpect(jsonPath("$.[*].addressLn1").value(hasItem(DEFAULT_ADDRESS_LN_1)))
            .andExpect(jsonPath("$.[*].addressLn2").value(hasItem(DEFAULT_ADDRESS_LN_2)))
            .andExpect(jsonPath("$.[*].addressLn3").value(hasItem(DEFAULT_ADDRESS_LN_3)))
            .andExpect(jsonPath("$.[*].addressLn4").value(hasItem(DEFAULT_ADDRESS_LN_4)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].village").value(hasItem(DEFAULT_VILLAGE.intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.intValue())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.intValue())))
            .andExpect(jsonPath("$.[*].parentTableId").value(hasItem(DEFAULT_PARENT_TABLE_ID)))
            .andExpect(jsonPath("$.[*].parentModuleKy").value(hasItem(DEFAULT_PARENT_MODULE_KY)))
            .andExpect(jsonPath("$.[*].parentTableKy").value(hasItem(DEFAULT_PARENT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.intValue())))
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
            .andExpect(jsonPath("$.[*].extraFields").value(hasItem(DEFAULT_EXTRA_FIELDS)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].hist").value(hasItem(DEFAULT_HIST)))
            .andExpect(jsonPath("$.[*].column1").value(hasItem(DEFAULT_COLUMN_1)));
    }
}
