package kh.houssem.just_smile.Repositories;

import kh.houssem.just_smile.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByPatientPatientId(Long patientId);

    @Query("SELECT p.patient.patientId AS patientId, SUM(p.amount) AS totalPayment FROM Payment p GROUP BY p.patient.patientId")
    List<Map<String, Object>> findTotalPaymentsByPatient();

    // Nouvelle méthode pour un patient spécifique
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.patient.patientId = :patientId")
    Double findTotalPaymentByPatientId(@Param("patientId") Long patientId);

    List<Payment> findAllByDateBetween(Date from, Date from1);
}
