package edu.ncsu.csc.iTrust2.models;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import edu.ncsu.csc.iTrust2.models.User;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.JsonAdapter;

import edu.ncsu.csc.iTrust2.adapters.LocalDateAdapter;
import edu.ncsu.csc.iTrust2.forms.UserForm;
import edu.ncsu.csc.iTrust2.models.enums.Role;


/* 
 * LabTest.java for UC23
 * @author: Yewon Lim 
 * @date: 2023/12/04
 */


@Entity
public class LabTest extends DomainObject{
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long      id;

    @NotNull
    private String    testName;

    @NotNull
    private String    labName;

    @NotNull
    private String    instructions;

    @NotNull
    private String    results;

    @Basic (optional = true)
    private String    notes;

    @NotNull
    @ManyToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "patient_id", columnDefinition = "varchar(100)" )
    private Patient      patient;

    @NotNull
    @ManyToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "hcp_id", columnDefinition = "varchar(100)" )
    private User      hcp;

    @NotNull
    @ManyToOne ( cascade = CascadeType.ALL )
    @JoinColumn ( name = "labtech_id", columnDefinition = "varchar(100)" )
    private User      labtech;


    /**
     * Empty constructor for Hibernate.
     */
    public LabTest () {
    }

    /**
     * Sets the LabTest's unique id.
     *
     * @param id
     *            the LabTest id
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Returns the id associated with the LabTest.
     *
     * @return the LabTest id
     */
    public Long getId () {
        return id;
    }

    /**
     * Returns the test name.
     *
     * @return the LabTest's name
     */
    public String getTestName () {
        return testName;
    }

    /**
     * Sets the LabTest's name to the given name.
     *
     * @param testName
     *            
     */
    public void setTestName ( final String testName ) {
        this.testName = testName;
    }

    /**
     * Returns the labName.
     *
     * @return the LabTest's labName
     */
    public String getLabName () {
        return labName;
    }

    /**
     * Sets the LabTest's labName
     *
     * @param labName
     *           
     */
    public void setLabName ( final String labName ) {
        this.labName = labName;
    }

    /**
     * Returns the LabTest's instructions.
     *
     * @return the instructions
     */
    public String getInstructions () {
        return instructions;
    }

    /**
     * Sets the LabTest's instructions
     *
     * @param instructions
     *            
     */
    public void setInstructions ( final String instructions ) {
        this.instructions = instructions;
    }

    /**
     * Returns the LabTest's results
     *
     * @return the LabTest's results
     */
    public String getResults () {
        return results;
    }

    /**
     * Sets the LabTest's results
     *
     * @param results
     *            
     */
    public void setResults ( final String results ) {
        this.results = results;
    }

    /**
     * Gets the LabTest's notes
     *
     * @return notes
     */
    public String getNotes () {
        return notes;
    }

    /**
     * Sets the LabTest's notes
     *
     * @param notes
     *            
     */
    public void setNotes ( final String notes ) {
        this.notes = notes;
    }

    
    public Patient getPatient () {
        return patient;
    }

    /**
     * Sets the LabTest's patient to the given user
     *
     * @param user
     *            
     */ 
    public void setPatient ( final Patient user ) {
        this.patient = user;
    }

    /**
     * Returns the LabTest's hcp
     *
     * @return the LabTest's hcp
     */
    public User getHcp () {
        return hcp;
    }

    /**
     * Sets the LabTest's hcp to the given user
     *
     * @param user
     *            
     */
    public void setHcp ( final User user ) {
        this.hcp = user;
    }

    /**
     * Returns the LabTest's labtech
     *
     * @return the LabTest's labtech
     */
    public User getLabtech () {
        return labtech;
    }

    /**
     * Sets the LabTest's labtech to the given user
     *
     * @param user
     *            
     */
    public void setLabtech ( final User user ) {
        this.labtech = user;
    }
}
