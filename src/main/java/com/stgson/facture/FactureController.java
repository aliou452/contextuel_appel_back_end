package com.stgson.facture;

import com.stgson.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class FactureController {

    private final FactureService factureService;

    @Autowired
    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @GetMapping("factures")
    public List<Facture> getFactures() {
        return factureService.getFactures();
    }

    @PostMapping("factures")
    public void addFacture(@RequestBody FactureRequest factureRequest) {
        Facture facture = new Facture(factureRequest.getContract(), FactureType.valueOf(factureRequest.getType()));
        this.factureService.addFacture(facture);
    }

    @GetMapping("factures/{contract}")
    public Boolean getFacture(@PathVariable Long contract) {
        return factureService.getFacture(contract);
    }

    @GetMapping("factures/{contract}/onefactures")
    public List<OneFacture> getOneFactures(@PathVariable Long contract) {
        return this.factureService.getOneFactures(contract);
    }

    @PostMapping("factures/{contract}/onefactures")
    public void addToFacture(@PathVariable Long contract, @RequestBody OneFactureRequest oneFactureRequest) {
        this.factureService.addToFacture(contract, oneFactureRequest);
    }

    @GetMapping("factures/{contract}/onefactures/{fact_id}")
    public OneFacture getOneFacture(@PathVariable Long contract, @PathVariable Long fact_id) {
        return this.factureService.getOneFacture(contract, fact_id);
    }

    @PostMapping("onefactures/{fact_id}")
    public void payment(@PathVariable Long fact_id,
                        @RequestHeader("Authorization") String authHeader,
                        @RequestBody FactRequest request) {
        AppUser dist = this.factureService.amIUser(authHeader, request.getCode());
        this.factureService.payment(fact_id, dist);
    }
}
