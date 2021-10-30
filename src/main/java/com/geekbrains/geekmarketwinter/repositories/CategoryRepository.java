package com.geekbrains.geekmarketwinter.repositories;

import com.geekbrains.geekmarketwinter.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
