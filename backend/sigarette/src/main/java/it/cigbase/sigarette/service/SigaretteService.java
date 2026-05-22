package it.cigbase.sigarette.service;

import it.cigbase.sigarette.dto.SigarettaDTO;
import it.cigbase.sigarette.exception.IdNotFoundException;
import it.cigbase.sigarette.mapper.Mapper;
import it.cigbase.sigarette.repository.SigaretteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SigaretteService {

    @Autowired
    private SigaretteRepo repo;

    public List<SigarettaDTO> findAllSigarette(){
        return repo.findAll()
                .stream()
                .map(Mapper::daEntityADTO)
                .toList();
    }

    public SigarettaDTO findSigaretta(int id){
        return repo.findById(id)
                .map(Mapper::daEntityADTO)
                .orElseThrow(() -> new IdNotFoundException("Sigaretta con id: " + id + " non trovata"));
    }
}
