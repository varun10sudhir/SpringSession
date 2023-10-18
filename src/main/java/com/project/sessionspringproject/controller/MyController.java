package com.project.sessionspringproject.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.model.IModel;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Controller
public class MyController {

    @GetMapping("/")
    public String home() {
        return "home";// Return the name of the view template (e.g., "home.html")
    }

    @GetMapping("/landing")
    public String landingGET(Model model, HttpSession httpSession) {
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = httpSession.getAttribute(attributeName);
            System.out.println("Attribute Name: " + attributeName + ", Attribute Value: " + attributeValue);
        }
        model.addAttribute("users", httpSession.getAttributeNames());
        return "table";
    }

    @PostMapping("/landing")
    public String handleForm(@RequestBody MultiValueMap<String, String> formData, HttpSession httpSession, Model model) {
        // Handle form-encoded data
        System.out.println("User name is " + formData.get("userName").get(0));
        httpSession.setAttribute(formData.get("userName").get(0), formData);
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = httpSession.getAttribute(attributeName);
            System.out.println("Attribute Name: " + attributeName + ", Attribute Value: " + attributeValue);
        }
        model.addAttribute("users", httpSession.getAttributeNames());
        return "table";
    }

    @PostMapping("/userDetails")
    public String editedUserDetails(@RequestParam String userName, @RequestBody MultiValueMap<String, String> map, HttpSession httpSession,Model model) {
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        System.out.println(userName);
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            if (userName.equals(attributeName)) {
                MultiValueMap<String, String> sessionObject = (MultiValueMap<String, String>) httpSession.getAttribute(attributeName);
                System.out.println(sessionObject);
                for (String key : map.keySet()) {
                    if (!map.get(key).get(0).equals(""))
                    {
                        sessionObject.put(key,map.get(key));
                    }
                }
                System.out.println(sessionObject);
                httpSession.setAttribute(attributeName, sessionObject);
                Object attributeValue = httpSession.getAttribute(attributeName);
                model.addAttribute("user",attributeValue);
                break;
            }
        }

        return "User";
    }

    @GetMapping("/userDetails")
    public String userDetails(@RequestParam String userName, Model model, HttpSession httpSession) {
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        System.out.println(userName);
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            if (userName.equals(attributeName)) {
                Object attributeValue = httpSession.getAttribute(attributeName);
                model.addAttribute("user", attributeValue);
                break;
            }
        }
        return "User";
    }


    @GetMapping("/userdelete")
    public String userdelete(@RequestParam String userName, HttpSession httpSession) {
        httpSession.removeAttribute(userName);
        return "Delete.html";
    }

    @GetMapping("/useredit")
    public String useredit(@RequestParam String userName, Model model, HttpSession httpSession) {
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        System.out.println(userName);
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            if (userName.equals(attributeName)) {
                Object attributeValue = httpSession.getAttribute(attributeName);
                model.addAttribute("user", attributeValue);
                break;
            }
        }
        return "Edit";
    }

}


