package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbExeption;
import entities.Electronics;


public class ElectronicsDAO extends ProductDAO{
    
    public void insert (Electronics electronic) {
        int pro_id = super.insert(electronic);
        String sql = "INSERT INTO electronic_tb (pro_id, marEle, fabricatorEle, modelEle, yearLaunchEle) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, pro_id);
            st.setString(2, electronic.getMark());
            st.setString(3, electronic.getFabricator());
            st.setString(4, electronic.getModel());
            st.setDate(5, electronic.getSqlYearLaunch());
            st.executeUpdate();
            System.out.println("inserido");
        } catch (SQLException e) {
            throw new DbExeption("Error inserting product: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM electronic_tb WHERE pro_id = ?";
        try (PreparedStatement st = DB.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
            super.deleteById(id);
        } catch (SQLException e) {
            throw new DbExeption("Error deleting product: " + e.getMessage());
        }
    }

    public Electronics findById(int id) {
        String sql = "SELECT p.*, e.* FROM product_tb p INNER JOIN electronic_tb e ON p.idPro = e.pro_id WHERE p.idPro = ?";
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
    
    
    public List<Electronics> findAllElectronics() {
    String sql = "SELECT p.*, e.* FROM product_tb p INNER JOIN electronic_tb e ON p.idPro = e.pro_id";
    List<Electronics> list = new ArrayList<>();
    try (PreparedStatement st = DB.getConnection().prepareStatement(sql);
         ResultSet rs = st.executeQuery()) {
        while (rs.next()) {
            list.add(instantiateProduct(rs)); // Adiciona cada objeto Electronics à lista
        }
    } catch (SQLException e) {
        throw new DbExeption("Error finding all products: " + e.getMessage());
    }
    return list; // Retorna a lista de Electronics
}
    public void update (int oldElectronicID,Electronics newElectronic) {
        super.update(oldElectronicID, newElectronic);

        String sql = "UPDATE electronic_tb SET marEle = ?, fabricatorEle = ?, modelEle = ?, yearLaunchEle = ? WHERE pro_id = ?";

        try (PreparedStatement pst = DB.getConnection().prepareStatement(sql)) {
            pst.setString(1, newElectronic.getMark());
            pst.setString(2, newElectronic.getFabricator());
            pst.setString(3, newElectronic.getModel());
            pst.setDate(4, newElectronic.getSqlYearLaunch());
            pst.setInt(5, oldElectronicID);

            pst.executeUpdate();
        }  catch (SQLException e) {
        throw new DbExeption("Error updating electronic product: " + e.getMessage());
        }
    }

    private Electronics instantiateProduct(ResultSet rs) throws SQLException {
        return new Electronics(
            rs.getInt("idPro"),
            rs.getString("namePro"),
            rs.getDouble("pricePro"),
            rs.getInt("quantityPro"),
            rs.getString("marEle"),
            rs.getString("fabricatorEle"),
            rs.getString("modelEle"),
            rs.getDate("yearLaunchEle")
        );
    }

}

