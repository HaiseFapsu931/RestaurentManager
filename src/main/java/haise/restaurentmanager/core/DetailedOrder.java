/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haise.restaurentmanager.core;

/**
 *
 * @author Hungnguyen
 */
public class DetailedOrder {
    private Dish dish; // 
    private int amount;
    private String note;
    

    public DetailedOrder(Dish dish, int amount, String note){
        this.dish = dish;
        this.amount = amount; 
        this.note = note;     
    }
    
    public Dish getDish() { return dish; }
    public int getAmount() { return amount; }
    public String getNote() { return note; }
    public void setAmount(int amount){
        this.amount = amount;
    }
}
