package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Estadio;
import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Seleccion;

import java.sql.*;
import java.util.ArrayList;

public class DaoPartidos extends DaoBase{


    public ArrayList<Partido> listaDePartidos() {

        ArrayList<Partido> partidos = new ArrayList<>();
        /*
        Inserte su código aquí
        */
        String sql = "select *, sl.nombre as `local`, sv.nombre as `visit`, e.nombre as `estadioLocal`,a.nombre as `aname`\n" +
                "from partido p\n" +
                "inner join arbitro a on p.arbitro = a.idArbitro\n" +
                "inner join seleccion sl on sl.idSeleccion = p.seleccionLocal\n" +
                "inner join seleccion sv on sv.idSeleccion = p.seleccionVisitante\n" +
                "inner join estadio e on sl.estadio_idEstadio = e.idEstadio";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()){
                Partido partido = new Partido();

                partido.setIdPartido(rs.getInt("idPartido"));
                partido.setNumeroJornada(rs.getInt("numeroJornada"));
                partido.setFecha(rs.getString("fecha"));
                Seleccion local = new Seleccion();
                local.setNombre(rs.getString("local"));
                partido.setSeleccionLocal(local);
                Seleccion visitante = new Seleccion();
                visitante.setNombre(rs.getString("visit"));
                partido.setSeleccionVisitante(visitante);
                Estadio estadioLocal = new Estadio();
                estadioLocal.setNombre(rs.getString("estadioLocal"));
                partido.setEstadio(estadioLocal);
                Arbitro arbitro = new Arbitro();
                arbitro.setNombre(rs.getString("aname"));
                partido.setArbitro(arbitro);

                partidos.add(partido);


            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return partidos;
    }

        public void crearPartido(Partido partido) {

        /*
        Inserte su código aquí
        */
    }
}
