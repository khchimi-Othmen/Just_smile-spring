package kh.houssem.just_smile.Interfaces;

import kh.houssem.just_smile.Entities.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    List<Invoice> findAllInvoices();

    Optional<Invoice> findInvoiceById(Long invoiceId);

    Invoice saveInvoice(Invoice invoice);

    Invoice updateInvoice(Long invoiceId, Invoice invoice);

    void deleteInvoice(Long invoiceId);
}
