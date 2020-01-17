package ru.vartanyan.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vartanyan.entities.Counteragent;
import ru.vartanyan.entities.Invoice;
import ru.vartanyan.entities.repositories.InvoiceRepos;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class InvoiceService {

    private final InvoiceRepos invoiceRepos;

    @Autowired
    public InvoiceService(InvoiceRepos invoiceRepos){
        this.invoiceRepos = invoiceRepos;
    }


    public Optional<Invoice> save(Invoice invoice){
        return Optional.of(invoiceRepos.save(invoice));
    }

    public boolean deleteById(Long id){
        if(!invoiceRepos.existsById(id))
            return false;
        else {
            invoiceRepos.deleteById(id);
            return true;
        }


    }
    public boolean deleteInvoiceByIdCounteragent(Long counteragent_id){
        boolean exist = false;
        for(Invoice invoice : invoiceRepos.findAll())
            if(invoice.getCounteragent().getId() == counteragent_id)
                if(this.deleteById(invoice.getId()))
                    exist = true;

        return exist;
    }

    public Optional<Invoice> findById(Long id){
        return invoiceRepos.findById(id);
    }

    public List<Invoice> findAll(){
        List<Invoice> invoices = new ArrayList<>();
        for (Invoice invoice : invoiceRepos.findAll())
            invoices.add(invoice);

        return invoices;
    }

    public boolean existsById(Long id){
        return invoiceRepos.existsById(id);
    }

    public List<Invoice> findAllByType(String type){
        List<Invoice> invoiceList = new ArrayList<>();
        for(Invoice invoice : invoiceRepos.findAll())
            if(invoice.getType().equals(type))
                invoiceList.add(invoice);

        return invoiceList;
    }



}
