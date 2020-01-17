package ru.vartanyan.entities;

import javax.persistence.*;


@Entity
public class ChangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String typeIvoice;
    @OneToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    private  Warehouse warehouse;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "part_product_id", referencedColumnName = "id")
    private PartProduct partProduct;

    public ChangeHistory() {
    }

    public ChangeHistory(String  date, String typeIvoice, Warehouse warehouse, PartProduct partProduct) {
        this.date = date;
        this.typeIvoice = typeIvoice;
        this.warehouse = warehouse;
        this.partProduct = partProduct;
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

    public String getTypeIvoice() {
        return typeIvoice;
    }

    public void setTypeIvoice(String typeIvoice) {
        this.typeIvoice = typeIvoice;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public PartProduct getPartProduct() {
        return partProduct;
    }

    public void setPartProduct(PartProduct partProduct) {
        this.partProduct = partProduct;
    }
}
