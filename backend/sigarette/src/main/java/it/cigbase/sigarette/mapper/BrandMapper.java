package it.cigbase.sigarette.mapper;

import it.cigbase.sigarette.dto.BrandResponseDTO;
import it.cigbase.sigarette.entity.Brand;

public class BrandMapper {

    public static BrandResponseDTO mapToDTO(Brand brand){
        return new BrandResponseDTO(
                brand.getId(),
                brand.getName(),
                brand.getCountry(),
                brand.getDescription(),
                brand.getLogoUrl()
        );
    }
}
