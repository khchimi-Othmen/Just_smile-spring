package kh.houssem.just_smile.Repositories;

import kh.houssem.just_smile.Entities.FinancialSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialSummaryRepository extends JpaRepository<FinancialSummary, Long> {
}
