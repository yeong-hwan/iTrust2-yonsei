package edu.ncsu.csc.iTrust2.services;

import edu.ncsu.csc.iTrust2.forms.EyecheckupForm;
import edu.ncsu.csc.iTrust2.forms.OfficeVisitForm;
import edu.ncsu.csc.iTrust2.models.Eyecheckup;
import edu.ncsu.csc.iTrust2.repositories.EyecheckupRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class EyecheckupService extends Service{

    @Autowired
    private EyecheckupRepository repository;

    @Autowired
    private UserService userService;
    @Override
    protected JpaRepository getRepository() {
        return repository;
    }

    public Eyecheckup build (final OfficeVisitForm ovf){
        final Eyecheckup eyecheckup = new Eyecheckup();
        eyecheckup.setPatient(userService.findByName(ovf.getPatient()));
        eyecheckup.setHcp(userService.findByName(ovf.getHcp()));

        EyecheckupForm eyecheckupForm = ovf.getEyecheckup();

        eyecheckup.setVisualAcuityOD(eyecheckupForm.getVisualAcuityOD());
        eyecheckup.setVisualAcuityOS(eyecheckupForm.getVisualAcuityOS());
        eyecheckup.setSphereOD(eyecheckupForm.getSphereOD());
        eyecheckup.setSphereOS(eyecheckupForm.getSphereOS());
        eyecheckup.setCylinderOD(eyecheckupForm.getCylinderOD());
        eyecheckup.setCylinderOS(eyecheckupForm.getCylinderOS());
        eyecheckup.setAxisOD(eyecheckupForm.getAxisOD());
        eyecheckup.setAxisOS(eyecheckupForm.getAxisOS());
        eyecheckup.setNote(eyecheckupForm.getNote());

        return eyecheckup;
    }
}
