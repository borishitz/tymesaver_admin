//package com.tracker.recordSearch;
//
//
//import com.tracker.recordSearch.domain.Contact;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class main {
//
//    @GetMapping("/")
//    public String home(){
//        return "index";
//    }
//
//    @GetMapping("/contact")
//    public String home(Model model){
//        Contact contact = new Contact();
//        model.addAttribute("newContact", contact);
//        return "contact";
//    }
//
//    @GetMapping("/ourServices")
//    public String ourServices(){
//        return "ourServices";
//    }
//
//    @GetMapping("/team")
//    public String teamPage(){
//        return "team";
//    }
//
//    @GetMapping("/community")
//    public String community(){
//        return "community";
//    }
//
//    @GetMapping("/cognition")
//    public String cognition(){
//        return "cognition";
//    }
//
//    @GetMapping("/personality")
//    public String personality(){
//        return "personality";
//    }
//
//    public String dashboardPage(){
//        return "dashboard/index";
//    }
//
//    @GetMapping("/about")
//    public String about(){
//        return "about";
//    }
//
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
//
//}
