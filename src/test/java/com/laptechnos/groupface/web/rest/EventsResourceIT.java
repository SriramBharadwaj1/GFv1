package com.laptechnos.groupface.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.laptechnos.groupface.IntegrationTest;
import com.laptechnos.groupface.domain.Events;
import com.laptechnos.groupface.repository.EventsRepository;
import com.laptechnos.groupface.repository.search.EventsSearchRepository;
import com.laptechnos.groupface.service.EventsService;
import com.laptechnos.groupface.service.dto.EventsDTO;
import com.laptechnos.groupface.service.mapper.EventsMapper;
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
 * Integration tests for the {@link EventsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EventsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Integer DEFAULT_IS_ACTIVE = 1;
    private static final Integer UPDATED_IS_ACTIVE = 2;

    private static final Integer DEFAULT_APPR_REJ_REASON = 1;
    private static final Integer UPDATED_APPR_REJ_REASON = 2;

    private static final Integer DEFAULT_EVENT_TYPE = 1;
    private static final Integer UPDATED_EVENT_TYPE = 2;

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

    private static final Long DEFAULT_ORG_ID = 1L;
    private static final Long UPDATED_ORG_ID = 2L;

    private static final LocalDate DEFAULT_APPROVED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVED_ON = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/events";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventsRepository eventsRepository;

    @Mock
    private EventsRepository eventsRepositoryMock;

    @Autowired
    private EventsMapper eventsMapper;

    @Mock
    private EventsService eventsServiceMock;

    @Autowired
    private EventsSearchRepository eventsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventsMockMvc;

    private Events events;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Events createEntity(EntityManager em) {
        Events events = new Events()
            .name(DEFAULT_NAME)
            .userId(DEFAULT_USER_ID)
            .isActive(DEFAULT_IS_ACTIVE)
            .apprRejReason(DEFAULT_APPR_REJ_REASON)
            .eventType(DEFAULT_EVENT_TYPE)
            .isEnable(DEFAULT_IS_ENABLE)
            .addedBy(DEFAULT_ADDED_BY)
            .addedOn(DEFAULT_ADDED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .approvedBy(DEFAULT_APPROVED_BY)
            .orgId(DEFAULT_ORG_ID)
            .approvedOn(DEFAULT_APPROVED_ON);
        return events;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Events createUpdatedEntity(EntityManager em) {
        Events events = new Events()
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .eventType(UPDATED_EVENT_TYPE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .orgId(UPDATED_ORG_ID)
            .approvedOn(UPDATED_APPROVED_ON);
        return events;
    }

    @AfterEach
    public void cleanupElasticSearchRepository() {
        eventsSearchRepository.deleteAll();
        assertThat(eventsSearchRepository.count()).isEqualTo(0);
    }

    @BeforeEach
    public void initTest() {
        events = createEntity(em);
    }

    @Test
    @Transactional
    void createEvents() throws Exception {
        int databaseSizeBeforeCreate = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        // Create the Events
        EventsDTO eventsDTO = eventsMapper.toDto(events);
        restEventsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventsDTO)))
            .andExpect(status().isCreated());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeCreate + 1);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvents.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEvents.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testEvents.getApprRejReason()).isEqualTo(DEFAULT_APPR_REJ_REASON);
        assertThat(testEvents.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testEvents.getIsEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testEvents.getAddedBy()).isEqualTo(DEFAULT_ADDED_BY);
        assertThat(testEvents.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testEvents.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testEvents.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testEvents.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testEvents.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testEvents.getApprovedOn()).isEqualTo(DEFAULT_APPROVED_ON);
    }

    @Test
    @Transactional
    void createEventsWithExistingId() throws Exception {
        // Create the Events with an existing ID
        events.setId(1L);
        EventsDTO eventsDTO = eventsMapper.toDto(events);

        int databaseSizeBeforeCreate = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get all the eventsList
        restEventsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(events.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].apprRejReason").value(hasItem(DEFAULT_APPR_REJ_REASON)))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventsWithEagerRelationshipsIsEnabled() throws Exception {
        when(eventsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(eventsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(eventsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(eventsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get the events
        restEventsMockMvc
            .perform(get(ENTITY_API_URL_ID, events.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(events.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.apprRejReason").value(DEFAULT_APPR_REJ_REASON))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE))
            .andExpect(jsonPath("$.addedBy").value(DEFAULT_ADDED_BY.intValue()))
            .andExpect(jsonPath("$.addedOn").value(DEFAULT_ADDED_ON.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.intValue()))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID.intValue()))
            .andExpect(jsonPath("$.approvedOn").value(DEFAULT_APPROVED_ON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEvents() throws Exception {
        // Get the events
        restEventsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        eventsSearchRepository.save(events);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());

        // Update the events
        Events updatedEvents = eventsRepository.findById(events.getId()).get();
        // Disconnect from session so that the updates on updatedEvents are not directly saved in db
        em.detach(updatedEvents);
        updatedEvents
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .eventType(UPDATED_EVENT_TYPE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .orgId(UPDATED_ORG_ID)
            .approvedOn(UPDATED_APPROVED_ON);
        EventsDTO eventsDTO = eventsMapper.toDto(updatedEvents);

        restEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvents.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEvents.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEvents.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testEvents.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testEvents.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testEvents.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testEvents.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testEvents.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEvents.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testEvents.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testEvents.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testEvents.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Events> eventsSearchList = IterableUtils.toList(eventsSearchRepository.findAll());
                Events testEventsSearch = eventsSearchList.get(searchDatabaseSizeAfter - 1);
                assertThat(testEventsSearch.getName()).isEqualTo(UPDATED_NAME);
                assertThat(testEventsSearch.getUserId()).isEqualTo(UPDATED_USER_ID);
                assertThat(testEventsSearch.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
                assertThat(testEventsSearch.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
                assertThat(testEventsSearch.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
                assertThat(testEventsSearch.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
                assertThat(testEventsSearch.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
                assertThat(testEventsSearch.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
                assertThat(testEventsSearch.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
                assertThat(testEventsSearch.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
                assertThat(testEventsSearch.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
                assertThat(testEventsSearch.getOrgId()).isEqualTo(UPDATED_ORG_ID);
                assertThat(testEventsSearch.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
            });
    }

    @Test
    @Transactional
    void putNonExistingEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        events.setId(count.incrementAndGet());

        // Create the Events
        EventsDTO eventsDTO = eventsMapper.toDto(events);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        events.setId(count.incrementAndGet());

        // Create the Events
        EventsDTO eventsDTO = eventsMapper.toDto(events);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        events.setId(count.incrementAndGet());

        // Create the Events
        EventsDTO eventsDTO = eventsMapper.toDto(events);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateEventsWithPatch() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();

        // Update the events using partial update
        Events partialUpdatedEvents = new Events();
        partialUpdatedEvents.setId(events.getId());

        partialUpdatedEvents
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .eventType(UPDATED_EVENT_TYPE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .approvedOn(UPDATED_APPROVED_ON);

        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvents.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvents))
            )
            .andExpect(status().isOk());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvents.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEvents.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testEvents.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testEvents.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testEvents.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testEvents.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testEvents.getAddedOn()).isEqualTo(DEFAULT_ADDED_ON);
        assertThat(testEvents.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testEvents.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testEvents.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testEvents.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testEvents.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
    }

    @Test
    @Transactional
    void fullUpdateEventsWithPatch() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();

        // Update the events using partial update
        Events partialUpdatedEvents = new Events();
        partialUpdatedEvents.setId(events.getId());

        partialUpdatedEvents
            .name(UPDATED_NAME)
            .userId(UPDATED_USER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .apprRejReason(UPDATED_APPR_REJ_REASON)
            .eventType(UPDATED_EVENT_TYPE)
            .isEnable(UPDATED_IS_ENABLE)
            .addedBy(UPDATED_ADDED_BY)
            .addedOn(UPDATED_ADDED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .approvedBy(UPDATED_APPROVED_BY)
            .orgId(UPDATED_ORG_ID)
            .approvedOn(UPDATED_APPROVED_ON);

        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvents.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvents))
            )
            .andExpect(status().isOk());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        Events testEvents = eventsList.get(eventsList.size() - 1);
        assertThat(testEvents.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvents.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEvents.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEvents.getApprRejReason()).isEqualTo(UPDATED_APPR_REJ_REASON);
        assertThat(testEvents.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testEvents.getIsEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testEvents.getAddedBy()).isEqualTo(UPDATED_ADDED_BY);
        assertThat(testEvents.getAddedOn()).isEqualTo(UPDATED_ADDED_ON);
        assertThat(testEvents.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testEvents.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testEvents.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testEvents.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testEvents.getApprovedOn()).isEqualTo(UPDATED_APPROVED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        events.setId(count.incrementAndGet());

        // Create the Events
        EventsDTO eventsDTO = eventsMapper.toDto(events);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        events.setId(count.incrementAndGet());

        // Create the Events
        EventsDTO eventsDTO = eventsMapper.toDto(events);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvents() throws Exception {
        int databaseSizeBeforeUpdate = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        events.setId(count.incrementAndGet());

        // Create the Events
        EventsDTO eventsDTO = eventsMapper.toDto(events);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Events in the database
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);
        eventsRepository.save(events);
        eventsSearchRepository.save(events);

        int databaseSizeBeforeDelete = eventsRepository.findAll().size();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the events
        restEventsMockMvc
            .perform(delete(ENTITY_API_URL_ID, events.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Events> eventsList = eventsRepository.findAll();
        assertThat(eventsList).hasSize(databaseSizeBeforeDelete - 1);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(eventsSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchEvents() throws Exception {
        // Initialize the database
        events = eventsRepository.saveAndFlush(events);
        eventsSearchRepository.save(events);

        // Search the events
        restEventsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + events.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(events.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].apprRejReason").value(hasItem(DEFAULT_APPR_REJ_REASON)))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE)))
            .andExpect(jsonPath("$.[*].addedBy").value(hasItem(DEFAULT_ADDED_BY.intValue())))
            .andExpect(jsonPath("$.[*].addedOn").value(hasItem(DEFAULT_ADDED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.intValue())))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvedOn").value(hasItem(DEFAULT_APPROVED_ON.toString())));
    }
}
