package kh.houssem.just_smile.Controllers;

import kh.houssem.just_smile.Entities.Patient;
import kh.houssem.just_smile.Entities.Payment;
import kh.houssem.just_smile.Interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.findAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long patientId) {
        Optional<Patient> patient = patientService.findPatientById(patientId);
        return patient.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") Long patientId, @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(patientId, patient);
        if (updatedPatient != null) {
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long patientId) {
        patientService.deletePatient(patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/payments")
    public ResponseEntity<List<Payment>> getPaymentsByPatientId(@PathVariable("id") Long patientId) {
        List<Payment> payments = patientService.findPaymentsByPatientId(patientId);
        if (payments != null) {
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
