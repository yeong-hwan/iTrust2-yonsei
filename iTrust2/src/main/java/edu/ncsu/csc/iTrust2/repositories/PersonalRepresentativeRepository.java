package edu.ncsu.csc.iTrust2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.iTrust2.models.PersonalRepresentative;

public interface PersonalRepresentativeRepository extends JpaRepository<PersonalRepresentative, Long> {
  List<PersonalRepresentative> findAllByAssignorConataining(String assignor);

  List<PersonalRepresentative> findAllByAssigneeContaining(String assignee);

  List<PersonalRepresentative> findAllByAssignorAndAssigneeContaining(String assignor, String assignee);
}
