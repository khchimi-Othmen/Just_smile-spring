package kh.houssem.just_smile.Controllers;

import kh.houssem.just_smile.Entities.Payment;
import kh.houssem.just_smile.Interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://192.168.1.25:80")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

//    @GetMapping
//    public ResponseEntity<List<Payment>> getAllPayments() {
//        List<Payment> payments = paymentService.findAllPayments();
//        return new ResponseEntity<>(payments, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long paymentId) {
        Optional<Payment> payment = paymentService.findPaymentById(paymentId);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Payment>> getPaymentsByPatientId(@PathVariable("patientId") Long patientId) {
        List<Payment> payments = paymentService.findPaymentsByPatientId(patientId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
//        Payment savedPayment = paymentService.savePayment(payment);
//        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") Long paymentId, @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(paymentId, payment);
        if (updatedPayment != null) {
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") Long paymentId) {
        paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    // Récupérer tous les paiements avec leurs patients
//    @GetMapping("/all-with-patients")
//    public List<Payment> getAllPaymentsWithPatients() {
//        return paymentService.getAllPaymentsWithPatients();
//    }
//
//    // Assigner un paiement à un patient
//    @PutMapping("/{paymentId}/assign-to-patient/{patientId}")
//    public ResponseEntity<Payment> assignPaymentToPatient(
//            @PathVariable Long paymentId, @PathVariable Long patientId) {
//        Payment updatedPayment = paymentService.assignPaymentToPatient(paymentId, patientId);
//        if (updatedPayment != null) {
//            return ResponseEntity.ok(updatedPayment);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping("/create-with-patient/{patientId}")
    public ResponseEntity<Payment> createPaymentWithPatient(
            @PathVariable Long patientId, @RequestBody Payment payment) {
        Payment savedPayment = paymentService.createPaymentWithPatient(patientId, payment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
    @GetMapping("/total-by-patient")
    public ResponseEntity<List<Map<String, Object>>> getTotalPaymentsByPatient() {
        List<Map<String, Object>> totalPayments = paymentService.findTotalPaymentsByPatient();
        return new ResponseEntity<>(totalPayments, HttpStatus.OK);
    }
    @GetMapping("/total-by-patient/{patientId}")
    public ResponseEntity<Double> getTotalPaymentByPatientId(@PathVariable Long patientId) {
        Double totalPayment = paymentService.findTotalPaymentByPatientId(patientId);
        if (totalPayment != null) {
            return new ResponseEntity<>(totalPayment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0.0, HttpStatus.OK);  // Renvoie 0 si le patient n'a aucun paiement
        }
    }

    @GetMapping("/day/{date}")
    public List<Payment> getPaymentsForDay(@PathVariable String date) {
        return paymentService.getPaymentsForDay(LocalDate.parse(date));
    }

    @GetMapping("/week/{date}")
    public List<Payment> getPaymentsForWeek(@PathVariable String date) {
        return paymentService.getPaymentsForWeek(LocalDate.parse(date));
    }

    @GetMapping("/month/{date}")
    public List<Payment> getPaymentsForMonth(@PathVariable String date) {
        return paymentService.getPaymentsForMonth(LocalDate.parse(date));
    }
}
