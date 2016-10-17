package com.github.wkennedy.repo;

import com.github.wkennedy.entities.MakeDimEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeDimRepository  extends PagingAndSortingRepository<MakeDimEntity, Integer> {
    MakeDimEntity findByName(String name);
}
