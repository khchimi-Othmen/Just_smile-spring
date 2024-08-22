package kh.houssem.just_smile.Controllers;

import kh.houssem.just_smile.Entities.Appointment;
import kh.houssem.just_smile.Interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Récupérer tous les rendez-vous
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.findAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Récupérer un rendez-vous par ID
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.findAppointmentById(id);
        return appointment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Récupérer les rendez-vous par ID de patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientId(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.findAppointmentsByPatientId(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    // Créer un nouveau rendez-vous
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment newAppointment = appointmentService.saveAppointment(appointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    // Mettre à jour un rendez-vous existant
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        return updatedAppointment != null ? new ResponseEntity<>(updatedAppointment, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Supprimer un rendez-vous
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Assigner un rendez-vous à un patient
    @PostMapping("/{appointmentId}/assignToPatient/{patientId}")
    public ResponseEntity<Appointment> assignAppointmentToPatient(@PathVariable Long appointmentId, @PathVariable Long patientId) {
        Appointment assignedAppointment = appointmentService.assignAppointmentToPatient(appointmentId, patientId);
        return assignedAppointment != null ? new ResponseEntity<>(assignedAppointment, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<Appointment> createAppointmentWithPatient(@PathVariable Long patientId, @RequestBody Appointment appointment) {
        try {
            Appointment newAppointment = appointmentService.createAppointmentWithPatient(patientId, appointment);
            return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Vérifier si une date est disponible pour un rendez-vous
    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam Long patientId, @RequestParam Date date, @RequestParam String time) {
        boolean available = appointmentService.isDateAvailable(patientId, date, time);
        return new ResponseEntity<>(available, HttpStatus.OK);
    }

    // Réserver un rendez-vous à une date spécifique
    @PostMapping("/reserve")
    public ResponseEntity<Appointment> reserveAppointment(@RequestParam Long patientId, @RequestParam Date date, @RequestParam String time, @RequestParam String reason) {
        try {
            Appointment reservedAppointment = appointmentService.reserveDate(patientId, date, time, reason);
            return new ResponseEntity<>(reservedAppointment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/by-date")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(@RequestParam String date) {
        try {
            Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            List<Appointment> appointments = appointmentService.findAppointmentsByDate(selectedDate);
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
