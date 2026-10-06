package Persistencia;
import Modelo.alumno;
import Modelo.miConexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class alumnoData {
    private Connection con = null;

    public alumnoData() {
        //conexión a la base de datos usando miConexion
        this.con = miConexion.getConexion();
    }

    // 1. INSERT INTO ALUMNO
    public void guardarAlumno(alumno a) {
        String sql = "INSERT INTO alumno (dni, nombre, fechaNac, activo) VALUES (?, ?, ?, ?)"; // 1
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 2
            ps.setInt(1, a.getDni());
            ps.setString(2, a.getNombre());
            ps.setDate(3, Date.valueOf(a.getFechaNac()));
            ps.setBoolean(4, a.isActivo());
            
            ps.executeUpdate(); // 3
            
            ResultSet rs = ps.getGeneratedKeys(); // id generado por la bdd
            if (rs.next()) {
                a.setIdAlumno(rs.getInt(1)); // se lo asignamos a objeto alumno
                System.out.println("Guardado! ID: " + a.getIdAlumno());
            } else {
                System.out.println("No se pudo obtener el ID");
            }
            ps.close();
            
        } catch (SQLException ex) {
            System.out.println("No pude insertar: " + ex.getMessage());
        }
    }

    // 2. SELECT 1 ALUMNO (Buscar por ID)
    public alumno buscarAlumno(int id) {
        alumno a = null;
        String sql = "SELECT * FROM alumno WHERE idAlumno = ?"; // 1
        
        try {
            PreparedStatement ps = con.prepareStatement(sql); // 2
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery(); // 3
            
            if (rs.next()) { 
                a = new alumno();
                a.setIdAlumno(rs.getInt("idAlumno"));
                a.setDni(rs.getInt("dni"));
                a.setNombre(rs.getString("nombre"));
                a.setFechaNac(rs.getDate("fechaNac").toLocalDate());
                a.setActivo(rs.getBoolean("activo"));
            }
            ps.close(); // 5
            
        } catch (SQLException ex) {
            Logger.getLogger(alumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    // 3. SELECT * FROM ALUMNO (Listar todos)
    public List<alumno> listarAlumnos() {
        List<alumno> alumnos = new ArrayList<>();
        String query = "SELECT * FROM alumno"; // 1
        
        try {
            PreparedStatement ps = con.prepareStatement(query); // 2
            ResultSet rs = ps.executeQuery(); // 3
            
            while (rs.next()) { // 4.
                alumno a = new alumno();
                a.setIdAlumno(rs.getInt("idAlumno"));
                a.setDni(rs.getInt("dni"));
                a.setNombre(rs.getString("nombre"));
                a.setFechaNac(rs.getDate("fechaNac").toLocalDate());
                a.setActivo(rs.getBoolean("activo"));
                
                alumnos.add(a); // Agrego el alumno a la lista
            }
            ps.close(); // 5
            
        } catch (SQLException ex) {
            Logger.getLogger(alumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alumnos;
    }
}
