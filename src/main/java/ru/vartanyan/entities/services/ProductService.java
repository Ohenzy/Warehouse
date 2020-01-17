package ru.vartanyan.entities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vartanyan.entities.Product;
import ru.vartanyan.entities.repositories.ProductRepos;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


@Service
public class ProductService {

    private ProductRepos productRepos;
    @Autowired
    public ProductService(ProductRepos partProductRepos){
        this.productRepos = partProductRepos;
    }


    public boolean saveAll(List<Product> productList){
        ConcurrentLinkedQueue<Product> products = new ConcurrentLinkedQueue<>();
        products.addAll(productList);
        for(Product pdct : productRepos.findAll())
            for(Product product : products)
                if (pdct.getName().equals(product.getName()) && pdct.getUnit().equals(product.getUnit()) )
                    if ((pdct.getQuantity() + product.getQuantity()) > 0) { // проверка на отрицательные числа
                        product.setId(pdct.getId());
                        product.setQuantity(pdct.getQuantity() + product.getQuantity());
                    } else if ((pdct.getQuantity() + product.getQuantity()) == 0){
                        productRepos.deleteById(pdct.getId());
                        products.remove(product);
                    }
                    else if ((pdct.getQuantity() + product.getQuantity()) < 0)
                        return false;
        productRepos.saveAll(products);
        return true;
    }

    public List<Product> findAll(){
        List<Product> products = new LinkedList<>();
        for (Product product : productRepos.findAll())
            products.add(product);
        return products;
    }

}
