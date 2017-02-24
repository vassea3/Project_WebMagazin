
package com.mycompany.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Taniusha
 */
@Entity
@Table(name="buyer")
public class Buyer {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buyerId;
    private String nume;
    private String phone;
    private String ora;
    private String data;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "BuyerShopping", joinColumns = @JoinColumn(name = "shoppingId"),
            inverseJoinColumns = @JoinColumn(name = "buyerId"))
    private List<Shopping> shoppingList;

    public Buyer() {
    }

    public Buyer(int buyerId, String nume, String phone, String ora, String data, List<Shopping> shoppingList) {
        this.buyerId = buyerId;
        this.nume = nume;
        this.phone = phone;
        this.ora = ora;
        this.data = data;
        this.shoppingList = shoppingList;
    }

    public List<Shopping> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Shopping> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Buyer{" + "buyerId=" + buyerId + ", nume=" + nume + ", phone=" + phone + ", ora=" + ora + ", data=" + data + ", shoppingList=" + shoppingList + '}';
    }

 
}
