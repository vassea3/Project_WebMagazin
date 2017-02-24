/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.intf;

import com.mycompany.model.Buyer;
import java.util.List;

/**
 *
 * @author Taniusha
 */
public interface BuyerIntf {
    public void save(Buyer buyer);
    public void delete(Buyer buyer);
    public List<Buyer> findAll();
    public Buyer findById(int id);
    
}
