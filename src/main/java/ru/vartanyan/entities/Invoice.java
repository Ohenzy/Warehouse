package ru.vartanyan.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String date;
    @OneToOne()
    @JoinColumn(name = "counteragent_id", referencedColumnName = "id")
    private Counteragent counteragent;
    @OneToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private Warehouse warehouse;
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<PartProduct> partProducts;


    public Invoice(){

    }
    public Invoice(String type, String date, Counteragent counteragent, Warehouse warehouse,List<PartProduct> partProducts){
        this.type = type;
        this.date = date;
        this.counteragent = counteragent;
        this.warehouse = warehouse;
        this.partProducts = partProducts;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Counteragent getCounteragent() {
        return counteragent;
    }
    public void setCounteragent(Counteragent counteragent) {
        this.counteragent = counteragent;
    }
    public Warehouse getWarehouse() {
        return warehouse;
    }
    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
    public List<PartProduct> getPartProducts() {
        return partProducts;
    }
    public void setPartProducts(List<PartProduct> partProducts) {
        this.partProducts = partProducts;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}




