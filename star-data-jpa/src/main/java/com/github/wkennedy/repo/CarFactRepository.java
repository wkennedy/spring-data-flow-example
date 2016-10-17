package com.github.wkennedy.repo;

import com.github.wkennedy.entities.CarFactEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarFactRepository extends PagingAndSortingRepository<CarFactEntity, Integer> {
}
