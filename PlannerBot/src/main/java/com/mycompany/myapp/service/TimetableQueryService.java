package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Timetable;
import com.mycompany.myapp.repository.TimetableRepository;
import com.mycompany.myapp.service.criteria.TimetableCriteria;
import com.mycompany.myapp.service.dto.TimetableDTO;
import com.mycompany.myapp.service.mapper.TimetableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Timetable} entities in the database.
 * The main input is a {@link TimetableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link TimetableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TimetableQueryService extends QueryService<Timetable> {

    private static final Logger log = LoggerFactory.getLogger(TimetableQueryService.class);

    private final TimetableRepository timetableRepository;

    private final TimetableMapper timetableMapper;

    public TimetableQueryService(TimetableRepository timetableRepository, TimetableMapper timetableMapper) {
        this.timetableRepository = timetableRepository;
        this.timetableMapper = timetableMapper;
    }

    /**
     * Return a {@link Page} of {@link TimetableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TimetableDTO> findByCriteria(TimetableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Timetable> specification = createSpecification(criteria);
        return timetableRepository.findAll(specification, page).map(timetableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TimetableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Timetable> specification = createSpecification(criteria);
        return timetableRepository.count(specification);
    }

    /**
     * Function to convert {@link TimetableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Timetable> createSpecification(TimetableCriteria criteria) {
        Specification<Timetable> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Timetable_.id));
            }
            if (criteria.getAppUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAppUserId(), Timetable_.appUserId));
            }
            if (criteria.getDayOfWeek() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDayOfWeek(), Timetable_.dayOfWeek));
            }
            if (criteria.getDateOfActivity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfActivity(), Timetable_.dateOfActivity));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), Timetable_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), Timetable_.endTime));
            }
            if (criteria.getActivity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivity(), Timetable_.activity));
            }
            if (criteria.getIsDone() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDone(), Timetable_.isDone));
            }
            if (criteria.getLevelOfImportance() != null) {
                specification = specification.and(buildSpecification(criteria.getLevelOfImportance(), Timetable_.levelOfImportance));
            }
        }
        return specification;
    }
}
