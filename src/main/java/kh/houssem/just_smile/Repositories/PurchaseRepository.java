package kh.houssem.just_smile.Repositories;

import kh.houssem.just_smile.Entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByDateBetween(Date from, Date from1);
    @Query("SELECT p FROM Purchase p WHERE p.date BETWEEN :startDate AND :endDate")
    List<Purchase> findPurchasesByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
