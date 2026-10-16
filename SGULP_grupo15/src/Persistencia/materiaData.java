/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.materia;
import Modelo.miConexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author tizzi
 */
public class materiaData {
    private Connection con = null;

    public materiaData() {
        this.con = miConexion.getConexion();
    }
    
    // reutilizamos la logica y el codigo de alumno data reemplazando los campos sql, etc
    
    // 1. INSERT INTO MATERIA
    public void guardarMateria(materia m) {
        String sql = "INSERT INTO materia (nombre, anio, estado) VALUES (?, ?, ?)";//1 
        System.out.println("SQL QUE SE EJECUTA: " + sql);
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//2
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getAnio());
            ps.setBoolean(3, m.isEstado());

            ps.executeUpdate();//3

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                m.setIdMateria(rs.getInt(1));
                System.out.println("Materia guardada con éxito. ID: " + m.getIdMateria());
            }
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al guardar la materia: " + ex.getMessage());
        }
    }

    // 2. SELECT MATERIA, (buscamos por id)
    public materia buscarMateria(int id) {
        materia m = null;
        String sql = "SELECT * FROM materia WHERE idMateria = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                m = new materia();
                m.setIdMateria(rs.getInt("idMateria"));
                m.setNombre(rs.getString("nombre"));
                m.setAnio(rs.getInt("anio"));
                m.setEstado(rs.getBoolean("estado"));
            }
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al buscar materia " + ex.getMessage());
        }

        return m;
    }

    // 3. SELECT * FROM materia (listar materias)
    public List<materia> listarMaterias() {
        List<materia> materias = new ArrayList<>();
        String sql = "SELECT * FROM materia WHERE estado = 1";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
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
            System.out.println("Error al listar materias: " + ex.getMessage());
        }

        return materias;
    }

    // 4. UPDATE SET MATERIA (actualizamos datos)
    public void actualizarMateria(materia m) {
        String sql = "UPDATE materia SET nombre = ?, anio = ?, estado = ? WHERE idMateria = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getAnio());
            ps.setBoolean(3, m.isEstado());
            ps.setInt(4, m.getIdMateria());

            int exito = ps.executeUpdate();
            if (exito == 1) {
                System.out.println("Materia actualizada correctamente.");
            }
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al actualizar la materia: " + ex.getMessage());
        }
    }

    // 5. estado = 0(dar de baja una materia)
    public void bajaLogica(int id) {
        String sql = "UPDATE materia SET estado = 0 WHERE idMateria = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int exito = ps.executeUpdate();
            if (exito == 1) {
                System.out.println("Se dio de baja la materia con ID: " + id);
            }
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // 6. estado = 1 (dar de alta una materia que haya sido dado de baja)
    public void altaLogica(int id) {
        String sql = "UPDATE materia SET estado = 1 WHERE idMateria = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int exito = ps.executeUpdate();
            if (exito == 1) {
                System.out.println("Se dio de alta la materia con ID: " + id);
            }
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // 7. BORRADO FÍSICO
    public void borrarMateria(int id) {
        String sql = "DELETE FROM materia WHERE idMateria = ?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int exito = ps.executeUpdate();
            if (exito == 1) {
                System.out.println("Materia eliminada físicamente de la base de datos.");
            }
            ps.close();

        } catch (SQLException ex) {
            System.out.println("Error al borrar materia: " + ex.getMessage());
        }
    }
}


