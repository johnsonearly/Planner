package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.FreeTimeDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.FreeTime}.
 */
public interface FreeTimeService {
    /**
     * Save a freeTime.
     *
     * @param freeTimeDTO the entity to save.
     * @return the persisted entity.
     */
    FreeTimeDTO save(FreeTimeDTO freeTimeDTO);

    /**
     * Updates a freeTime.
     *
     * @param freeTimeDTO the entity to update.
     * @return the persisted entity.
     */
    FreeTimeDTO update(FreeTimeDTO freeTimeDTO);

    /**
     * Partially updates a freeTime.
     *
     * @param freeTimeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FreeTimeDTO> partialUpdate(FreeTimeDTO freeTimeDTO);

    /**
     * Get the "id" freeTime.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FreeTimeDTO> findOne(Long id);

    /**
     * Delete the "id" freeTime.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
