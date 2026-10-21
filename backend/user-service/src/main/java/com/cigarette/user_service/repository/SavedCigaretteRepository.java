package com.cigarette.user_service.repository;

import com.cigarette.user_service.entity.SavedCigarette;
import com.cigarette.user_service.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SavedCigaretteRepository extends JpaRepository<SavedCigarette, Long> {

    @Query(value =
    """
    select sc
        from SavedCigarette sc
    where sc.idUser =:userId
    and sc.status =: status
    """)
    List<SavedCigarette> findAllSavedCigaretteOfUser(@Param("userId") UUID userId, @Param("status") Status status);

    Optional<SavedCigarette> findByIdAndIdUser(Long idSavedCigarette, UUID idUser);


    @Query(value =
    """
    select *
        from saved_cigarettes sc
    join users u on u.id = sc.id_user
     where u.username =:username
        and
     sc.status =: status
    """, nativeQuery = true)
    List<SavedCigarette> findAllByUsername(@Param("username") String username, @Param("status") String status);
}
