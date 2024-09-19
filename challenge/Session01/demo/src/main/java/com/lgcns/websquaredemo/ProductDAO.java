package com.lgcns.websquaredemo;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class ProductDAO {
    private String url = "jdbc:sqlite:mydatabase.db";

    public List<Product> getAllProducts(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products where name like ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement  stmt = conn.prepareStatement(sql)){
            if (name == null ) {
                name = "";
            }
            // secure sql injection
            stmt.setString(1, "%"+name+"%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO products(name, price) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}