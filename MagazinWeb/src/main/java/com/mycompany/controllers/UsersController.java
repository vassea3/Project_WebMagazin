package com.mycompany.controllers;

import com.mycompany.model.Buyer;
import com.mycompany.model.Products;
import com.mycompany.model.Shopping;
import com.mycompany.model.Users;
import com.mycompany.service.intf.BuyerServiceIntf;
import com.mycompany.service.intf.ProductsServiceIntf;
import com.mycompany.service.intf.UsersServiceIntf;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

    @Autowired(required = true)
    @Qualifier(value = "usersService")
    UsersServiceIntf usersServiceIntf;
    @Autowired(required = true)
    @Qualifier(value = "buyerService")
    BuyerServiceIntf buyerServiceIntf;
    @Autowired(required = true)
    @Qualifier(value = "productsService")
    ProductsServiceIntf productsServiceIntf;

    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    public String register(@ModelAttribute Users users, Model model) {
        if (!usersServiceIntf.findAllUsernames().contains(users.getUsername())) {
            System.out.println("bun");
            if (users.getId() == 0) {

                this.usersServiceIntf.save(users);
            } else {

                this.usersServiceIntf.save(users);
            }
        } else {
            System.out.println("rau");
        }
        return "redirect:/login?success=true";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        model.addAttribute(new Users());
        return "login";
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String buy(@RequestParam("nume") String nume, @RequestParam("phone") String phone,
            @RequestParam("quantity") String quantity[], @RequestParam("modelul") String modelul[],
            HttpServletRequest request, Model model) {
        List<Shopping> shoppingList = new ArrayList<Shopping>();
        for (int i = 0; i < modelul.length; i++) {
            Shopping shopping = new Shopping();
            shopping.setModelul(modelul[i]);
            if (Integer.parseInt(quantity[i]) == 1) {
                shopping.setQuantity(quantity[i] + " piece");
            } else {
                shopping.setQuantity(quantity[i] + " pieces");
            }
            shoppingList.add(shopping);
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        DateFormat tf = new SimpleDateFormat("HH:mm");
        Date dateobj = new Date();
        Date dateobj1 = new Date();
        String data = df.format(dateobj);
        String ora = tf.format(dateobj1);

        Buyer buyer = new Buyer();
        buyer.setNume(nume);
        buyer.setPhone(phone);
        buyer.setData(data);
        buyer.setOra(ora);
        buyer.setShoppingList(shoppingList);

        buyerServiceIntf.save(buyer);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/commands", method = RequestMethod.GET)
    public String showComands(Model model,HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            page = "redirect:/login";
        } else {
            page = "cmsViewCommands"; 
            List<Buyer> buyerList = buyerServiceIntf.findAll();
        Map<Buyer, List<Map<Products, String>>> buyerMap = new HashMap<Buyer, List<Map<Products, String>>>();
        for (Buyer buyer : buyerList) {
            List<Map<Products, String>> listMap = new ArrayList<Map<Products, String>>();
            for (Shopping shopping : buyer.getShoppingList()) {
                Map<Products, String> buyMap = new HashMap<Products, String>();
                Products prod = productsServiceIntf.findProductByModel(shopping.getModelul());
                if (prod != null) {
                    buyMap.put(prod, shopping.getQuantity());
                    listMap.add(buyMap);
                }
            }
            buyerMap.put(buyer, listMap);
        }
        model.addAttribute("buyerList", buyerMap);
        }
        return page;
    }

    @RequestMapping("/remove/{id}")
    public String removeMessage(@PathVariable("id") int id) {
        Buyer buyer = this.buyerServiceIntf.findById(id);
        this.buyerServiceIntf.delete(buyer);
        return "redirect:/commands";
    }

}
