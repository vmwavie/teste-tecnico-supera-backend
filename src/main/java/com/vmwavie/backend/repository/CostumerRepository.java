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

    @Query("SELECT c FROM Costumer c WHERE c.cpf = :cpf")
    List<Costumer> findByCpfContaining(@Param("cpf") String cpf);

    @Query("SELECT c FROM Costumer c WHERE c.whatsapp = :whatsapp")
    List<Costumer> findByWhatsappContaining(@Param("whatsapp") Long whatsapp);
}
