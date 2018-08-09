package com.encryptnode.springServer.controllers;

import com.encryptnode.springServer.db.UserDB;
import com.encryptnode.springServer.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/private")
public class PrivateController {




    @RequestMapping("/*")
    public ModelAndView handlePrivateRequests(HttpServletRequest request) {
        String servlet = request.getServletPath();
        ModelAndView mv = new ModelAndView();

        HttpSession session = request.getSession();
        boolean isLoggedIn = (boolean) session.getAttribute("loggedin");
        System.out.println("THIS IS REQUEST MAPPING: " + "/private " + session.getAttribute("loggedin"));
        if (isLoggedIn) {
            mv.setViewName("profile");
        } else {
            mv.setViewName("accessdenied");
        }

        return mv;
    }



    // IDK IF THIS WILL WORK
//    @GetMapping("/{username}")
//    @ResponseBody
//    public User getOneUser(@PathVariable("username") String username) {
//        User result = UserDB.getUserByName(username);
//        System.out.println("GOT JUST ONE USER AND THAT IS: " + result);
//        return result;
//    }

}
