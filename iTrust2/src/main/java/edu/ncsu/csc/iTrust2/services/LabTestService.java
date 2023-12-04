package edu.ncsu.csc.iTrust2.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import edu.ncsu.csc.iTrust2.repositories.LabTestRepository;
import edu.ncsu.csc.iTrust2.models.LabTest;
import java.util.List;


@Component
@Transactional
public class LabTestService extends Service{
    @Autowired
    private LabTestRepository repository;

    @Autowired
    private UserService            userService;

    @Override
    protected JpaRepository getRepository () {
        return repository;
    }

    public List<LabTest> findAll(){
        return repository.findAll();
    }

    public List<LabTest> findByPatient ( final String patientName ) {
        return repository.findByPatient( userService.findByName( patientName ) );
    }
}
