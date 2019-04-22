package edu.wsu;

import edu.wsu.UpdateDataBase;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateReview")

public class UpdateReview extends HttpServlet
{
    private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String reportid = request.getParameter("reportid");

        String sdate = request.getParameter("sdate");

        String comm = request.getParameter("comm");

        String recommendation = request.getParameter("recommendation");

        String paperid = request.getParameter("paperid");

        String memberid = request.getParameter("memberid");

        String sdateX = request.getParameter("sdateX");

        String commX = request.getParameter("commX");

        String recommendationX = request.getParameter("recommendationX");

        String paperidX = request.getParameter("paperidX");

        String memberidX = request.getParameter("memberidX");



        if(request.getParameter("update")!=null)
        {
            DBConnection i = new  DBConnection();
            int x = i.updateReview(reportid,sdate,comm,recommendation,paperid,memberid);
            System.out.print(x);
        }

        if(request.getParameter("delete")!=null)
        {
            DBConnection b = new  DBConnection();
            int y = b.deleteReview(reportid,sdate,comm,recommendation,paperid,memberid);
            System.out.print(y);
        }

        if(request.getParameter("addnew")!=null)
        {
            DBConnection c = new  DBConnection();
            int z = c.addReview(sdateX,commX,recommendationX, paperidX,memberidX);
            System.out.print(z);
        }
        request.getRequestDispatcher("updateReview.jsp").forward(request, response);
    }
}
