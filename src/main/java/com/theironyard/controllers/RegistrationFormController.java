package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.services.UserRepository;
import org.apache.catalina.valves.rewrite.Substitution;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.SQLException;



/**
 * Created by Troy on 10/26/16.
 */
@RestController
public class RegistrationFormController {
    @Autowired
    UserRepository users;

    Server h2Server;
    @PostConstruct
    public void init() throws SQLException {
        h2Server = Server.createWebServer().start();
    }

    @PreDestroy
    public void destroy() {
        h2Server.stop();
    }

    @RequestMapping(path = "/user",method = RequestMethod.POST)
    public User addUser(@RequestBody User user) {
        return users.save(user);
    }

    @RequestMapping(path = "/user",method = RequestMethod.GET)
    public Iterable<User> getUsers() {
        return users.findAll();
    }

    @RequestMapping(path = "/user",method = RequestMethod.PUT)
    public User editUser(@RequestBody User user) {
        return users.save(user);
    }

    @RequestMapping(path = "/user/{id}",method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id) {
        users.delete(id);
    }
}
