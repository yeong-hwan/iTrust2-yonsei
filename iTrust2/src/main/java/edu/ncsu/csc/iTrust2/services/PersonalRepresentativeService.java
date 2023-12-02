package edu.ncsu.csc.iTrust2.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import edu.ncsu.csc.iTrust2.repositories.PersonalRepresentativeRepository;
import edu.ncsu.csc.iTrust2.models.PersonalRepresentative;

@Component
@Transactional
public class PersonalRepresentativeService extends Service {

  @Autowired
  private PersonalRepresentativeRepository repository;

  @Override
  protected JpaRepository getRepository() {
    return repository;
  }

  public List<PersonalRepresentative> findByAssginorContains(final String assignor) {
    return repository.findAllByAssignorContaining(assignor);
  }

  public List<PersonalRepresentative> findByAssgineeContains(final String assignee) {
    return repository.findAllByAssigneeContaining(assignee);
  }

  public List<PersonalRepresentative> findByAssginorAndAssigneeContains(final String assignor, final String assignee) {
    return repository.findAllByAssignorAndAssigneeContaining(assignor, assignee);
  }

}
