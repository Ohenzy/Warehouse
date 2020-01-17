package ru.vartanyan.controllers;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.vartanyan.entities.*;
import ru.vartanyan.entities.loginin.Role;
import ru.vartanyan.entities.loginin.User;
import ru.vartanyan.entities.repositories.*;
import ru.vartanyan.entities.services.*;
import ru.vartanyan.json_parser.JsonInList;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Controller
public class WebController {

    private  String error;
    @Autowired
    private InvoiceService invoices;
    @Autowired
    private ProductService products;
    @Autowired
    private UnitRepos units;
    @Autowired
    private CounteragentService counteragents;
    @Autowired
    private WarehouseRepos warehouses;
    @Autowired
    private ChangeHistoryRepos history;
    @Autowired
    private UserService users;



    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
    @GetMapping("/registration")
    public String regView() {
        return "registration";
    }
    @PostMapping("/registration")
    public RedirectView registration(@RequestParam String username,@RequestParam String password) {
        if(users.isEmpty()) //Если в базе нет пользователей то создасться администратор
            users.save(new User(username,password,true,Collections.singleton(Role.ADMIN)));
        else{
            if(!users.save(new User(username,password,true,Collections.singleton(Role.USER)))){
                error = "Такой логин уже имеется";
                return new RedirectView("/error-change");
            }
        }
        return new RedirectView("/login");
    }

    @GetMapping("/")
    public String homeView(Model model) {
        model.addAttribute("admin",Role.ADMIN);
        model.addAttribute("role",users.getRoleByName(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "home.html";
    }

    @GetMapping("/users")
    public String usersView(Model model) {
        model.addAttribute("users", users.findAllBut(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "users";
    }
    @PostMapping("/delete-user")
    public RedirectView userDelete(@RequestParam()Long id_delete){
        if(!users.deleteById(id_delete)){
            error = "В базе нет такой записи";
            return new RedirectView("/error-change");
        }
        return new RedirectView("/users");
    }
    @PostMapping("/change-user")
    public RedirectView userChange(@RequestParam()Long id_change){
        if(!users.swapTypeById(id_change)){
            error = "В базе нет такой записи";
            return new RedirectView("/error-change");
        }
        return new RedirectView("/users");
    }

    @GetMapping("/database")
    public String databaseView() {
        return "database";
    }

    @GetMapping("/database/product")
    public String productView(Model model) {
        model.addAttribute("products", products.findAll());
        return "database/product";
    }


    @GetMapping("/database/unit")
    public String unitView(Model model){
        model.addAttribute("units",units.findAll());
        return "database/unit";
    }

    @PostMapping("/add-unit")
    public RedirectView unitAdd(@RequestParam String name){
        units.save(new Unit(name));
        return new RedirectView("/database/unit");
    }

    @PostMapping("/delete-unit")
    public RedirectView unitDelete(@RequestParam()Long id_delete){
        if(!units.existsById(id_delete)){
            error = "В базе нет такой записи";
            return new RedirectView("/error-change");
        }
        else units.deleteById(id_delete);
        return new RedirectView("/database/unit");
    }


    @GetMapping("/database/counteragent")
    public String counterpartyView(Model model){
        model.addAttribute("counteragents", counteragents.findAll());
        return "database/counteragent";
    }

    @PostMapping("/add-counteragent")
    public RedirectView counterpartyAdd(@RequestParam String name, @RequestParam String nameDirector,
                                        @RequestParam String phone, @RequestParam  String address,
                                        @RequestParam String email, @RequestParam String INN,
                                        @RequestParam  String OGRN){
        counteragents.save(new Counteragent(name,nameDirector,phone,address,email,INN,OGRN));
        return new RedirectView("/database/counteragent");
    }

    @PostMapping("/delete-counteragent")
    public RedirectView counteragentDelete(@RequestParam()Long id_delete){
        counteragents.deleteById(id_delete);
        return new RedirectView("/database/counteragent");
    }



    @GetMapping("/database/warehouse")
    public String warehouseView(Model model){
        model.addAttribute("warehouses", warehouses.findAll());
        return "database/warehouse";
    }

    @PostMapping("/add-warehouse")
    public RedirectView warehouseAdd(@RequestParam String name,@RequestParam String address,@RequestParam String phone){
        warehouses.save(new Warehouse(name,address,phone));
        return new RedirectView("/database/warehouse");
    }

    @PostMapping("/delete-warehouse")
    public RedirectView warehouseDelete(@RequestParam Long id_delete){
        if(!warehouses.existsById(id_delete)){
            error = "В базе нет такой записи";
            return new RedirectView("/error-change");
        }
        else warehouses.deleteById(id_delete);

        return new RedirectView("/database/warehouse");
    }


    @GetMapping("/operation")
    public String operationView(Model model){
        model.addAttribute("date_value", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        model.addAttribute("counteragents", counteragents.findAll());
        model.addAttribute("warehouses", warehouses.findAll());
        model.addAttribute("units", units.findAll());
        model.addAttribute("parishInvoices", invoices.findAllByType("приход"));
        model.addAttribute("salesInvoices", invoices.findAllByType("расход"));
        model.addAttribute("products", products.findAll());

        return "operation";
    }

    @PostMapping("/delete-invoice")
    public RedirectView parishInvoiceDelete(@RequestParam Long id_delete){
        if(!invoices.existsById(id_delete)){
            error = "В базе нет такой записи";
            return new RedirectView("/error-change");
        }
        else invoices.deleteById(id_delete);
        return new RedirectView("/operation");
    }

    @PostMapping("/add-invoice")
    public RedirectView salesInvoiceAdd(@RequestParam() String date, @RequestParam Long counteragent,
                                        @RequestParam Long warehouse, @RequestParam String jsonProducts) throws ParseException {
        JsonInList jsonInList = new JsonInList(jsonProducts);
        Counteragent cntag = counteragents.findById(counteragent);
        Warehouse whouse = warehouses.findById(warehouse).get();
        List<ChangeHistory> historyList = new LinkedList<>();
        List<PartProduct> partProducts = jsonInList.getListPartProducts(units);

        if (!products.saveAll(jsonInList.getListProducts(units))){
            error = "Не хватает товаров на складе";
            return new RedirectView("/error-change");
        }
        for(PartProduct partProduct : partProducts)
            historyList.add(new ChangeHistory(date,jsonInList.getType(),whouse,partProduct));
        history.saveAll(historyList);
        invoices.save(new Invoice( jsonInList.getType(), date, cntag,whouse,partProducts));

        return new RedirectView("/operation");
    }

    @GetMapping("/history")
    public String reportView(Model model){
        List<ChangeHistory> htry = new LinkedList<>();
        for(ChangeHistory changeHistory : history.findAll())
            htry.add(changeHistory);
        Collections.reverse(htry);
        model.addAttribute("history",htry);
        return "change_history";
    }
    @PostMapping("/delete-history")
    public RedirectView historyDelete(@RequestParam Long id_delete){
        if(!history.existsById(id_delete)){
            error = "В базе нет такой записи";
            return new RedirectView("/error-change");
        }
        history.deleteById(id_delete);
        return new RedirectView("/history");
    }

    @GetMapping("/error-change")
    public String errorAddingProduct(Model model){
        model.addAttribute("error", error);
        return "errors/error_change";
    }



}
