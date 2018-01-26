package com.joanna.onlineclinic.web.appointment;

import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentService;
import com.joanna.onlineclinic.domain.doctor.DoctorService;
import com.joanna.onlineclinic.web.ErrorsResource;
import com.joanna.onlineclinic.web.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors/{doctorId}/appointments")
public class AppointmentController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AppointmentController.class);
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private AppointmentCreationValidator validator;

    public AppointmentController(
            AppointmentService appointmentService, DoctorService doctorService,
            AppointmentCreationValidator validator) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.validator = validator;
    }

    @PostMapping
    public ResponseEntity<Object> addAppointment(
            @PathVariable long doctorId, @RequestBody AppointmentResource resource) {
        LOGGER.info("Appointment available added, doctor id: {}, date and time: {}",
                doctorId, resource.getAppointmentDateTime());

        validateDoctorExistence(doctorId);

        ErrorsResource errorsResource = validator.validate(doctorId, resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            Appointment appointment =
                    appointmentService.addAppointment(doctorId, resource);

            return new ResponseEntity<Object>(
                    getAppointmentResource(appointment), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(errorsResource, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<AppointmentResource> getAppointments(@PathVariable long doctorId) {
        validateDoctorExistence(doctorId);

        return getAppointmentResources(appointmentService.findAppointments(doctorId));
    }

    @GetMapping(params = {"available=true"})
    public List<AppointmentResource> getAvailableAppointments(@PathVariable long doctorId) {
        validateDoctorExistence(doctorId);

        return getAppointmentResources(appointmentService.findAvailableAppointments(doctorId));
    }

    private void validateDoctorExistence(@PathVariable long doctorId) {
        if (!doctorService.doctorExists(doctorId)) {
            throw new ResourceNotFoundException();
        }
    }

    private AppointmentResource getAppointmentResource(Appointment appointment) {
        return new AppointmentResource(appointment);
    }

    private List<AppointmentResource> getAppointmentResources(List<Appointment> appointments) {
        return appointments.stream()
                .map(AppointmentResource::new)
                .collect(Collectors.toList());
    }
}
