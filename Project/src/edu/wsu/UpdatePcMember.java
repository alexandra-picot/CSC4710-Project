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

            UpdateDataBase i = new UpdateDataBase();

            int x = i.updatePCMember(memberid, email, name);

            System.out.print(x);

        }

        if(request.getParameter("delete") != null){

            UpdateDataBase b = new UpdateDataBase();

            int y = b.deletePCMember(memberid);

            System.out.print(y);

        }

        if(request.getParameter("addnew") != null){

            UpdateDataBase c = new UpdateDataBase();

            int z = c.insertPCMember(emailX, nameX);

            System.out.print(z);

        }

        request.getRequestDispatcher("updatePCMember.jsp").forward(request, response);

    }

}
