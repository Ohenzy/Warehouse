package ru.vartanyan.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vartanyan.entities.Warehouse;

public interface WarehouseRepos extends CrudRepository<Warehouse,Long> {
}
