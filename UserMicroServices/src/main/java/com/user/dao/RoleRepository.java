package com.user.dao;

import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(String roleName);
}
