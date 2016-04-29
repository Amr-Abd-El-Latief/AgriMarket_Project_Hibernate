/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAOImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dto.Credit;
import model.dto.User;
import util.JdbcConnection;

/**
 *
 * @author Amr
 */
public class CreditDaoImpl implements model.DAO.CreditDao {

    Connection con;

    public CreditDaoImpl() {
        try {
            con = JdbcConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public boolean checkCredit(Credit credit) {

        try {
            PreparedStatement pst = con.prepareStatement("select * from credit where number=?");

            pst.setString(1, credit.getNumber());

            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CreditDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public int getBalance(String creditNumber) {
        try {
            PreparedStatement pst = con.prepareStatement("select balance from agri_project.credit where number =?");
            pst.setString(1, creditNumber);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return res.getInt("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
