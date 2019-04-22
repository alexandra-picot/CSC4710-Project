package edu.wsu;

import edu.wsu.UpdateDataBase;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatepcmember")
public class UpdatePcMember extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memberid = request.getParameter("memberid");

        String email = request.getParameter("email");

        String name = request.getParameter("name");


        String emailX = request.getParameter("emailX");

        String nameX = request.getParameter("nameX");



        if(request.getParameter("update") != null)
        {

            DBConnection i = new DBConnection();

            int x = i.updatePCMember(memberid, email, name);

            System.out.print(x);

        }

        if(request.getParameter("delete") != null){

            DBConnection b = new DBConnection();

            int y = b.deletePCMember(memberid, email, name);

            System.out.print(y);

        }

        if(request.getParameter("addnew") != null){

            DBConnection c = new DBConnection();

            int z = c.addPCMember(emailX, nameX);

            System.out.print(z);

        }

        request.getRequestDispatcher("updatePCMember.jsp").forward(request, response);

    }

}
