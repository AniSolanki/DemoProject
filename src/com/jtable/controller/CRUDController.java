package com.jtable.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.jtable.dao.CrudDao;
import com.jtable.model.Country;
import com.jtable.model.State;
import com.jtable.model.User;
import com.jtable.model.UserIP;
import com.jtable.model.UserType;

public class CRUDController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CrudDao dao;

    public CRUDController() {
        dao = new CrudDao();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getQueryString());
        if (request.getParameter("action") != null) {
            List<User> lstUser = new ArrayList<User>();
            List<UserIP> lstUserip = new ArrayList<UserIP>();
            List<UserType> lstUserType = new ArrayList<UserType>();
            List<Country> lstCountry = new ArrayList<Country>();
            List<State> lstState = new ArrayList<State>();
            String action = (String) request.getParameter("action");
            String firstName = (String) request.getParameter("firstName");
            String jtStartIndex = (String) request.getParameter("jtStartIndex");
            String jtPageSize = (String) request.getParameter("jtPageSize");
            String jtSorting = (String) request.getParameter("jtSorting");
            jtStartIndex = jtStartIndex == null ? "0" : jtStartIndex;
            jtPageSize = jtPageSize == null ? "10" : jtPageSize;
            jtSorting = jtSorting == null ? "ASC" : jtSorting;
            firstName = firstName == null ? "" : firstName;
            Gson gson = new Gson();
            response.setContentType("application/json");

            if(action.equals("countrylist")){
                 try {
                    String usertypeid = (String) request.getParameter("usertypeid");
                    lstCountry = dao.getAllCountry(usertypeid);
                    //Convert Java Object to Json				
                    JsonElement element = gson.toJsonTree(lstCountry, new TypeToken<List<Country>>() {
                    }.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();
                    //Return Json in the format required by jTable plugin
                    listData = "{\"Result\":\"OK\",\"Options\":" + listData + "}";
                    System.out.println(listData);
                    response.getWriter().print(listData);
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getMessage() + "}";
                    response.getWriter().print(error);
                    ex.printStackTrace();
                }
            } else if(action.equals("statelist")){
                 try {
                    String countryid = (String) request.getParameter("countryid");
                    lstState = dao.getAllState(countryid);
                    //Convert Java Object to Json				
                    JsonElement element = gson.toJsonTree(lstState, new TypeToken<List<State>>() {
                    }.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();
                    //Return Json in the format required by jTable plugin
                    listData = "{\"Result\":\"OK\",\"Options\":" + listData + "}";
                    System.out.println(listData);
                    response.getWriter().print(listData);
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getMessage() + "}";
                    response.getWriter().print(error);
                    ex.printStackTrace();
                }
            }else if (action.equals("list")) {
                try {
                    //Fetch Data from User Table
                    lstUser = dao.getAllUsers(firstName, jtStartIndex, jtPageSize, jtSorting);
                    int totalcount = dao.countAllUsers(firstName);

                    //Convert Java Object to Json				
                    JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<User>>() {
                    }.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();
                    //Return Json in the format required by jTable plugin
                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ",\"TotalRecordCount\":" + totalcount + "}";
                    //  System.out.println(listData);
                    response.getWriter().print(listData);
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getMessage() + "}";
                    response.getWriter().print(error);
                    ex.printStackTrace();
                }
            }else if (action.equals("listip")) {
                try {
                    //Fetch Data from User Table
                    int userid = Integer.parseInt(request.getParameter("userid"));
                    lstUserip = dao.getUserIPs(userid,jtStartIndex, jtPageSize, jtSorting);
                    int totalcount = dao.countAllUserIPs(userid);

                    //Convert Java Object to Json				
                    JsonElement element = gson.toJsonTree(lstUserip, new TypeToken<List<UserIP>>() {}.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();
                    //Return Json in the format required by jTable plugin
                    listData = "{\"Result\":\"OK\",\"Records\":" + listData + ",\"TotalRecordCount\":" + totalcount + "}";
                    //  System.out.println(listData);
                    response.getWriter().print(listData);
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getMessage() + "}";
                    response.getWriter().print(error);
                    ex.printStackTrace();
                }
            } else if (action.equals("usertypelist")) {
                try {
                    lstUserType = dao.getAllUsersType();
                    //Convert Java Object to Json				
                    JsonElement element = gson.toJsonTree(lstUserType, new TypeToken<List<UserType>>() {
                    }.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    String listData = jsonArray.toString();
                    //Return Json in the format required by jTable plugin
                    listData = "{\"Result\":\"OK\",\"Options\":" + listData + "}";
                    System.out.println(listData);
                    response.getWriter().print(listData);
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getMessage() + "}";
                    response.getWriter().print(error);
                    ex.printStackTrace();
                }

            } else if (action.equals("create") || action.equals("update")) {
                User user = new User();
                if (request.getParameter("userid") != null) {
                    int userid = Integer.parseInt(request.getParameter("userid"));
                    user.setUserid(userid);
                }
                if (request.getParameter("firstName") != null) {
                    String firstname = (String) request.getParameter("firstName");
                    user.setFirstName(firstname);
                }
                if (request.getParameter("lastName") != null) {
                    String lastname = (String) request.getParameter("lastName");
                    user.setLastName(lastname);
                }
                if (request.getParameter("email") != null) {
                    String email = (String) request.getParameter("email");
                    user.setEmail(email);
                }
                if (request.getParameter("isActive") != null) {
                    String isActive = (String) request.getParameter("isActive");
                    user.setIsActive(isActive);
                }
                if (request.getParameter("education") != null) {
                    String education = (String) request.getParameter("education");
                    user.setEducation(education);
                }
                if (request.getParameter("gender") != null) {
                    String gender = (String) request.getParameter("gender");
                    user.setGender(gender);
                }
                if (request.getParameter("usertypeid") != null) {
                    String usertypeid = (String) request.getParameter("usertypeid");
                    user.setUsertypeid(usertypeid);
                }
                if (request.getParameter("about") != null) {
                    String about= (String) request.getParameter("about");
                    user.setAbout(about);
                }
                try {
                    if (action.equals("create")) {//Create new record
                        dao.addUser(user);
                        lstUser.add(user);
                        //Convert Java Object to Json				
                        String json = gson.toJson(user);
                        //Return Json in the format required by jTable plugin
                        String listData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
                        response.getWriter().print(listData);
                    } else if (action.equals("update")) {//Update existing record
                        dao.updateUser(user);
                        String listData = "{\"Result\":\"OK\"}";
                        response.getWriter().print(listData);
                    }
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace().toString() + "}";
                    response.getWriter().print(error);
                }
            } else if (action.equals("createip") || action.equals("updateip")) {
                UserIP userip = new UserIP();
                if (request.getParameter("userid") != null) {
                    int userid = Integer.parseInt(request.getParameter("userid"));
                    userip.setUserid(userid);
                }
                if (request.getParameter("id") != null) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    userip.setId(id);
                }
                if (request.getParameter("ip") != null) {
                    String ip = (String) request.getParameter("ip");
                    userip.setIp(ip);
                }
                 try {
                    if (action.equals("createip")) {//Create new record
                        dao.addIp(userip);
                        lstUserip.add(userip);
                        //Convert Java Object to Json				
                        String json = gson.toJson(userip);
                        //Return Json in the format required by jTable plugin
                        String listData = "{\"Result\":\"OK\",\"Record\":" + json + "}";
                        response.getWriter().print(listData);
                    } else if (action.equals("updateip")) {//Update existing record
                        dao.updateIp(userip);
                        String listData = "{\"Result\":\"OK\"}";
                        response.getWriter().print(listData);
                    }
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace().toString() + "}";
                    response.getWriter().print(error);
                }
            } else if (action.equals("delete")) {//Delete record
                try {
                    if (request.getParameter("userid") != null) {
                        String userid = (String) request.getParameter("userid");
                        dao.deleteUser(Integer.parseInt(userid));
                        String listData = "{\"Result\":\"OK\"}";
                        response.getWriter().print(listData);
                    }
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace().toString() + "}";
                    response.getWriter().print(error);
                }
            } else if (action.equals("deleteip")) {//Delete record
                try {
                    if (request.getParameter("id") != null) {
                        String id = (String) request.getParameter("id");
                        dao.deleteIp(Integer.parseInt(id));
                        String listData = "{\"Result\":\"OK\"}";
                        response.getWriter().print(listData);
                    }
                } catch (Exception ex) {
                    String error = "{\"Result\":\"ERROR\",\"Message\":" + ex.getStackTrace().toString() + "}";
                    response.getWriter().print(error);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }
}
