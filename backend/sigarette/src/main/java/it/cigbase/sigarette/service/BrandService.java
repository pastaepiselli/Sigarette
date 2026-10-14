package it.cigbase.sigarette.service;

import it.cigbase.sigarette.dto.BrandResponseDTO;
import it.cigbase.sigarette.mapper.BrandMapper;
import it.cigbase.sigarette.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public BrandResponseDTO getBrandById(Long id){
        return brandRepository.findById(id)
                .map(BrandMapper::mapToDTO)
                .orElseThrow(
                        () -> new IllegalArgumentException("Brand with id: " + id + " not found")
                );
    }

    public List<BrandResponseDTO> getAllBrands(){
        return brandRepository.findAll().stream()
                .map(BrandMapper::mapToDTO)
                .toList();
    }
}
