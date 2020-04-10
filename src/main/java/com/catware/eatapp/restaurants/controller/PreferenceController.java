package com.catware.eatapp.restaurants.controller;

import com.catware.eatapp.restaurants.service.PreferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PreferenceController {
    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping("/preferences")
    public ResponseEntity<Map<String, Boolean>> getPreferences(@RequestParam(name = "userId") String userId) {
        return ResponseEntity.ok(preferenceService.getPreferences(userId));
    }

    @PostMapping("/choose-preferences")
    public ResponseEntity<String> savePreferences(@RequestParam(name = "userId") String userId,
                                                  @RequestBody Map<String, Boolean> preferenceValues) {
        preferenceService.savePreferences(userId, preferenceValues);
        return ResponseEntity.ok("Added");
    }
}
