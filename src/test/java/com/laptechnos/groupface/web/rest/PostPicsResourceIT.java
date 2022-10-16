package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.PostPics;
import com.laptechnos.groupface.repository.PostPicsRepository;
import com.laptechnos.groupface.repository.search.PostPicsSearchRepository;
import com.laptechnos.groupface.service.PostPicsService;
import com.laptechnos.groupface.service.dto.PostPicsDTO;
import com.laptechnos.groupface.service.mapper.PostPicsMapper;
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
 * Integration tests for the {@link PostPicsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostPicsResourceIT {

    private static final Long DEFAULT_POSTID = 1L;
    private static final Long UPDATED_POSTID = 2L;

    private static final String DEFAULT_PIC = "AAAAAAAAAA";
    private static final String UPDATED_PIC = "BBBBBBBBBB";

    private static final String DEFAULT_PICPATH = "AAAAAAAAAA";
    private static final String UPDATED_PICPATH = "BBBBBBBBBB";

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

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String ENTITY_API_URL = "/api/post-pics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/post-pics";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostPicsRepository postPicsRepository;

    @Mock
    private PostPicsRepository postPicsRepositoryMock;

    @Autowired
    private PostPicsMapper postPicsMapper;

    @Mock
    private PostPicsService postPicsServiceMock;

    @Autowired
    private PostPicsSearchRepository postPicsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostPicsMockMvc;

    private PostPics postPics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostPics createEntity(EntityManager em) {
        PostPics postPics = new PostPics()
            .postid(DEFAULT_POSTID)
            .pic(DEFAULT_PIC)
            .picpath(DEFAULT_PICPATH)
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
            .status(DEFAULT_STATUS);
        return postPics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostPics createUpdatedEntity(EntityManager em) {
        PostPics postPics = new PostPics()
            .postid(UPDATED_POSTID)
            .pic(UPDATED_PIC)
            .picpath(UPDATED_PICPATH)
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
            .status(UPDATED_STATUS);
        return postPics;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        postPicsSearchRepository.deleteAll();
        assertThat(postPicsSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        postPics = createEntity(em);
    }

    @Test
    @Transactional
    void createPostPics() throws Exception {
        int databaseSizeBeforeCreate = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        // Create the PostPics
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(postPics);
        restPostPicsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postPicsDTO)))
            .andExpect(status().isCreated());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        PostPics testPostPics = postPicsList.get(postPicsList.size() - 1);
        assertThat(testPostPics.getPostid()).isEqualTo(DEFAULT_POSTID);
        assertThat(testPostPics.getPic()).isEqualTo(DEFAULT_PIC);
        assertThat(testPostPics.getPicpath()).isEqualTo(DEFAULT_PICPATH);
        assertThat(testPostPics.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPostPics.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testPostPics.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testPostPics.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testPostPics.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPostPics.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testPostPics.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testPostPics.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testPostPics.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPostPics.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testPostPics.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testPostPics.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createPostPicsWithExistingId() throws Exception {
        // Create the PostPics with an existing ID
        postPics.setId(1L);
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(postPics);

        int databaseSizeBeforeCreate = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostPicsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postPicsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllPostPics() throws Exception {
        // Initialize the database
        postPicsRepository.saveAndFlush(postPics);

        // Get all the postPicsList
        restPostPicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postPics.getId().intValue())))
            .andExpect(jsonPath("$.[*].postid").value(hasItem(DEFAULT_POSTID.intValue())))
            .andExpect(jsonPath("$.[*].pic").value(hasItem(DEFAULT_PIC)))
            .andExpect(jsonPath("$.[*].picpath").value(hasItem(DEFAULT_PICPATH)))
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
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostPicsWithEagerRelationshipsIsEnabled() throws Exception {
        when(postPicsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostPicsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(postPicsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostPicsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postPicsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostPicsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(postPicsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPostPics() throws Exception {
        // Initialize the database
        postPicsRepository.saveAndFlush(postPics);

        // Get the postPics
        restPostPicsMockMvc
            .perform(get(ENTITY_API_URL_ID, postPics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postPics.getId().intValue()))
            .andExpect(jsonPath("$.postid").value(DEFAULT_POSTID.intValue()))
            .andExpect(jsonPath("$.pic").value(DEFAULT_PIC))
            .andExpect(jsonPath("$.picpath").value(DEFAULT_PICPATH))
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
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingPostPics() throws Exception {
        // Get the postPics
        restPostPicsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPostPics() throws Exception {
        // Initialize the database
        postPicsRepository.saveAndFlush(postPics);

        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();
        postPicsSearchRepository.save(postPics);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());

        // Update the postPics
        PostPics updatedPostPics = postPicsRepository.findById(postPics.getId()).get();
        // Disconnect from session so that the updates on updatedPostPics are not directly saved in db
        em.detach(updatedPostPics);
        updatedPostPics
            .postid(UPDATED_POSTID)
            .pic(UPDATED_PIC)
            .picpath(UPDATED_PICPATH)
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
            .status(UPDATED_STATUS);
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(updatedPostPics);

        restPostPicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postPicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postPicsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        PostPics testPostPics = postPicsList.get(postPicsList.size() - 1);
        assertThat(testPostPics.getPostid()).isEqualTo(UPDATED_POSTID);
        assertThat(testPostPics.getPic()).isEqualTo(UPDATED_PIC);
        assertThat(testPostPics.getPicpath()).isEqualTo(UPDATED_PICPATH);
        assertThat(testPostPics.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPostPics.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testPostPics.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testPostPics.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testPostPics.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPostPics.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPostPics.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testPostPics.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testPostPics.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPostPics.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPostPics.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testPostPics.getStatus()).isEqualTo(UPDATED_STATUS);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<PostPics> postPicsSearchList = IterableUtils.toList(postPicsSearchRepository.findAll());
                PostPics testPostPicsSearch = postPicsSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testPostPicsSearch.getPostid()).isEqualTo(UPDATED_POSTID);
                assertThat(testPostPicsSearch.getPic()).isEqualTo(UPDATED_PIC);
                assertThat(testPostPicsSearch.getPicpath()).isEqualTo(UPDATED_PICPATH);
                assertThat(testPostPicsSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testPostPicsSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testPostPicsSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testPostPicsSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testPostPicsSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testPostPicsSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testPostPicsSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testPostPicsSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testPostPicsSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testPostPicsSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testPostPicsSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testPostPicsSearch.getStatus()).isEqualTo(UPDATED_STATUS);
            });
    }

    @Test
    @Transactional
    void putNonExistingPostPics() throws Exception {
        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        postPics.setId(count.incrementAndGet());

        // Create the PostPics
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(postPics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostPicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postPicsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postPicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostPics() throws Exception {
        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        postPics.setId(count.incrementAndGet());

        // Create the PostPics
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(postPics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostPicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postPicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostPics() throws Exception {
        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        postPics.setId(count.incrementAndGet());

        // Create the PostPics
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(postPics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostPicsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postPicsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdatePostPicsWithPatch() throws Exception {
        // Initialize the database
        postPicsRepository.saveAndFlush(postPics);

        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();

        // Update the postPics using partial update
        PostPics partialUpdatedPostPics = new PostPics();
        partialUpdatedPostPics.setId(postPics.getId());

        partialUpdatedPostPics
            .picpath(UPDATED_PICPATH)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .remarks(UPDATED_REMARKS)
            .orgId(UPDATED_ORG_ID)
            .status(UPDATED_STATUS);

        restPostPicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostPics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostPics))
            )
            .andExpect(status().isOk());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        PostPics testPostPics = postPicsList.get(postPicsList.size() - 1);
        assertThat(testPostPics.getPostid()).isEqualTo(DEFAULT_POSTID);
        assertThat(testPostPics.getPic()).isEqualTo(DEFAULT_PIC);
        assertThat(testPostPics.getPicpath()).isEqualTo(UPDATED_PICPATH);
        assertThat(testPostPics.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPostPics.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testPostPics.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testPostPics.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testPostPics.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPostPics.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPostPics.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testPostPics.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testPostPics.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPostPics.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPostPics.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testPostPics.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePostPicsWithPatch() throws Exception {
        // Initialize the database
        postPicsRepository.saveAndFlush(postPics);

        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();

        // Update the postPics using partial update
        PostPics partialUpdatedPostPics = new PostPics();
        partialUpdatedPostPics.setId(postPics.getId());

        partialUpdatedPostPics
            .postid(UPDATED_POSTID)
            .pic(UPDATED_PIC)
            .picpath(UPDATED_PICPATH)
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
            .status(UPDATED_STATUS);

        restPostPicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostPics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostPics))
            )
            .andExpect(status().isOk());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        PostPics testPostPics = postPicsList.get(postPicsList.size() - 1);
        assertThat(testPostPics.getPostid()).isEqualTo(UPDATED_POSTID);
        assertThat(testPostPics.getPic()).isEqualTo(UPDATED_PIC);
        assertThat(testPostPics.getPicpath()).isEqualTo(UPDATED_PICPATH);
        assertThat(testPostPics.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPostPics.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testPostPics.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testPostPics.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testPostPics.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPostPics.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPostPics.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testPostPics.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testPostPics.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPostPics.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPostPics.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testPostPics.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPostPics() throws Exception {
        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        postPics.setId(count.incrementAndGet());

        // Create the PostPics
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(postPics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostPicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postPicsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postPicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostPics() throws Exception {
        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        postPics.setId(count.incrementAndGet());

        // Create the PostPics
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(postPics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostPicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postPicsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostPics() throws Exception {
        int databaseSizeBeforeUpdate = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        postPics.setId(count.incrementAndGet());

        // Create the PostPics
        PostPicsDTO postPicsDTO = postPicsMapper.toDto(postPics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostPicsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postPicsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostPics in the database
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deletePostPics() throws Exception {
        // Initialize the database
        postPicsRepository.saveAndFlush(postPics);
        postPicsRepository.save(postPics);
        postPicsSearchRepository.save(postPics);

        int databaseSizeBeforeDelete = postPicsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the postPics
        restPostPicsMockMvc
            .perform(delete(ENTITY_API_URL_ID, postPics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostPics> postPicsList = postPicsRepository.findAll();
        assertThat(postPicsList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postPicsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchPostPics() throws Exception {
        // Initialize the database
        postPics = postPicsRepository.saveAndFlush(postPics);
        postPicsSearchRepository.save(postPics);

        // Search the postPics
        restPostPicsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + postPics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postPics.getId().intValue())))
            .andExpect(jsonPath("$.[*].postid").value(hasItem(DEFAULT_POSTID.intValue())))
            .andExpect(jsonPath("$.[*].pic").value(hasItem(DEFAULT_PIC)))
            .andExpect(jsonPath("$.[*].picpath").value(hasItem(DEFAULT_PICPATH)))
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
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
