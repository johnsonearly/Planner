package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.FreeTime;
import com.mycompany.myapp.repository.FreeTimeRepository;
import com.mycompany.myapp.service.criteria.FreeTimeCriteria;
import com.mycompany.myapp.service.dto.FreeTimeDTO;
import com.mycompany.myapp.service.mapper.FreeTimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link FreeTime} entities in the database.
 * The main input is a {@link FreeTimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FreeTimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FreeTimeQueryService extends QueryService<FreeTime> {

    private static final Logger log = LoggerFactory.getLogger(FreeTimeQueryService.class);

    private final FreeTimeRepository freeTimeRepository;

    private final FreeTimeMapper freeTimeMapper;

    public FreeTimeQueryService(FreeTimeRepository freeTimeRepository, FreeTimeMapper freeTimeMapper) {
        this.freeTimeRepository = freeTimeRepository;
        this.freeTimeMapper = freeTimeMapper;
    }

    /**
     * Return a {@link Page} of {@link FreeTimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FreeTimeDTO> findByCriteria(FreeTimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FreeTime> specification = createSpecification(criteria);
        return freeTimeRepository.findAll(specification, page).map(freeTimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FreeTimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FreeTime> specification = createSpecification(criteria);
        return freeTimeRepository.count(specification);
    }

    /**
     * Function to convert {@link FreeTimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FreeTime> createSpecification(FreeTimeCriteria criteria) {
        Specification<FreeTime> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FreeTime_.id));
            }
            if (criteria.getStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStart(), FreeTime_.start));
            }
            if (criteria.getEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEnd(), FreeTime_.end));
            }
        }
        return specification;
    }
}
