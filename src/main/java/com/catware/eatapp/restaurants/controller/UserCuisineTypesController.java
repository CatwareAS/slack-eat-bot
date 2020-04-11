package com.catware.eatapp.restaurants.controller;

import com.catware.eatapp.restaurants.service.UserCuisinePreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserCuisineTypesController {
    private final UserCuisinePreferencesService userCuisinePreferencesService;

    public UserCuisineTypesController(UserCuisinePreferencesService userCuisinePreferencesService) {
        this.userCuisinePreferencesService = userCuisinePreferencesService;
    }

    @GetMapping("/preferences")
    public ResponseEntity<List<String>> getPreferences(@RequestParam(name = "userId") String userId) {
        return ResponseEntity.ok(userCuisinePreferencesService.getUserExcludedCuisineTypes(userId));
    }

    @PostMapping("/choose-preferences")
    public ResponseEntity<String> savePreferences(@RequestParam(name = "userId") String userId,
                                                  @RequestBody List<String> userPreferences) {
        userCuisinePreferencesService.saveUserExcludedCuisineTypes(userId, userPreferences);
        return ResponseEntity.ok("Added");
    }
}
