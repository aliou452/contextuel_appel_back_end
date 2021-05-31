package com.stgson.deposit;

import com.stgson.auth.AppUser;
import com.stgson.transaction.TransactionService;
import com.stgson.transaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Client client = clientRepository.findByNumber(number);
        if (client == null) {
            throw new IllegalArgumentException("This client does not exist");
        }
        return client;
    }

    public Set<Client> getClDist(Long id) {
        List<Deposit> deposits = new ArrayList<>(depositRepository.getTransBetweenDates(id,
                LocalDateTime.now().minusMonths(1),
                LocalDateTime.now()));

        return deposits.stream().map(Deposit::getClient).collect(Collectors.toSet());
    }

    public void CreateClient(Client client) {
        clientRepository.save(client);
    }

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public void updateClient(Client client, Double amount, String transType) {
        if(transType.equals(TransactionType.MONEY.name())) {
            client.setPocket(client.getPocket() + amount);
        } else{
            client.setSeddo(client.getSeddo() + amount);
        }
        clientRepository.save(client);
    }

}
