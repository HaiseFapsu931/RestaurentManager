/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haise.restaurentmanager.core;

/**
 *
 * @author Hungnguyen
 */
public class Table4 extends Table {
    public Table4(String tableId){
        super(tableId);
    }
    @Override
    public double calculateTotalPrice() {
        double total = 0;
        for (DetailedOrder order : getDetailedOrders()) {
            total += order.getDish().getPrice() * order.getAmount();
        }
        return total;
    }
}
