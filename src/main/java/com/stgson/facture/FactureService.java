package com.stgson.facture;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserService;
import com.stgson.transaction.Transaction;
import com.stgson.transaction.TransactionService;
import com.stgson.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FactureService {

    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private OneFactureRepository oneFactureRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AppUserService appUserService;

    Boolean getFacture(Long contractNum) {
        boolean present = this.factureRepository.findByContract(contractNum).isPresent();
        return present;
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
        Facture facture = this.factureRepository.findByContract(contractNum)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Contract doesn't exist")
                );
        facture.addTo(oneFacture);
        this.factureRepository.save(facture);
    }

    List<OneFacture> getOneFactures(Long contractNum) {
        List<OneFacture> listOneFactures = this.factureRepository.findByContract(contractNum)
                .orElseThrow(() -> new UsernameNotFoundException("Contract does not exist"))
                .getList();
        return listOneFactures.stream()
                .filter(oneFacture -> !oneFacture.getPaid())
                .collect(Collectors.toList());
    }

    OneFacture getOneFacture(Long contractNum, Long fact_id) {
        Facture facture = this.factureRepository.findByContract(contractNum)
                .orElseThrow(() -> new UsernameNotFoundException("Contract does not exist"));
        return facture.getOneFacture(fact_id);
    }

    public AppUser amIUser(String authHeader, String code){
        return transactionService.amIUser(authHeader, code);
    }

    void payment(Long fact_id, AppUser dist) {
        OneFacture oneFacture = this.oneFactureRepository.findById(fact_id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Facture not found")
                );
        Double pocket = dist.getPocket();
        if (pocket >= oneFacture.getAmount()) {

            dist.setPocket(pocket - oneFacture.getAmount());
            oneFacture.setPaid(true);
            appUserService.saveUser(dist);
            oneFactureRepository.save(oneFacture);
            transactionService.addTransaction(
                    new Transaction(dist, LocalDateTime.now(), oneFacture.getAmount(), TransactionType.FACTURE)
            );
        } else {
            throw new IllegalStateException("Pocket is not enough");
        }

    }

}
