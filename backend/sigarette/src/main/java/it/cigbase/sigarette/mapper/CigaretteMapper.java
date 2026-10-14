package it.cigbase.sigarette.mapper;

import it.cigbase.sigarette.dto.CigaretteResponseDTO;
import it.cigbase.sigarette.entity.Cigarette;

public class CigaretteMapper {
    public static CigaretteResponseDTO mapToDTO(Cigarette cigarette){
        return new CigaretteResponseDTO(
                cigarette.getId(),
                BrandMapper.mapToDTO(cigarette.getBrand()),
                cigarette.getName(),
                cigarette.getDescription(),
                cigarette.getImageUrl(),
                cigarette.getNicotine(),
                cigarette.getTar(),
                cigarette.getCo(),
                cigarette.getFormat(),
                cigarette.getSticksTot(),
                cigarette.getIsMenthol(),
                cigarette.getAvgRating(),
                cigarette.getRatingsCount(),
                cigarette.getCreatedAt()
        );
    }
}
