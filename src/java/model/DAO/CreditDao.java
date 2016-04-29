/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.sql.Connection;
import model.dto.Credit;

/**
 *
 * @author Amr
 */
public interface CreditDao {
    
     
    public boolean checkCredit(Credit credit);
   
    
    
}
