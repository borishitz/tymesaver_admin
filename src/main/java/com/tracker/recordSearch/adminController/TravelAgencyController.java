package com.tracker.recordSearch.adminController;

import com.tracker.recordSearch.domain.TravelAgency;
import com.tracker.recordSearch.repository.TravelAgencyRepo;
import com.tracker.recordSearch.services.TravelAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class TravelAgencyController {

    @Autowired
    TravelAgencyService travelAgencySer;

    @Autowired
    TravelAgencyRepo travelAgencyRepo;

    //    display all TravelAgency
    @GetMapping("/agencies")
    public String getAllTravelAgency(Model model){
        List<TravelAgency> travelAgencyList = travelAgencySer.getAllTravelAgency();
//        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("travelAgencyList", travelAgencyList);
        return "travelAgency/AllTravelAgency";
    }

    //    Create a new TravelAgency
    @GetMapping("/addTravelAgency")
    public String addTravelAgency(Model model){
        TravelAgency travelAgency= new TravelAgency();
        model.addAttribute("travelAgency", travelAgency);
        return "travelAgency/addTravelAgency";
    }

    //SAVE TravelAgency TO DATABASE
    @PostMapping("/saveTravelAgency")
    public String saveTravelAgency(TravelAgency travelAgency) {
        travelAgencySer.saveTravelAgency(travelAgency);
        return "redirect:/agencies";
    }

    //  EDIT TravelAgency
    @RequestMapping("/edit/travelAgency/{id}")
    public String EditTravelAgencyForm(@PathVariable("id") Long id, Model model){
        TravelAgency travelAgency = travelAgencyRepo.findById(id).get();
        model.addAttribute("travelAgency", travelAgency);
        return "travelAgency/addTravelAgency";
    }

}
