/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haise.restaurentmanager.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hungnguyen
 */
public abstract class Table {
    private String tableId;
    private boolean isAvailable = true;
    private List<DetailedOrder> detailedOrders = new ArrayList<>();
    
    public Table(String tableId){
        this.tableId = tableId;
    }
    
    public void doingOrder(List<DetailedOrder> newOrders){
        this.detailedOrders = newOrders;
        this.isAvailable = false;
    }
    
    public String getTableId() {return tableId;}
    public boolean isAvailable() {return isAvailable;}
    public List<DetailedOrder> getDetailedOrders(){return detailedOrders; }
    
    public boolean setAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
        return this.isAvailable;
    }
    public abstract double calculateTotalPrice();
}
