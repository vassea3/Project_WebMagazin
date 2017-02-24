/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.dao.intf.BuyerIntf;
import com.mycompany.model.Buyer;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Taniusha
 */
@Repository
public class BuyerImpl implements BuyerIntf {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public void save(Buyer buyer) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(buyer);
    }

    @Override
    public void delete(Buyer buyer) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(buyer);
    }

    @Override
    public List<Buyer> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        Query serch = session.createQuery("from Buyer");
        return serch.list();
    }

    @Override
    public Buyer findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query serch = session.createQuery("from Buyer where id like '" + id + "'");
        return (Buyer) serch.uniqueResult();
    }

}
