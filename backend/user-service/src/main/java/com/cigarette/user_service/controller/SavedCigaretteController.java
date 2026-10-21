package com.cigarette.user_service.controller;

import com.cigarette.user_service.dto.SavedCigaretteRequestDTO;
import com.cigarette.user_service.dto.SavedCigaretteResponseDTO;
import com.cigarette.user_service.service.SavedCigaretteService;
import com.cigarette.user_service.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class SavedCigaretteController {

    @Autowired
    private SavedCigaretteService service;

    @GetMapping(path = "/me/saved-cigarettes")
    public ResponseEntity<List<SavedCigaretteResponseDTO>> findAllSavedCigaretteOfUser(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam Status status
    )
    {
        List<SavedCigaretteResponseDTO> savedCigarette = service.findAllSavedCigaretteOfUser(jwt, status);
        return ResponseEntity.ok(savedCigarette);
    }

    @GetMapping(path = "/{username}/saved-cigarettes")
    public ResponseEntity<List<SavedCigaretteResponseDTO>> findAllSavedCigaretteOfUserByUsername(
            @PathVariable String username,
            @RequestParam Status status
    )
    {
        List<SavedCigaretteResponseDTO> list =  service.findAllSavedCigaretteOfUserByUsername(username, status);
        return ResponseEntity.ok(list);
    }

    @PostMapping(path = "/me/saved-cigarettes")
    public ResponseEntity<SavedCigaretteResponseDTO> saveCigarette(
            @RequestBody SavedCigaretteRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    )
    {
        SavedCigaretteResponseDTO savedCigarette = service.saveCigarette(dto, jwt);
        return ResponseEntity.ok(savedCigarette);
    }

    @DeleteMapping(path = "/me/saved-cigarettes/{idSavedCig}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCigarette(
            @PathVariable Long idSavedCig,
            @AuthenticationPrincipal Jwt jwt
    )
    {
        service.deleteSavedCigarette(idSavedCig, jwt);
    }






}
