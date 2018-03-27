package com.joanna.onlineclinic.web.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBooked;
import com.joanna.onlineclinic.domain.appointment.booked.AppointmentBookedService;
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
@RequestMapping("/appointmentsBooked")
public class AppointmentBookedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentBookedController.class);
    private AppointmentBookedCreationValidator creationValidator;
    private AppointmentBookedStatusChangeValidator statusChangeValidator;
    private AppointmentBookedService appointmentBookedService;

    public AppointmentBookedController(AppointmentBookedCreationValidator creationValidator,
                                       AppointmentBookedStatusChangeValidator statusChangeValidator,
                                       AppointmentBookedService appointmentBookedService) {
        this.creationValidator = creationValidator;
        this.statusChangeValidator = statusChangeValidator;
        this.appointmentBookedService = appointmentBookedService;
    }

    @PostMapping
    public ResponseEntity<Object> bookAppointment(
            @RequestBody AppointmentBookedCreationResource resource) {
        LOGGER.info("Booking appointment: appointment id: {}, patient id: {}, reason: {}",
                resource.getAppointmentId(), resource.getPatientId(), resource.getReason());

        ErrorsResource errorsResource = creationValidator.validate(resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            AppointmentBooked appointmentBooked = appointmentBookedService.addAppointment(resource);

            return new ResponseEntity<Object>(
                    getAppointmentBookedResource(appointmentBooked), HttpStatus.OK);
        } else {
            LOGGER.info("Failed: {}", errorsResource.getValidationErrors().toString());

            return new ResponseEntity<Object>(errorsResource, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{appointmentId}", params = {"action=changeStatus"})
    public ResponseEntity<Object> changeAppointmentStatus
            (@PathVariable long appointmentId,
             @RequestBody AppointmentBookedStatusChangeResource resource) {
        LOGGER.info("Changing appointment status: appointment booked id: {}, new status: {}",
                appointmentId, resource);

        validateAppointmentExistence(appointmentId);
        ErrorsResource errorsResource = statusChangeValidator.validate(appointmentId, resource);

        if (errorsResource.getValidationErrors().isEmpty()) {
            AppointmentBooked appointmentChanged =
                    appointmentBookedService.changeStatus(appointmentId, resource);

            return new ResponseEntity<Object>(
                    getAppointmentBookedResource(appointmentChanged), HttpStatus.OK);
        } else {
            LOGGER.info("Failed: {}", errorsResource.getValidationErrors().toString());

            return new ResponseEntity<Object>(errorsResource, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(params = {"doctorId"})
    public List<AppointmentBookedResource> getAppointmentsByDoctorId(@RequestParam long doctorId) {
        return getAppointmentBookedResources(appointmentBookedService.findByDoctorId(doctorId));
    }

    @GetMapping(params= {"doctorId", "current"})
    public List<AppointmentBookedResource> geAppointmentsByDoctorId(
            @RequestParam long doctorId,
            @RequestParam boolean current) {
        return getAppointmentBookedResources(appointmentBookedService.findByDoctorId(doctorId, current));
    }

    @GetMapping(params = {"patientId"})
    public List<AppointmentBookedResource> getAppointmentsByPatientId(@RequestParam long patientId) {
        return getAppointmentBookedResources(appointmentBookedService.findByPatientId(patientId));
    }

    @GetMapping(params= {"patientId", "current"})
    public List<AppointmentBookedResource> geAppointmentsByPatientId(
            @RequestParam long patientId,
            @RequestParam boolean current) {
        return getAppointmentBookedResources(appointmentBookedService.findByPatientId(patientId, current));
    }

    private void validateAppointmentExistence(long appointmentId) {
        if (!appointmentBookedService.appointmentBookedExists(appointmentId)) {
            throw new ResourceNotFoundException();
        }
    }

    private AppointmentBookedResource getAppointmentBookedResource(AppointmentBooked appointment) {
        return new AppointmentBookedResource(appointment);
    }

    private List<AppointmentBookedResource> getAppointmentBookedResources(
            List<AppointmentBooked> appointments) {
        return appointments.stream()
                .map(AppointmentBookedResource::new).collect(Collectors.toList());
    }
}
