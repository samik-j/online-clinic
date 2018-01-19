package com.joanna.onlineclinic.web.appointment.available;

import com.joanna.onlineclinic.domain.appointment.available.AppointmentAvailable;
import com.joanna.onlineclinic.domain.appointment.available.AppointmentAvailableService;
import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.web.ErrorsResource;
import com.joanna.onlineclinic.web.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors/{doctorId}/appointmentsAvailable")
public class AppointmentAvailableController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AppointmentAvailableController.class);
    private AppointmentAvailableService appointmentService;
    private DoctorService doctorService;
    private AppointmentAvailableCreationValidator validator;

    public AppointmentAvailableController(
            AppointmentAvailableService appointmentService, DoctorService doctorService,
            AppointmentAvailableCreationValidator validator) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.validator = validator;
    }

    @PostMapping
    public ResponseEntity<Object> addAppointment(
            @PathVariable long doctorId, @RequestBody AppointmentAvailableResource resource) {
        LOGGER.info("Appointment available added, doctor id: {}, date and time: {}",
                doctorId, resource.getAppointmentDateTime());

        validateDoctorExistence(doctorId);

        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            AppointmentAvailable appointment = appointmentService.addAppointment(doctorId, resource);

            return new ResponseEntity<Object>(getAppointmentAvailableResource(appointment), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(errorsResource, HttpStatus.BAD_REQUEST);
        }

    }

    private void validateDoctorExistence(@PathVariable long doctorId) {
        if (!doctorService.doctorExists(doctorId)) {
            throw new ResourceNotFoundException();
        }
    }

    private AppointmentAvailableResource getAppointmentAvailableResource(AppointmentAvailable appointment) {
        return new AppointmentAvailableResource(appointment);
    }
}
