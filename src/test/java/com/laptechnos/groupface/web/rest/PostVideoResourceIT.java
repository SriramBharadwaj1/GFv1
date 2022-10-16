package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.PostVideo;
import com.laptechnos.groupface.repository.PostVideoRepository;
import com.laptechnos.groupface.repository.search.PostVideoSearchRepository;
import com.laptechnos.groupface.service.PostVideoService;
import com.laptechnos.groupface.service.dto.PostVideoDTO;
import com.laptechnos.groupface.service.mapper.PostVideoMapper;
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
 * Integration tests for the {@link PostVideoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostVideoResourceIT {

    private static final Long DEFAULT_POSTID = 1L;
    private static final Long UPDATED_POSTID = 2L;

    private static final String DEFAULT_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEOPATH = "AAAAAAAAAA";
    private static final String UPDATED_VIDEOPATH = "BBBBBBBBBB";

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

    private static final Integer DEFAULT_PRIM_VIDEO = 1;
    private static final Integer UPDATED_PRIM_VIDEO = 2;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String ENTITY_API_URL = "/api/post-videos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/post-videos";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostVideoRepository postVideoRepository;

    @Mock
    private PostVideoRepository postVideoRepositoryMock;

    @Autowired
    private PostVideoMapper postVideoMapper;

    @Mock
    private PostVideoService postVideoServiceMock;

    @Autowired
    private PostVideoSearchRepository postVideoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostVideoMockMvc;

    private PostVideo postVideo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostVideo createEntity(EntityManager em) {
        PostVideo postVideo = new PostVideo()
            .postid(DEFAULT_POSTID)
            .video(DEFAULT_VIDEO)
            .videopath(DEFAULT_VIDEOPATH)
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
            .primVideo(DEFAULT_PRIM_VIDEO)
            .orgId(DEFAULT_ORG_ID)
            .status(DEFAULT_STATUS);
        return postVideo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostVideo createUpdatedEntity(EntityManager em) {
        PostVideo postVideo = new PostVideo()
            .postid(UPDATED_POSTID)
            .video(UPDATED_VIDEO)
            .videopath(UPDATED_VIDEOPATH)
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
            .primVideo(UPDATED_PRIM_VIDEO)
            .orgId(UPDATED_ORG_ID)
            .status(UPDATED_STATUS);
        return postVideo;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        postVideoSearchRepository.deleteAll();
        assertThat(postVideoSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        postVideo = createEntity(em);
    }

    @Test
    @Transactional
    void createPostVideo() throws Exception {
        int databaseSizeBeforeCreate = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        // Create the PostVideo
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(postVideo);
        restPostVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postVideoDTO)))
            .andExpect(status().isCreated());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        PostVideo testPostVideo = postVideoList.get(postVideoList.size() - 1);
        assertThat(testPostVideo.getPostid()).isEqualTo(DEFAULT_POSTID);
        assertThat(testPostVideo.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testPostVideo.getVideopath()).isEqualTo(DEFAULT_VIDEOPATH);
        assertThat(testPostVideo.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPostVideo.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testPostVideo.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testPostVideo.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testPostVideo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPostVideo.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testPostVideo.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testPostVideo.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testPostVideo.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPostVideo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testPostVideo.getPrimVideo()).isEqualTo(DEFAULT_PRIM_VIDEO);
        assertThat(testPostVideo.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testPostVideo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createPostVideoWithExistingId() throws Exception {
        // Create the PostVideo with an existing ID
        postVideo.setId(1L);
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(postVideo);

        int databaseSizeBeforeCreate = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostVideoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postVideoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllPostVideos() throws Exception {
        // Initialize the database
        postVideoRepository.saveAndFlush(postVideo);

        // Get all the postVideoList
        restPostVideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postVideo.getId().intValue())))
            .andExpect(jsonPath("$.[*].postid").value(hasItem(DEFAULT_POSTID.intValue())))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].videopath").value(hasItem(DEFAULT_VIDEOPATH)))
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
            .andExpect(jsonPath("$.[*].primVideo").value(hasItem(DEFAULT_PRIM_VIDEO)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostVideosWithEagerRelationshipsIsEnabled() throws Exception {
        when(postVideoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostVideoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(postVideoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostVideosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postVideoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostVideoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(postVideoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPostVideo() throws Exception {
        // Initialize the database
        postVideoRepository.saveAndFlush(postVideo);

        // Get the postVideo
        restPostVideoMockMvc
            .perform(get(ENTITY_API_URL_ID, postVideo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postVideo.getId().intValue()))
            .andExpect(jsonPath("$.postid").value(DEFAULT_POSTID.intValue()))
            .andExpect(jsonPath("$.video").value(DEFAULT_VIDEO))
            .andExpect(jsonPath("$.videopath").value(DEFAULT_VIDEOPATH))
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
            .andExpect(jsonPath("$.primVideo").value(DEFAULT_PRIM_VIDEO))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingPostVideo() throws Exception {
        // Get the postVideo
        restPostVideoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPostVideo() throws Exception {
        // Initialize the database
        postVideoRepository.saveAndFlush(postVideo);

        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();
        postVideoSearchRepository.save(postVideo);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());

        // Update the postVideo
        PostVideo updatedPostVideo = postVideoRepository.findById(postVideo.getId()).get();
        // Disconnect from session so that the updates on updatedPostVideo are not directly saved in db
        em.detach(updatedPostVideo);
        updatedPostVideo
            .postid(UPDATED_POSTID)
            .video(UPDATED_VIDEO)
            .videopath(UPDATED_VIDEOPATH)
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
            .primVideo(UPDATED_PRIM_VIDEO)
            .orgId(UPDATED_ORG_ID)
            .status(UPDATED_STATUS);
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(updatedPostVideo);

        restPostVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postVideoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postVideoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        PostVideo testPostVideo = postVideoList.get(postVideoList.size() - 1);
        assertThat(testPostVideo.getPostid()).isEqualTo(UPDATED_POSTID);
        assertThat(testPostVideo.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testPostVideo.getVideopath()).isEqualTo(UPDATED_VIDEOPATH);
        assertThat(testPostVideo.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPostVideo.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testPostVideo.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testPostVideo.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testPostVideo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPostVideo.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPostVideo.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testPostVideo.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testPostVideo.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPostVideo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPostVideo.getPrimVideo()).isEqualTo(UPDATED_PRIM_VIDEO);
        assertThat(testPostVideo.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testPostVideo.getStatus()).isEqualTo(UPDATED_STATUS);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<PostVideo> postVideoSearchList = IterableUtils.toList(postVideoSearchRepository.findAll());
                PostVideo testPostVideoSearch = postVideoSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testPostVideoSearch.getPostid()).isEqualTo(UPDATED_POSTID);
                assertThat(testPostVideoSearch.getVideo()).isEqualTo(UPDATED_VIDEO);
                assertThat(testPostVideoSearch.getVideopath()).isEqualTo(UPDATED_VIDEOPATH);
                assertThat(testPostVideoSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testPostVideoSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testPostVideoSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testPostVideoSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testPostVideoSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testPostVideoSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testPostVideoSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testPostVideoSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testPostVideoSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testPostVideoSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testPostVideoSearch.getPrimVideo()).isEqualTo(UPDATED_PRIM_VIDEO);
                assertThat(testPostVideoSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testPostVideoSearch.getStatus()).isEqualTo(UPDATED_STATUS);
            });
    }

    @Test
    @Transactional
    void putNonExistingPostVideo() throws Exception {
        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        postVideo.setId(count.incrementAndGet());

        // Create the PostVideo
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(postVideo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postVideoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchPostVideo() throws Exception {
        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        postVideo.setId(count.incrementAndGet());

        // Create the PostVideo
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(postVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPostVideo() throws Exception {
        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        postVideo.setId(count.incrementAndGet());

        // Create the PostVideo
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(postVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostVideoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postVideoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdatePostVideoWithPatch() throws Exception {
        // Initialize the database
        postVideoRepository.saveAndFlush(postVideo);

        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();

        // Update the postVideo using partial update
        PostVideo partialUpdatedPostVideo = new PostVideo();
        partialUpdatedPostVideo.setId(postVideo.getId());

        partialUpdatedPostVideo
            .videopath(UPDATED_VIDEOPATH)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .comments(UPDATED_COMMENTS);

        restPostVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostVideo))
            )
            .andExpect(status().isOk());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        PostVideo testPostVideo = postVideoList.get(postVideoList.size() - 1);
        assertThat(testPostVideo.getPostid()).isEqualTo(DEFAULT_POSTID);
        assertThat(testPostVideo.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testPostVideo.getVideopath()).isEqualTo(UPDATED_VIDEOPATH);
        assertThat(testPostVideo.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPostVideo.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testPostVideo.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testPostVideo.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testPostVideo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPostVideo.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testPostVideo.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testPostVideo.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testPostVideo.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPostVideo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testPostVideo.getPrimVideo()).isEqualTo(DEFAULT_PRIM_VIDEO);
        assertThat(testPostVideo.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testPostVideo.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePostVideoWithPatch() throws Exception {
        // Initialize the database
        postVideoRepository.saveAndFlush(postVideo);

        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();

        // Update the postVideo using partial update
        PostVideo partialUpdatedPostVideo = new PostVideo();
        partialUpdatedPostVideo.setId(postVideo.getId());

        partialUpdatedPostVideo
            .postid(UPDATED_POSTID)
            .video(UPDATED_VIDEO)
            .videopath(UPDATED_VIDEOPATH)
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
            .primVideo(UPDATED_PRIM_VIDEO)
            .orgId(UPDATED_ORG_ID)
            .status(UPDATED_STATUS);

        restPostVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPostVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPostVideo))
            )
            .andExpect(status().isOk());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        PostVideo testPostVideo = postVideoList.get(postVideoList.size() - 1);
        assertThat(testPostVideo.getPostid()).isEqualTo(UPDATED_POSTID);
        assertThat(testPostVideo.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testPostVideo.getVideopath()).isEqualTo(UPDATED_VIDEOPATH);
        assertThat(testPostVideo.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPostVideo.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testPostVideo.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testPostVideo.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testPostVideo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPostVideo.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPostVideo.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testPostVideo.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testPostVideo.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPostVideo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPostVideo.getPrimVideo()).isEqualTo(UPDATED_PRIM_VIDEO);
        assertThat(testPostVideo.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testPostVideo.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPostVideo() throws Exception {
        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        postVideo.setId(count.incrementAndGet());

        // Create the PostVideo
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(postVideo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postVideoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPostVideo() throws Exception {
        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        postVideo.setId(count.incrementAndGet());

        // Create the PostVideo
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(postVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPostVideo() throws Exception {
        int databaseSizeBeforeUpdate = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        postVideo.setId(count.incrementAndGet());

        // Create the PostVideo
        PostVideoDTO postVideoDTO = postVideoMapper.toDto(postVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostVideoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postVideoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PostVideo in the database
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deletePostVideo() throws Exception {
        // Initialize the database
        postVideoRepository.saveAndFlush(postVideo);
        postVideoRepository.save(postVideo);
        postVideoSearchRepository.save(postVideo);

        int databaseSizeBeforeDelete = postVideoRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the postVideo
        restPostVideoMockMvc
            .perform(delete(ENTITY_API_URL_ID, postVideo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostVideo> postVideoList = postVideoRepository.findAll();
        assertThat(postVideoList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postVideoSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchPostVideo() throws Exception {
        // Initialize the database
        postVideo = postVideoRepository.saveAndFlush(postVideo);
        postVideoSearchRepository.save(postVideo);

        // Search the postVideo
        restPostVideoMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + postVideo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postVideo.getId().intValue())))
            .andExpect(jsonPath("$.[*].postid").value(hasItem(DEFAULT_POSTID.intValue())))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].videopath").value(hasItem(DEFAULT_VIDEOPATH)))
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
            .andExpect(jsonPath("$.[*].primVideo").value(hasItem(DEFAULT_PRIM_VIDEO)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
