package com.stgson.facture;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    Facture findByContract(Long contract);
}
