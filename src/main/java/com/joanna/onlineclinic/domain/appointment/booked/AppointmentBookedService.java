package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientRepository;
import com.joanna.onlineclinic.web.appointment.booked.AppointmentBookedCreationResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentBookedService {

    private AppointmentBookedRepository appointmentBookedRepository;
    private AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    public AppointmentBookedService(AppointmentBookedRepository appointmentBookedRepository,
                                    AppointmentRepository appointmentRepository,
                                    PatientRepository patientRepository,
                                    DoctorRepository doctorRepository) {
        this.appointmentBookedRepository = appointmentBookedRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public boolean appointmentExists(AppointmentBookedCreationResource resource) {
        Appointment appointment = appointmentRepository.findOne(resource.getAppointmentId());

        return appointmentBookedRepository.existsByAppointmentDateTimeAndDoctorIdAndPatientId(
                appointment.getAppointmentDateTime(), appointment.getDoctor().getId(),
                resource.getPatientId());
    }

    @Transactional
    public AppointmentBooked registerAppointment(AppointmentBookedCreationResource resource) {
        Appointment appointment = appointmentRepository.findOne(resource.getAppointmentId());
        Patient patient = patientRepository.findOne(resource.getPatientId());
        Doctor doctor = appointment.getDoctor();
        AppointmentBooked appointmentBooked = new AppointmentBooked(
                appointment.getDoctor(), appointment.getAppointmentDateTime(), 
                patient, resource.getReason());

        appointment.book();
        doctor.addAppointmentBooked(appointmentBooked);
        patient.addAppointmentBooked(appointmentBooked);

        AppointmentBooked savedAppointment = appointmentBookedRepository.save(appointmentBooked);
        appointmentRepository.save(appointment);
        doctorRepository.save(doctor);
        patientRepository.save(patient);

        return savedAppointment;
    }
}
