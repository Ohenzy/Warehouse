package ru.vartanyan.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vartanyan.entities.PartProduct;

public interface PartProductRepos extends CrudRepository<PartProduct,Long> {
}
