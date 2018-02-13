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

import java.util.List;

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

    public boolean appointmentBookedExists(AppointmentBookedCreationResource resource) {
        Appointment appointment = appointmentRepository.findOne(resource.getAppointmentId());

        return appointment != null
                && appointmentBookedRepository.existsByDateAndTimeAndDoctorIdAndPatientId(
                appointment.getDate(), appointment.getTime(),
                appointment.getDoctor().getId(), resource.getPatientId());
    }

    @Transactional
    public AppointmentBooked addAppointment(AppointmentBookedCreationResource resource) {
        Appointment appointment = appointmentRepository.findOne(resource.getAppointmentId());
        Patient patient = patientRepository.findOne(resource.getPatientId());
        Doctor doctor = appointment.getDoctor();
        AppointmentBooked appointmentBooked = new AppointmentBooked(
                appointment.getDoctor(), appointment.getDate(), appointment.getTime(),
                patient, resource.getReason());

        appointment.book();
        doctor.addAppointmentBooked(appointmentBooked);
        patient.addAppointmentBooked(appointmentBooked);

        //jak jest transactional to nie musze wolac save na patient, doctor, appointment
        return appointmentBookedRepository.save(appointmentBooked);
    }

    public List<AppointmentBooked> findByDoctorId(long doctorId) {
        return appointmentBookedRepository.findByDoctorId(doctorId);
    }

    public List<AppointmentBooked> findByPatientId(long patientId) {
        return appointmentBookedRepository.findByPatientId(patientId);
    }
}
