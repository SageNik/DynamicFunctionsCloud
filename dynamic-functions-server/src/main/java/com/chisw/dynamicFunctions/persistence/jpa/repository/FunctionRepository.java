package com.chisw.dynamicFunctions.persistence.jpa.repository;

import com.chisw.dynamicFunctions.entity.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link com.chisw.dynamicFunctions.entity.Function} data implemented using Spring Data JPA.
 */
@Repository
public interface FunctionRepository extends JpaRepository<Function,Long>{

    List<Function> findAllByAvailableAndContainerId(boolean available, Long containerId);

    Function findFirstByNameAndSwitchedOnAndContainerId(String functionName, boolean switchedOn, Long containerId);

    List<Function> findAllByAvailableAndSwitchedOnAndContainerId(boolean available, boolean switchedOn, Long containerId);

    List<Function> findAllByAvailableAndSwitchedOn(boolean available, boolean switchedOn);
}
