package edu.ncsu.csc.iTrust2.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import edu.ncsu.csc.iTrust2.repositories.LabOrderRepository;
import edu.ncsu.csc.iTrust2.models.LabOrder;
import java.util.List;


@Component
@Transactional
public class LabOrderService extends Service{
    @Autowired
    private LabOrderRepository repository;

    @Override
    protected JpaRepository getRepository () {
        return repository;
    }
}
