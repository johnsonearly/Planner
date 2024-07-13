package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TimetableRepository;
import com.mycompany.myapp.service.TimetableQueryService;
import com.mycompany.myapp.service.TimetableService;
import com.mycompany.myapp.service.criteria.TimetableCriteria;
import com.mycompany.myapp.service.dto.TimetableDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Timetable}.
 */
@RestController
@RequestMapping("/api/timetables")
public class TimetableResource {

    private static final Logger log = LoggerFactory.getLogger(TimetableResource.class);

    private static final String ENTITY_NAME = "timetable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimetableService timetableService;

    private final TimetableRepository timetableRepository;

    private final TimetableQueryService timetableQueryService;

    public TimetableResource(
        TimetableService timetableService,
        TimetableRepository timetableRepository,
        TimetableQueryService timetableQueryService
    ) {
        this.timetableService = timetableService;
        this.timetableRepository = timetableRepository;
        this.timetableQueryService = timetableQueryService;
    }

    /**
     * {@code POST  /timetables} : Create a new timetable.
     *
     * @param timetableDTO the timetableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timetableDTO, or with status {@code 400 (Bad Request)} if the timetable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TimetableDTO> createTimetable(@RequestBody TimetableDTO timetableDTO) throws URISyntaxException {
        log.debug("REST request to save Timetable : {}", timetableDTO);
        if (timetableDTO.getId() != null) {
            throw new BadRequestAlertException("A new timetable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        timetableDTO = timetableService.save(timetableDTO);
        return ResponseEntity.created(new URI("/api/timetables/" + timetableDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, timetableDTO.getId().toString()))
            .body(timetableDTO);
    }

    /**
     * {@code PUT  /timetables/:id} : Updates an existing timetable.
     *
     * @param id the id of the timetableDTO to save.
     * @param timetableDTO the timetableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timetableDTO,
     * or with status {@code 400 (Bad Request)} if the timetableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timetableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TimetableDTO> updateTimetable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimetableDTO timetableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Timetable : {}, {}", id, timetableDTO);
        if (timetableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timetableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timetableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        timetableDTO = timetableService.update(timetableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timetableDTO.getId().toString()))
            .body(timetableDTO);
    }

    /**
     * {@code PATCH  /timetables/:id} : Partial updates given fields of an existing timetable, field will ignore if it is null
     *
     * @param id the id of the timetableDTO to save.
     * @param timetableDTO the timetableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timetableDTO,
     * or with status {@code 400 (Bad Request)} if the timetableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the timetableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the timetableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TimetableDTO> partialUpdateTimetable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimetableDTO timetableDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Timetable partially : {}, {}", id, timetableDTO);
        if (timetableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timetableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timetableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TimetableDTO> result = timetableService.partialUpdate(timetableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timetableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /timetables} : get all the timetables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timetables in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TimetableDTO>> getAllTimetables(
        TimetableCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Timetables by criteria: {}", criteria);

        Page<TimetableDTO> page = timetableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /timetables/count} : count all the timetables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTimetables(TimetableCriteria criteria) {
        log.debug("REST request to count Timetables by criteria: {}", criteria);
        return ResponseEntity.ok().body(timetableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /timetables/:id} : get the "id" timetable.
     *
     * @param id the id of the timetableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timetableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TimetableDTO> getTimetable(@PathVariable("id") Long id) {
        log.debug("REST request to get Timetable : {}", id);
        Optional<TimetableDTO> timetableDTO = timetableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timetableDTO);
    }

    /**
     * {@code DELETE  /timetables/:id} : delete the "id" timetable.
     *
     * @param id the id of the timetableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimetable(@PathVariable("id") Long id) {
        log.debug("REST request to delete Timetable : {}", id);
        timetableService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
