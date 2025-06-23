package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbExeption;
import entities.Product;

public class ProductDAO {

    // @Override
    public int insert (Product product) {
        String sql = "INSERT INTO product_tb (namePro, pricePro, quantityPro) VALUES (?, ?, ?)";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, product.getName());
            st.setDouble(2, product.getPrice());
            st.setInt(3, product.getQuantity());
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID gerado
                } else {
                    throw new DbExeption("No ID generated for the product.");
                }
            }
        } catch (SQLException e) {
            throw new DbExeption("Error inserting product: " + e.getMessage());
        }
    }

    // @Override
    public void update(int oldProdID, Product newProduct) {
        String sql = "UPDATE product_tb SET namePro = ?, pricePro = ?, quantityPro = ? WHERE idPro = ?";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql)) {
            st.setString(1, newProduct.getName());
            st.setDouble(2, newProduct.getPrice());
            st.setInt(3, newProduct.getQuantity());
            st.setInt(4, oldProdID);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbExeption("Error updating product: " + e.getMessage());
        }
    }

    // @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM product_tb WHERE idPro = ?";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbExeption("Error deleting product: " + e.getMessage());
        }
    }

    // @Override
    public Product findByIdProducts(int id) {
        String sql = "SELECT * FROM product_tb WHERE idPro = ?";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateProduct(rs);
                }
            }
        } catch (SQLException e) {
            throw new DbExeption("Error finding product: " + e.getMessage());
        }
        return null;
    }

    // @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product_tb";
        List<Product> list = new ArrayList<>();
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(instantiateProduct(rs));
            }
        } catch (SQLException e) {
            throw new DbExeption("Error finding all products: " + e.getMessage());
        }
        return list;
    }

    private Product instantiateProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("idPro"),
            rs.getString("namePro"),
            rs.getDouble("pricePro"),
            rs.getInt("quantityPro")
        );
    }
}


