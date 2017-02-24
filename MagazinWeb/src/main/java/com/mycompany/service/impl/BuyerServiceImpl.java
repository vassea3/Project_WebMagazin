/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service.impl;

import com.mycompany.dao.intf.BuyerIntf;
import com.mycompany.model.Buyer;
import com.mycompany.service.intf.BuyerServiceIntf;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Taniusha
 */
@Service("buyerService")
public class BuyerServiceImpl implements BuyerServiceIntf {

    @Autowired
    private BuyerIntf buyerIntf;

    public void setBuyerIntf(BuyerIntf buyerIntf) {
        this.buyerIntf = buyerIntf;
    }

    @Override
    @Transactional
    public void save(Buyer buyer) {
        this.buyerIntf.save(buyer);
    }

    @Override
    @Transactional
    public void delete(Buyer buyer) {
        this.buyerIntf.delete(buyer);
    }

    @Override
    @Transactional
    public List<Buyer> findAll() {
        return this.buyerIntf.findAll();
    }

    @Override
    @Transactional
    public Buyer findById(int id) {
        return this.buyerIntf.findById(id);
    }

}
