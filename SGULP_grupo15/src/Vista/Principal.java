package Vista;

import Modelo.Alumno;
import Modelo.Materia;
import Persistencia.AlumnoData;
import Persistencia.MateriaData;
import java.time.LocalDate;
import java.util.List;

public class Principal {

    public static void main(String[] args) {

        System.out.println("----------------------------------------------------------------------");
        AlumnoData ad = new AlumnoData();
        System.out.println("Insertando alumnos en la base de datos.");

        Alumno a1 = new Alumno(45988763, "Cabral Javier Gerardo", LocalDate.of(2006, 9, 22), true);
        //Alumno a2 = new Alumno(46784332, "Astudillo Duran Lourdes", LocalDate.of(2006, 8, 21), true);
        //Alumno a3 = new Alumno(46808227, "Nunez Lisandro Valentin", LocalDate.of(2006, 2, 15), true);

        ad.guardarAlumno(a1);
        //.guardarAlumno(a2);
        //ad.guardarAlumno(a3);

        System.out.println("Listando alumnos desde la base de datos");

        List<Alumno> lista = ad.listarAlumnos();

        for (Alumno alum : lista) {
            System.out.println(alum);
        }

        System.out.println("----------------------------------------------------------------------");
        
        MateriaData md = new MateriaData();
        
        System.out.println("Insertando materias en la base de datos.");

        Materia m1 = new Materia("Quimica", 2025, true);
        //Materia m2 = new Materia("Programacion Web 2", 2, true);
        //Materia m3 = new Materia("Base de Datos 1", 1, true);

        md.guardarMateria(m1);
        //md.guardarMateria(m2);
        //md.guardarMateria(m3);

        System.out.println("Listando materias desde la base de datos");
        
        List<Materia> listaMaterias = md.listarMaterias();
        
        for (Materia mat : listaMaterias) {
            System.out.println(mat);
        }
    }
}
