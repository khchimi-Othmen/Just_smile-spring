package kh.houssem.just_smile.Services;

import kh.houssem.just_smile.Entities.Patient;
import kh.houssem.just_smile.Entities.Payment;
import kh.houssem.just_smile.Interfaces.PatientService;
import kh.houssem.just_smile.Repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> findPatientById(Long patientId) {
        return patientRepository.findById(patientId);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long patientId, Patient patient) {
        if (patientRepository.existsById(patientId)) {
            patient.setPatientId(patientId);
            return patientRepository.save(patient);
        }
        return null; // Vous pouvez lancer une exception si le patient n'existe pas
    }

    @Override
    public void deletePatient(Long patientId) {
        if (patientRepository.existsById(patientId)) {
            patientRepository.deleteById(patientId);
        }  // Vous pouvez lancer une exception si le patient n'existe pas

    }
    @Override
    public List<Payment> findPaymentsByPatientId(Long patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        return patient.map(Patient::getPayments).orElse(null);
    }

}
