package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class TestConnection {
    public static void main(String[]args)  {

        try {
            //step 1 : Load driver (optional in newer version)
            Class.forName("com.mysql.cj.jdbc.Driver");

            //step 2 : create connection
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testdb",
                    "root",
                    "root123"
            );

            /*
            String sql="insert into students (id,name,marks) values(?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);

            ps.setInt(1,103);
            ps.setString(2,"sanket");
            ps.setDouble(3,76.00);

            ps.executeUpdate();*/

            /*String sql="select * from students where id=?";
            PreparedStatement ps=con.prepareStatement(sql);

            ps.setInt(1,101);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getDouble("marks"));
            }*/

            /*String sql="update students set marks = ? where id = ?";
            PreparedStatement ps=con.prepareStatement(sql);

            ps.setDouble(1,90.05);
            ps.setInt(2,103);

            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("updated successfully");
            }*/

            /*String sql="delete from students where id = ?";
            PreparedStatement ps=con.prepareStatement(sql);

            ps.setInt(1,102);
            int rows = ps.executeUpdate();

            if(rows > 0){
                System.out.println("deleted successfully");
            }*/

            /*
            String sql="insert into students (id , name ,marks) values ( ?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);

            ps.setInt(1,104);
            ps.setString(2,"alex");
            ps.setDouble(3,67.70);

            ps.executeUpdate();*/

            /*String sql="select * from students where id = ?";
            PreparedStatement ps= con.prepareStatement(sql);

            ps.setInt(1,104);
            ResultSet rs= ps.executeQuery();

            while(rs.next()){
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getDouble("marks"));
            }*/

            //step 1 disable auto - commit
            con.setAutoCommit(false);
            String sql1="update account set balance = balance - 1000 where id=1";
            String sql2="update account set balance = balance + 1000 where id=2";
            try{
                //operation 1
                PreparedStatement ps1=con.prepareStatement(sql1);

                ps1.executeUpdate();

                //operation 2
                PreparedStatement ps2=con.prepareStatement(sql2);

                ps2.executeUpdate();

                //step 2 commit
                con.commit();
            }
            catch (Exception e){
                con.rollback();
            }





            //System.out.println("connected successfully");

            //step 3 : close connection
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}