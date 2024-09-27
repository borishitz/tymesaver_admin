package com.tracker.recordSearch.adminController;

import com.tracker.recordSearch.domain.Admin;
import com.tracker.recordSearch.domain.Category;
import com.tracker.recordSearch.domain.Customer;
import com.tracker.recordSearch.domain.ShoppingCart;
import com.tracker.recordSearch.dto.TripDto;
import com.tracker.recordSearch.services.AdminService;
import com.tracker.recordSearch.services.CategoryService;
import com.tracker.recordSearch.services.CityService;
import com.tracker.recordSearch.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class Main {

    private final AdminService customerService;
    private final TripService tripService;
    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;
    private final CityService cityService;

//    @GetMapping({"/","/home",""})
//    public String dashboard(){
//        return "dashboard";
//    }

    @RequestMapping(value = {"/","/home",""}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        model.addAttribute("title", "Home");
        model.addAttribute("page", "Home");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        List<TripDto> trips = tripService.trips();
        model.addAttribute("trips", trips);
        model.addAttribute("categories", categories);
        if (principal != null) {
            Admin admin = customerService.findByUsername(principal.getName());
            session.setAttribute("username", admin.getFirstName() + " " + admin.getLastName());
//            ShoppingCart shoppingCart = admin.getCart();
//            if (shoppingCart != null) {
//                session.setAttribute("totalItems", shoppingCart.getTotalItems());
//            }
        }
        return "dashboard";
    }

}
