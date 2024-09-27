package com.tracker.recordSearch.adminController;

import com.tracker.recordSearch.domain.Category;
import com.tracker.recordSearch.domain.TravelAgency;
import com.tracker.recordSearch.domain.Trip;
import com.tracker.recordSearch.dto.CategoryDto;
import com.tracker.recordSearch.dto.TripDto;
import com.tracker.recordSearch.repository.TravelAgencyRepo;
import com.tracker.recordSearch.repository.TripRepository;
import com.tracker.recordSearch.services.CategoryService;
import com.tracker.recordSearch.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TripController {
    private final TripService tripService;

    private final TripRepository tripRepository;

    private final CategoryService categoryService;

    @Autowired
    TravelAgencyRepo travelAgencyRepo;

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("page", "Trips");
        model.addAttribute("title", "Menu");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        List<TripDto> trips = tripService.trips();
        model.addAttribute("trips", trips);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/trip-detail/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        TripDto trip = tripService.getById(id);
        List<TripDto> tripDtoList = tripService.findAllByCategory(trip.getCategory().getName());
        model.addAttribute("trips", tripDtoList);
        model.addAttribute("title", "Trip Detail");
        model.addAttribute("page", "Trip Detail");
        model.addAttribute("tripDetail", trip);
        return "trip-detail";
    }

    @GetMapping("/trip-detail")
    public String shopDetail(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);
        List<TripDto> trips = tripService.randomTrip();
        List<TripDto> listView = tripService.listViewTrips();
        model.addAttribute("productViews", listView);
        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("trips", trips);
        return "shop-detail";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);
        List<TripDto> trips = tripService.filterHighTrips();
        List<TripDto> listView = tripService.listViewTrips();
        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("tripViews", listView);
        model.addAttribute("trips", trips);
        return "shop-detail";
    }

    @GetMapping("/lower-price")
    public String filterLowerPrice(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);
        List<TripDto> trips = tripService.filterLowerTrips();
        List<TripDto> listView = tripService.listViewTrips();
        model.addAttribute("tripViews", listView);
        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("trips", trips);
        return "shop-detail";
    }

    @GetMapping("/find-trips/{id}")
    public String tripsInCategory(@PathVariable("id") Long id, Model model) {
        List<CategoryDto> categoryDtos = categoryService.getCategoriesAndSize();
        List<TripDto> tripDtos = tripService.findByCategoryId(id);
        List<TripDto> listView = tripService.listViewTrips();
        model.addAttribute("tripViews", listView);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("title", tripDtos.get(0).getCategory().getName());
        model.addAttribute("page", "trip-Category");
        model.addAttribute("trips", tripDtos);
        return "trips";
    }

    @GetMapping("/search-trip")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<CategoryDto> categoryDtos = categoryService.getCategoriesAndSize();
        List<TripDto> tripDtos = tripService.searchTrips(keyword);
        List<TripDto> listView = tripService.listViewTrips();
        model.addAttribute("tripViews", listView);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("title", "Search Trips");
        model.addAttribute("page", "Result Search");
        model.addAttribute("trips", tripDtos);
        return "trips";
    }

    @PostMapping("/saveTrip")
    public String saveTrip(Trip trip) {
        tripService.saveTrip(trip);
        return "redirect:/";
    }

    //find all trip in a travel agency
    @RequestMapping(value = "/travel/agency/trips/{id}")
    public String getTripByTravelAgencyId(@PathVariable Long id,Model model){
        List<Trip> travelAgencyTrip = tripService.getTripByTravelAgencyId(id);
        TravelAgency travelAgency = travelAgencyRepo.findById(id).get();
        model.addAttribute("travelAgencyTrip",travelAgencyTrip);
        model.addAttribute("travelAgency", travelAgency);
        return "trip/travelAgencyTrips";
    }

    //SEE THE FULL PROFILE OF TRIP
    @GetMapping("/trip/detail/{id}")
    public String showTripDetail(@PathVariable("id") Long id, Model model){
        Trip trip = tripRepository.findById(id).get();
        model.addAttribute("trip", trip);
        return "trip/tripDetail";
    }

}
