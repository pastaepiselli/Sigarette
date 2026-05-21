package it.cigbase.sigarette.repository;

import it.cigbase.sigarette.entity.Sigaretta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SigaretteRepo extends JpaRepository<Sigaretta, Integer> {
}
