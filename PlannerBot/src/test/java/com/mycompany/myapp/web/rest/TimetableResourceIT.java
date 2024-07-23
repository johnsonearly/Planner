package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.TimetableAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Timetable;
import com.mycompany.myapp.domain.enumeration.Importance;
import com.mycompany.myapp.repository.TimetableRepository;
import com.mycompany.myapp.service.dto.TimetableDTO;
import com.mycompany.myapp.service.mapper.TimetableMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TimetableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TimetableResourceIT {

    private static final Long DEFAULT_APP_USER_ID = 1L;
    private static final Long UPDATED_APP_USER_ID = 2L;
    private static final Long SMALLER_APP_USER_ID = 1L - 1L;

    private static final String DEFAULT_DAY_OF_WEEK = "AAAAAAAAAA";
    private static final String UPDATED_DAY_OF_WEEK = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_ACTIVITY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_ACTIVITY = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_ACTIVITY = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DONE = false;
    private static final Boolean UPDATED_IS_DONE = true;

    private static final Importance DEFAULT_LEVEL_OF_IMPORTANCE = Importance.LOW;
    private static final Importance UPDATED_LEVEL_OF_IMPORTANCE = Importance.MEDIUM;

    private static final String ENTITY_API_URL = "/api/timetables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TimetableMapper timetableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTimetableMockMvc;

    private Timetable timetable;

    private Timetable insertedTimetable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timetable createEntity(EntityManager em) {
        Timetable timetable = new Timetable()
            .appUserId(DEFAULT_APP_USER_ID)
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .dateOfActivity(DEFAULT_DATE_OF_ACTIVITY)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .activity(DEFAULT_ACTIVITY)
            .isDone(DEFAULT_IS_DONE)
            .levelOfImportance(DEFAULT_LEVEL_OF_IMPORTANCE);
        return timetable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timetable createUpdatedEntity(EntityManager em) {
        Timetable timetable = new Timetable()
            .appUserId(UPDATED_APP_USER_ID)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .dateOfActivity(UPDATED_DATE_OF_ACTIVITY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .activity(UPDATED_ACTIVITY)
            .isDone(UPDATED_IS_DONE)
            .levelOfImportance(UPDATED_LEVEL_OF_IMPORTANCE);
        return timetable;
    }

    @BeforeEach
    public void initTest() {
        timetable = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTimetable != null) {
            timetableRepository.delete(insertedTimetable);
            insertedTimetable = null;
        }
    }

    @Test
    @Transactional
    void createTimetable() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);
        var returnedTimetableDTO = om.readValue(
            restTimetableMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(timetableDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TimetableDTO.class
        );

        // Validate the Timetable in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTimetable = timetableMapper.toEntity(returnedTimetableDTO);
        assertTimetableUpdatableFieldsEquals(returnedTimetable, getPersistedTimetable(returnedTimetable));

        insertedTimetable = returnedTimetable;
    }

    @Test
    @Transactional
    void createTimetableWithExistingId() throws Exception {
        // Create the Timetable with an existing ID
        timetable.setId(1L);
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimetableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTimetables() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList
        restTimetableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetable.getId().intValue())))
            .andExpect(jsonPath("$.[*].appUserId").value(hasItem(DEFAULT_APP_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].dateOfActivity").value(hasItem(DEFAULT_DATE_OF_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].isDone").value(hasItem(DEFAULT_IS_DONE.booleanValue())))
            .andExpect(jsonPath("$.[*].levelOfImportance").value(hasItem(DEFAULT_LEVEL_OF_IMPORTANCE.toString())));
    }

    @Test
    @Transactional
    void getTimetable() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get the timetable
        restTimetableMockMvc
            .perform(get(ENTITY_API_URL_ID, timetable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timetable.getId().intValue()))
            .andExpect(jsonPath("$.appUserId").value(DEFAULT_APP_USER_ID.intValue()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK))
            .andExpect(jsonPath("$.dateOfActivity").value(DEFAULT_DATE_OF_ACTIVITY.toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.isDone").value(DEFAULT_IS_DONE.booleanValue()))
            .andExpect(jsonPath("$.levelOfImportance").value(DEFAULT_LEVEL_OF_IMPORTANCE.toString()));
    }

    @Test
    @Transactional
    void getTimetablesByIdFiltering() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        Long id = timetable.getId();

        defaultTimetableFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTimetableFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTimetableFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTimetablesByAppUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where appUserId equals to
        defaultTimetableFiltering("appUserId.equals=" + DEFAULT_APP_USER_ID, "appUserId.equals=" + UPDATED_APP_USER_ID);
    }

    @Test
    @Transactional
    void getAllTimetablesByAppUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where appUserId in
        defaultTimetableFiltering("appUserId.in=" + DEFAULT_APP_USER_ID + "," + UPDATED_APP_USER_ID, "appUserId.in=" + UPDATED_APP_USER_ID);
    }

    @Test
    @Transactional
    void getAllTimetablesByAppUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where appUserId is not null
        defaultTimetableFiltering("appUserId.specified=true", "appUserId.specified=false");
    }

    @Test
    @Transactional
    void getAllTimetablesByAppUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where appUserId is greater than or equal to
        defaultTimetableFiltering(
            "appUserId.greaterThanOrEqual=" + DEFAULT_APP_USER_ID,
            "appUserId.greaterThanOrEqual=" + UPDATED_APP_USER_ID
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByAppUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where appUserId is less than or equal to
        defaultTimetableFiltering("appUserId.lessThanOrEqual=" + DEFAULT_APP_USER_ID, "appUserId.lessThanOrEqual=" + SMALLER_APP_USER_ID);
    }

    @Test
    @Transactional
    void getAllTimetablesByAppUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where appUserId is less than
        defaultTimetableFiltering("appUserId.lessThan=" + UPDATED_APP_USER_ID, "appUserId.lessThan=" + DEFAULT_APP_USER_ID);
    }

    @Test
    @Transactional
    void getAllTimetablesByAppUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where appUserId is greater than
        defaultTimetableFiltering("appUserId.greaterThan=" + SMALLER_APP_USER_ID, "appUserId.greaterThan=" + DEFAULT_APP_USER_ID);
    }

    @Test
    @Transactional
    void getAllTimetablesByDayOfWeekIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dayOfWeek equals to
        defaultTimetableFiltering("dayOfWeek.equals=" + DEFAULT_DAY_OF_WEEK, "dayOfWeek.equals=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    void getAllTimetablesByDayOfWeekIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dayOfWeek in
        defaultTimetableFiltering("dayOfWeek.in=" + DEFAULT_DAY_OF_WEEK + "," + UPDATED_DAY_OF_WEEK, "dayOfWeek.in=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    void getAllTimetablesByDayOfWeekIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dayOfWeek is not null
        defaultTimetableFiltering("dayOfWeek.specified=true", "dayOfWeek.specified=false");
    }

    @Test
    @Transactional
    void getAllTimetablesByDayOfWeekContainsSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dayOfWeek contains
        defaultTimetableFiltering("dayOfWeek.contains=" + DEFAULT_DAY_OF_WEEK, "dayOfWeek.contains=" + UPDATED_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    void getAllTimetablesByDayOfWeekNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dayOfWeek does not contain
        defaultTimetableFiltering("dayOfWeek.doesNotContain=" + UPDATED_DAY_OF_WEEK, "dayOfWeek.doesNotContain=" + DEFAULT_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    void getAllTimetablesByDateOfActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dateOfActivity equals to
        defaultTimetableFiltering("dateOfActivity.equals=" + DEFAULT_DATE_OF_ACTIVITY, "dateOfActivity.equals=" + UPDATED_DATE_OF_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllTimetablesByDateOfActivityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dateOfActivity in
        defaultTimetableFiltering(
            "dateOfActivity.in=" + DEFAULT_DATE_OF_ACTIVITY + "," + UPDATED_DATE_OF_ACTIVITY,
            "dateOfActivity.in=" + UPDATED_DATE_OF_ACTIVITY
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByDateOfActivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dateOfActivity is not null
        defaultTimetableFiltering("dateOfActivity.specified=true", "dateOfActivity.specified=false");
    }

    @Test
    @Transactional
    void getAllTimetablesByDateOfActivityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dateOfActivity is greater than or equal to
        defaultTimetableFiltering(
            "dateOfActivity.greaterThanOrEqual=" + DEFAULT_DATE_OF_ACTIVITY,
            "dateOfActivity.greaterThanOrEqual=" + UPDATED_DATE_OF_ACTIVITY
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByDateOfActivityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dateOfActivity is less than or equal to
        defaultTimetableFiltering(
            "dateOfActivity.lessThanOrEqual=" + DEFAULT_DATE_OF_ACTIVITY,
            "dateOfActivity.lessThanOrEqual=" + SMALLER_DATE_OF_ACTIVITY
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByDateOfActivityIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dateOfActivity is less than
        defaultTimetableFiltering(
            "dateOfActivity.lessThan=" + UPDATED_DATE_OF_ACTIVITY,
            "dateOfActivity.lessThan=" + DEFAULT_DATE_OF_ACTIVITY
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByDateOfActivityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where dateOfActivity is greater than
        defaultTimetableFiltering(
            "dateOfActivity.greaterThan=" + SMALLER_DATE_OF_ACTIVITY,
            "dateOfActivity.greaterThan=" + DEFAULT_DATE_OF_ACTIVITY
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where startTime equals to
        defaultTimetableFiltering("startTime.equals=" + DEFAULT_START_TIME, "startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where startTime in
        defaultTimetableFiltering("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME, "startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where startTime is not null
        defaultTimetableFiltering("startTime.specified=true", "startTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTimetablesByStartTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where startTime is greater than or equal to
        defaultTimetableFiltering(
            "startTime.greaterThanOrEqual=" + DEFAULT_START_TIME,
            "startTime.greaterThanOrEqual=" + UPDATED_START_TIME
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByStartTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where startTime is less than or equal to
        defaultTimetableFiltering("startTime.lessThanOrEqual=" + DEFAULT_START_TIME, "startTime.lessThanOrEqual=" + SMALLER_START_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByStartTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where startTime is less than
        defaultTimetableFiltering("startTime.lessThan=" + UPDATED_START_TIME, "startTime.lessThan=" + DEFAULT_START_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByStartTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where startTime is greater than
        defaultTimetableFiltering("startTime.greaterThan=" + SMALLER_START_TIME, "startTime.greaterThan=" + DEFAULT_START_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where endTime equals to
        defaultTimetableFiltering("endTime.equals=" + DEFAULT_END_TIME, "endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where endTime in
        defaultTimetableFiltering("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME, "endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where endTime is not null
        defaultTimetableFiltering("endTime.specified=true", "endTime.specified=false");
    }

    @Test
    @Transactional
    void getAllTimetablesByEndTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where endTime is greater than or equal to
        defaultTimetableFiltering("endTime.greaterThanOrEqual=" + DEFAULT_END_TIME, "endTime.greaterThanOrEqual=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByEndTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where endTime is less than or equal to
        defaultTimetableFiltering("endTime.lessThanOrEqual=" + DEFAULT_END_TIME, "endTime.lessThanOrEqual=" + SMALLER_END_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByEndTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where endTime is less than
        defaultTimetableFiltering("endTime.lessThan=" + UPDATED_END_TIME, "endTime.lessThan=" + DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByEndTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where endTime is greater than
        defaultTimetableFiltering("endTime.greaterThan=" + SMALLER_END_TIME, "endTime.greaterThan=" + DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    void getAllTimetablesByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where activity equals to
        defaultTimetableFiltering("activity.equals=" + DEFAULT_ACTIVITY, "activity.equals=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllTimetablesByActivityIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where activity in
        defaultTimetableFiltering("activity.in=" + DEFAULT_ACTIVITY + "," + UPDATED_ACTIVITY, "activity.in=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllTimetablesByActivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where activity is not null
        defaultTimetableFiltering("activity.specified=true", "activity.specified=false");
    }

    @Test
    @Transactional
    void getAllTimetablesByActivityContainsSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where activity contains
        defaultTimetableFiltering("activity.contains=" + DEFAULT_ACTIVITY, "activity.contains=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllTimetablesByActivityNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where activity does not contain
        defaultTimetableFiltering("activity.doesNotContain=" + UPDATED_ACTIVITY, "activity.doesNotContain=" + DEFAULT_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllTimetablesByIsDoneIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where isDone equals to
        defaultTimetableFiltering("isDone.equals=" + DEFAULT_IS_DONE, "isDone.equals=" + UPDATED_IS_DONE);
    }

    @Test
    @Transactional
    void getAllTimetablesByIsDoneIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where isDone in
        defaultTimetableFiltering("isDone.in=" + DEFAULT_IS_DONE + "," + UPDATED_IS_DONE, "isDone.in=" + UPDATED_IS_DONE);
    }

    @Test
    @Transactional
    void getAllTimetablesByIsDoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where isDone is not null
        defaultTimetableFiltering("isDone.specified=true", "isDone.specified=false");
    }

    @Test
    @Transactional
    void getAllTimetablesByLevelOfImportanceIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where levelOfImportance equals to
        defaultTimetableFiltering(
            "levelOfImportance.equals=" + DEFAULT_LEVEL_OF_IMPORTANCE,
            "levelOfImportance.equals=" + UPDATED_LEVEL_OF_IMPORTANCE
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByLevelOfImportanceIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where levelOfImportance in
        defaultTimetableFiltering(
            "levelOfImportance.in=" + DEFAULT_LEVEL_OF_IMPORTANCE + "," + UPDATED_LEVEL_OF_IMPORTANCE,
            "levelOfImportance.in=" + UPDATED_LEVEL_OF_IMPORTANCE
        );
    }

    @Test
    @Transactional
    void getAllTimetablesByLevelOfImportanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList where levelOfImportance is not null
        defaultTimetableFiltering("levelOfImportance.specified=true", "levelOfImportance.specified=false");
    }

    private void defaultTimetableFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTimetableShouldBeFound(shouldBeFound);
        defaultTimetableShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTimetableShouldBeFound(String filter) throws Exception {
        restTimetableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetable.getId().intValue())))
            .andExpect(jsonPath("$.[*].appUserId").value(hasItem(DEFAULT_APP_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK)))
            .andExpect(jsonPath("$.[*].dateOfActivity").value(hasItem(DEFAULT_DATE_OF_ACTIVITY.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].isDone").value(hasItem(DEFAULT_IS_DONE.booleanValue())))
            .andExpect(jsonPath("$.[*].levelOfImportance").value(hasItem(DEFAULT_LEVEL_OF_IMPORTANCE.toString())));

        // Check, that the count call also returns 1
        restTimetableMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTimetableShouldNotBeFound(String filter) throws Exception {
        restTimetableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTimetableMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTimetable() throws Exception {
        // Get the timetable
        restTimetableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTimetable() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the timetable
        Timetable updatedTimetable = timetableRepository.findById(timetable.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTimetable are not directly saved in db
        em.detach(updatedTimetable);
        updatedTimetable
            .appUserId(UPDATED_APP_USER_ID)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .dateOfActivity(UPDATED_DATE_OF_ACTIVITY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .activity(UPDATED_ACTIVITY)
            .isDone(UPDATED_IS_DONE)
            .levelOfImportance(UPDATED_LEVEL_OF_IMPORTANCE);
        TimetableDTO timetableDTO = timetableMapper.toDto(updatedTimetable);

        restTimetableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timetableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(timetableDTO))
            )
            .andExpect(status().isOk());

        // Validate the Timetable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTimetableToMatchAllProperties(updatedTimetable);
    }

    @Test
    @Transactional
    void putNonExistingTimetable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timetable.setId(longCount.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timetableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(timetableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimetable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timetable.setId(longCount.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(timetableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimetable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timetable.setId(longCount.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(timetableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Timetable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimetableWithPatch() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the timetable using partial update
        Timetable partialUpdatedTimetable = new Timetable();
        partialUpdatedTimetable.setId(timetable.getId());

        partialUpdatedTimetable
            .appUserId(UPDATED_APP_USER_ID)
            .dateOfActivity(UPDATED_DATE_OF_ACTIVITY)
            .activity(UPDATED_ACTIVITY)
            .isDone(UPDATED_IS_DONE);

        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimetable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTimetable))
            )
            .andExpect(status().isOk());

        // Validate the Timetable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTimetableUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTimetable, timetable),
            getPersistedTimetable(timetable)
        );
    }

    @Test
    @Transactional
    void fullUpdateTimetableWithPatch() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the timetable using partial update
        Timetable partialUpdatedTimetable = new Timetable();
        partialUpdatedTimetable.setId(timetable.getId());

        partialUpdatedTimetable
            .appUserId(UPDATED_APP_USER_ID)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .dateOfActivity(UPDATED_DATE_OF_ACTIVITY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .activity(UPDATED_ACTIVITY)
            .isDone(UPDATED_IS_DONE)
            .levelOfImportance(UPDATED_LEVEL_OF_IMPORTANCE);

        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimetable.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTimetable))
            )
            .andExpect(status().isOk());

        // Validate the Timetable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTimetableUpdatableFieldsEquals(partialUpdatedTimetable, getPersistedTimetable(partialUpdatedTimetable));
    }

    @Test
    @Transactional
    void patchNonExistingTimetable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timetable.setId(longCount.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timetableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(timetableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimetable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timetable.setId(longCount.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(timetableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Timetable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimetable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        timetable.setId(longCount.incrementAndGet());

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimetableMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(timetableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Timetable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimetable() throws Exception {
        // Initialize the database
        insertedTimetable = timetableRepository.saveAndFlush(timetable);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the timetable
        restTimetableMockMvc
            .perform(delete(ENTITY_API_URL_ID, timetable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return timetableRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Timetable getPersistedTimetable(Timetable timetable) {
        return timetableRepository.findById(timetable.getId()).orElseThrow();
    }

    protected void assertPersistedTimetableToMatchAllProperties(Timetable expectedTimetable) {
        assertTimetableAllPropertiesEquals(expectedTimetable, getPersistedTimetable(expectedTimetable));
    }

    protected void assertPersistedTimetableToMatchUpdatableProperties(Timetable expectedTimetable) {
        assertTimetableAllUpdatablePropertiesEquals(expectedTimetable, getPersistedTimetable(expectedTimetable));
    }
}
