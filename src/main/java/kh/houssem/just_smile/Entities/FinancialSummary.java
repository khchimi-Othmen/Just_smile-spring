package kh.houssem.just_smile.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "financial_summaries")
public class FinancialSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long summaryId;

    private Double totalIncome;  // Représente le total des paiements reçus
    private Double totalOutcome; // Représente le total des achats (dépenses)
    private Double netTotal;     // Différence entre totalIncome et totalOutcome
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "summary_id")
    @JsonIgnore
    private List<Payment> payments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "summary_id")
    @JsonIgnore
    private List<Purchase> purchases;
}
