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


    public Patient findByName ( final String name ) {
        return repository.findByUsername( name );
    }
    /*
     * @yewon added 2023.11.19
     * This is the service for interacting with the Patient table
     * - findByNameContains
     * - findByUsernameContains
     */
    public List<Patient> findByNameContains ( final String name ) {
        String firstName, lastName;
        // empty string
        if (name.equals("")){
            return repository.findAll();
        }
        // only last name
        else if (name.charAt(0) == '_') {
            lastName = name.substring(1);
            return repository.findAllByLastNameContaining(lastName);

        } else {
            String[] names = name.split("_");
            // only first name
            if (names.length == 1) {
                firstName = names[0];
                return repository.findAllByFirstNameContaining(firstName);
            } else {
                // both first and last name
                firstName = names[0];
                lastName = names[1];
                return repository.findAllByFirstNameContainingAndLastNameContaining(firstName, lastName);
            }
        }
    }

    public List<Patient> findByUsernameContains(final String username) {
        return repository.findAllByUsernameContaining(username);
    }

}
