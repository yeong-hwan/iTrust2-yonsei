package edu.ncsu.csc.iTrust2.forms;

import edu.ncsu.csc.iTrust2.models.PersonalRepresentative;

/**
 * * A form for REST API communication. Contains fields for constructing
 * PersonalRepresentative objects.
 * 
 * @author jang-yeonghwan
 */
public class PersonalRepresentativeForm {
  private String assignor;
  private String assignee;

  public PersonalRepresentativeForm() {
  }

  public PersonalRepresentativeForm(final PersonalRepresentative persnoalRepresentative) {
    setAssignor(persnoalRepresentative.getAssignor());
    setAssignee(persnoalRepresentative.getAssignor());
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
