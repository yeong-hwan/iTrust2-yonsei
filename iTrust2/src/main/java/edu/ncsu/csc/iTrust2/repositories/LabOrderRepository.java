package edu.ncsu.csc.iTrust2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import edu.ncsu.csc.iTrust2.models.LabOrder;

public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {
    // @auther: Yewon Lim
    // Nothing to do here
    // This is a repository for LabOrder
    
}
