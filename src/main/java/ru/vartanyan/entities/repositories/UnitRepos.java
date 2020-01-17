package ru.vartanyan.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vartanyan.entities.Unit;

public interface UnitRepos extends CrudRepository<Unit,Long> {
}
