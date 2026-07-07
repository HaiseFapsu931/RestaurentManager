/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package haise.restaurentmanager.controller;

import haise.restaurentmanager.core.*;
import haise.restaurentmanager.dto.OrderRequestDTO;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hungnguyen
 */
@RestController
@RequestMapping("/api")
public class OrderController {
    private List<Dish> menuTinh = new ArrayList<>();
    private List<Table> tableData = new ArrayList<>();
    
    public OrderController(){
        menuTinh.add(new Dish("D001", "bo sot vang", 65000));
        menuTinh.add(new Dish("D002", "com", 30000));
        menuTinh.add(new Dish("D003", "mi cay", 45000));
        
        tableData.add(new Table2("T2_001"));
        tableData.add(new Table4("T4_001"));
        tableData.add(new TableOutdoor("OUT_001"));
        tableData.add(new TableVip1("VIP1_001"));
        tableData.add(new TableVip2("VIP2_001"));
        tableData.add(new TableVip3("VIP3_001"));
    }
    @PostMapping("/order")
    public Table handleOrder(@RequestBody OrderRequestDTO inputJson) {

        // 1. Tìm bàn
        Table currentTable = null;
        for (Table t : tableData) {
            if (t.getTableId().equalsIgnoreCase(inputJson.getTableId())) {
                currentTable = t;
                break;
            }
        }
        if (currentTable == null) {
            throw new RuntimeException("Bàn không tồn tại!");
        }

        currentTable.setAvailable(false);

        if (currentTable.getDetailedOrders() == null) {
            currentTable.doingOrder(new ArrayList<>());
        }
        List<DetailedOrder> currentOrdersOfTable = currentTable.getDetailedOrders();

        for (OrderRequestDTO.ItemOrderDTO item : inputJson.getOrders()) {
            Dish foundDish = null;

            if (item.getDishId() != null && !item.getDishId().trim().isEmpty()) {
                for (Dish d : menuTinh) {
                    if (d.getDishId().equalsIgnoreCase(item.getDishId().trim())) {
                        foundDish = d;
                        break;
                    }
                }
            }

            if (foundDish == null && item.getDishName() != null && !item.getDishName().trim().isEmpty()) {
                for (Dish d : menuTinh) {
                    if (d.getDishName().equalsIgnoreCase(item.getDishName().trim())) {
                        foundDish = d;
                        break;
                    }
                }
            }

            if (foundDish != null) {
                boolean isExisted = false;


                for (DetailedOrder existingOrder : currentOrdersOfTable) {
                    String itemNote = item.getNote() != null ? item.getNote().trim() : "";
                    String existingNote = existingOrder.getNote() != null ? existingOrder.getNote().trim() : "";

     
                    if (existingOrder.getDish().getDishId().equalsIgnoreCase(foundDish.getDishId()) 
                            && existingNote.equalsIgnoreCase(itemNote)) {

                        existingOrder.setAmount(existingOrder.getAmount() + item.getAmount());
                        isExisted = true;
                        break;
                    }
                }

                
                if (!isExisted) {
                    currentOrdersOfTable.add(new DetailedOrder(foundDish, item.getAmount(), item.getNote()));
                }
            }
        }

        return currentTable;
        }
    @GetMapping("/checkout/{tableId}")
    public String checkoutTable(@PathVariable String tableId) {
        Table currentTable = null;
        for (Table t : tableData) {
            if (t.getTableId().equalsIgnoreCase(tableId)){
                currentTable = t;
                break;
            }
        }
        if (currentTable == null) {
            return "Bàn không tồn tại!";
        }
        if (currentTable.isAvailable()) {
            return "Bàn này hiện đang trống, chưa có hoá đơn cần thanh toán";
        }
        double finalBill = currentTable.calculateTotalPrice();
        return "HÓA ĐƠN THANH TOÁN BÀN: " + currentTable.getTableId() + "\n"
            + "Trạng thái: Đang bận\n"
            + "Số lượng món đã gọi: " + currentTable.getDetailedOrders().size() + "\n"
            + "TỔNG TIỀN PHẢI TRẢ: " + finalBill + " VND";       
    }
    @PostMapping("/pay/{tableId}")
    public String payTable(@PathVariable String tableId) {
        Table currentTable = null;
        for (Table t : tableData) {
            if (t.getTableId().equalsIgnoreCase(tableId)) {
                currentTable = t;
                break;
            }
        }

        if (currentTable == null) return "Bàn không tồn tại!";
        if (currentTable.isAvailable()) return "Bàn này đã trống sẵn rồi, không cần thanh toán.";

        // 1. Tính tiền lần cuối để thông báo
        double finalBill = currentTable.calculateTotalPrice();

        // 2. Dọn sạch danh sách món đã gọi trên bàn
        currentTable.getDetailedOrders().clear();

        // 3. Chuyển trạng thái bàn về Trống để đón khách mới
        currentTable.setAvailable(true);

        return "Thanh toán thành công bàn: " + currentTable.getTableId() + "\n"
                + "Tổng số tiền đã thu: " + finalBill + " VND\n"
                + "Trạng thái bàn hiện tại: Trống (Sẵn sàng đón khách mới!)";
    }
}