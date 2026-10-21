package com.cigarette.user_service.repository;

import com.cigarette.user_service.entity.SavedCigarette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SavedCigaretteRepository extends JpaRepository<SavedCigarette, Long> {

    @Query(value =
    """
    select *
        from saved_cigarettes sv
    where sv.id_user =:userId
    """, nativeQuery = true)
    List<SavedCigarette> findAllSavedCigaretteOfUser(UUID userId);
}
