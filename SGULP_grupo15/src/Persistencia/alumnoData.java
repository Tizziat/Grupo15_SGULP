package Persistencia;
import Modelo.Alumno;
import Modelo.MiConexion;

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
import javax.swing.JOptionPane;

public class AlumnoData {
    private Connection con = null;

    public AlumnoData() {
        //conexión a la base de datos usando miConexion
        this.con = MiConexion.getConexion();
    }

    // 1. INSERT INTO ALUMNO
    public void guardarAlumno(Alumno a) {
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
    public Alumno buscarAlumno(int dni) {
        String sql = "SELECT * FROM alumno WHERE dni = ?"; // 1
        Alumno a = null;
        
        try {
            PreparedStatement ps = con.prepareStatement(sql); // 2
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery(); // 3
            
            if (rs.next()) { 
                a = new Alumno();
                a.setIdAlumno(rs.getInt("idAlumno"));
                a.setDni(rs.getInt("dni"));
                a.setNombre(rs.getString("nombre"));
                a.setFechaNac(rs.getDate("fechaNac").toLocalDate());
                a.setActivo(rs.getBoolean("activo"));
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "No se encontro ningun alumno con el DNI ingresado.");
            }
            ps.close(); // 5
            
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al buscar alumno.");
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    // 3. SELECT * FROM ALUMNO (Listar todos)
    public List<Alumno> listarAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT * FROM alumno"; // 1
        
        try {
            PreparedStatement ps = con.prepareStatement(query); // 2
            ResultSet rs = ps.executeQuery(); // 3
            
            while (rs.next()) { // 4.
                Alumno a = new Alumno();
                a.setIdAlumno(rs.getInt("idAlumno"));
                a.setDni(rs.getInt("dni"));
                a.setNombre(rs.getString("nombre"));
                a.setFechaNac(rs.getDate("fechaNac").toLocalDate());
                a.setActivo(rs.getBoolean("activo"));
                
                alumnos.add(a); // Agrego el alumno a la lista
            }
            ps.close(); // 5
            
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alumnos;
    }
        
      // 4. UPDATE SET ALUMNO (Actualizar datos)
    public void actualizarAlumno(Alumno a) {
        String query = "UPDATE alumno SET nombre = ?, fechaNac = ?, activo = ? WHERE dni = ?"; // 1
        
        try {
            PreparedStatement ps = con.prepareStatement(query); // 2
            ps.setString(1, a.getNombre());
            ps.setDate(2, Date.valueOf(a.getFechaNac()));
            ps.setBoolean(3, a.isActivo());
            ps.setInt(4, a.getDni());
            
            int exito = ps.executeUpdate(); // 3
            if (exito == 1) {
                System.out.println("Alumno actualizado correctamente.");
            } else {
                System.out.println("No se encontro ningun alumno con ese DNI.");
            }
            ps.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // 5. DELETE FROM ALUMNO (Borrar físicamente)
    public void borrarAlumno(int dni) {
        String query = "DELETE FROM alumno WHERE dni = ?"; // 1
        
        try {
            PreparedStatement ps = con.prepareStatement(query); // 2
            ps.setInt(1, dni);

            int exito = ps.executeUpdate(); // 3
            if (exito == 1) {
                //JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro ningun alumno con ese DNI.");
            }
            ps.close(); // 4

        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1451 || ex.getMessage().contains("foreign key")) {
                JOptionPane.showMessageDialog(null, "No se puede eliminar, el alumno está inscripto en una materia.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el alumno.");
            }
        }
    }

    public void altaLogica(int dni){
        String query = "UPDATE alumno SET activo = 1 WHERE dni = ?";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, dni);
            int filas = ps.executeUpdate();
            if(filas > 0){
                //JOptionPane.showMessageDialog(null, "Alumno dado de alta");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al dar de alta: " + ex.getMessage());
        }
    }
    
    public void bajaLogica(int dni){
        String query = "UPDATE alumno SET activo = 0 WHERE dni = ?";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, dni);
            int filas = ps.executeUpdate();
            if(filas > 0){
                //JOptionPane.showMessageDialog(null, "Alumno dado de baja");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al dar de baja: " + ex.getMessage());
        }
    }
}
