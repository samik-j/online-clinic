package com.joanna.onlineclinic.domain.appointment.booked;

import com.joanna.onlineclinic.domain.ObjectNotFoundException;
import com.joanna.onlineclinic.domain.appointment.Appointment;
import com.joanna.onlineclinic.domain.appointment.AppointmentRepository;
import com.joanna.onlineclinic.domain.doctor.Doctor;
import com.joanna.onlineclinic.domain.doctor.DoctorRepository;
import com.joanna.onlineclinic.domain.patient.Patient;
import com.joanna.onlineclinic.domain.patient.PatientRepository;
import com.joanna.onlineclinic.web.appointment.booked.AppointmentBookedCreationResource;
import com.joanna.onlineclinic.web.appointment.booked.AppointmentBookedStatusChangeResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public boolean appointmentBookedExists(long appointmentId) {
        return appointmentBookedRepository.exists(appointmentId);
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

    public AppointmentBooked findById(long appointmentId) {
        return appointmentBookedRepository.findOne(appointmentId);
    }

    @Transactional
    public AppointmentBooked changeStatus(long appointmentId, AppointmentBookedStatusChangeResource resource) {
        AppointmentBooked appointmentBooked = appointmentBookedRepository.findOne(appointmentId);

        appointmentBooked.changeStatus(resource.getStatus());

        if (resource.getStatus().equals(AppointmentBookedStatus.CANCELLED)) {
            cancelAppointment(appointmentBooked);
        }

        return appointmentBookedRepository.save(appointmentBooked);
    }

    private void cancelAppointment(AppointmentBooked appointmentBooked) {
        Appointment appointment = appointmentRepository.findByDoctorAndDateAndTime(
                appointmentBooked.getDoctor(), appointmentBooked.getDate(),
                appointmentBooked.getTime());

        if (appointment != null) {
            appointment.cancel();
            appointmentRepository.save(appointment);
        } else {
            throw new ObjectNotFoundException();
        }
    }

    public List<AppointmentBooked> findByPatientId(long patientId, boolean current) {
        if (current) {
            return appointmentBookedRepository.findCurrentByPatientId(patientId, LocalDate.now());
        } else {
            return appointmentBookedRepository.findPastByPatientId(patientId, LocalDate.now());
        }
    }
}
