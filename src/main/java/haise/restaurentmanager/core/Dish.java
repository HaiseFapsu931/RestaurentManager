/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haise.restaurentmanager.core;

/**
 *
 * @author Hungnguyen
 */
public class Dish {
    private String dishId;
    private String dishName;
    private double price;
    
    public Dish(String dishId, String dishName, double price){
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
    }
    
    public String getDishId() { return dishId; }
    public String getDishName() { return dishName; }
    public double getPrice() { return price; }
}
