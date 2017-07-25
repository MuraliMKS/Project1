package com.db;

import com.db.Jdbc.JdbcHelper;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by gundojim on 7/15/2017.
 */
@WebServlet(name = "Check")
public class Check extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd;
        System.out.print("hiiiiii2");
        response.setContentType("text/html");
        DataBaseRelatedFunctions d=new DataBaseRelatedFunctions();
        Variable v=d.Check(request.getParameter("uname"),request.getParameter("pwd"));
        if(v.access){
            HttpSession session =request.getSession();
            session.setAttribute("user",request.getParameter("uname"));

            if(v.i==1){

                rd=request.getRequestDispatcher("Operator.html");

            }
            else{
                rd=request.getRequestDispatcher("Admin.html");
            }
            rd.forward(request,response);
        }
        else{
            PrintWriter o=response.getWriter();

            o.println("<p style=\"color:red;\">"+v.error+"</p>");

            System.out.println("here");
           rd=request.getRequestDispatcher("index.html");
            rd.include(request,response);
        }

        }
}
