/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haise.restaurentmanager.dto;

import java.util.List;

/**
 *
 * @author Hungnguyen
 */
public class OrderRequestDTO {
    private String tableId;
    private List<ItemOrderDTO> orders;

    public String getTableId() { return tableId; }
    public void setTableId(String tableId) { this.tableId = tableId; }

    public List<ItemOrderDTO> getOrders() { return orders; }
    public void setOrders(List<ItemOrderDTO> orders) { this.orders = orders; }

    // Class con tĩnh mô tả cấu trúc từng món ăn gửi lên từ JSON
    public static class ItemOrderDTO {
        private String dishId;
        private String dishName;
        private int amount;
        private String note;

        public String getDishId() { return dishId; }
        public void setDishId(String dishId) { this.dishId = dishId; }
        
        public String getDishName() { return dishName; }
        public void setDishName(String dishName) { this.dishName = dishName; }

        public int getAmount() { return amount; }
        public void setAmount(int amount) { this.amount = amount; }

        public String getNote() { return note; }
        public void setNote(String note) { this.note = note; }
    }
}

