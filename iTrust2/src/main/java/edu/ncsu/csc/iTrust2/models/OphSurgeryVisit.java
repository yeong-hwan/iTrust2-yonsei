package edu.ncsu.csc.iTrust2.models;

import edu.ncsu.csc.iTrust2.models.enums.SurgeryType;

// 12.02 patient가 볼 수 있는 정보 저장하는 class 추가
public class OphSurgeryVisit {
    private Eyecheckup eyecheckup;
    private SurgeryType surgeryType;
    private String note;

    // 생성자, getter 및 setter 메소드 추가
    public OphSurgeryVisit(Eyecheckup eyecheckup, SurgeryType surgeryType, String note) {
        this.eyecheckup = eyecheckup;
        this.surgeryType = surgeryType;
        this.note = note;
    }
    
    // Eyecheckup에 대한 getter 및 setter
    public Eyecheckup getEyecheckup() {
        return eyecheckup;
    }

    public void setEyecheckup(Eyecheckup eyecheckup) {
        this.eyecheckup = eyecheckup;
    }

    // SurgeryType에 대한 getter 및 setter
    public SurgeryType getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(SurgeryType surgeryType) {
        this.surgeryType = surgeryType;
    }

    // Note에 대한 getter 및 setter
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
}