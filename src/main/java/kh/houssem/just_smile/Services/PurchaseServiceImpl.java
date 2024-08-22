package kh.houssem.just_smile.Services;

import kh.houssem.just_smile.Entities.Purchase;
import kh.houssem.just_smile.Interfaces.PurchaseService;
import kh.houssem.just_smile.Repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> findPurchaseById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId);
    }

    @Override
    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Override
    public Purchase updatePurchase(Long purchaseId, Purchase purchase) {
        if (purchaseRepository.existsById(purchaseId)) {
            purchase.setPurchaseId(purchaseId);
            return purchaseRepository.save(purchase);
        }
        return null; // Vous pouvez lancer une exception si l'achat n'existe pas
    }

    @Override
    public void deletePurchase(Long purchaseId) {
        if (purchaseRepository.existsById(purchaseId)) {
            purchaseRepository.deleteById(purchaseId);
        } else {
            // Vous pouvez lancer une exception si l'achat n'existe pas
        }
    }
    @Override
    public List<Purchase> getPurchasesByDateRange(Date startDate, Date endDate) {
        return purchaseRepository.findPurchasesByDateRange(startDate, endDate);
    }
}
