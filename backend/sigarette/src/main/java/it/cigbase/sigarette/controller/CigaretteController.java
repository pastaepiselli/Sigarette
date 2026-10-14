package it.cigbase.sigarette.controller;

import it.cigbase.sigarette.dto.CigaretteResponseDTO;
import it.cigbase.sigarette.service.CigaretteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/cigarette")
public class CigaretteController {

    @Autowired
    private CigaretteService cigaretteService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<CigaretteResponseDTO> getCigaretteById(@PathVariable Long id){
        CigaretteResponseDTO cigarette = cigaretteService.getCigaretteById(id);
        return ResponseEntity.ok(cigarette);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<CigaretteResponseDTO>> getAllCigarette(){
        List<CigaretteResponseDTO> cigarettes = cigaretteService.getAllCigarette();
        return ResponseEntity.ok(cigarettes);
    }
}
