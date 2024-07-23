package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.FreeTimeRepository;
import com.mycompany.myapp.service.FreeTimeQueryService;
import com.mycompany.myapp.service.FreeTimeService;
import com.mycompany.myapp.service.criteria.FreeTimeCriteria;
import com.mycompany.myapp.service.dto.FreeTimeDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FreeTime}.
 */
@RestController
@RequestMapping("/api/free-times")
public class FreeTimeResource {

    private static final Logger log = LoggerFactory.getLogger(FreeTimeResource.class);

    private static final String ENTITY_NAME = "freeTime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FreeTimeService freeTimeService;

    private final FreeTimeRepository freeTimeRepository;

    private final FreeTimeQueryService freeTimeQueryService;

    public FreeTimeResource(
        FreeTimeService freeTimeService,
        FreeTimeRepository freeTimeRepository,
        FreeTimeQueryService freeTimeQueryService
    ) {
        this.freeTimeService = freeTimeService;
        this.freeTimeRepository = freeTimeRepository;
        this.freeTimeQueryService = freeTimeQueryService;
    }

    /**
     * {@code POST  /free-times} : Create a new freeTime.
     *
     * @param freeTimeDTO the freeTimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new freeTimeDTO, or with status {@code 400 (Bad Request)} if the freeTime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FreeTimeDTO> createFreeTime(@RequestBody FreeTimeDTO freeTimeDTO) throws URISyntaxException {
        log.debug("REST request to save FreeTime : {}", freeTimeDTO);
        if (freeTimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new freeTime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        freeTimeDTO = freeTimeService.save(freeTimeDTO);
        return ResponseEntity.created(new URI("/api/free-times/" + freeTimeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, freeTimeDTO.getId().toString()))
            .body(freeTimeDTO);
    }

    /**
     * {@code PUT  /free-times/:id} : Updates an existing freeTime.
     *
     * @param id the id of the freeTimeDTO to save.
     * @param freeTimeDTO the freeTimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated freeTimeDTO,
     * or with status {@code 400 (Bad Request)} if the freeTimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the freeTimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FreeTimeDTO> updateFreeTime(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FreeTimeDTO freeTimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FreeTime : {}, {}", id, freeTimeDTO);
        if (freeTimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, freeTimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!freeTimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        freeTimeDTO = freeTimeService.update(freeTimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, freeTimeDTO.getId().toString()))
            .body(freeTimeDTO);
    }

    /**
     * {@code PATCH  /free-times/:id} : Partial updates given fields of an existing freeTime, field will ignore if it is null
     *
     * @param id the id of the freeTimeDTO to save.
     * @param freeTimeDTO the freeTimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated freeTimeDTO,
     * or with status {@code 400 (Bad Request)} if the freeTimeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the freeTimeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the freeTimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FreeTimeDTO> partialUpdateFreeTime(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FreeTimeDTO freeTimeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FreeTime partially : {}, {}", id, freeTimeDTO);
        if (freeTimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, freeTimeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!freeTimeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FreeTimeDTO> result = freeTimeService.partialUpdate(freeTimeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, freeTimeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /free-times} : get all the freeTimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of freeTimes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FreeTimeDTO>> getAllFreeTimes(
        FreeTimeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FreeTimes by criteria: {}", criteria);

        Page<FreeTimeDTO> page = freeTimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /free-times/count} : count all the freeTimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFreeTimes(FreeTimeCriteria criteria) {
        log.debug("REST request to count FreeTimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(freeTimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /free-times/:id} : get the "id" freeTime.
     *
     * @param id the id of the freeTimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the freeTimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FreeTimeDTO> getFreeTime(@PathVariable("id") Long id) {
        log.debug("REST request to get FreeTime : {}", id);
        Optional<FreeTimeDTO> freeTimeDTO = freeTimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(freeTimeDTO);
    }

    /**
     * {@code DELETE  /free-times/:id} : delete the "id" freeTime.
     *
     * @param id the id of the freeTimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFreeTime(@PathVariable("id") Long id) {
        log.debug("REST request to delete FreeTime : {}", id);
        freeTimeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
