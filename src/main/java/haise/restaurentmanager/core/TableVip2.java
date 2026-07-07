/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haise.restaurentmanager.core;

/**
 *
 * @author Hungnguyen
 */
public class TableVip2 extends Table {
    private final double serviceFee = 500000;
    
    public TableVip2(String tableId){
        super(tableId);
    }
    
    public double getServiceFee(){
        return serviceFee;
    }
    @Override
    public double calculateTotalPrice() {
        double total = 0;
        for (DetailedOrder order : getDetailedOrders()) {
            total += order.getDish().getPrice() * order.getAmount();
        }
        total +=  this.serviceFee;
        return total;
    }
}
