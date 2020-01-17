package ru.vartanyan.json_parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.vartanyan.entities.PartProduct;
import ru.vartanyan.entities.Product;
import ru.vartanyan.entities.repositories.UnitRepos;
import java.util.LinkedList;
import java.util.List;


public class JsonInList {
    private final JSONArray nameProductList;
    private final JSONArray quantityList;
    private final JSONArray unitList;
    private final JSONArray priceList;
    private final String typeInvoice;



    public JsonInList(String jsonString) throws ParseException{
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonString);
        nameProductList = (JSONArray) jsonObject.get("nameProduct");
        quantityList = (JSONArray) jsonObject.get("quantity");
        unitList = (JSONArray) jsonObject.get("unit");
        priceList = (JSONArray) jsonObject.get("price");
        typeInvoice = (String) jsonObject.get("type");
    }

    public List<Product> getListProducts(UnitRepos units) {
        List<Product> productList = new LinkedList<>();

       for(int i = 0;i<nameProductList.size();i++) {
           productList.add(new Product(
                            nameProductList.get(i).toString(),
                            Long.parseLong(quantityList.get(i).toString()),
                            units.findById(Long.parseLong(unitList.get(i).toString())).get()
           ));
       }
        if(typeInvoice.equals("расход"))
            for(Product product : productList)
                product.setQuantity(product.getQuantity() * -1);

        return productList;
    }

    public List<PartProduct> getListPartProducts(UnitRepos units) {
        List<PartProduct> partProductsList = new LinkedList<>();
        for(int i = 0;i<nameProductList.size();i++) {
            partProductsList.add(new PartProduct(
                    nameProductList.get(i).toString(),
                    Long.parseLong(quantityList.get(i).toString()),
                    units.findById(Long.parseLong(unitList.get(i).toString())).get(),
                    Double.parseDouble(priceList.get(i).toString())) );
        }
        return partProductsList;
    }

    public String getType() {
        return typeInvoice;
    }
}
