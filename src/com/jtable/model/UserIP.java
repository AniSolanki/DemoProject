/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jtable.model;

/**
 *
 * @author BDII1227
 */
public class UserIP {
   private int id; 
   private int userid; 
   private String ip; 
   private String req; 

    @Override
    public String toString() {
        return "UserIP{" + "id=" + id + ", userid=" + userid + ", ip=" + ip + ", req=" + req + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }
   
   
}
