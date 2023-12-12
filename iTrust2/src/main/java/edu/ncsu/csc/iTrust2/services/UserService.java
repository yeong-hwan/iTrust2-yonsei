package edu.ncsu.csc.iTrust2.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.models.User;
import edu.ncsu.csc.iTrust2.models.enums.Role;
import edu.ncsu.csc.iTrust2.repositories.UserRepository;
import java.util.List;
import java.util.Set;


@Component
@Transactional
@Primary
public class UserService extends Service {

    @Autowired
    private UserRepository repository;

    @Override
    protected JpaRepository getRepository () {
        return repository;
    }

    public User findByName ( final String username ) {
        return repository.findByUsername( username );
    }

    // @yewon added 2023.12.05
    public List<User> findByRole ( final Role role ) {
        Set<Role> roles = Set.of(role);
        return repository.findByRolesIn( roles );
    }
}
