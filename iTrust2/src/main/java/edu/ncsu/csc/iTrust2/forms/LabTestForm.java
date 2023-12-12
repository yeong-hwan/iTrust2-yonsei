package edu.ncsu.csc.iTrust2.forms;

import edu.ncsu.csc.iTrust2.models.LabTest;

public class LabTestForm {
    private String testName;
    private String hcpName;
    private String patientName;
    private String labName;
    private String instructions;
    private String results;
    private String notes; 

    public LabTestForm() {
    }

    public LabTestForm(final LabTest labTest) {
        setTestName(labTest.getTestName());
        setLabName(labTest.getLabName());
        setInstructions(labTest.getInstructions());
        setResults(labTest.getResults());
        setNotes(labTest.getNotes());
        setPatientName(labTest.getPatient().getUsername());
        setHcpName(labTest.getHcp().getUsername());
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(final String testName) {
        this.testName = testName;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(final String labName) {
        this.labName = labName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(final String instructions) {
        this.instructions = instructions;
    }

    public String getResults() {
        return results;
    }

    public void setResults(final String results) {
        this.results = results;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(final String patientName) {
        this.patientName = patientName;
    }

    public String getHcpName() {
        return hcpName;
    }

    public void setHcpName(final String hcpName) {
        this.hcpName = hcpName;
    }
    


}
