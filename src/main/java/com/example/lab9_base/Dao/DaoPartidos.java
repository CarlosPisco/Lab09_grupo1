package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Estadio;
import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Seleccion;

import java.sql.*;
import java.util.ArrayList;

public class DaoPartidos extends DaoBase {


    public ArrayList<Partido> listaDePartidos() {

        ArrayList<Partido> partidos = new ArrayList<>();
        String sql = "select p.idPartido, p.numeroJornada, p.fecha, sl.idSeleccion, sl.nombre , sv.idSeleccion, sv.nombre , e.nombre, e.idEstadio ,a.nombre, a.idArbitro \n" +
                "from partido p\n" +
                "inner join arbitro a on p.arbitro = a.idArbitro\n" +
                "inner join seleccion sl on sl.idSeleccion = p.seleccionLocal\n" +
                "inner join seleccion sv on sv.idSeleccion = p.seleccionVisitante\n" +
                "inner join estadio e on sl.estadio_idEstadio = e.idEstadio;";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Partido partido = new Partido();

                partido.setIdPartido(rs.getInt("p.idPartido"));
                partido.setNumeroJornada(rs.getInt("p.numeroJornada"));
                partido.setFecha(rs.getString("p.fecha"));
                Seleccion local = new Seleccion();
                local.setNombre(rs.getString("sl.nombre"));
                local.setIdSeleccion(rs.getInt("sl.idSeleccion"));
                partido.setSeleccionLocal(local);
                Seleccion visitante = new Seleccion();
                visitante.setNombre(rs.getString("sv.nombre"));
                visitante.setIdSeleccion(rs.getInt("sv.idSeleccion"));
                partido.setSeleccionVisitante(visitante);
                Estadio estadioLocal = new Estadio();
                estadioLocal.setNombre(rs.getString("e.nombre"));
                estadioLocal.setIdEstadio(rs.getInt("e.idEstadio"));
                partido.setEstadio(estadioLocal);
                Arbitro arbitro = new Arbitro();
                arbitro.setNombre(rs.getString("a.nombre"));
                arbitro.setIdArbitro(rs.getInt("a.idArbitro"));
                partido.setArbitro(arbitro);

                partidos.add(partido);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return partidos;
    }

    public void crearPartido(Partido partido) {


        String sql = "insert into partido (fecha,numeroJornada,seleccionlocal,seleccionVisitante,arbitro) values (?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1,partido.getFecha());
            pstm.setInt(2,partido.getNumeroJornada());
            pstm.setInt(3,partido.getSeleccionLocal().getIdSeleccion());
            pstm.setInt(4,partido.getSeleccionVisitante().getIdSeleccion());
            pstm.setInt(5,partido.getArbitro().getIdArbitro());

            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
