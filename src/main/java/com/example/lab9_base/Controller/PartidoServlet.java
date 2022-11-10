package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Bean.Partido;
import com.example.lab9_base.Bean.Seleccion;
import com.example.lab9_base.Dao.DaoArbitros;
import com.example.lab9_base.Dao.DaoPartidos;
import com.example.lab9_base.Dao.DaoSelecciones;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "PartidoServlet", urlPatterns = {"/PartidoServlet", ""})
public class PartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        RequestDispatcher view;
        DaoPartidos daoP = new DaoPartidos();
        DaoSelecciones daoS = new DaoSelecciones();
        DaoArbitros daoA = new DaoArbitros();
        switch (action) {
            case "guardar":
                Partido partido = new Partido();
                partido.setNumeroJornada(Integer.parseInt(request.getParameter("jornada")));
                partido.setFecha(request.getParameter("fecha"));
                Seleccion local = new Seleccion();
                local.setIdSeleccion(Integer.parseInt(request.getParameter("local")));
                partido.setSeleccionLocal(local);
                Seleccion visitante = new Seleccion();
                visitante.setIdSeleccion(Integer.parseInt(request.getParameter("visitante")));
                partido.setSeleccionVisitante(visitante);
                if(partido.getSeleccionLocal().getIdSeleccion()==partido.getSeleccionVisitante().getIdSeleccion()){
                    ArrayList<Seleccion> listarSelecciones = daoS.listarSelecciones();
                    ArrayList<Arbitro> listarArbitros = daoA.listarArbitros();
                    request.setAttribute("listarSelecciones", listarSelecciones);
                    request.setAttribute("listarArbitros", listarArbitros);
                    request.setAttribute("error1", "Las selecciones local y visitante deben ser diferentes");
                    view = request.getRequestDispatcher("partidos/form.jsp");
                    view.forward(request, response);
                }
                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(Integer.parseInt(request.getParameter("arbitro")));
                partido.setArbitro(arbitro);
                daoP.crearPartido(partido);
                response.sendRedirect(request.getContextPath() + "/PartidoServlet?action=lista");
                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        DaoSelecciones daoS = new DaoSelecciones();
        DaoArbitros daoA = new DaoArbitros();
        DaoPartidos daoP = new DaoPartidos();
        switch (action) {
            case "lista":
                ArrayList<Partido> listarPartidos = daoP.listaDePartidos();
                request.setAttribute("listarPartidos", listarPartidos);
                view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
                break;
            case "crear":
                ArrayList<Seleccion> listarSelecciones = daoS.listarSelecciones();
                ArrayList<Arbitro> listarArbitros = daoA.listarArbitros();
                request.setAttribute("listarSelecciones", listarSelecciones);
                request.setAttribute("listarArbitros", listarArbitros);
                view = request.getRequestDispatcher("partidos/form.jsp");
                view.forward(request, response);
                break;

        }

    }
}
