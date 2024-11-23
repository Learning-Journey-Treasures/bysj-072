package com.ssm.guard.controller;

import com.ssm.guard.po.Admin;
import com.ssm.guard.po.PageInfo;
import com.ssm.guard.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @description: 控制层
 * @author: mty
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    // 依赖注入
    @Autowired
    private AdminService adminService;


    /**
     * 分页查询
     * pageIndex 当前页码
     * pageSize  显示条数
     */
    @RequestMapping(value = "/findAdmin")
    public String findAdmin(Integer pageIndex, Integer pageSize, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("ad") == null){
            session.setAttribute("msg", "对不起，请登录！");
            return "login";
        }
        Map mp = new HashMap();
        PageInfo<Admin> pageList = adminService.findPageInfo(pageIndex,pageSize,mp);
        model.addAttribute("pageList",pageList);
        return "AdminList";
    }


    /**
     * 添加
     */
    @RequestMapping(value = "/addAdmin" ,method = RequestMethod.POST)
    @ResponseBody
    public String addAdmin( @RequestBody Admin admin) {
        try{
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            admin.setCreateTime(sf.format(new Date()));
            adminService.addAdmin(admin);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 删除
     */
    @RequestMapping( "/deleteAdmin")
    @ResponseBody
    public String deleteAdmin(String id) {
        int d = adminService.deleteAdmin(id);
        return "AdminList";
    }


    /**
     * 修改
     */
    @RequestMapping( "/updateAdmin")
    @ResponseBody
    public String updateAdmin(@RequestBody  Admin admin) {
        try{
            adminService.updateAdmin(admin);
            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }


    /**
     * 按照ID查询
     */
    @RequestMapping( "/findAdminById")
    @ResponseBody
    public Admin findAdminById(String id,Model model,HttpServletRequest request) {
        Admin admin= adminService.findAdminById(id);
        return admin;
    }


}
