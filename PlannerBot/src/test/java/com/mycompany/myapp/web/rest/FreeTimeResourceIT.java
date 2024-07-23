package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.FreeTimeAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FreeTime;
import com.mycompany.myapp.repository.FreeTimeRepository;
import com.mycompany.myapp.service.dto.FreeTimeDTO;
import com.mycompany.myapp.service.mapper.FreeTimeMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
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
 * Integration tests for the {@link FreeTimeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FreeTimeResourceIT {

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/free-times";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FreeTimeRepository freeTimeRepository;

    @Autowired
    private FreeTimeMapper freeTimeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFreeTimeMockMvc;

    private FreeTime freeTime;

    private FreeTime insertedFreeTime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FreeTime createEntity(EntityManager em) {
        FreeTime freeTime = new FreeTime().start(DEFAULT_START).end(DEFAULT_END);
        return freeTime;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FreeTime createUpdatedEntity(EntityManager em) {
        FreeTime freeTime = new FreeTime().start(UPDATED_START).end(UPDATED_END);
        return freeTime;
    }

    @BeforeEach
    public void initTest() {
        freeTime = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFreeTime != null) {
            freeTimeRepository.delete(insertedFreeTime);
            insertedFreeTime = null;
        }
    }

    @Test
    @Transactional
    void createFreeTime() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FreeTime
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(freeTime);
        var returnedFreeTimeDTO = om.readValue(
            restFreeTimeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(freeTimeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FreeTimeDTO.class
        );

        // Validate the FreeTime in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFreeTime = freeTimeMapper.toEntity(returnedFreeTimeDTO);
        assertFreeTimeUpdatableFieldsEquals(returnedFreeTime, getPersistedFreeTime(returnedFreeTime));

        insertedFreeTime = returnedFreeTime;
    }

    @Test
    @Transactional
    void createFreeTimeWithExistingId() throws Exception {
        // Create the FreeTime with an existing ID
        freeTime.setId(1L);
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(freeTime);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFreeTimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(freeTimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FreeTime in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFreeTimes() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList
        restFreeTimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freeTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].end").value(hasItem(sameInstant(DEFAULT_END))));
    }

    @Test
    @Transactional
    void getFreeTime() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get the freeTime
        restFreeTimeMockMvc
            .perform(get(ENTITY_API_URL_ID, freeTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(freeTime.getId().intValue()))
            .andExpect(jsonPath("$.start").value(sameInstant(DEFAULT_START)))
            .andExpect(jsonPath("$.end").value(sameInstant(DEFAULT_END)));
    }

    @Test
    @Transactional
    void getFreeTimesByIdFiltering() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        Long id = freeTime.getId();

        defaultFreeTimeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFreeTimeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFreeTimeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFreeTimesByStartIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where start equals to
        defaultFreeTimeFiltering("start.equals=" + DEFAULT_START, "start.equals=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllFreeTimesByStartIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where start in
        defaultFreeTimeFiltering("start.in=" + DEFAULT_START + "," + UPDATED_START, "start.in=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllFreeTimesByStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where start is not null
        defaultFreeTimeFiltering("start.specified=true", "start.specified=false");
    }

    @Test
    @Transactional
    void getAllFreeTimesByStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where start is greater than or equal to
        defaultFreeTimeFiltering("start.greaterThanOrEqual=" + DEFAULT_START, "start.greaterThanOrEqual=" + UPDATED_START);
    }

    @Test
    @Transactional
    void getAllFreeTimesByStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where start is less than or equal to
        defaultFreeTimeFiltering("start.lessThanOrEqual=" + DEFAULT_START, "start.lessThanOrEqual=" + SMALLER_START);
    }

    @Test
    @Transactional
    void getAllFreeTimesByStartIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where start is less than
        defaultFreeTimeFiltering("start.lessThan=" + UPDATED_START, "start.lessThan=" + DEFAULT_START);
    }

    @Test
    @Transactional
    void getAllFreeTimesByStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where start is greater than
        defaultFreeTimeFiltering("start.greaterThan=" + SMALLER_START, "start.greaterThan=" + DEFAULT_START);
    }

    @Test
    @Transactional
    void getAllFreeTimesByEndIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where end equals to
        defaultFreeTimeFiltering("end.equals=" + DEFAULT_END, "end.equals=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllFreeTimesByEndIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where end in
        defaultFreeTimeFiltering("end.in=" + DEFAULT_END + "," + UPDATED_END, "end.in=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllFreeTimesByEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where end is not null
        defaultFreeTimeFiltering("end.specified=true", "end.specified=false");
    }

    @Test
    @Transactional
    void getAllFreeTimesByEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where end is greater than or equal to
        defaultFreeTimeFiltering("end.greaterThanOrEqual=" + DEFAULT_END, "end.greaterThanOrEqual=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllFreeTimesByEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where end is less than or equal to
        defaultFreeTimeFiltering("end.lessThanOrEqual=" + DEFAULT_END, "end.lessThanOrEqual=" + SMALLER_END);
    }

    @Test
    @Transactional
    void getAllFreeTimesByEndIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where end is less than
        defaultFreeTimeFiltering("end.lessThan=" + UPDATED_END, "end.lessThan=" + DEFAULT_END);
    }

    @Test
    @Transactional
    void getAllFreeTimesByEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        // Get all the freeTimeList where end is greater than
        defaultFreeTimeFiltering("end.greaterThan=" + SMALLER_END, "end.greaterThan=" + DEFAULT_END);
    }

    private void defaultFreeTimeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFreeTimeShouldBeFound(shouldBeFound);
        defaultFreeTimeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFreeTimeShouldBeFound(String filter) throws Exception {
        restFreeTimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freeTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].end").value(hasItem(sameInstant(DEFAULT_END))));

        // Check, that the count call also returns 1
        restFreeTimeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFreeTimeShouldNotBeFound(String filter) throws Exception {
        restFreeTimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFreeTimeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFreeTime() throws Exception {
        // Get the freeTime
        restFreeTimeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFreeTime() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the freeTime
        FreeTime updatedFreeTime = freeTimeRepository.findById(freeTime.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFreeTime are not directly saved in db
        em.detach(updatedFreeTime);
        updatedFreeTime.start(UPDATED_START).end(UPDATED_END);
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(updatedFreeTime);

        restFreeTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, freeTimeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(freeTimeDTO))
            )
            .andExpect(status().isOk());

        // Validate the FreeTime in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFreeTimeToMatchAllProperties(updatedFreeTime);
    }

    @Test
    @Transactional
    void putNonExistingFreeTime() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        freeTime.setId(longCount.incrementAndGet());

        // Create the FreeTime
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(freeTime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFreeTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, freeTimeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(freeTimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FreeTime in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFreeTime() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        freeTime.setId(longCount.incrementAndGet());

        // Create the FreeTime
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(freeTime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFreeTimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(freeTimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FreeTime in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFreeTime() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        freeTime.setId(longCount.incrementAndGet());

        // Create the FreeTime
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(freeTime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFreeTimeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(freeTimeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FreeTime in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFreeTimeWithPatch() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the freeTime using partial update
        FreeTime partialUpdatedFreeTime = new FreeTime();
        partialUpdatedFreeTime.setId(freeTime.getId());

        partialUpdatedFreeTime.start(UPDATED_START);

        restFreeTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFreeTime.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFreeTime))
            )
            .andExpect(status().isOk());

        // Validate the FreeTime in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFreeTimeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFreeTime, freeTime), getPersistedFreeTime(freeTime));
    }

    @Test
    @Transactional
    void fullUpdateFreeTimeWithPatch() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the freeTime using partial update
        FreeTime partialUpdatedFreeTime = new FreeTime();
        partialUpdatedFreeTime.setId(freeTime.getId());

        partialUpdatedFreeTime.start(UPDATED_START).end(UPDATED_END);

        restFreeTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFreeTime.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFreeTime))
            )
            .andExpect(status().isOk());

        // Validate the FreeTime in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFreeTimeUpdatableFieldsEquals(partialUpdatedFreeTime, getPersistedFreeTime(partialUpdatedFreeTime));
    }

    @Test
    @Transactional
    void patchNonExistingFreeTime() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        freeTime.setId(longCount.incrementAndGet());

        // Create the FreeTime
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(freeTime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFreeTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, freeTimeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(freeTimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FreeTime in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFreeTime() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        freeTime.setId(longCount.incrementAndGet());

        // Create the FreeTime
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(freeTime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFreeTimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(freeTimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FreeTime in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFreeTime() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        freeTime.setId(longCount.incrementAndGet());

        // Create the FreeTime
        FreeTimeDTO freeTimeDTO = freeTimeMapper.toDto(freeTime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFreeTimeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(freeTimeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FreeTime in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFreeTime() throws Exception {
        // Initialize the database
        insertedFreeTime = freeTimeRepository.saveAndFlush(freeTime);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the freeTime
        restFreeTimeMockMvc
            .perform(delete(ENTITY_API_URL_ID, freeTime.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return freeTimeRepository.count();
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

    protected FreeTime getPersistedFreeTime(FreeTime freeTime) {
        return freeTimeRepository.findById(freeTime.getId()).orElseThrow();
    }

    protected void assertPersistedFreeTimeToMatchAllProperties(FreeTime expectedFreeTime) {
        assertFreeTimeAllPropertiesEquals(expectedFreeTime, getPersistedFreeTime(expectedFreeTime));
    }

    protected void assertPersistedFreeTimeToMatchUpdatableProperties(FreeTime expectedFreeTime) {
        assertFreeTimeAllUpdatablePropertiesEquals(expectedFreeTime, getPersistedFreeTime(expectedFreeTime));
    }
}
