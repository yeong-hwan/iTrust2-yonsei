package edu.ncsu.csc.iTrust2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import edu.ncsu.csc.iTrust2.models.LabTest;
import edu.ncsu.csc.iTrust2.models.User;


// @yewon added 2023.12.04
/*
 * This is the repository for interacting with the LabTest Table
 * Expecting these functions to be automatically implemented by Spring JPA.
 */
 

public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    List<LabTest> findAll();

    List<LabTest> findByPatient(User patient);

    List<LabTest> findByLabtech(User labtech);

    LabTest findByPatientAndTestNameAndLabNameAndLabtech(User patient, String testName, String labName, User labtech);
} 
