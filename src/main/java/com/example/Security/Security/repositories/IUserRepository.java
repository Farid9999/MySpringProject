package com.example.Security.Security.repositories;

import com.example.Security.Security.models.AppUser;
import com.example.Security.Security.models.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<AppUser,Long> {
    AppUser getByUserId(Long id);
    AppUser getUserByUserName(String username);


}
