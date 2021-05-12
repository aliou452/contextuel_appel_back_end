package com.stgson.deposit;

import com.stgson.auth.AppUser;
import com.stgson.auth.AppUserService;
import com.stgson.order.TypeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class DepositController {

    private final DepositService depositService;
    private final AppUserService appUserService;

    @Autowired
    public DepositController(DepositService depositService, AppUserService appUserService) {
        this.depositService = depositService;
        this.appUserService = appUserService;
    }

    @PostMapping("deposits")
    public void doDeposit(
            @RequestBody DepositRequest request,
            @RequestHeader("authorization") String authHeader
    )
    {
        AppUser author = depositService.amIUser(authHeader, request.getCode());
        Client client = depositService.getClient(request.getNumber());
        if((request.getAmount() > author.getPocket()) && ((request.getType()).equals(TypeOrder.MONEY.name()))){
            throw new IllegalStateException("Compte UV distributeur insuffisant");
        }
        if((request.getAmount() > author.getSeddo()) && ((request.getType()).equals(TypeOrder.SEDDO.name()))){
            throw new IllegalStateException("Compte Seddo distributeur insuffisant");
        }

        Deposit deposit = new Deposit(author, client,LocalDateTime.now(), request.getAmount(), TypeDeposit.DEPOSIT);
        depositService.addDeposit(deposit);
        appUserService.updateUser(author, -deposit.getAmount(), TypeOrder.valueOf(request.getType()));
        depositService.updateClient(client, request.getAmount(), TypeOrder.valueOf(request.getType()));
    }


    @PostMapping("withdrawal")
    public void doWithdrawal(
            @RequestBody DepositRequest request,
            @RequestHeader("authorization") String authHeader
    )
    {
        AppUser author = depositService.amIUser(authHeader, request.getCode());
        Client client = depositService.getClient(request.getNumber());
        if(1.01*request.getAmount() > client.getPocket()){
            throw new IllegalStateException("Compte client insuffisant");
        }
        Deposit deposit = new Deposit(author, client,LocalDateTime.now(), request.getAmount(), TypeDeposit.WITHDRAWAL);
        depositService.addDeposit(deposit);
        appUserService.updateUser(author, (1.01)*deposit.getAmount(), TypeOrder.MONEY);
        depositService.updateClient(client, -(1.01)*request.getAmount(), TypeOrder.MONEY);
    }

    @GetMapping("deposits")
    public List<Deposit> getDeposit(@RequestHeader("authorization") String authHeader){
        Long id = depositService.amIUser(authHeader, "").getId();
        return depositService.getAllDeposits(id);
    }

    @PostMapping("clients")
    public void createClient(@RequestBody RequestClient requestClient) {
        Client client = new Client(
                requestClient.getFirstName(),
                requestClient.getLastName(),
                requestClient.getNumber(),
                0.0,
                0.0);
        depositService.CreateClient(client);
    }

    @GetMapping("clients")
    public List<Client> getClients() {
        return depositService.getClients();
    }
}
