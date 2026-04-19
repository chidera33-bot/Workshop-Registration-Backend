package com.example.workshopsystem.controller;

import com.example.workshopsystem.dto.UserRequest;
import com.example.workshopsystem.dto.WorkshopRequest;
import com.example.workshopsystem.entity.Role;
import com.example.workshopsystem.entity.User;
import com.example.workshopsystem.service.RegistrationService;
import com.example.workshopsystem.service.UserService;
import com.example.workshopsystem.service.WorkshopService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    private final WorkshopService workshopService;
    private final UserService userService;
    private final RegistrationService registrationService;

    public HomeController(WorkshopService workshopService, UserService userService,
                          RegistrationService registrationService) {
        this.workshopService = workshopService;
        this.userService = userService;
        this.registrationService = registrationService;
    }

    // Home page - list of workshops
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("workshops", workshopService.getAllWorkshops());
        return "home";
    }

    // Workshop detail page
    @GetMapping("/workshops/{id}")
    public String workshopDetail(@PathVariable Long id, Model model) {
        model.addAttribute("workshop", workshopService.getWorkshopById(id));
        return "workshop-detail";
    }

    // Login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Register page
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userRequest") UserRequest request,
                           BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.createUser(request, Role.ATTENDEE);
            redirectAttributes.addFlashAttribute("success", "Account created! Please log in.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.email", e.getMessage());
            return "register";
        }
    }

    // My Registrations
    @GetMapping("/my/registrations")
    public String myRegistrations(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUserEntityByEmail(userDetails.getUsername());
        model.addAttribute("registrations", registrationService.getMyRegistrations(user.getId()));
        return "my-registrations";
    }

    // Register for a workshop (from UI)
    @PostMapping("/workshops/{id}/register")
    public String registerForWorkshop(@PathVariable Long id,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserEntityByEmail(userDetails.getUsername());
            var request = new com.example.workshopsystem.dto.RegistrationRequest();
            request.setUserId(user.getId());
            request.setWorkshopId(id);
            registrationService.registerUser(request);
            redirectAttributes.addFlashAttribute("success", "Successfully registered!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/workshops/" + id;
    }

    // Cancel registration (from UI)
    @PostMapping("/my/registrations/{id}/cancel")
    public String cancelRegistration(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetails userDetails,
                                     RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserEntityByEmail(userDetails.getUsername());
            registrationService.cancelRegistration(id, user.getId());
            redirectAttributes.addFlashAttribute("success", "Registration cancelled.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/my/registrations";
    }

    // ---- Admin pages ----

    @GetMapping("/admin/workshops")
    public String adminWorkshops(Model model) {
        model.addAttribute("workshops", workshopService.getAllWorkshops());
        return "admin/workshops";
    }

    @GetMapping("/admin/workshops/new")
    public String newWorkshopForm(Model model) {
        model.addAttribute("workshopRequest", new WorkshopRequest());
        return "admin/workshop-form";
    }

    @PostMapping("/admin/workshops/new")
    public String createWorkshop(@Valid @ModelAttribute("workshopRequest") WorkshopRequest request,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/workshop-form";
        }
        workshopService.createWorkshop(request);
        redirectAttributes.addFlashAttribute("success", "Workshop created!");
        return "redirect:/admin/workshops";
    }

    @GetMapping("/admin/workshops/{id}/edit")
    public String editWorkshopForm(@PathVariable Long id, Model model) {
        var workshop = workshopService.getWorkshopById(id);
        WorkshopRequest request = new WorkshopRequest();
        request.setTitle(workshop.getTitle());
        request.setDescription(workshop.getDescription());
        request.setLocation(workshop.getLocation());
        request.setStartDatetime(workshop.getStartDatetime());
        request.setTotalSeats(workshop.getTotalSeats());
        model.addAttribute("workshopRequest", request);
        model.addAttribute("workshopId", id);
        return "admin/workshop-form";
    }

    @PostMapping("/admin/workshops/{id}/edit")
    public String updateWorkshop(@PathVariable Long id,
                                 @Valid @ModelAttribute("workshopRequest") WorkshopRequest request,
                                 BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/workshop-form";
        }
        workshopService.updateWorkshop(id, request);
        redirectAttributes.addFlashAttribute("success", "Workshop updated!");
        return "redirect:/admin/workshops";
    }

    @PostMapping("/admin/workshops/{id}/cancel")
    public String cancelWorkshopAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        workshopService.cancelWorkshop(id);
        redirectAttributes.addFlashAttribute("success", "Workshop cancelled.");
        return "redirect:/admin/workshops";
    }

    @GetMapping("/admin/workshops/{id}/registrations")
    public String viewWorkshopRegistrations(@PathVariable Long id, Model model) {
        model.addAttribute("workshop", workshopService.getWorkshopById(id));
        model.addAttribute("registrations", registrationService.getRegistrationsByWorkshop(id));
        return "admin/registrations";
    }
}

