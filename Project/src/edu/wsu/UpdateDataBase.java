package edu.wsu;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UpdateDataBase
{
    private java.sql.Connection conn;

    public int insertPCMember(String email, String name){
        try
        {
            String sql = "INSERT INTO pcmember(email, name) VALUES("+email+", "+name+");";
            System.out.println(sql);
            return 1;
        }

        catch(Exception e)
        {
            return 0;
        }
    }


    public int deletePCMember(String memberid){
        try
        {
            String sql = "DELETE FROM pcmemeber WHERE memberid = "+memberid;
            System.out.println(sql);
            return 1;
        }

        catch(Exception e)
        {
            return 0;
        }
    }

    public int updatePCMember(String email, String name, String memberid){
        try
        {
            String sql = "UPDATE pcmember SET email = "+email+" name = "+name+" WHERE memberid = "+memberid;
            System.out.println(sql);
            return 1;
        }

        catch (Exception e)
        {
            return 0;
        }
    }

    public int insertReview(String sdate, String comm, String recommendation, String paperid, String email){
        try
        {
            String sql = "INSERT INTO review(sdate, comm, recommendation, paperid, email) VALUES("+sdate+", "+comm+", "+recommendation+", "+paperid+", "+email+");";
            System.out.println(sql);
            return 1;
        }

        catch(Exception e)
        {
            return 0;
        }
    }

    public int deleteReview(String reportid){
        try
        {
            String sql = "DELETE FROM review WHERE reportid = "+reportid;
            System.out.println(sql);
            return 1;
        }

        catch(Exception e)
        {
            return 0;
        }
    }

    public int updateReview(String sdate, String comm, String recommendation, String paperid, String memberid, String reportid){
        try
        {
            String sql = "UPDATE pcmember SET sdate = "+sdate+" comm = "+comm+"recommendation = "+recommendation+" paperid = "+paperid+" memberid = "+memberid+" WHERE reportid = "+reportid;
            System.out.println(sql);
            return 1;
        }

        catch (Exception e)
        {
            return 0;
        }
    }
}
