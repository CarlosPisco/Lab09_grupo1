package com.example.lab9_base.Dao;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Estadio;
import com.example.lab9_base.Bean.Seleccion;

import java.sql.*;
import java.util.ArrayList;

public class DaoArbitros extends DaoBase{
    public ArrayList<Arbitro> listarArbitros() {
        ArrayList<Arbitro> arbitros = new ArrayList<>();
        try(Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM lab9.arbitro;")) {

            while(rs.next()){
                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(rs.getInt(1));
                arbitro.setNombre(rs.getString(2));
                arbitro.setPais(rs.getString(3));
                arbitros.add(arbitro);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arbitros;
    }

    public void crearArbitro(Arbitro arbitro) {
        
        String sql = "insert into arbitro (nombre,pais) values (?,?)";


        try (Connection conn = getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)){

            pstm.setString(1,arbitro.getNombre());
            pstm.setString(2,arbitro.getPais());



            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }

    public ArrayList<Arbitro> busquedaPais(String pais) {

        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "select * \n" +
                "\tfrom arbitro\n" +
                "\twhere pais = ?";

        try (Connection conn = getConnection();

             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1,pais);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                Arbitro arbitro = new Arbitro();
                arbitro.setNombre(rs.getString(2));
                arbitro.setIdArbitro(rs.getInt(1));
                arbitro.setPais(rs.getString(3));
                arbitros.add(arbitro);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return arbitros;
    }

    public ArrayList<Arbitro> busquedaNombre(String nombre) {

        ArrayList<Arbitro> arbitros = new ArrayList<>();
        String sql = "select *\n" +
                "from arbitro\n" +
                "where nombre like ?";

        try (Connection conn = getConnection();

             PreparedStatement pstm = conn.prepareStatement(sql)){

            pstm.setString(1,"%"+nombre+"%");

            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                Arbitro arbitro = new Arbitro();
                arbitro.setNombre(rs.getString(2));
                arbitro.setIdArbitro(rs.getInt(1));
                arbitro.setPais(rs.getString(3));

                arbitros.add(arbitro);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return arbitros;
    }

    public Arbitro buscarArbitro(int id) {
        Arbitro arbitro = new Arbitro();
        String sql = "select * from arbitro where idArbitro = ?";

        try (Connection conn = getConnection();

             PreparedStatement pstm = conn.prepareStatement(sql)) {


            pstm.setInt(1,id);

            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                arbitro.setNombre(rs.getString(2));
                arbitro.setIdArbitro(rs.getInt(1));
                arbitro.setPais(rs.getString(3));


            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        
        return arbitro;
    }

    public void borrarArbitro(int id) {
        String sql = "Delete from arbitro where idArbitro = ?";

            try (Connection conn = getConnection();

                 PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1,id);
                pstm.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        
    }
}
