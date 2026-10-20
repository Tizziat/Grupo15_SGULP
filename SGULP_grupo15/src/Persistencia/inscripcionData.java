package Persistencia;

import Modelo.materia;
import Modelo.miConexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
        

public class inscripcionData {
    
    private Connection con = null;
    private materiaData mData = new materiaData();
    private alumnoData aData = new alumnoData();

    public inscripcionData() {
        this.con = miConexion.getConexion();
    }
    
    // 1- inscribimos un alumno en una materia
    public void guardarInscripcion (int idAlumno, int idMateria){
        String sql = "INSERT INTO cursada (idAlumno, idMateria, nota) VALUEs (?,?,0)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                System.out.println("Inscripcion exitosa. Id:" + rs.getInt(1));
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al inscribirse" + ex.getMessage());
        }
        
    }
    
    // obtenemos las materias que cursa el alumno
    public List<materia> obtenerMateriasCursadas(int idAlumno) {
        List<materia> materias = new ArrayList<>();
        String sql = "SELECT m.idMateria, m.nombre, m.anio, m.estado " +
                 "FROM materia m JOIN cursada c ON m.idMateria = c.idMateria " +
                 "WHERE c.idAlumno = ?";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idAlumno);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            materia m = new materia();
            m.setIdMateria(rs.getInt("idMateria"));
            m.setNombre(rs.getString("nombre"));
            m.setAnio(rs.getInt("anio"));
            m.setEstado(rs.getBoolean("estado"));
            
            materias.add(m);
        }
        ps.close();

    } catch (SQLException ex) {
        System.out.println("Error al obtener materias cursadas: " + ex.getMessage());
    }

    return materias;
    }
    
    
    // usando un NOT IN obtenemos las materias que el alumno NO cursa
    public List<materia> obtenerMateriasNOCursadas(int idAlumno) {
    List<materia> materias = new ArrayList<>();
    String sql = "SELECT * FROM materia WHERE estado = 1 AND idMateria NOT IN " +
                 "(SELECT idMateria FROM cursada WHERE idAlumno = ?)";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idAlumno);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            materia m = new materia();
            m.setIdMateria(rs.getInt("idMateria"));
            m.setNombre(rs.getString("nombre"));
            m.setAnio(rs.getInt("anio"));
            m.setEstado(rs.getBoolean("estado"));
            
            materias.add(m);
        }
        ps.close();

    } catch (SQLException ex) {
        System.out.println("Error al obtener materias NO cursadas: " + ex.getMessage());
    }

    return materias;
    }
    
    
    // anulamos - borramos la inscripcion
    public void borrarInscripcion(int idAlumno, int idMateria) {
        String sql = "DELETE FROM cursada WHERE idAlumno = ? AND idMateria = ?";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idAlumno);
            ps.setInt(2, idMateria);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Inscripcion anulada exitosamente.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Eror al anular la inscripcion: " + ex.getMessage());
        }
    }
    
    // actualizamos la nota de x alumno en x materia
    public void actualizarNota(int idAlumno, int idMateria, double nota){
        String sql = "UPDATE cursada SET nota = ? WHERE idAlumno = ? AND idMateria = ?";
        
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, nota);
            ps.setInt(2, idAlumno);
            ps.setInt(3, idMateria);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Nota actualizada con exito.");
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar la nota: " + ex.getMessage());
        }        
    }
      
}