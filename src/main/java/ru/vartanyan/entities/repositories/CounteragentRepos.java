package ru.vartanyan.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vartanyan.entities.Counteragent;

public interface CounteragentRepos extends CrudRepository<Counteragent, Long> {

}
