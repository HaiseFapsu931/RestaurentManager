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

        // Chuyển trạng thái bàn thành bận
        currentTable.setAvailable(false);

        // 2. Lấy trực tiếp danh sách món ĐANG CÓ SẴN trên bàn này ra để xử lý
        // Nếu bàn chưa có món nào (mới vào), hãy đảm bảo danh sách không bị null
        if (currentTable.getDetailedOrders() == null) {
            currentTable.doingOrder(new ArrayList<>());
        }
        List<DetailedOrder> currentOrdersOfTable = currentTable.getDetailedOrders();

        // 3. Duyệt danh sách món khách vừa gửi lên từ Postman
        for (OrderRequestDTO.ItemOrderDTO item : inputJson.getOrders()) {
            Dish foundDish = null;

            // Tìm món ăn trong Menu theo ID
            if (item.getDishId() != null && !item.getDishId().trim().isEmpty()) {
                for (Dish d : menuTinh) {
                    if (d.getDishId().equalsIgnoreCase(item.getDishId().trim())) {
                        foundDish = d;
                        break;
                    }
                }
            }

            // Dự phòng tìm theo tên nếu thiếu ID
            if (foundDish == null && item.getDishName() != null && !item.getDishName().trim().isEmpty()) {
                for (Dish d : menuTinh) {
                    if (d.getDishName().equalsIgnoreCase(item.getDishName().trim())) {
                        foundDish = d;
                        break;
                    }
                }
            }

            // Nếu món hợp lệ, tiến hành đối chiếu gộp món
            if (foundDish != null) {
                boolean isExisted = false;

                // Duyệt danh sách ĐÃ CÓ của bàn để check trùng
                for (DetailedOrder existingOrder : currentOrdersOfTable) {
                    String itemNote = item.getNote() != null ? item.getNote().trim() : "";
                    String existingNote = existingOrder.getNote() != null ? existingOrder.getNote().trim() : "";

                    // Trùng mã món VÀ trùng ghi chú -> Cộng dồn
                    if (existingOrder.getDish().getDishId().equalsIgnoreCase(foundDish.getDishId()) 
                            && existingNote.equalsIgnoreCase(itemNote)) {

                        existingOrder.setAmount(existingOrder.getAmount() + item.getAmount());
                        isExisted = true;
                        break;
                    }
                }

                // Nếu món này chưa từng có trên bàn (hoặc trùng món nhưng khác ghi chú) -> Thêm mới dòng
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
}