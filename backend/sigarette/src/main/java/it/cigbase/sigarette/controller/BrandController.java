package it.cigbase.sigarette.controller;

import it.cigbase.sigarette.dto.BrandResponseDTO;
import it.cigbase.sigarette.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<BrandResponseDTO> getBrandById(@PathVariable Long id){
        BrandResponseDTO brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<BrandResponseDTO>> getAllBrands(){
        List<BrandResponseDTO> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

}
