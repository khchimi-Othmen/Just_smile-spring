package kh.houssem.just_smile.Services;

import kh.houssem.just_smile.Entities.FinancialSummary;
import kh.houssem.just_smile.Entities.Payment;
import kh.houssem.just_smile.Entities.Purchase;
import kh.houssem.just_smile.Interfaces.FinancialSummaryService;
import kh.houssem.just_smile.Repositories.PaymentRepository;
import kh.houssem.just_smile.Repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@Service
public class FinancialSummaryServiceImpl implements FinancialSummaryService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public FinancialSummary getSummaryForDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return calculateSummary(startOfDay, endOfDay);
    }

    @Override
    public FinancialSummary getSummaryForWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

        return calculateSummary(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59));
    }

    @Override
    public FinancialSummary getSummaryForMonth(LocalDate date) {
        LocalDate startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        return calculateSummary(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59));
    }

    private FinancialSummary calculateSummary(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Payment> payments = paymentRepository.findAllByDateBetween(
                Date.from(startDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()),
                Date.from(endDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant())
        );

        List<Purchase> purchases = purchaseRepository.findAllByDateBetween(
                Date.from(startDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()),
                Date.from(endDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant())
        );

        double totalIncome = payments.stream().mapToDouble(Payment::getAmount).sum();
        double totalOutcome = purchases.stream().mapToDouble(Purchase::getAmount).sum();
        double netTotal = totalIncome - totalOutcome;  // Calcul de la différence entre revenus et dépenses

        FinancialSummary summary = new FinancialSummary();
        summary.setDate(Date.from(startDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()));
        summary.setTotalIncome(totalIncome);           // Somme des paiements reçus
        summary.setTotalOutcome(totalOutcome);         // Somme des achats (dépenses)
        summary.setNetTotal(netTotal);                 // Différence entre revenus et dépenses
        summary.setPayments(payments);
        summary.setPurchases(purchases);

        return summary;
    }
    @Override
    public FinancialSummary getSummaryForPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return calculateSummary(startDateTime, endDateTime);
    }


}
