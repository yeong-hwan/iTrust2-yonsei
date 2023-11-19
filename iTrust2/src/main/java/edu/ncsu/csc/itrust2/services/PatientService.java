package edu.ncsu.csc.iTrust2.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.repositories.PatientRepository;
import edu.ncsu.csc.iTrust2.models.Patient;
import java.util.List;

@Component
@Transactional
public class PatientService extends UserService {

    @Autowired
    private PatientRepository repository;

    @Override
    protected JpaRepository getRepository () {
        return repository;
    }
    /*
     * @yewon added 2023.11.19
     * This is the service for interacting with the Patient table
     * - findByNameContains
     * - findByUsernameContains
     */
    public List<Patient> findByNameContains ( final String name ) {
        String[] names = name.split("_");
        String firstName = names[0];
        String lastName = names[1];
        if (firstName.equals("") && (!lastName.equals(""))) {
            return repository.findAllByLastNameLike(lastName);

        } else if ((!firstName.equals("")) && lastName.equals("")) {
            return repository.findAllByFirstNameLike(firstName);

        } else if ((!firstName.equals("")) && (!lastName.equals(""))) {
            return repository.findAllByFirstNameLikeAndLastNameLike(firstName, lastName);

        } else {
            return repository.findAll();
        }
    }

    public List<Patient> findByUsernameContains(final String username) {
        return repository.findAllByUsernameLike(username);
    }

}
