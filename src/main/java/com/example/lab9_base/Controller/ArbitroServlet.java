package com.example.lab9_base.Controller;

import com.example.lab9_base.Bean.Arbitro;
import com.example.lab9_base.Dao.DaoArbitros;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ArbitroServlet", urlPatterns = {"/ArbitroServlet"})
public class ArbitroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        ArrayList<String> paises = new ArrayList<>();
        paises.add("Peru");
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Paraguay");
        paises.add("Uruguay");
        paises.add("Colombia");
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");
        ArrayList<Arbitro> listaArbitros = new ArrayList<>();
        DaoArbitros daoArbitros = new DaoArbitros();
        aea:
        switch (action) {

            case "buscar":
                String busqueda = request.getParameter("tipo");
                String buscar = request.getParameter("buscar");
                switch (busqueda){
                    case "nombre":

                        listaArbitros = daoArbitros.busquedaNombre(buscar);
                        request.setAttribute("arbitros",listaArbitros);
                        request.setAttribute("buscarPor",opciones);
                        request.setAttribute("buscado",buscar);
                        request.setAttribute("tipo",busqueda);
                        view = request.getRequestDispatcher("/arbitros/list.jsp");
                        view.forward(request, response);

                        break;

                    case "pais":

                        listaArbitros = daoArbitros.busquedaPais(buscar);
                        request.setAttribute("arbitros",listaArbitros);
                        request.setAttribute("buscarPor",opciones);
                        request.setAttribute("buscado",buscar);
                        request.setAttribute("tipo",busqueda);
                        view = request.getRequestDispatcher("/arbitros/list.jsp");
                        view.forward(request, response);

                        break;
                }

                break;

            case "guardar":
                Arbitro arbitro = new Arbitro();

                arbitro.setNombre(request.getParameter("nombre"));
                ArrayList<Arbitro> arbitros = daoArbitros.listarArbitros();
                for(Arbitro a : arbitros){
                    if(a.getNombre().equalsIgnoreCase(arbitro.getNombre())){
                        request.setAttribute("paises",paises);
                        request.setAttribute("error1","Arbitro ya registrado");
                        view = request.getRequestDispatcher("arbitros/form.jsp");
                        view.forward(request, response);
                        break aea;
                    }
                }
                arbitro.setPais(request.getParameter("pais"));

                daoArbitros.crearArbitro(arbitro);

                response.sendRedirect(request.getContextPath()+"/ArbitroServlet");

                break;

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        ArrayList<String> paises = new ArrayList<>();
        paises.add("Peru");
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Paraguay");
        paises.add("Uruguay");
        paises.add("Colombia");
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("nombre");
        opciones.add("pais");

        DaoArbitros daoArbitros = new DaoArbitros();

        switch (action) {
            case "lista":
                ArrayList<Arbitro> listaArbitros = new ArrayList<>();
                listaArbitros = daoArbitros.listarArbitros();
                request.setAttribute("arbitros",listaArbitros);
                request.setAttribute("buscarPor",opciones);

                view = request.getRequestDispatcher("arbitros/list.jsp");
                view.forward(request, response);
                break;
            case "crear":
                request.setAttribute("paises",paises);

                view = request.getRequestDispatcher("arbitros/form.jsp");
                view.forward(request, response);

                break;
            case "borrar":
                int idArbitro = Integer.parseInt(request.getParameter("id"));


                daoArbitros.borrarArbitro(idArbitro);

                response.sendRedirect(request.getContextPath()+"/ArbitroServlet");


                break;
        }
    }
}
