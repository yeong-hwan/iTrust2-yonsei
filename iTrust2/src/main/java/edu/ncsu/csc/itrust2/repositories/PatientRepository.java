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
    
        List<Patient> findAllByFirstNameLike(String firstName);
        List<Patient> findAllByLastNameLike(String lastName);
        List<Patient> findAllByUsernameLike(String username);
        List<Patient> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName);
}
