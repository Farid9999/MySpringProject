package com.example.Security.Security.repositories;

import com.example.Security.Security.models.AppUser;
import com.example.Security.Security.models.UserQuestion;
import com.example.Security.Security.models.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface IUserRoleRepository extends CrudRepository<UserRole,Long> {
    UserRole getUserRoleByAppUser(AppUser user);
}
