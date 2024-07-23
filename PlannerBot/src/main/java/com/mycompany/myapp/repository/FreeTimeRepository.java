package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FreeTime;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FreeTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FreeTimeRepository extends JpaRepository<FreeTime, Long>, JpaSpecificationExecutor<FreeTime> {}
