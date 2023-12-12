package edu.ncsu.csc.iTrust2.models;

import java.time.ZonedDateTime;

import edu.ncsu.csc.iTrust2.models.enums.SurgeryType;

// 12.02 patient가 볼 수 있는 정보 저장하는 class 추가
public class OphSurgeryVisit {
	private Long id;
    private Eyecheckup eyecheckup;
    private SurgeryType surgeryType;
    private String note;
    private ZonedDateTime date;
    private Hospital hospital;

    // 생성자, getter 및 setter 메소드 추가
    public OphSurgeryVisit(Long id, Eyecheckup eyecheckup, SurgeryType surgeryType, String note, ZonedDateTime date, Hospital hospital) {
        this.id = id;
    	this.eyecheckup = eyecheckup;
        this.surgeryType = surgeryType;
        this.note = note;
        this.date = date;
        this.hospital = hospital;
    }
    // id getter/setter
    public Long getId() {
    	return id;
    }
    
    public void setId ( Long id ) {
        this.id = id;
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
    
    // date getter/setter
    public ZonedDateTime getDate() {
    	return date;
    }
    
    public void setDate(ZonedDateTime date) {
    	this.date = date;
    }
    
    // date getter/setter
    public Hospital getHospital() {
    	return hospital;
    }
    
    public void setDate(Hospital hospital) {
    	this.hospital = hospital;
    }
    
    
}