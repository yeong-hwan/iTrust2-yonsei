package edu.ncsu.csc.iTrust2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import edu.ncsu.csc.iTrust2.models.Patient;


// @yewon added 2023.11.19
/*
 * This is the repository for interacting with the Patient table
 * - findAllByFirstNameLike
 * - findAllByLastNameLike
 * - findAllByUsernameLike
 * Expecting these functions to be automatically implemented by Spring JPA.
 */
 
public interface PatientRepository extends JpaRepository<Patient, String> {
        
        Patient findByUsername(String username);
        List<Patient> findAllByFirstNameContaining(String firstName);
        List<Patient> findAllByLastNameContaining(String lastName);
        List<Patient> findAllByUsernameContaining(String username);
        List<Patient> findAllByFirstNameContainingAndLastNameContaining(String firstName, String lastName);
}
