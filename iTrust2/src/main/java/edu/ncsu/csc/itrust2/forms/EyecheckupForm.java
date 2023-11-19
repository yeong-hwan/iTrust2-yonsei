package edu.ncsu.csc.iTrust2.forms;

import edu.ncsu.csc.iTrust2.models.Eyecheckup;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class EyecheckupForm implements Serializable {

    // Empty Eyecheckup Form
    public EyecheckupForm() {
    }

    @NotEmpty
    private String patient;
    @NotEmpty
    private String hcp;

    @NotEmpty
    private Integer visualAcuityOS;
    @NotEmpty
    private Integer visualAcuityOD;
    @NotEmpty
    private Float sphereOS;
    @NotEmpty
    private Float sphereOD;
    private Float cylinderOS;
    private Float cylinderOD;
    private Integer axisOS;
    private Integer axisOD;
    @Length(max = 500)
    private String note;

    public EyecheckupForm(final Eyecheckup eyecheckup) {
        setPatient(eyecheckup.getPatient().getUsername());
        setHcp(eyecheckup.getHcp().getUsername());
        setVisualAcuityOS(eyecheckup.getVisualAcuityOS());
        setVisualAcuityOD(eyecheckup.getVisualAcuityOD());
        setSphereOS(eyecheckup.getSphereOS());
        setSphereOD(eyecheckup.getSphereOD());
        setCylinderOS(eyecheckup.getCylinderOS());
        setCylinderOD(eyecheckup.getCylinderOD());
        setAxisOS(eyecheckup.getAxisOS());
        setAxisOD(eyecheckup.getAxisOD());
    }

    // Getters and Setters
    public String getPatient () {
        return this.patient;
    }

    public void setPatient ( final String patient ) {
        this.patient = patient;
    }
    public String getHcp () {
        return this.hcp;
    }
    public void setHcp ( final String hcp ) {
        this.hcp = hcp;
    }


    public Integer getVisualAcuityOS() {
        return visualAcuityOS;
    }

    public void setVisualAcuityOS(final Integer visualAcuityOS) {
        this.visualAcuityOS = visualAcuityOS;
    }

    public Integer getVisualAcuityOD() {
        return visualAcuityOD;
    }

    public void setVisualAcuityOD(final Integer visualAcuityOD) {
        this.visualAcuityOD = visualAcuityOD;
    }

    public Float getSphereOS() {
        return sphereOS;
    }

    public void setSphereOS(final Float sphereOS) {
        this.sphereOS = sphereOS;
    }

    public Float getSphereOD() {
        return sphereOD;
    }

    public void setSphereOD(final Float sphereOD) {
        this.sphereOD = sphereOD;
    }

    public Float getCylinderOS() {
        return cylinderOS;
    }

    public void setCylinderOS(final Float cylinderOS) {
        this.cylinderOS = cylinderOS;
    }

    public Float getCylinderOD() {
        return cylinderOD;
    }

    public void setCylinderOD(final Float cylinderOD) {
        this.cylinderOD = cylinderOD;
    }

    public Integer getAxisOS() {
        return axisOS;
    }

    public void setAxisOS(final Integer axisOS) {
        this.axisOS = axisOS;
    }

    public Integer getAxisOD() {
        return axisOD;
    }

    public void setAxisOD(final Integer axisOD) {
        this.axisOD = axisOD;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }


}
