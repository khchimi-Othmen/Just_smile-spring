package kh.houssem.just_smile.Interfaces;

import kh.houssem.just_smile.Entities.Payment;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PaymentService {
    List<Payment> findAllPayments();

    Optional<Payment> findPaymentById(Long paymentId);

    List<Payment> findPaymentsByPatientId(Long patientId);

    Payment savePayment(Payment payment);

    Payment updatePayment(Long paymentId, Payment payment);

    void deletePayment(Long paymentId);
    Payment assignPaymentToPatient(Long paymentId, Long patientId);
    List<Payment> getAllPaymentsWithPatients() ;
    Payment createPaymentWithPatient(Long patientId, Payment payment);
    List<Payment> getAllPayments();
    List<Map<String, Object>> findTotalPaymentsByPatient();
    Double findTotalPaymentByPatientId(Long patientId);

    List<Payment> getPaymentsForDay(LocalDate date);

    List<Payment> getPaymentsForWeek(LocalDate date);

    List<Payment> getPaymentsForMonth(LocalDate date);

}
