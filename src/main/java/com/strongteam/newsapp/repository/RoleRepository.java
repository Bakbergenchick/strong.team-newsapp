package com.strongteam.newsapp.repository;


import com.strongteam.newsapp.entity.Role;
import com.strongteam.newsapp.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(ERole roleType);
}
