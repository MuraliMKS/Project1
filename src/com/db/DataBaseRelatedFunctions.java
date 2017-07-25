package com.db;

import com.db.Jdbc.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Created by gundojim on 7/21/2017.
 */
public class DataBaseRelatedFunctions {

      Variable v=new Variable();
    public Variable Check(String uname,String pwd){
        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p=null;
            con = JdbcHelper.getConnection();
            String sql="select * from members where username like " + "\"" + uname+"\"";
            //o.println(sql);
            p=con.prepareStatement(sql);
            ResultSet r1=p.executeQuery();
            java.util.Date date =new java.util.Date();
            Calendar s=Calendar.getInstance();
            s.setTime(date);
            if(r1.next()){
                Date d =r1.getDate(4);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
                //int days = Calendar.
               if((r1.getInt(5)>=3)&&((date.getYear()>d.getYear())||(s.get(Calendar.MONTH)>d.getMonth())||(s.get(Calendar.DATE)>d.getDate()))){
                   v.access=false;
                   v.error="Blocked for today ...Try tomorrow";
                   return  v;
               }
               ResultSet r;
                /// /o.println("hello    ");
                sql="select * from members where password like " + "\"" + pwd+"\"" +"and username like " + "\"" + uname+"\"";
                p=con.prepareStatement(sql);
                r=p.executeQuery();
              //  Date d=r.getDate(4);

                // if(d.compareTo())
                if(r.next()){
                    if(r.getInt(3)==0){
                        v.error="you are blocked by admin .";
                        v.access=false;
                        v.i=r.getInt(3);
                        return v;
                    }
                   v.access=true;
                   v.i=r.getInt(3);
                   if(r.getInt(5)!=0){
                    sql="UPDATE members  set attempts =0 where password like " + "\"" + pwd+"\"" +"and username like " + "\"" + uname+"\"";
                    p=con.prepareStatement(sql);
                    p.executeUpdate();}
                   return v;
                }
                else{
                    v.access=false;
                    v.error="Wrong Password";

                    if(r1.getInt(5)==2){
                        LocalDate l=LocalDate.now(  );
                        System.out.print(l.toString());
                        v.error+=" you are blocked for today";
                        sql="UPDATE members  set attempts = attempts+1 ,time = STR_TO_DATE(\""+l.toString()+"\", '%Y-%m-%d')  where username like " + "\"" + uname+"\"";

                    }else{
                        System.out.print("you are here"+ r1.getInt(5));
                        sql="UPDATE members  set attempts = "+(r1.getInt(5)+1)+" where  username like " + "\"" + uname+"\"";
                    }
                    p=con.prepareStatement(sql);
                    p.executeUpdate();
                    return v;
                }
            }else {
               v.error="Wrong UserName";
               v.access=false;
               return v;
            }

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return v;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }


    }
    public boolean InsertSparePart(String sparePartName,String vechileName,String who){
        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "Insert into spareparts(sparepartname,vehiclename,who) values(?,?,?)";
            //o.println(sql);
            p = con.prepareStatement(sql);
            p.setString(1, sparePartName);
            p.setString(2, vechileName);
            p.setString(3, who);

            p.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return false;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }


    }
    public List<SpareParts> getRows(){
        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "SELECT id,sparepartname,vehiclename from spareparts";
            //o.println(sql);
            p = con.prepareStatement(sql);
            ResultSet r=p.executeQuery();
            List<SpareParts> s=new ArrayList<>();
            SpareParts s1;
            while(r.next()){
                s1=new SpareParts();
                s1.i=r.getInt(1);
                s1.sparepartname=r.getString(2);
                s1.vehiclename=r.getString(3);
                s.add(s1);
            }
            return  s;
        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return null;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }


    }


    public List<Operator> getOperators(){
        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "SELECT id,username,permissions from members";
            //o.println(sql);
            p = con.prepareStatement(sql);
            ResultSet r=p.executeQuery();
            List<Operator> s=new ArrayList<>();
            Operator s1;
            while(r.next()){
                s1=new Operator();
                s1.i=r.getInt(1);
                s1.name=r.getString(2);
                if(r.getInt(3)==1){
                    s1.state="Active";
                }else if(r.getInt(3)==0){
                    s1.state="Blocked";
                }else{
                    s1.state="Admin";
                }
               // s1.vehiclename=r.getString(3);
                s.add(s1);
            }
            return  s;
        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return null;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }


    }





    public boolean Create(String name,String email,int mobile,String username,String password) {

        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "Insert into signup values(?,?,?,?,?)";
            //o.println(sql);
            p = con.prepareStatement(sql);
            p.setString(1, name);
            p.setString(2, email);
            p.setInt(3, mobile);
            p.setString(4, username);
            p.setString(5, password);
            p.execute();
           // sql = "Insert into members values(?,?,?,?,?)";
           addOperator(username,password);

            return true;

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return false;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }
    }

    public boolean delete(int i) {

        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "DELETE  from spareparts where id="+i;
            //o.println(sql);
            p = con.prepareStatement(sql);
            p.execute();
            // sql = "Insert into members values(?,?,?,?,?)";
           // addOperator(username,password);

            return true;

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return false;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }
    }
    public boolean deleteOperator(int i) {

        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "DELETE  from members where id="+i;
            //o.println(sql);
            p = con.prepareStatement(sql);
            p.execute();
            // sql = "Insert into members values(?,?,?,?,?)";
            // addOperator(username,password);

            return true;

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return false;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }
    }

    public boolean update(int i,String sn,String vn,String who) {

        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "UPDATE spareparts  set sparepartname=\""+sn+"\", vehiclename=\""+vn+"\",who=\""+who+"\" where id="+i;
            //o.println(sql);
            p = con.prepareStatement(sql);
            p.executeUpdate();
            // sql = "Insert into members values(?,?,?,?,?)";
            // addOperator(username,password);

            return true;

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return false;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }
    }
    public boolean BlockOperator(int i) {

        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "UPDATE members set permissions=0 where id="+i;
            //o.println(sql);
            p = con.prepareStatement(sql);
            p.executeUpdate();
            // sql = "Insert into members values(?,?,?,?,?)";
            // addOperator(username,password);

            return true;

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return false;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }
    }
    public boolean UnBlockOperator(int i) {

        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "UPDATE members set permissions=1 where id="+i;
            //o.println(sql);
            p = con.prepareStatement(sql);
            p.executeUpdate();
            // sql = "Insert into members values(?,?,?,?,?)";
            // addOperator(username,password);

            return true;

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return false;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }
    }

    public boolean addOperator(String username,String password){
        Connection con = null;
        PreparedStatement ps_sel = null;
        ResultSet rs = null;
        try {
            PreparedStatement p = null;
            con = JdbcHelper.getConnection();
            String sql = "Insert into members(username,password) values(?,?)";
            //o.println(sql);
            p = con.prepareStatement(sql);
            p.setString(1, username);
            p.setString(2, password);
            p.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("OOPs error occured in connecting database " + e.getMessage());
            return false;
        } finally {
            JdbcHelper.close(rs);
            JdbcHelper.close(ps_sel);
            JdbcHelper.close(con);
        }
    }







}
