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

    // this is not best alogorithmic for this way, the best in
    // my opninion is use sql alogithm for mapping results with more compatibility with input, this dont make this actualy, this code is a bullshit.

    @Query(value = "SELECT * FROM Costumer c WHERE c.name ILIKE %:name% LIMIT 10", nativeQuery = true)
    List<Costumer> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query(value = "SELECT * FROM Costumer c WHERE c.cpf ILIKE %:cpf% LIMIT 10", nativeQuery = true)
    List<Costumer> findByCpfContaining(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM Costumer c WHERE c.whatsapp ILIKE %:whatsapp% LIMIT 10", nativeQuery = true)
    List<Costumer> findByWhatsappContaining(@Param("whatsapp") Long whatsapp);
}
