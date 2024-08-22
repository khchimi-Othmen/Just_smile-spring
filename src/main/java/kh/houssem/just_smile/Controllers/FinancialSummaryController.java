package kh.houssem.just_smile.Controllers;

import kh.houssem.just_smile.Entities.FinancialSummary;
import kh.houssem.just_smile.Interfaces.FinancialSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/financial-summaries")
public class FinancialSummaryController {

    @Autowired
    private FinancialSummaryService financialSummaryService;

    @GetMapping("/day/{date}")
    public FinancialSummary getSummaryForDay(@PathVariable String date) {
        return financialSummaryService.getSummaryForDay(LocalDate.parse(date));
    }

    @GetMapping("/week/{date}")
    public FinancialSummary getSummaryForWeek(@PathVariable String date) {
        return financialSummaryService.getSummaryForWeek(LocalDate.parse(date));
    }

    @GetMapping("/month/{date}")
    public FinancialSummary getSummaryForMonth(@PathVariable String date) {
        return financialSummaryService.getSummaryForMonth(LocalDate.parse(date));
    }
    @GetMapping("/period")
    public FinancialSummary getSummaryForPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        return financialSummaryService.getSummaryForPeriod(startDateTime, endDateTime);
    }

}
