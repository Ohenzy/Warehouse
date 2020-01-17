package ru.vartanyan.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vartanyan.entities.Product;

public interface ProductRepos extends CrudRepository<Product,Long> {




}
