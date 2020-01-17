package ru.vartanyan.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vartanyan.entities.loginin.User;

public interface UserRepos extends CrudRepository<User,Long> {
}
