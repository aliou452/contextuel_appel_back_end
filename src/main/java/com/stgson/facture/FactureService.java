package com.stgson.facture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private OneFactureRepository oneFactureRepository;

    Facture getFacture(Long contractNum) {
        return this.factureRepository.findByContract(contractNum);
    }

    List<Facture> getFactures() {
        return this.factureRepository.findAll();
    }

    void addFacture(Facture facture) {
        this.factureRepository.save(facture);
    }

    void addToFacture(Long contractNum, OneFactureRequest oneFactureRequest) {

        boolean isDate = oneFactureRequest.getDate().matches("[0-9]+");
        if(!isDate) {
            throw new IllegalStateException("Format date non valide");
        }

        String dateNum = oneFactureRequest.getDate();
        int year = Integer.parseInt("20"+dateNum.substring(0, 2));
        int month = Integer.parseInt(dateNum.substring(2, 4));
        int day = Integer.parseInt(dateNum.substring(4, 6));

        OneFacture oneFacture = new OneFacture(oneFactureRequest.getId(),
                LocalDate.of(year, month, day),
                oneFactureRequest.getAmount(),
                false);
        this.oneFactureRepository.save(oneFacture);
        Facture facture = this.factureRepository.findByContract(contractNum);
        facture.addTo(oneFacture);
        this.factureRepository.save(facture);
    }

    List<OneFacture> getOneFactures(Long contractNum) {
        List<OneFacture> listOneFactures = this.factureRepository.findByContract(contractNum).getList();
        return listOneFactures.stream()
                .filter(oneFacture -> !oneFacture.getPaid())
                .collect(Collectors.toList());
    }

    OneFacture getOneFacture(Long contractNum, Long fact_id) {
        Facture facture = this.factureRepository.findByContract(contractNum);
        return facture.getFacture(fact_id);
    }

}
