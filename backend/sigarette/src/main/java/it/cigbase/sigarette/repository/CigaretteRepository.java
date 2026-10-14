package it.cigbase.sigarette.repository;

import it.cigbase.sigarette.entity.Cigarette;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CigaretteRepository extends JpaRepository<Cigarette, Long> {
}
