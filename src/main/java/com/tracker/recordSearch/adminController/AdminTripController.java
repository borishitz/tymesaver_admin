package com.tracker.recordSearch.adminController;

import com.tracker.recordSearch.domain.Category;
import com.tracker.recordSearch.domain.TravelAgency;
import com.tracker.recordSearch.domain.Trip;
import com.tracker.recordSearch.dto.TripDto;
import com.tracker.recordSearch.repository.TravelAgencyRepo;
import com.tracker.recordSearch.services.CategoryService;
import com.tracker.recordSearch.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminTripController {
    private final TripService tripService;

    private final CategoryService categoryService;

    private final TravelAgencyRepo travelAgencyRepo;

    @GetMapping("/trips")
    public String trips(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<TripDto> trips = tripService.allTrips();
        model.addAttribute("trips", trips);
        model.addAttribute("size", trips.size());
        return "admin/trips";
    }

    @GetMapping("/trips/{pageNo}")
    public String allTrips(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<TripDto> trips = tripService.getAllTrips(pageNo);
        model.addAttribute("title", "Manage Trips");
        model.addAttribute("size", trips.getSize());
        model.addAttribute("trips", trips);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", trips.getTotalPages());
        return "admin/trips";
    }

    @GetMapping("/search-trips/{pageNo}")
    public String searchTrip(@PathVariable("pageNo") int pageNo,
                                @RequestParam(value = "keyword") String keyword,
                                Model model, Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<TripDto> trips = tripService.searchTrips(pageNo, keyword);
        model.addAttribute("title", "Result Search Trips");
        model.addAttribute("size", trips.getSize());
        model.addAttribute("trips", trips);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", trips.getTotalPages());
        return "admin/trip-result";
    }


    @GetMapping("/new/trip/{id}")
    public String addTripPage(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        TravelAgency agency = travelAgencyRepo.findById(id).get();
        model.addAttribute("title", "Add Trip");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("agency", agency);
        model.addAttribute("categories", categories);
        model.addAttribute("tripDto", new Trip());
        return "trip/addTrip";
    }

    @GetMapping("/update-trip/{id}")
    public String updateTripForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivatedTrue();
        TripDto tripDto = tripService.getById(id);
        model.addAttribute("title", "Add Trip");
        model.addAttribute("categories", categories);
        model.addAttribute("tripDto", tripDto);
        return "admin/update-trip";
    }

    @PostMapping("/update-trip/{id}")
    public String updateTrip(@ModelAttribute("tripDto") TripDto tripDto,
                                @RequestParam("imageTrip") MultipartFile imageTrip,
                                RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            tripService.update(imageTrip, tripDto);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/menu";
    }

    @RequestMapping(value = "/enable-trip", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledTrip(Long id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            tripService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enabled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/trips/0";
    }

    @RequestMapping(value = "/delete-trip", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deletedTrip(Long id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            tripService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Deleted failed!");
        }
        return "redirect:/trip/0";
    }
}
