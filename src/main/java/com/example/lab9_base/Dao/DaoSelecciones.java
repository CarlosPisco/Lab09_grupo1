package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Estadio;
import com.example.lab9_base.Bean.Seleccion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoSelecciones extends DaoBase {
    public ArrayList<Seleccion> listarSelecciones() {

        ArrayList<Seleccion> selecciones = new ArrayList<>();
        try(Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT s.idSeleccion, s.nombre, s.tecnico, e.idEstadio, e.nombre, e.provincia, e.club FROM seleccion s \n" +
                    "left join estadio e on (s.estadio_idEstadio=e.idEstadio);")) {

            while(rs.next()){
                Seleccion seleccion = new Seleccion();
                Estadio estadio = new Estadio();
                seleccion.setIdSeleccion(rs.getInt(1));
                seleccion.setNombre(rs.getString(2));
                seleccion.setTecnico(rs.getString(3));
                estadio.setIdEstadio(rs.getInt(4));
                estadio.setNombre(rs.getString(5));
                estadio.setProvincia(rs.getString(6));
                estadio.setClub(rs.getString(7));
                seleccion.setEstadio(estadio);
                listarSelecciones().add(seleccion);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selecciones;
    }

}
