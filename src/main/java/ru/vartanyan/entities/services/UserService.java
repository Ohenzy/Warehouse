package ru.vartanyan.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vartanyan.entities.loginin.Role;
import ru.vartanyan.entities.loginin.User;
import ru.vartanyan.entities.repositories.UserRepos;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepos userRepos;

    public boolean save(User user){
        for (User usr : userRepos.findAll())
            if(usr.getUsername().equals(user.getUsername()))
                return false;

        userRepos.save(user);
        return true;
    }

    public List<User> findAllBut(String name){
        List<User> users = new LinkedList<>();
        for(User user : userRepos.findAll())
            if(!user.getUsername().equals(name))
                users.add(user);
        return users;
    }

    public boolean changePassword(User user, String oldPass){
        for (User usr : userRepos.findAll())
            if(usr.getUsername().equals(user.getUsername()) && usr.getPassword().equals(oldPass)) {
                user.setId(usr.getId());
                userRepos.save(user);
                return true;
            }
        return false;
    }

    public boolean deleteById(Long id){
        if(userRepos.existsById(id)) {
            userRepos.deleteById(id);
            return true;
        }
        else return false;
    }
    public boolean swapTypeById(Long id) {
        if(userRepos.existsById(id)){
            User tmpUser = userRepos.findById(id).get();// Бросает исключение
            User user = new User(tmpUser.getUsername(),tmpUser.getPassword(),true,tmpUser.getRole());
            for(Role role : user.getRole())
                if(role.equals(Role.ADMIN)){
                    user.setId(id);
                    user.setRole(Collections.singleton(Role.USER));
                } else if(role.equals(Role.USER)) {
                    user.setId(id);
                    user.setRole(Collections.singleton(Role.ADMIN));
                }
            userRepos.save(user);
                    return true;
        }
        else return false;
    }
    public boolean isEmpty() {
        return !userRepos.findAll().iterator().hasNext();
    }

    public List<User> findAll(){
        List<User> users = new LinkedList<>();
        for (User user : userRepos.findAll())
            users.add(user);
        return users;
    }

    public Role getRoleByName(String name) {
        return this.findByName(name).getRole().iterator().next();
    }

    public User findByName(String name) {
        for(User user : userRepos.findAll())
            if(user.getUsername().equals(name))
                return user;
        return null;
    }
}
