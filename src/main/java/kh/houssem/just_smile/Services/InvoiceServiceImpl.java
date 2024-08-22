package kh.houssem.just_smile.Services;

import kh.houssem.just_smile.Entities.Invoice;
import kh.houssem.just_smile.Interfaces.InvoiceService;
import kh.houssem.just_smile.Repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> findAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> findInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Long invoiceId, Invoice invoice) {
        if (invoiceRepository.existsById(invoiceId)) {
            invoice.setInvoiceId(invoiceId);
            return invoiceRepository.save(invoice);
        }
        return null; // Vous pouvez lancer une exception si la facture n'existe pas
    }

    @Override
    public void deleteInvoice(Long invoiceId) {
        if (invoiceRepository.existsById(invoiceId)) {
            invoiceRepository.deleteById(invoiceId);
        } else {
            // Vous pouvez lancer une exception si la facture n'existe pas
        }
    }
}
