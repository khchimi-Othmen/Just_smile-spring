package kh.houssem.just_smile.Interfaces;

import kh.houssem.just_smile.Entities.Purchase;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PurchaseService {

    List<Purchase> findAllPurchases();

    Optional<Purchase> findPurchaseById(Long purchaseId);

    Purchase savePurchase(Purchase purchase);

    Purchase updatePurchase(Long purchaseId, Purchase purchase);

    void deletePurchase(Long purchaseId);
    List<Purchase> getPurchasesByDateRange(Date startDate, Date endDate);

}
