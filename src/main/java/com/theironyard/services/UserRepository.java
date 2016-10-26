package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Troy on 10/26/16.
 */
public interface UserRepository extends CrudRepository<User,Integer> {
}
