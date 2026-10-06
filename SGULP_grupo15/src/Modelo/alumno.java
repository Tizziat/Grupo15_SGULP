package Modelo;

import java.time.LocalDate;


public class alumno {
    private int idAlumno;
    private int dni;
    private String nombre; // !!!VARCHAr
    private LocalDate fechaNac; // !!!parsear DATE
    private boolean activo; // !!!TYNYINT

    // constructores, vacio, completo y sin idAlumno para nuevos alumnos
    public alumno() {
    }

    public alumno(int idAlumno, int dni, String nombre, LocalDate fechaNac, boolean activo) {
        this.idAlumno = idAlumno;
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.activo = activo;
    }

    
    public alumno(int dni, String nombre, LocalDate fechaNac, boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.activo = activo;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    
    
    @Override
    public String toString(){
    
            return idAlumno+"-"+nombre;
    }
    
}
