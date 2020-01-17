package ru.vartanyan.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vartanyan.entities.Counteragent;
import ru.vartanyan.entities.Invoice;
import ru.vartanyan.entities.repositories.CounteragentRepos;

import java.util.LinkedList;
import java.util.List;

@Service
public class CounteragentService {
    @Autowired
    CounteragentRepos counteragentRepos;
    @Autowired
    InvoiceService invoiceService;

    public void save(Counteragent counteragent){
        counteragentRepos.save(counteragent);
    }
    public List<Counteragent> findAll(){
        List<Counteragent> counteragents = new LinkedList<>();
        for(Counteragent counteragent : counteragentRepos.findAll())
            counteragents.add(counteragent);
        return counteragents;
    }
    public boolean deleteById(Long id){
        if(!counteragentRepos.existsById(id))
            return false;
        else {
            invoiceService.deleteInvoiceByIdCounteragent(id);
            counteragentRepos.deleteById(id);
            return true;
        }
    }

    public Counteragent findById(Long id) {
        return counteragentRepos.findById(id).get();
    }
}
