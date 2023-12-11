package edu.ncsu.csc.iTrust2.controllers.routing;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ncsu.csc.iTrust2.models.enums.Role;

/**
 * Controller to manage basic abilities for Lab Tech roles
 *
 * @author Amalan Iyengar
 *
 */

@Controller
public class LabTechController {

    /**
     * Returns the Lab Tech for the given model
     *
     * @param model
     *            model to check
     * @return role
     */
    @RequestMapping ( value = "labtech/index" )
    @PreAuthorize ( "hasRole('ROLE_LABTECH')" )
    public String index ( final Model model ) {
        return Role.ROLE_LABTECH.getLanding();
    }

    /* 
     * Returns the form page for a HCP to view Request Laboratory Test
     *
     * @param model
     *            Data for the front end
     * @return The page to display to the user
     */
    @GetMapping ( "/labtech/recordRequestedLaboratoryTest" )
    @PreAuthorize ( "hasRole('ROLE_LABTECH')" )
    public String recordRequestedLaboratoryTest ( final Model model ) {
        return "labtech/recordRequestedLaboratoryTest";
    }
}