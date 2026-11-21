package com.example.userservice.repository;

import com.example.userservice.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRespository extends CrudRepository<UserInfo, String> {

	Optional<UserInfo> findByUserId(String userId);
}
