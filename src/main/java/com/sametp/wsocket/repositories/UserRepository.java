package com.sametp.wsocket.repositories;

import com.sametp.wsocket.models.Status;
import com.sametp.wsocket.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    List<User> findAllByStatus(Status status);
}
