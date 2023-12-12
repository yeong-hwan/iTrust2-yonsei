package edu.ncsu.csc.iTrust2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import java.util.List;
import java.util.Set;


public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername ( String username );

    // @yewon added 2023.12.05
    List<User> findByRolesIn ( Set<Role> roles );
}
