package edu.ncsu.csc.iTrust2.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

@Entity
public class Eyecheckup extends DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
    Visual Acuity Results. 3-digit integer between 20 and 200, Required.
     */
    @NotNull
    @Range(min = 20, max = 200)
    private Integer visualAcuityOS;

    @NotNull
    @Range(min = 20, max = 200)
    private Integer visualAcuityOD;

    /*
    Sphere. Positive or Negative floating point numbers up to two digits. Required
     */
    @NotNull
    private Float sphereOS;

    @NotNull
    private Float sphereOD;

    /*
    Cylinder. Positive or Negative floating point numbers up to two digits.
     */
    private Float cylinderOS;
    private Float cylinderOD;

    /*
    Axis.Integer between 1 and 180, inclusive. Required if Cylinder exists.
     */
    @Range(min = 1, max = 180)
    private Integer axisOS;
    @Range(min = 1, max = 180)
    private Integer axisOD;

    /*
    General visit notes, up to 500 characters
     */
    private String note;


    /*
     * The Patient who is associated with this eyecheckup
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id", columnDefinition = "varchar(100)")
    private User patient;

    /*
     * The HCP(OD/OPH) who is associated with this eyecheckup
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "hcp_id", columnDefinition = "varchar(100)")
    private User hcp;

    @Override
    public Serializable getId() {
        return id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(final User patient) {
        this.patient = patient;
    }

    public User getHcp() {
        return hcp;
    }

    public void setHcp(final User hcp) {
        this.hcp = hcp;
    }

    public Integer getVisualAcuityOS() {
        return visualAcuityOS;
    }

    public void setVisualAcuityOS(final Integer visualAcuityOS) {
        if (visualAcuityOS != null && visualAcuityOS >= 20 && visualAcuityOS <= 200) {
            this.visualAcuityOS = visualAcuityOS;
        } else {
            throw new IllegalArgumentException("Visual Acuity OS must be a 3-digit number between 20 and 200");
        }
    }

    public Integer getVisualAcuityOD() {
        return visualAcuityOD;
    }

    public void setVisualAcuityOD(final Integer visualAcuityOD) {
        if (visualAcuityOD != null && visualAcuityOD >= 20 && visualAcuityOD <= 200) {
            this.visualAcuityOD = visualAcuityOD;
        } else {
            throw new IllegalArgumentException("Visual Acuity OS must be a 3-digit number between 20 and 200");
        }
    }

    public Float getSphereOS() {
        return sphereOS;
    }

    public void setSphereOS(final Float sphereOS) {
        if (isValidSphereValue(sphereOS)) {
            this.sphereOS = sphereOS;
        } else {
            throw new IllegalArgumentException("Sphere OS must be a floating-point number with up to two digits.");
        }
    }

    public Float getSphereOD() {
        return sphereOD;
    }

    public void setSphereOD(final Float sphereOD) {
        if (isValidSphereValue(sphereOD)) {
            this.sphereOD = sphereOD;
        } else {
            throw new IllegalArgumentException("Sphere OS must be a floating-point number with up to two digits.");
        }
    }

    public Float getCylinderOS() {
        return cylinderOS;
    }

    public void setCylinderOS(final Float cylinderOS) {
        if (isValidCylinderValue(cylinderOS)) {
            this.cylinderOS = cylinderOS;
        } else {
            throw new IllegalArgumentException("Cylinder OS must be a floating-point number with up to two digits.");
        }
    }

    public Float getCylinderOD() {
        return cylinderOD;
    }

    public void setCylinderOD(Float cylinderOD) {
        if (isValidCylinderValue(cylinderOD)) {
            this.cylinderOD = cylinderOD;
        } else {
            throw new IllegalArgumentException("Cylinder OD must be a floating-point number with up to two digits.");
        }
    }

    public Integer getAxisOS() {
        return axisOS;
    }

    public void setAxisOS(Integer axisOS) {
        if (cylinderOS != null) {
            if (isValidAxisValue(axisOS)) {
                this.axisOS = axisOS;
            } else {
                throw new IllegalArgumentException("Axis OS must be an integer between 1 and 180.");
            }
        }
    }

    public Integer getAxisOD() {
        return axisOD;
    }

    public void setAxisOD(Integer axisOD) {
        if (cylinderOD != null) {
            if (isValidAxisValue(axisOD)) {
                this.axisOD = axisOD;
            } else {
                throw new IllegalArgumentException("Axis OD must be an integer between 1 and 180.");
            }
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if (note != null && note.length() > 500) {
            throw new IllegalArgumentException("Note must not exceed 500 characters.");
        }
        this.note = note;
    }


    // validate sphere value
    private boolean isValidSphereValue(Float value) {
        if (value == null) {
            return false;
        }
        String valueStr = value.toString();
        int decimalPointIndex = valueStr.indexOf(".");
        if (decimalPointIndex == -1) { // No decimal point, check the total length
            return valueStr.length() <= 2;
        } else {
            String integerPart = valueStr.substring(0, decimalPointIndex);
            String fractionalPart = valueStr.substring(decimalPointIndex + 1);
            return integerPart.length() <= 2 && fractionalPart.length() <= 2;
        }
    }

    // validate cylinder value
    private boolean isValidCylinderValue(Float value) {
        if (value == null) {
            return true; //  null is acceptable
        }
        String valueStr = value.toString();
        int decimalPointIndex = valueStr.indexOf(".");
        if (decimalPointIndex == -1) { // No decimal point, check the total length
            return valueStr.length() <= 2;
        } else {
            String integerPart = valueStr.substring(0, decimalPointIndex);
            String fractionalPart = valueStr.substring(decimalPointIndex + 1);
            return integerPart.length() <= 2 && fractionalPart.length() <= 2;
        }
    }

    // validate axis value
    private boolean isValidAxisValue(Integer value) {
        return value != null && value >= 1 && value <= 180;
    }


}
