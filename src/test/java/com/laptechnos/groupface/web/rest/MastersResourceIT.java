package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Masters;
import com.laptechnos.groupface.repository.MastersRepository;
import com.laptechnos.groupface.repository.search.MastersSearchRepository;
import com.laptechnos.groupface.service.MastersService;
import com.laptechnos.groupface.service.dto.MastersDTO;
import com.laptechnos.groupface.service.mapper.MastersMapper;
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
 * Integration tests for the {@link MastersResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MastersResourceIT {

    private static final Integer DEFAULT_KEY = 1;
    private static final Integer UPDATED_KEY = 2;

    private static final String DEFAULT_VAL = "AAAAAAAAAA";
    private static final String UPDATED_VAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_CODEVAL = "AAAAAAAAAA";
    private static final String UPDATED_CODEVAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final String ENTITY_API_URL = "/api/masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/masters";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MastersRepository mastersRepository;

    @Mock
    private MastersRepository mastersRepositoryMock;

    @Autowired
    private MastersMapper mastersMapper;

    @Mock
    private MastersService mastersServiceMock;

    @Autowired
    private MastersSearchRepository mastersSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMastersMockMvc;

    private Masters masters;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Masters createEntity(EntityManager em) {
        Masters masters = new Masters()
            .key(DEFAULT_KEY)
            .val(DEFAULT_VAL)
            .code(DEFAULT_CODE)
            .codeval(DEFAULT_CODEVAL)
            .status(DEFAULT_STATUS)
            .orgId(DEFAULT_ORG_ID);
        return masters;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Masters createUpdatedEntity(EntityManager em) {
        Masters masters = new Masters()
            .key(UPDATED_KEY)
            .val(UPDATED_VAL)
            .code(UPDATED_CODE)
            .codeval(UPDATED_CODEVAL)
            .status(UPDATED_STATUS)
            .orgId(UPDATED_ORG_ID);
        return masters;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        mastersSearchRepository.deleteAll();
        assertThat(mastersSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        masters = createEntity(em);
    }

    @Test
    @Transactional
    void createMasters() throws Exception {
        int databaseSizeBeforeCreate = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        // Create the Masters
        MastersDTO mastersDTO = mastersMapper.toDto(masters);
        restMastersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mastersDTO)))
            .andExpect(status().isCreated());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Masters testMasters = mastersList.get(mastersList.size() - 1);
        assertThat(testMasters.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testMasters.getVal()).isEqualTo(DEFAULT_VAL);
        assertThat(testMasters.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMasters.getCodeval()).isEqualTo(DEFAULT_CODEVAL);
        assertThat(testMasters.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMasters.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
    }

    @Test
    @Transactional
    void createMastersWithExistingId() throws Exception {
        // Create the Masters with an existing ID
        masters.setId(1L);
        MastersDTO mastersDTO = mastersMapper.toDto(masters);

        int databaseSizeBeforeCreate = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restMastersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mastersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllMasters() throws Exception {
        // Initialize the database
        mastersRepository.saveAndFlush(masters);

        // Get all the mastersList
        restMastersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masters.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].val").value(hasItem(DEFAULT_VAL)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeval").value(hasItem(DEFAULT_CODEVAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMastersWithEagerRelationshipsIsEnabled() throws Exception {
        when(mastersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMastersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mastersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMastersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mastersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMastersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(mastersRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMasters() throws Exception {
        // Initialize the database
        mastersRepository.saveAndFlush(masters);

        // Get the masters
        restMastersMockMvc
            .perform(get(ENTITY_API_URL_ID, masters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(masters.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.val").value(DEFAULT_VAL))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.codeval").value(DEFAULT_CODEVAL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingMasters() throws Exception {
        // Get the masters
        restMastersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMasters() throws Exception {
        // Initialize the database
        mastersRepository.saveAndFlush(masters);

        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();
        mastersSearchRepository.save(masters);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());

        // Update the masters
        Masters updatedMasters = mastersRepository.findById(masters.getId()).get();
        // Disconnect from session so that the updates on updatedMasters are not directly saved in db
        em.detach(updatedMasters);
        updatedMasters
            .key(UPDATED_KEY)
            .val(UPDATED_VAL)
            .code(UPDATED_CODE)
            .codeval(UPDATED_CODEVAL)
            .status(UPDATED_STATUS)
            .orgId(UPDATED_ORG_ID);
        MastersDTO mastersDTO = mastersMapper.toDto(updatedMasters);

        restMastersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mastersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mastersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        Masters testMasters = mastersList.get(mastersList.size() - 1);
        assertThat(testMasters.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testMasters.getVal()).isEqualTo(UPDATED_VAL);
        assertThat(testMasters.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMasters.getCodeval()).isEqualTo(UPDATED_CODEVAL);
        assertThat(testMasters.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMasters.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Masters> mastersSearchList = IterableUtils.toList(mastersSearchRepository.findAll());
                Masters testMastersSearch = mastersSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testMastersSearch.getKey()).isEqualTo(UPDATED_KEY);
                assertThat(testMastersSearch.getVal()).isEqualTo(UPDATED_VAL);
                assertThat(testMastersSearch.getCode()).isEqualTo(UPDATED_CODE);
                assertThat(testMastersSearch.getCodeval()).isEqualTo(UPDATED_CODEVAL);
                assertThat(testMastersSearch.getStatus()).isEqualTo(UPDATED_STATUS);
                assertThat(testMastersSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
            });
    }

    @Test
    @Transactional
    void putNonExistingMasters() throws Exception {
        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        masters.setId(count.incrementAndGet());

        // Create the Masters
        MastersDTO mastersDTO = mastersMapper.toDto(masters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMastersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mastersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mastersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchMasters() throws Exception {
        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        masters.setId(count.incrementAndGet());

        // Create the Masters
        MastersDTO mastersDTO = mastersMapper.toDto(masters);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMastersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mastersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMasters() throws Exception {
        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        masters.setId(count.incrementAndGet());

        // Create the Masters
        MastersDTO mastersDTO = mastersMapper.toDto(masters);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMastersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mastersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateMastersWithPatch() throws Exception {
        // Initialize the database
        mastersRepository.saveAndFlush(masters);

        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();

        // Update the masters using partial update
        Masters partialUpdatedMasters = new Masters();
        partialUpdatedMasters.setId(masters.getId());

        partialUpdatedMasters.code(UPDATED_CODE).codeval(UPDATED_CODEVAL).orgId(UPDATED_ORG_ID);

        restMastersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMasters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMasters))
            )
            .andExpect(status().isOk());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        Masters testMasters = mastersList.get(mastersList.size() - 1);
        assertThat(testMasters.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testMasters.getVal()).isEqualTo(DEFAULT_VAL);
        assertThat(testMasters.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMasters.getCodeval()).isEqualTo(UPDATED_CODEVAL);
        assertThat(testMasters.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMasters.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    void fullUpdateMastersWithPatch() throws Exception {
        // Initialize the database
        mastersRepository.saveAndFlush(masters);

        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();

        // Update the masters using partial update
        Masters partialUpdatedMasters = new Masters();
        partialUpdatedMasters.setId(masters.getId());

        partialUpdatedMasters
            .key(UPDATED_KEY)
            .val(UPDATED_VAL)
            .code(UPDATED_CODE)
            .codeval(UPDATED_CODEVAL)
            .status(UPDATED_STATUS)
            .orgId(UPDATED_ORG_ID);

        restMastersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMasters.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMasters))
            )
            .andExpect(status().isOk());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        Masters testMasters = mastersList.get(mastersList.size() - 1);
        assertThat(testMasters.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testMasters.getVal()).isEqualTo(UPDATED_VAL);
        assertThat(testMasters.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMasters.getCodeval()).isEqualTo(UPDATED_CODEVAL);
        assertThat(testMasters.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMasters.getOrgId()).isEqualTo(UPDATED_ORG_ID);
    }

    @Test
    @Transactional
    void patchNonExistingMasters() throws Exception {
        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        masters.setId(count.incrementAndGet());

        // Create the Masters
        MastersDTO mastersDTO = mastersMapper.toDto(masters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMastersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mastersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mastersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMasters() throws Exception {
        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        masters.setId(count.incrementAndGet());

        // Create the Masters
        MastersDTO mastersDTO = mastersMapper.toDto(masters);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMastersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mastersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMasters() throws Exception {
        int databaseSizeBeforeUpdate = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        masters.setId(count.incrementAndGet());

        // Create the Masters
        MastersDTO mastersDTO = mastersMapper.toDto(masters);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMastersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mastersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Masters in the database
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteMasters() throws Exception {
        // Initialize the database
        mastersRepository.saveAndFlush(masters);
        mastersRepository.save(masters);
        mastersSearchRepository.save(masters);

        int databaseSizeBeforeDelete = mastersRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the masters
        restMastersMockMvc
            .perform(delete(ENTITY_API_URL_ID, masters.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Masters> mastersList = mastersRepository.findAll();
        assertThat(mastersList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(mastersSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchMasters() throws Exception {
        // Initialize the database
        masters = mastersRepository.saveAndFlush(masters);
        mastersSearchRepository.save(masters);

        // Search the masters
        restMastersMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + masters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masters.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].val").value(hasItem(DEFAULT_VAL)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeval").value(hasItem(DEFAULT_CODEVAL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())));
    }
}
