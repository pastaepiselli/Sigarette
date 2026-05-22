package it.cigbase.sigarette.repository;

import it.cigbase.sigarette.entity.Sigaretta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SigaretteRepo extends JpaRepository<Sigaretta, Integer> {
}
