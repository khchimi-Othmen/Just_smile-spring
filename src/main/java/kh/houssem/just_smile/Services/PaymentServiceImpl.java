package kh.houssem.just_smile.Services;


import kh.houssem.just_smile.Entities.Patient;
import kh.houssem.just_smile.Entities.Payment;
import kh.houssem.just_smile.Interfaces.PaymentService;
import kh.houssem.just_smile.Repositories.PatientRepository;
import kh.houssem.just_smile.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> findPaymentsByPatientId(Long patientId) {
        return paymentRepository.findByPatientPatientId(patientId);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long paymentId, Payment payment) {
        if (paymentRepository.existsById(paymentId)) {
            payment.setPaymentId(paymentId);
            return paymentRepository.save(payment);
        }
        return null; // Vous pouvez lancer une exception si le paiement n'existe pas
    }

    @Override
    public void deletePayment(Long paymentId) {
        if (paymentRepository.existsById(paymentId)) {
            paymentRepository.deleteById(paymentId);
        }  // Vous pouvez lancer une exception si le paiement n'existe pas
    }


    @Override
    public Payment assignPaymentToPatient(Long paymentId, Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);

        if (patientOpt.isPresent() && paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setPatient(patientOpt.get());
            return paymentRepository.save(payment);
        }
        return null; // Vous pouvez lancer une exception si l'un des deux n'existe pas
    }

    @Override
    public List<Payment> getAllPaymentsWithPatients() {
        return paymentRepository.findAll(); // Comme chaque Payment contient déjà le Patient
    }

    @Override
    public Payment createPaymentWithPatient(Long patientId, Payment payment) {
        Optional<Patient> patientOptional = patientRepository.findById(patientId);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            payment.setPatient(patient); // Assign the patient to the payment
            return paymentRepository.save(payment); // Save the payment
        } else {
            throw new RuntimeException("Patient not found with id: " + patientId);
        }
    }
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll(); // Retrieves all payments, each with their associated patient
    }

    @Override
    public List<Map<String, Object>> findTotalPaymentsByPatient() {
        return paymentRepository.findTotalPaymentsByPatient();
    }
    @Override
    public Double findTotalPaymentByPatientId(Long patientId) {
        return paymentRepository.findTotalPaymentByPatientId(patientId);
    }


    @Override
    public List<Payment> getPaymentsForDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return paymentRepository.findAllByDateBetween(
                Date.from(startOfDay.atZone(java.time.ZoneId.systemDefault()).toInstant()),
                Date.from(endOfDay.atZone(java.time.ZoneId.systemDefault()).toInstant())
        );
    }
    @Override
    public List<Payment> getPaymentsForWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

        return paymentRepository.findAllByDateBetween(
                Date.from(startOfWeek.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()),
                Date.from(endOfWeek.atTime(23, 59, 59).atZone(java.time.ZoneId.systemDefault()).toInstant())
        );
    }
    @Override
    public List<Payment> getPaymentsForMonth(LocalDate date) {
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        return paymentRepository.findAllByDateBetween(
                Date.from(startOfMonth.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()),
                Date.from(endOfMonth.atTime(23, 59, 59).atZone(java.time.ZoneId.systemDefault()).toInstant())
        );
    }
}
