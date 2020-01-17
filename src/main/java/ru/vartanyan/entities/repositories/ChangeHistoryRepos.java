package ru.vartanyan.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vartanyan.entities.ChangeHistory;

public interface ChangeHistoryRepos extends CrudRepository<ChangeHistory,Long> {
}
