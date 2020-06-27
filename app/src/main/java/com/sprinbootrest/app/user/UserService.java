package com.sprinbootrest.app.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserService {

    private static List<User> usersList = new ArrayList<>();
    private static int userCount = 3;

    static {
        usersList.add(new User(1,"Ajay",new Date()));
        usersList.add(new User(2,"Vijay",new Date()));
        usersList.add(new User(3,"Sanjay",new Date()));
    }

    public List<User> findAll(){
        return usersList;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
            usersList.add(user);
        }
        return user;
    }

    public User findOne(int id){
        for (User user :usersList) {
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteUser(Integer id){

        Iterator<User> iterator = usersList.iterator();
        while(iterator.hasNext()){
            User next = iterator.next();
            if(next.getId() == id){
                iterator.remove();
                return next;
            }
        }
        return null;
    }
}
