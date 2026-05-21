package it.cigbase.sigarette.dto;

public record SigarettaDTO(
        long id,
        String brand,
        double prezzo,
        double nicotina,
        double catrame,
        String tipo,
        String descrizione,
        String url_immagine
) {}
