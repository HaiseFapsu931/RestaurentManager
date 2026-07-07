/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haise.restaurentmanager.core;

/**
 *
 * @author Hungnguyen
 */
public class TableVip1 extends Table {
    private final double serviceFee = 100000;
    
    public TableVip1(String tableId){
        super(tableId);
    }
    
    public double getServiceFee(){
        return serviceFee;
    }
}
