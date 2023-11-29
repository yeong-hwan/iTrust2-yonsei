package edu.ncsu.csc.iTrust2.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.ncsu.csc.iTrust2.forms.PersonalRepresentativeForm;

@Entity
public class PersonalRepresentative extends DomainObject {

  public PersonalRepresentative() {
  }

  public PersonalRepresentative(final PersonalRepresentativeForm form) {
    setAssignor(form.getAssignor());
    setAssignee(form.getAssignee());
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String assignor;

  private String assignee;

  public Long getId() {
    return id;
  }

  public String getAssignor() {
    return assignor;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignor(final String assignor) {
    this.assignor = assignor;
  }

  public void setAssignee(final String assignee) {
    this.assignee = assignee;
  }

}
