package com.sametp.wsocket.services;

import com.sametp.wsocket.models.Status;
import com.sametp.wsocket.models.User;
import com.sametp.wsocket.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public void create(User user){
        repository.save(
                User.builder()
                        .fullName(user.getFullName())
                        .username(user.getUsername())
                        .status(Status.ONLINE)
                        .build()
        );
    }
    public void disconnect(User user){
        user = repository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        user.setStatus(Status.OFFLINE);
        repository.save(user);
    }
    public List<User> findConnectedUsers(){
        return repository.findAllByStatus(Status.ONLINE);
    }
}
