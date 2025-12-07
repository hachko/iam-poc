package org.hachko.poc.repository;

import org.hachko.poc.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
