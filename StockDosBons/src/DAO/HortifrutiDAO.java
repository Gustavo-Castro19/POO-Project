package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbExeption;
import entities.Hortifruti;

public class HortifrutiDAO extends ProductDAO {
    
    public void insert(Hortifruti hortifruti) {
        int pro_id = super.insert(hortifruti);
        String sql = "INSERT INTO hortifruti_tb (pro_id, middleWeightHor) VALUES (?, ?)";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, pro_id);
            st.setDouble(2, hortifruti.getMiddleWeight());
            st.executeUpdate();
            System.out.println("Hortifruti inserted successfully");
        } catch (SQLException e) {
            throw new DbExeption("Error inserting hortifruti: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM hortifruti_tb WHERE pro_id = ?";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
            super.deleteById(id);
        } catch (SQLException e) {
            throw new DbExeption("Error deleting hortifruti: " + e.getMessage());
        }
    }

    public Hortifruti findById(int id) {
        String sql = "SELECT p.*, h.* FROM product_tb p INNER JOIN hortifruti_tb h ON p.idPro = h.pro_id WHERE p.idPro = ?";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateProduct(rs);
                }
            }
        } catch (SQLException e) {
            throw new DbExeption("Error finding hortifruti: " + e.getMessage());
        }
        return null;
    }

    public List<Hortifruti> findAllHortifruti() {
        String sql = "SELECT p.*, h.* FROM product_tb p INNER JOIN hortifruti_tb h ON p.idPro = h.pro_id";
        List<Hortifruti> list = new ArrayList<>();
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(instantiateProduct(rs));
            }
        } catch (SQLException e) {
            throw new DbExeption("Error finding all hortifruti: " + e.getMessage());
        }
        return list;
    }

    public void update(int oldHortifrutiID, Hortifruti newHortifruti) {
        super.update(oldHortifrutiID, newHortifruti);

        String sql = "UPDATE hortifruti_tb SET middleWeightHor = ? WHERE pro_id = ?";

        try (PreparedStatement pst = DB.getConnection().prepareStatement(sql)) {
            pst.setDouble(1, newHortifruti.getMiddleWeight());
            pst.setInt(2, oldHortifrutiID);

            pst.executeUpdate();
            System.out.println("Hortifruti updated successfully");
        } catch (SQLException e) {
            throw new DbExeption("Error updating hortifruti: " + e.getMessage());
        }
    }

    private Hortifruti instantiateProduct(ResultSet rs) throws SQLException {
        return new Hortifruti(
            rs.getInt("idPro"),
            rs.getString("namePro"),
            rs.getDouble("pricePro"),
            rs.getInt("quantityPro"),
            rs.getDouble("middleWeightHor")
        );
    }
}
