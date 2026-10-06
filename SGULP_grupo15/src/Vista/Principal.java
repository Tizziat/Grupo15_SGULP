package Vista;

import Modelo.alumno;
import Persistencia.alumnoData;
import java.time.LocalDate;
import java.util.List;


public class Principal {

    
    public static void main(String[] args) {
    
        alumnoData ad = new alumnoData();
        
        System.out.println("Insertando alumnos en la base de datos.");
        
        alumno a1 = new alumno(45982786, "Tobares Ahsley Tiziano", LocalDate.of(2005,3,31), true);
        alumno a2 = new alumno(46784332, "Astudillo Duran Lourdes", LocalDate.of(2006,8,21), true);
        alumno a3 = new alumno(46808227, "Nunez Lisandro Valentin", LocalDate.of(2006,2,15), true);
        
        ad.guardarAlumno(a1);
        ad.guardarAlumno(a2);
        ad.guardarAlumno(a3);
        
        System.out.println("Listando alumnos desde la base de datos");
        
        List<alumno> lista = ad.listarAlumnos();
        
        for(alumno al : lista){
            System.out.println(al);
        }
        
    }
}
