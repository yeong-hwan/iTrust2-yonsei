package edu.ncsu.csc.iTrust2.controllers.routing;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ncsu.csc.iTrust2.models.enums.Role;

/**
 * Controller class responsible for managing the behavior for the HCP Landing
 * Screen
 *
 * @author Kai Presler-Marshall
 *
 */
@Controller
public class HCPController {

    /**
     * Returns the Landing screen for the HCP
     *
     * @param model
     *              Data from the front end
     * @return The page to display
     */
    @RequestMapping(value = "hcp/index")
    @PreAuthorize("hasAnyRole('ROLE_HCP', 'ROLE_OPH','ROLE_OD')")
    public String index(final Model model) {
        return Role.ROLE_HCP.getLanding();
    }

    /**
     * Returns the page allowing HCPs to edit patient demographics
     *
     * @return The page to display
     */
    @GetMapping("/hcp/editPatientDemographics")
    @PreAuthorize("hasAnyRole('ROLE_HCP',  'ROLE_OPH','ROLE_OD')")
    public String editPatientDemographics() {
        return "/hcp/editPatientDemographics";
    }

    /**
     * Returns the page allowing HCPs to edit prescriptions
     *
     * @return The page to display
     */
    @GetMapping("/hcp/editPrescriptions")
    @PreAuthorize("hasAnyRole('ROLE_HCP', 'ROLE_OPH','ROLE_OD')")
    public String editPrescriptions() {
        return "/hcp/editPrescriptions";
    }

    /**
     * Returns the ER for the given model
     *
     * @param model
     *              model to check
     * @return role
     */
    @RequestMapping(value = "hcp/records")
    @PreAuthorize("hasAnyRole('ROLE_HCP', 'ROLE_OPH','ROLE_OD')")
    public String emergencyRecords(final Model model) {
        return "personnel/records";
    }

    /**
     * Method responsible for HCP's Accept/Reject requested appointment
     * functionality. This prepares the page.
     *
     * @param model
     *              Data for the front end
     * @return The page to display to the user
     */
    @GetMapping("/hcp/appointmentRequests")
    @PreAuthorize("hasAnyRole('ROLE_HCP','ROLE_OPH','ROLE_OD')")
    public String requestAppointmentForm(final Model model) {
        return "hcp/appointmentRequests";
    }

    /**
     * Method responsible for HCP's Accept/Reject requested appointment
     * functionality. This prepares the page.
     *
     * @param model
     *              Data for the front end
     * @return The page to display to the user
     */
    @GetMapping("/hcp/appointmentOphthalmologistRequests")
    @PreAuthorize("hasAnyRole('ROLE_OPH', 'ROLE_OD')")
    public String requestOphthalmologistAppointmentForm(final Model model) {
        return "hcp/appointmentOphthalmologistRequests";
    }

    /**
     * Returns the form page for a HCP to document an OfficeVisit
     *
     * @param model
     *              The data for the front end
     * @return Page to display to the user
     */
    @GetMapping("/hcp/documentOfficeVisit")
    @PreAuthorize("hasAnyRole('ROLE_HCP', 'ROLE_OPH','ROLE_OD')")
    public String documentOfficeVisit(final Model model) {
        return "/hcp/documentOfficeVisit";
    }

    /**
     * Method responsible for HCP's Accept/Reject requested appointment
     * functionality. This prepares the page.
     *
     * @param model
     *              Data for the front end
     * @return The page to display to the user
     */
    @GetMapping("hcp/FoodDiary/viewPatientFoodDiary")
    @PreAuthorize("hasAnyRole('ROLE_HCP','ROLE_OPH','ROLE_OD')")
    public String viewPatientFoodDiary(final Model model) {
        return "/hcp/FoodDiary/viewPatientFoodDiary";
    }

}
