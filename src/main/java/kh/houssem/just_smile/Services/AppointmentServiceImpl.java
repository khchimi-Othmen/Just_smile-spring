package kh.houssem.just_smile.Services;

import kh.houssem.just_smile.Entities.Appointment;
import kh.houssem.just_smile.Entities.Patient;
import kh.houssem.just_smile.Interfaces.AppointmentService;
import kh.houssem.just_smile.Repositories.AppointmentRepository;
import kh.houssem.just_smile.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;


    @Override
    public Appointment createAppointmentWithPatient(Long patientId, Appointment appointment) {
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            appointment.setPatient(patient);

            // Vérification des conflits de temps
            if (!isTimeSlotAvailable(appointment.getDate(), appointment.getStartTime(), appointment.getEndTime())) {
                // Retourne null ou un objet spécial si la plage horaire est occupée
                return null;
            }
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    private boolean isTimeSlotAvailable(Date date, LocalTime startTime, LocalTime endTime) {
        List<Appointment> appointments = appointmentRepository.findByDate(date);
        for (Appointment existingAppointment : appointments) {
            if (startTime.isBefore(existingAppointment.getEndTime()) && endTime.isAfter(existingAppointment.getStartTime())) {
                return false; // Le créneau horaire est occupé
            }
        }
        return true; // Le créneau horaire est disponible
    }
    @Override
    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public List<Appointment> findAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientPatientId(patientId);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Long appointmentId, Appointment appointment) {
        if (appointmentRepository.existsById(appointmentId)) {
            appointment.setAppointmentId(appointmentId);
            return appointmentRepository.save(appointment);
        }
        return null; // Vous pouvez lancer une exception si le rendez-vous n'existe pas
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        if (appointmentRepository.existsById(appointmentId)) {
            appointmentRepository.deleteById(appointmentId);
        }  // Vous pouvez lancer une exception si le rendez-vous n'existe pas
    }

    @Override
    public Appointment assignAppointmentToPatient(Long appointmentId, Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);

        if (patientOpt.isPresent() && appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setPatient(patientOpt.get());
            return appointmentRepository.save(appointment);
        }
        return null; // Vous pouvez lancer une exception si l'un des deux n'existe pas
    }

    @Override
    public List<Appointment> getAllAppointmentsWithPatients() {
        return appointmentRepository.findAll(); // Comme chaque Appointment contient déjà le Patient
    }




    @Override
    public boolean isDateAvailable(Long patientId, Date date, String time) {
        List<Appointment> appointments = appointmentRepository.findByPatientPatientIdAndDateAndTime(patientId, date, time);
        return appointments.isEmpty();
    }

    @Override
    public Appointment reserveDate(Long patientId, Date date, String time, String reason) {
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (patientOptional.isPresent() && isDateAvailable(patientId, date, time)) {
            Appointment appointment = new Appointment();
            appointment.setPatient(patientOptional.get());
            appointment.setDate(date);
            appointment.setTime(time);
            appointment.setReason(reason);
            return appointmentRepository.save(appointment);
        } else {
            throw new RuntimeException("Date not available or patient not found");
        }
    }
    @Override
    public List<Appointment> findAppointmentsByDate(Date date) {
        return appointmentRepository.findByDateOrderByStartTimeAsc(date);
    }
}
