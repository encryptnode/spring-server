package com.encryptnode.springServer.controllers;

import com.encryptnode.springServer.db.UserDB;
import com.encryptnode.springServer.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
@SessionAttributes("username")
public class AuthController {
    @PostMapping("/register")
    public ModelAndView register(
            @RequestParam String username,
            @RequestParam String password) {
        ModelAndView mv = new ModelAndView();

        if (UserDB.getUserByName(username) != null) {
            mv.setViewName("loginerror");
            mv.addObject("error", "Sorry, that username already exists. Choose another.");
        } else {
            UserDB.createUser(username, password);
            mv.setViewName("loggedin");
            mv.addObject("username", username);
        }
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView login(
            HttpServletRequest request,
            @RequestParam String username,
            @RequestParam String password
    ) {
        ModelAndView mv = new ModelAndView();

        User user = UserDB.getUserByName(username);
        if (user == null) {
            mv.setViewName("loginerror");
            mv.addObject("error", "Username not found. Choose another.");
        } else {
            boolean isCorrectPassword = user.checkPassword(password);
            if(isCorrectPassword) {
                mv.setViewName("loggedin");
                mv.addObject("username", username);

                HttpSession session = request.getSession();
                session.setAttribute("loggedin", true);
                session.setAttribute("user", user);

                System.out.println("User has logged in as: " + username);
            } else {
                mv.setViewName("loginerror");
                mv.addObject("error", "Wrong password. Try again.");
            }
        }

        return mv;
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("loggedin", false);
        return new ModelAndView("loggedout");
    }
}
