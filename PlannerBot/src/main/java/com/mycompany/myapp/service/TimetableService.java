package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TimetableDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Timetable}.
 */
public interface TimetableService {
    /**
     * Save a timetable.
     *
     * @param timetableDTO the entity to save.
     * @return the persisted entity.
     */
    TimetableDTO save(TimetableDTO timetableDTO);

    /**
     * Updates a timetable.
     *
     * @param timetableDTO the entity to update.
     * @return the persisted entity.
     */
    TimetableDTO update(TimetableDTO timetableDTO);

    /**
     * Partially updates a timetable.
     *
     * @param timetableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TimetableDTO> partialUpdate(TimetableDTO timetableDTO);

    /**
     * Get the "id" timetable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimetableDTO> findOne(Long id);

    /**
     * Delete the "id" timetable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
