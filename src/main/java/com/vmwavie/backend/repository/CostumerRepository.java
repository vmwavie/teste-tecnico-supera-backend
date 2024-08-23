package com.vmwavie.backend.repository;

import com.vmwavie.backend.model.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostumerRepository extends JpaRepository<Costumer, Long> {
    Optional<Costumer> findByCpf(String cpf);

    List<Costumer> findByNameContainingIgnoreCase(String filter);

    @Query(value = "SELECT * FROM Costumer c WHERE c.cpf LIKE %:cpf% LIMIT 10", nativeQuery = true)
    List<Costumer> findByCpfContaining(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM Costumer c WHERE c.whatsapp = :whatsapp LIMIT 10", nativeQuery = true)
    List<Costumer> findByWhatsappContaining(@Param("whatsapp") Long whatsapp);
}
