package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.bean.BuscaPorEstadoBean;
import br.ufscar.dc.dsw.domain.Cidade;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/cidades/estado"})
public class CidadesPorEstadoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String sigla = request.getParameter("estado");

        Gson gsonBuilder = new GsonBuilder().create();
        List<Cidade> cidades = new ArrayList<>();
        for (Cidade cidade : new BuscaPorEstadoBean().getCidades(sigla)) {
            cidades.add(cidade);
        }

        response.getWriter().write(gsonBuilder.toJson(cidades));
    }
}