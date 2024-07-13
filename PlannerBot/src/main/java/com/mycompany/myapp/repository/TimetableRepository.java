package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Timetable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Timetable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long>, JpaSpecificationExecutor<Timetable> {}
