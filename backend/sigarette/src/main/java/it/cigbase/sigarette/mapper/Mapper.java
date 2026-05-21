package it.cigbase.sigarette.mapper;

import it.cigbase.sigarette.dto.SigarettaDTO;
import it.cigbase.sigarette.entity.Sigaretta;

public class Mapper {
    public static SigarettaDTO daEntityADTO(Sigaretta s){
        return new SigarettaDTO(
            s.getId(),
                s.getBrand(),
                s.getPrezzo(),
                s.getNicotina(),
                s.getCatrame(),
                s.getTipo(),
                s.getDescrizione(),
                s.getUrl_immagine()
        );
    }
}
