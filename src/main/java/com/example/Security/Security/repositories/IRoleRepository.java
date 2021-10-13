package com.example.Security.Security.repositories;

import com.example.Security.Security.models.AppRole;
import com.example.Security.Security.models.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface IRoleRepository extends CrudRepository<AppRole,Long> {
    AppRole getAppRoleByRoleName(String name);
}
