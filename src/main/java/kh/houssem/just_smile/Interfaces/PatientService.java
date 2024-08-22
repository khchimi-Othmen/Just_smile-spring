package kh.houssem.just_smile.Interfaces;

import kh.houssem.just_smile.Entities.Patient;
import kh.houssem.just_smile.Entities.Payment;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> findAllPatients();

    Optional<Patient> findPatientById(Long patientId);

    Patient savePatient(Patient patient);

    Patient updatePatient(Long patientId, Patient patient);

    void deletePatient(Long patientId);
    List<Payment> findPaymentsByPatientId(Long patientId);


    }
