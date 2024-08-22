package kh.houssem.just_smile.Interfaces;

import kh.houssem.just_smile.Entities.FinancialSummary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FinancialSummaryService {

    FinancialSummary getSummaryForDay(LocalDate date) ;
    FinancialSummary getSummaryForWeek(LocalDate date) ;
    FinancialSummary getSummaryForMonth(LocalDate date) ;
    FinancialSummary getSummaryForPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime);


    }
