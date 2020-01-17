package ru.vartanyan.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vartanyan.entities.Invoice;

public interface InvoiceRepos extends CrudRepository<Invoice,Long> {
}
