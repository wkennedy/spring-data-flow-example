package com.github.wkennedy.repo;

import com.github.wkennedy.entities.EngineDimEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineDimRepository extends PagingAndSortingRepository<EngineDimEntity, Integer> {
    EngineDimEntity findByCode(String code);
}
