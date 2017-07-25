package com.db;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.SwitchPoint;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gundojim on 7/22/2017.
 */
@WebServlet(name = "Methods")
public class Methods extends HttpServlet {
    protected void service(HttpServletResponse response,HttpServletRequest request){

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession(false);
        PrintWriter o=response.getWriter();
        RequestDispatcher rd;
        rd=request.getRequestDispatcher("Operator.html");
        DataBaseRelatedFunctions db=new DataBaseRelatedFunctions();
        switch (Integer.parseInt(session.getAttribute("operation").toString())){
            case 1:{
                if(db.InsertSparePart(request.getParameter("sparepartname").toString(),request.getParameter("Vehiclename").toString(),session.getAttribute("user").toString())){
                o.println("<p style =\"color:blue\" >INSERTION SUCCESSFULL</p>");

                rd.include(request,response);
            }
                break;
            }
            case 2:{if(db.delete(Integer.parseInt(request.getParameter("choose")))) {
                o.println("<p style =\"color:blue\" >DELETION  SUCCESSFULL</p>");
            }else{
                o.println("<p style =\"color:red\" >DELETION  FAILED</p>");}
                rd.include(request,response);

                break;
            }
            case 3:{ if(db.update(Integer.parseInt(request.getParameter("choose")),request.getParameter("sn").toString(),request.getParameter("vn"),session.getAttribute("user").toString())){
                o.println("<p style =\"color:blue\" >UPDATION  SUCCESSFULL</p>");
                rd.include(request,response);
            }
                break;
            }
            case 4:{
                rd=request.getRequestDispatcher("Operator.html");
                rd.forward(request,response);
            }
            case 5:{
                rd=request.getRequestDispatcher("Admin.html");
                if(db.addOperator(request.getParameter("name"),request.getParameter("password"))){
                    o.println("<p style =\"color:blue\" >Addition  SUCCESSFULL</p>");
                }else{
                    o.println("<p style =\"color:red\" >Addition  FAILED</p>");
                }
                rd.include(request,response);

                break;
            }
            case 6:{
                rd=request.getRequestDispatcher("Admin.html");
                if(db.deleteOperator(Integer.parseInt(request.getParameter("choose")))) {
                    o.println("<p style =\"color:blue\" >DELETION  SUCCESSFULL</p>");
                }else{
                    o.println("<p style =\"color:red\" >DELETION  FAILED</p>");
                }
                    rd.include(request,response);

                break;
            }
            case 7:{
                rd=request.getRequestDispatcher("Admin.html");
                if(db.BlockOperator(Integer.parseInt(request.getParameter("choose")))) {
                    o.println("<p style =\"color:blue\" >Operator blocked</p>");
                }else{
                    o.println("<p style =\"color:red\" >Operator unblocked</p>");
                }
                rd.include(request,response);

                break;
            }
            case 8:{
                rd=request.getRequestDispatcher("Admin.html");
                rd.forward(request,response);
               break;}

               case 9:{
                rd=request.getRequestDispatcher("Admin.html");
                if(db.UnBlockOperator(Integer.parseInt(request.getParameter("choose")))) {
                    o.println("<p style =\"color:blue\" >Operator Unblocked</p>");
                }else{
                    o.println("<p style =\"color:red\" >Operator unblocking failed</p>");
                }
                rd.include(request,response);

                break;
            }



        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        session.setAttribute("operation",request.getParameter("operation"));
        PrintWriter responseWriter=response.getWriter();
        DataBaseRelatedFunctions db=new DataBaseRelatedFunctions();
        switch(Integer.parseInt(request.getParameter("operation"))){
            case 1:{
                responseWriter.println("<form action=\"./Methods\" method = \"post\">" +
                    "<table>" +
                    "<tr>" +
                    "<td>enter spare part name</td>" +
                    "<td><input type=\"text\" name=\"sparepartname\"></td>"+
                    "</tr>"+
                    "<tr>" +
                    "<td>enter Vehicle name</td>" +
                    "<td><input type=\"text\" name=\"Vehiclename\"></td>"+
                    "</tr>"+

                    "<tr>" +
                    "<td>Inserted By :</td>" +
                    "<td><p name = \"user\">"+session.getAttribute("user")+"></td>"+
                    "</tr>"+
                    "<td><input type=\"submit\" value=\"Submit\"></td>"+
                    "</table></form>");
                break;}
            case 2:{
                    List<SpareParts> s=db.getRows();
                responseWriter.println("<style>td{border: 1px solid black;}</style><form action=\"./Methods\" method = \"post\"><table> <col width=\"130\">\n" +
                        "  <col width=\"80\">" +
                        "<col border=\"1px solid black\"><tr><th>SPAREPART NAME</th><th>VEHICLE NAME</th></tr>" );
                Iterator t=s.iterator();
                SpareParts sp;
                int i=1;
                while(t.hasNext()) {
                    sp = (SpareParts) t.next();
                    responseWriter.println("<tr><td><input type=\"radio\" name=\"choose\" value=\"" + sp.i + "\">" + sp.sparepartname + "</td><td> " + sp.vehiclename + "</td></tr>" );
                }
                        responseWriter.println("<tr></tr>"+
                        "</table><input type=\"submit\" value=\"Submit\"></form>");
                break;}
            case 3:{List<SpareParts> s=db.getRows();
                responseWriter.println("<style>td{border: 1px solid black;}</style><form action=\"./Methods\" method = \"post\"><table> <col width=\"130\">\n" +
                        "  <col width=\"80\"><tr><th>SPAREPART NAME</th><th>VEHICLE NAME</th></tr>" );
                Iterator t=s.iterator();
                SpareParts sp;
                int i=1;
                while(t.hasNext()) {
                    sp = (SpareParts) t.next();
                    responseWriter.println("<tr><td><input type=\"radio\" name=\"choose\" value=\"" + sp.i + "\">" + sp.sparepartname + "       " + sp.vehiclename + "</td></tr>" );
                }
                responseWriter.println("<tr>" +"<td>enter spare part name</td>" +
                        "<td><input type=\"text\" name=\"sn\"></td>"+
                        "</tr>"+
                        "<tr>" +
                        "<td>enter Vehicle name</td>" +
                        "<td><input type=\"text\" name=\"vn\"></td>"+
                        "</tr>"+
                        "</table><input type=\"submit\" value=\"Submit\"></form>");
                break;}
            case 4:{
                List<SpareParts> s=db.getRows();
                responseWriter.println("<style>td{border: 1px solid black;}</style><form action=\"./Methods\" method = \"post\"><table> <col width=\"130\">\n" +
                        "  <col width=\"80\"><tr><th>SPAREPART NAME</th><th>VEHICLE NAME</th></tr>" );
                Iterator t=s.iterator();
                SpareParts sp;
                int i=1;
                while(t.hasNext()) {
                    sp = (SpareParts) t.next();
                    responseWriter.println("<tr><td>"+ sp.sparepartname + "</td><td> " + sp.vehiclename + "</td></tr>" );
                }
                responseWriter.println("</table><input type=\"submit\" value=\"Submit\"></form>");
                break;}
            case 5:{
                responseWriter.println("<form action=\"./Methods\" method = \"post\">" +
                        "<table>" +
                        "<tr>" +
                        "<td>enter name</td>" +
                        "<td><input type=\"text\" name=\"name\"></td>"+
                        "</tr>"+
                        "<tr>" +
                        "<td>set Password</td>" +
                        "<td><input type=\"text\" name=\"password\"></td>"+
                        "</tr>"+

                        "<tr>" +
                        "<td>Inserted By :</td>" +
                        "<td><p name = \"user\">"+session.getAttribute("user")+"></td>"+
                        "</tr>"+
                        "<td><input type=\"submit\" value=\"Submit\"></td>"+
                        "</table></form>");
                break;}

            case 6:{List<Operator> s=db.getOperators();
                responseWriter.println("<style>td{border: 1px solid black;}</style><form action=\"./Methods\" method = \"post\"><table> <col width=\"130\">\n" +
                        "  <col width=\"80\">" +
                        "<col border=\"1px solid black\"><tr><th> OPERATOR NAME</th><th>CURRENT STATE</th></tr>" );
                Iterator t=s.iterator();
             Operator sp;

                while(t.hasNext()) {
                    sp = (Operator) t.next();
                    responseWriter.println("<tr><td><input type=\"radio\" name=\"choose\" value=\"" + sp.i + "\">" + sp.name + "</td><td> " + sp.state + "</td></tr>" );
                }
                responseWriter.println("<tr></tr>"+
                        "</table><input type=\"submit\" value=\"Submit\"></form>");

                break;
            }
            case 7:{List<Operator> s=db.getOperators();
                responseWriter.println("<style>td{border: 1px solid black;}</style><form action=\"./Methods\" method = \"post\"><table> <col width=\"130\">\n" +
                        "  <col width=\"80\">" +
                        "<col border=\"1px solid black\"><tr><th> OPERATOR NAME</th><th>CURRENT STATE</th></tr>" );
                Iterator t=s.iterator();
                Operator sp;

                while(t.hasNext()) {
                    sp = (Operator) t.next();
                    responseWriter.println("<tr><td><input type=\"radio\" name=\"choose\" value=\"" + sp.i + "\">" + sp.name + "</td><td> " + sp.state + "</td></tr>" );
                }
                responseWriter.println("<tr></tr>"+
                        "</table><input type=\"submit\" value=\"Submit\"></form>");

                break;
            }
            case 8:{List<Operator> s=db.getOperators();
            responseWriter.println("<style>td{border: 1px solid black;}</style><form action=\"./Methods\" method = \"post\"><table> <col width=\"130\">\n" +
                    "  <col width=\"80\">" +
                    "<col border=\"1px solid black\"><tr><th> OPERATOR NAME</th><th>CURRENT STATE</th></tr>" );
            Iterator t=s.iterator();
            Operator sp;

            while(t.hasNext()) {
                sp = (Operator) t.next();
                responseWriter.println("<tr><td>" + sp.name + "</td><td> " + sp.state + "</td></tr>" );
            }
            responseWriter.println("<tr></tr>"+
                    "</table><input type=\"submit\" value=\"Submit\"></form>");

            break;
        }
            case 9:{List<Operator> s=db.getOperators();
                responseWriter.println("<style>td{border: 1px solid black;}</style><form action=\"./Methods\" method = \"post\"><table> <col width=\"130\">\n" +
                        "  <col width=\"80\">" +
                        "<col border=\"1px solid black\"><tr><th> OPERATOR NAME</th><th>CURRENT STATE</th></tr>" );
                Iterator t=s.iterator();
                Operator sp;

                while(t.hasNext()) {
                    sp = (Operator) t.next();
                    if(sp.state.equals("Blocked")){
                    responseWriter.println("<tr><td><input type=\"radio\" name=\"choose\" value=\"" + sp.i + "\">" + sp.name + "</td><td> " + sp.state + "</td></tr>" );
                }}
                responseWriter.println("<tr></tr>"+
                        "</table><input type=\"submit\" value=\"Submit\"></form>");

                break;
     }
        }

    }
}
