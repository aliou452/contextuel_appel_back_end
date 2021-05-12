package com.stgson.deposit;

import com.stgson.auth.AppUser;
import com.stgson.order.TypeOrder;
import com.stgson.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final ClientRepository clientRepository;
    private final TransactionService transactionService;

    @Autowired
    public DepositService(DepositRepository depositRepository, ClientRepository clientRepository, TransactionService transactionService) {
        this.depositRepository = depositRepository;
        this.clientRepository = clientRepository;
        this.transactionService = transactionService;
    }

    public List<Deposit> getAllDeposits(@RequestHeader Long id) {
        return new ArrayList<>(depositRepository.findByDistId(id));
    }

    public void addDeposit(Deposit deposit) {
        depositRepository.save(deposit);
    }

    public AppUser amIUser(String authHeader, String code){
        return transactionService.amIUser(authHeader, code);
    }

    public Client getClient(String number) {
        return clientRepository.findByNumber(number);
    }

    public void CreateClient(Client client) {
        clientRepository.save(client);
    }

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public void updateClient(Client client, Double amount, TypeOrder typeOrder) {
        if(typeOrder.equals(TypeOrder.MONEY)) {
            client.setPocket(client.getPocket() + amount);
        } else{
            client.setSeddo(client.getSeddo() + amount);
        }
        clientRepository.save(client);
    }

}
