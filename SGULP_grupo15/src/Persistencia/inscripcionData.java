package Persistencia;

import Modelo.miConexion;
import java.sql.Connection;


public class inscripcionData {
    
    private Connection con = null;

    public inscripcionData() {
        
        this.con = miConexion.getConexion();
    }
    
    
    
    
    
}