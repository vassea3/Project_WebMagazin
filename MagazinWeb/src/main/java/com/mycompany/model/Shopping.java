package com.mycompany.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shopping")
public class Shopping {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shoppingId;
    private String modelul;
    private String quantity;

    public Shopping() {
    }
    
    public Shopping(int shoppingId, String modelul, String quantity) {
        this.shoppingId = shoppingId;
        this.modelul = modelul;
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(int shoppingId) {
        this.shoppingId = shoppingId;
    }

    public String getModelul() {
        return modelul;
    }

    public void setModelul(String modelul) {
        this.modelul = modelul;
    }

    @Override
    public String toString() {
        return "Shopping{" + "shoppingId=" + shoppingId + ", modelul=" + modelul + ", quantity=" + quantity + '}';
    }
    
    
}
