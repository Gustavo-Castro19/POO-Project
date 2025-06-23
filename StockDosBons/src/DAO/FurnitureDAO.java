package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbExeption;
import entities.Furniture;

public class FurnitureDAO extends ProductDAO {
      public void insert(Furniture furniture) {
        int pro_id = super.insert(furniture);
        String sql = "INSERT INTO furniture_tb (pro_id, heightFur, widthFur, materialFur) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, pro_id);
            st.setDouble(2, furniture.getHeight());
            st.setDouble(3, furniture.getWidth());
            st.setString(4, furniture.getMaterial());
            st.executeUpdate();
            System.out.println("Furniture inserted successfully");
        } catch (SQLException e) {
            throw new DbExeption("Error inserting furniture: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM furniture_tb WHERE pro_id = ?";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
            super.deleteById(id);
        } catch (SQLException e) {
            throw new DbExeption("Error deleting furniture: " + e.getMessage());
        }
    }

    public Furniture findById(int id) {
        String sql = "SELECT p.*, f.* FROM product_tb p INNER JOIN furniture_tb f ON p.idPro = f.pro_id WHERE p.idPro = ?";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateProduct(rs);
                }
            }
        } catch (SQLException e) {
            throw new DbExeption("Error finding furniture: " + e.getMessage());
        }
        return null;
    }

    public List<Furniture> findAllFurniture() {
        String sql = "SELECT p.*, f.* FROM product_tb p INNER JOIN furniture_tb f ON p.idPro = f.pro_id";
        List<Furniture> list = new ArrayList<>();
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(instantiateProduct(rs));
            }
        } catch (SQLException e) {
            throw new DbExeption("Error finding all furniture: " + e.getMessage());
        }
        return list;
    }    public void update(int oldFurnitureID, Furniture newFurniture) {
        super.update(oldFurnitureID, newFurniture);

        String sql = "UPDATE furniture_tb SET heightFur = ?, widthFur = ?, materialFur = ? WHERE pro_id = ?";

        try (PreparedStatement pst = DB.getConnection().prepareStatement(sql)) {
            pst.setDouble(1, newFurniture.getHeight());
            pst.setDouble(2, newFurniture.getWidth());
            pst.setString(3, newFurniture.getMaterial());
            pst.setInt(4, oldFurnitureID);

            pst.executeUpdate();
            System.out.println("Furniture updated successfully");
        } catch (SQLException e) {
            throw new DbExeption("Error updating furniture: " + e.getMessage());
        }
    }    private Furniture instantiateProduct(ResultSet rs) throws SQLException {
        return new Furniture(
            rs.getInt("idPro"),
            rs.getString("namePro"),
            rs.getDouble("pricePro"),
            rs.getInt("quantityPro"),
            rs.getDouble("heightFur"),
            rs.getDouble("widthFur"),
            rs.getString("materialFur")
        );
    }
}
