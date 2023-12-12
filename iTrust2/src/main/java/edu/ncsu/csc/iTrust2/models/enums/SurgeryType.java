package edu.ncsu.csc.iTrust2.models.enums;

public enum SurgeryType {
    // Surgery type for Ophthalmology Surgery
    CATARACT_SURGERY ( 1 , "cataract surgery"),
    LASER_SURGERY ( 2, "laser surgery" ),
    REFRACTIVE_SURGERY ( 3 , "refractive surgery");

    private int code;
    private String description;


    private SurgeryType ( final int code , final String description) {
        this.code = code;
        this.description = description;
    }


    public int getCode () {
        return code;
    }
}


