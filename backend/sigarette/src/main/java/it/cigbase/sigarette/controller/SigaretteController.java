package it.cigbase.sigarette.controller;

import it.cigbase.sigarette.dto.SigarettaDTO;
import it.cigbase.sigarette.service.SigaretteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/sigarette")
public class SigaretteController {

    @Autowired
    private SigaretteService service;

    @GetMapping( path = "")
    public List<SigarettaDTO> getAllSigarette(){
        return service.findAllSigarette();
    }
}
