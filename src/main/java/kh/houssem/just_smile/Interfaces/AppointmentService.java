package kh.houssem.just_smile.Interfaces;

import kh.houssem.just_smile.Entities.Appointment;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    List<Appointment> findAllAppointments();

    Optional<Appointment> findAppointmentById(Long appointmentId);

    Appointment saveAppointment(Appointment appointment);

    Appointment updateAppointment(Long appointmentId, Appointment appointment);

    void deleteAppointment(Long appointmentId);
    List<Appointment> findAppointmentsByPatientId(Long patientId);
    List<Appointment> getAllAppointmentsWithPatients();
    Appointment createAppointmentWithPatient(Long patientId, Appointment appointment);
    Appointment assignAppointmentToPatient(Long appointmentId, Long patientId);

    // Vérifier si une date et une heure sont disponibles pour un patient
    boolean isDateAvailable(Long patientId, Date date, String time);

    // Réserver un rendez-vous à une date spécifique pour un patient
    Appointment reserveDate(Long patientId, Date date, String time, String reason);
    List<Appointment> findAppointmentsByDate(Date date) ;

}
