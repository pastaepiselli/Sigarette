package it.cigbase.sigarette.controller;

import it.cigbase.sigarette.dto.CigaretteResponseDTO;
import it.cigbase.sigarette.service.CigaretteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cigarette")
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

    // TODO: sostiuire questi metodi con una coda di activemq o una coda qualsiasi
    /**
     * Endpoint chiamato dal microsevizio di review quando viene aggiunta una review su una sigaretta.
     * @param idCigarette
     * @param rating da 0.5 a 5
     * @return sigaretta con con avg aumentato
     */
    @PostMapping(path = "/{idCigarette}/reviews")
    public ResponseEntity<CigaretteResponseDTO> addRating(
            @PathVariable Long idCigarette,
            @RequestParam Float rating
    )
    {
        CigaretteResponseDTO cigarette = cigaretteService.addRating(idCigarette, rating);
        return ResponseEntity.ok(cigarette);
    }
}
