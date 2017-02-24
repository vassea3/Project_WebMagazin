package com.mycompany.controllers;

import com.mycompany.model.Messages;
import com.mycompany.service.intf.MessagesServiceIntf;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessagesController {

    @Autowired(required = true)
    @Qualifier(value = "messageService")
    MessagesServiceIntf messageServiceIntf;

    @RequestMapping("/cmsViewMessages")
    public String showMessages(Model model, HttpServletRequest request) {
        String page = "";
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("user"));

        if (session.getAttribute("user") == null) {
            page = "redirect:/login";
        } else {
            model.addAttribute("messages", this.messageServiceIntf.findAll());
            page = "cmsViewMessages";
        }
        return page;
        
    }

    @RequestMapping("/delete/{id}")
    public String removeMessage(@PathVariable("id") int id) {
        Messages message = this.messageServiceIntf.findById(id);
        this.messageServiceIntf.delete(message);
        return "redirect:/cmsViewMessages";
    }
}
