package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Post;
import com.laptechnos.groupface.repository.PostRepository;
import com.laptechnos.groupface.repository.search.PostSearchRepository;
import com.laptechnos.groupface.service.PostService;
import com.laptechnos.groupface.service.dto.PostDTO;
import com.laptechnos.groupface.service.mapper.PostMapper;
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
 * Integration tests for the {@link PostResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PostResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CATEGORY = 1;
    private static final Integer UPDATED_CATEGORY = 2;

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_META = "AAAAAAAAAA";
    private static final String UPDATED_META = "BBBBBBBBBB";

    private static final Integer DEFAULT_TABLE_KY = 1;
    private static final Integer UPDATED_TABLE_KY = 2;

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    private static final Integer DEFAULT_ACT_REJ_REASON = 1;
    private static final Integer UPDATED_ACT_REJ_REASON = 2;

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

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_IS_SALES_POST = 1;
    private static final Integer UPDATED_IS_SALES_POST = 2;

    private static final Integer DEFAULT_SALES_CATEGORY = 1;
    private static final Integer UPDATED_SALES_CATEGORY = 2;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALID_TILL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TILL = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE_AREA = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_PHNO = "AAAAAAAAAA";
    private static final String UPDATED_PHNO = "BBBBBBBBBB";

    private static final Long DEFAULT_VIDEO_GRP = 1L;
    private static final Long UPDATED_VIDEO_GRP = 2L;

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final String DEFAULT_OTHER_INFO = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_INFO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/posts";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PostRepository postRepository;

    @Mock
    private PostRepository postRepositoryMock;

    @Autowired
    private PostMapper postMapper;

    @Mock
    private PostService postServiceMock;

    @Autowired
    private PostSearchRepository postSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostMockMvc;

    private Post post;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
            .message(DEFAULT_MESSAGE)
            .category(DEFAULT_CATEGORY)
            .categoryName(DEFAULT_CATEGORY_NAME)
            .meta(DEFAULT_META)
            .tableKy(DEFAULT_TABLE_KY)
            .isActive(DEFAULT_IS_ACTIVE)
            .actRejReason(DEFAULT_ACT_REJ_REASON)
            .isEnable(DEFAULT_IS_ENABLE)
            .addedBy(DEFAULT_ADDED_BY)
            .addedOn(DEFAULT_ADDED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .approvedBy(DEFAULT_APPROVED_BY)
            .approvedOn(DEFAULT_APPROVED_ON)
            .comments(DEFAULT_COMMENTS)
            .remarks(DEFAULT_REMARKS)
            .type(DEFAULT_TYPE)
            .isSalesPost(DEFAULT_IS_SALES_POST)
            .salesCategory(DEFAULT_SALES_CATEGORY)
            .price(DEFAULT_PRICE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTill(DEFAULT_VALID_TILL)
            .phoneArea(DEFAULT_PHONE_AREA)
            .phno(DEFAULT_PHNO)
            .videoGrp(DEFAULT_VIDEO_GRP)
            .orgId(DEFAULT_ORG_ID)
            .otherInfo(DEFAULT_OTHER_INFO);
        return post;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity(EntityManager em) {
        Post post = new Post()
            .message(UPDATED_MESSAGE)
            .category(UPDATED_CATEGORY)
            .categoryName(UPDATED_CATEGORY_NAME)
            .meta(UPDATED_META)
            .tableKy(UPDATED_TABLE_KY)
            .isActive(UPDATED_IS_ACTIVE)
            .actRejReason(UPDATED_ACT_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS)
            .type(UPDATED_TYPE)
            .isSalesPost(UPDATED_IS_SALES_POST)
            .salesCategory(UPDATED_SALES_CATEGORY)
            .price(UPDATED_PRICE)
            .validFrom(UPDATED_VALID_FROM)
            .validTill(UPDATED_VALID_TILL)
            .phoneArea(UPDATED_PHONE_AREA)
            .phno(UPDATED_PHNO)
            .videoGrp(UPDATED_VIDEO_GRP)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);
        return post;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        postSearchRepository.deleteAll();
        assertThat(postSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        post = createEntity(em);
    }

    @Test
    @Transactional
    void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());
        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testPost.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPost.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testPost.getMeta()).isEqualTo(DEFAULT_META);
        assertThat(testPost.getTableKy()).isEqualTo(DEFAULT_TABLE_KY);
        assertThat(testPost.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testPost.getActRejReason()).isEqualTo(DEFAULT_ACT_REJ_REASON);
        assertThat(testPost.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testPost.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testPost.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testPost.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPost.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testPost.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testPost.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testPost.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testPost.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testPost.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPost.getIsSalesPost()).isEqualTo(DEFAULT_IS_SALES_POST);
        assertThat(testPost.getSalesCategory()).isEqualTo(DEFAULT_SALES_CATEGORY);
        assertThat(testPost.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPost.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPost.getValidTill()).isEqualTo(DEFAULT_VALID_TILL);
        assertThat(testPost.getPhoneArea()).isEqualTo(DEFAULT_PHONE_AREA);
        assertThat(testPost.getPhno()).isEqualTo(DEFAULT_PHNO);
        assertThat(testPost.getVideoGrp()).isEqualTo(DEFAULT_VIDEO_GRP);
        assertThat(testPost.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testPost.getOtherInfo()).isEqualTo(DEFAULT_OTHER_INFO);
    }

    @Test
    @Transactional
    void createPostWithExistingId() throws Exception {
        // Create the Post with an existing ID
        post.setId(1L);
        PostDTO postDTO = postMapper.toDto(post);

        int databaseSizeBeforeCreate = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllPosts() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList
        restPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].meta").value(hasItem(DEFAULT_META)))
            .andExpect(jsonPath("$.[*].tableKy").value(hasItem(DEFAULT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].actRejReason").value(hasItem(DEFAULT_ACT_REJ_REASON)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isSalesPost").value(hasItem(DEFAULT_IS_SALES_POST)))
            .andExpect(jsonPath("$.[*].salesCategory").value(hasItem(DEFAULT_SALES_CATEGORY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.toString())))
            .andExpect(jsonPath("$.[*].phoneArea").value(hasItem(DEFAULT_PHONE_AREA)))
            .andExpect(jsonPath("$.[*].phno").value(hasItem(DEFAULT_PHNO)))
            .andExpect(jsonPath("$.[*].videoGrp").value(hasItem(DEFAULT_VIDEO_GRP.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].otherInfo").value(hasItem(DEFAULT_OTHER_INFO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostsWithEagerRelationshipsIsEnabled() throws Exception {
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(postServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(postRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc
            .perform(get(ENTITY_API_URL_ID, post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(post.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME))
            .andExpect(jsonPath("$.meta").value(DEFAULT_META))
            .andExpect(jsonPath("$.tableKy").value(DEFAULT_TABLE_KY))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.actRejReason").value(DEFAULT_ACT_REJ_REASON))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.isSalesPost").value(DEFAULT_IS_SALES_POST))
            .andExpect(jsonPath("$.salesCategory").value(DEFAULT_SALES_CATEGORY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTill").value(DEFAULT_VALID_TILL.toString()))
            .andExpect(jsonPath("$.phoneArea").value(DEFAULT_PHONE_AREA))
            .andExpect(jsonPath("$.phno").value(DEFAULT_PHNO))
            .andExpect(jsonPath("$.videoGrp").value(DEFAULT_VIDEO_GRP.intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.otherInfo").value(DEFAULT_OTHER_INFO));
    }

    @Test
    @Transactional
    void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        postSearchRepository.save(post);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).get();
        // Disconnect from session so that the updates on updatedPost are not directly saved in db
        em.detach(updatedPost);
        updatedPost
            .message(UPDATED_MESSAGE)
            .category(UPDATED_CATEGORY)
            .categoryName(UPDATED_CATEGORY_NAME)
            .meta(UPDATED_META)
            .tableKy(UPDATED_TABLE_KY)
            .isActive(UPDATED_IS_ACTIVE)
            .actRejReason(UPDATED_ACT_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS)
            .type(UPDATED_TYPE)
            .isSalesPost(UPDATED_IS_SALES_POST)
            .salesCategory(UPDATED_SALES_CATEGORY)
            .price(UPDATED_PRICE)
            .validFrom(UPDATED_VALID_FROM)
            .validTill(UPDATED_VALID_TILL)
            .phoneArea(UPDATED_PHONE_AREA)
            .phno(UPDATED_PHNO)
            .videoGrp(UPDATED_VIDEO_GRP)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testPost.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPost.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testPost.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testPost.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testPost.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPost.getActRejReason()).isEqualTo(UPDATED_ACT_REJ_REASON);
        assertThat(testPost.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testPost.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testPost.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testPost.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPost.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPost.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testPost.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testPost.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPost.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPost.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPost.getIsSalesPost()).isEqualTo(UPDATED_IS_SALES_POST);
        assertThat(testPost.getSalesCategory()).isEqualTo(UPDATED_SALES_CATEGORY);
        assertThat(testPost.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPost.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPost.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
        assertThat(testPost.getPhoneArea()).isEqualTo(UPDATED_PHONE_AREA);
        assertThat(testPost.getPhno()).isEqualTo(UPDATED_PHNO);
        assertThat(testPost.getVideoGrp()).isEqualTo(UPDATED_VIDEO_GRP);
        assertThat(testPost.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testPost.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Post> postSearchList = IterableUtils.toList(postSearchRepository.findAll());
                Post testPostSearch = postSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testPostSearch.getMessage()).isEqualTo(UPDATED_MESSAGE);
                assertThat(testPostSearch.getCategory()).isEqualTo(UPDATED_CATEGORY);
                assertThat(testPostSearch.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
                assertThat(testPostSearch.getMeta()).isEqualTo(UPDATED_META);
                assertThat(testPostSearch.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
                assertThat(testPostSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testPostSearch.getActRejReason()).isEqualTo(UPDATED_ACT_REJ_REASON);
                assertThat(testPostSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testPostSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testPostSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testPostSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testPostSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testPostSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testPostSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
                assertThat(testPostSearch.getComments()).isEqualTo(UPDATED_COMMENTS);
                assertThat(testPostSearch.getRemarks()).isEqualTo(UPDATED_REMARKS);
                assertThat(testPostSearch.getType()).isEqualTo(UPDATED_TYPE);
                assertThat(testPostSearch.getIsSalesPost()).isEqualTo(UPDATED_IS_SALES_POST);
                assertThat(testPostSearch.getSalesCategory()).isEqualTo(UPDATED_SALES_CATEGORY);
                assertThat(testPostSearch.getPrice()).isEqualTo(UPDATED_PRICE);
                assertThat(testPostSearch.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
                assertThat(testPostSearch.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
                assertThat(testPostSearch.getPhoneArea()).isEqualTo(UPDATED_PHONE_AREA);
                assertThat(testPostSearch.getPhno()).isEqualTo(UPDATED_PHNO);
                assertThat(testPostSearch.getVideoGrp()).isEqualTo(UPDATED_VIDEO_GRP);
                assertThat(testPostSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testPostSearch.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
            });
    }

    @Test
    @Transactional
    void putNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, postDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdatePostWithPatch() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setId(post.getId());

        partialUpdatedPost
            .meta(UPDATED_META)
            .isActive(UPDATED_IS_ACTIVE)
            .actRejReason(UPDATED_ACT_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS)
            .price(UPDATED_PRICE)
            .phoneArea(UPDATED_PHONE_AREA)
            .orgId(UPDATED_ORG_ID);

        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPost))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testPost.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPost.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testPost.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testPost.getTableKy()).isEqualTo(DEFAULT_TABLE_KY);
        assertThat(testPost.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPost.getActRejReason()).isEqualTo(UPDATED_ACT_REJ_REASON);
        assertThat(testPost.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testPost.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testPost.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testPost.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testPost.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testPost.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testPost.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
        assertThat(testPost.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPost.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPost.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPost.getIsSalesPost()).isEqualTo(DEFAULT_IS_SALES_POST);
        assertThat(testPost.getSalesCategory()).isEqualTo(DEFAULT_SALES_CATEGORY);
        assertThat(testPost.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPost.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPost.getValidTill()).isEqualTo(DEFAULT_VALID_TILL);
        assertThat(testPost.getPhoneArea()).isEqualTo(UPDATED_PHONE_AREA);
        assertThat(testPost.getPhno()).isEqualTo(DEFAULT_PHNO);
        assertThat(testPost.getVideoGrp()).isEqualTo(DEFAULT_VIDEO_GRP);
        assertThat(testPost.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testPost.getOtherInfo()).isEqualTo(DEFAULT_OTHER_INFO);
    }

    @Test
    @Transactional
    void fullUpdatePostWithPatch() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post using partial update
        Post partialUpdatedPost = new Post();
        partialUpdatedPost.setId(post.getId());

        partialUpdatedPost
            .message(UPDATED_MESSAGE)
            .category(UPDATED_CATEGORY)
            .categoryName(UPDATED_CATEGORY_NAME)
            .meta(UPDATED_META)
            .tableKy(UPDATED_TABLE_KY)
            .isActive(UPDATED_IS_ACTIVE)
            .actRejReason(UPDATED_ACT_REJ_REASON)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON)
            .comments(UPDATED_COMMENTS)
            .remarks(UPDATED_REMARKS)
            .type(UPDATED_TYPE)
            .isSalesPost(UPDATED_IS_SALES_POST)
            .salesCategory(UPDATED_SALES_CATEGORY)
            .price(UPDATED_PRICE)
            .validFrom(UPDATED_VALID_FROM)
            .validTill(UPDATED_VALID_TILL)
            .phoneArea(UPDATED_PHONE_AREA)
            .phno(UPDATED_PHNO)
            .videoGrp(UPDATED_VIDEO_GRP)
            .orgId(UPDATED_ORG_ID)
            .otherInfo(UPDATED_OTHER_INFO);

        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPost))
            )
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testPost.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPost.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testPost.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testPost.getTableKy()).isEqualTo(UPDATED_TABLE_KY);
        assertThat(testPost.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testPost.getActRejReason()).isEqualTo(UPDATED_ACT_REJ_REASON);
        assertThat(testPost.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testPost.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testPost.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testPost.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testPost.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testPost.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testPost.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        assertThat(testPost.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testPost.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPost.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPost.getIsSalesPost()).isEqualTo(UPDATED_IS_SALES_POST);
        assertThat(testPost.getSalesCategory()).isEqualTo(UPDATED_SALES_CATEGORY);
        assertThat(testPost.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPost.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPost.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
        assertThat(testPost.getPhoneArea()).isEqualTo(UPDATED_PHONE_AREA);
        assertThat(testPost.getPhno()).isEqualTo(UPDATED_PHNO);
        assertThat(testPost.getVideoGrp()).isEqualTo(UPDATED_VIDEO_GRP);
        assertThat(testPost.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testPost.getOtherInfo()).isEqualTo(UPDATED_OTHER_INFO);
    }

    @Test
    @Transactional
    void patchNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, postDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(postDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());
        post.setId(count.incrementAndGet());

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPostMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deletePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        postRepository.save(post);
        postSearchRepository.save(post);

        int databaseSizeBeforeDelete = postRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the post
        restPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, post.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(postSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchPost() throws Exception {
        // Initialize the database
        post = postRepository.saveAndFlush(post);
        postSearchRepository.save(post);

        // Search the post
        restPostMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].meta").value(hasItem(DEFAULT_META)))
            .andExpect(jsonPath("$.[*].tableKy").value(hasItem(DEFAULT_TABLE_KY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].actRejReason").value(hasItem(DEFAULT_ACT_REJ_REASON)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].isSalesPost").value(hasItem(DEFAULT_IS_SALES_POST)))
            .andExpect(jsonPath("$.[*].salesCategory").value(hasItem(DEFAULT_SALES_CATEGORY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.toString())))
            .andExpect(jsonPath("$.[*].phoneArea").value(hasItem(DEFAULT_PHONE_AREA)))
            .andExpect(jsonPath("$.[*].phno").value(hasItem(DEFAULT_PHNO)))
            .andExpect(jsonPath("$.[*].videoGrp").value(hasItem(DEFAULT_VIDEO_GRP.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].otherInfo").value(hasItem(DEFAULT_OTHER_INFO)));
    }
}
