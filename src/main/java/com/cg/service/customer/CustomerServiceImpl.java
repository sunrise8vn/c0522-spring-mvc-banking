package com.cg.service.customer;


import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import com.cg.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private TransferRepository transferRepository;


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findAllByIdNot(long id) {
        return customerRepository.findAllByIdNot(id);
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Boolean existsByIdEquals(long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Customer deposit(Customer customer, Deposit deposit) {
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
//        BigDecimal newBalance = currentBalance.add(transactionAmount);

        try {
//            customer.setBalance(newBalance);
//            Customer newCustomer = customerRepository.save(customer);

            customerRepository.incrementBalance(transactionAmount, customer.getId());

            deposit.setId(0L);
            deposit.setCustomer(customer);
            depositRepository.save(deposit);

            Optional<Customer> newCustomer = customerRepository.findById(customer.getId());

            return newCustomer.get();
        } catch (Exception e) {
            e.printStackTrace();
            customer.setBalance(currentBalance);
            return customer;
        }
    }

    @Override
    public Customer transfer(Transfer transfer) {
        Customer sender = transfer.getSender();
//        sender.setBalance(sender.getBalance().subtract(transfer.getTransactionAmount()));
//        Customer newSender = customerRepository.save(sender);

        customerRepository.reduceBalance(transfer.getTransactionAmount(), transfer.getSender().getId());

//        Customer recipient = transfer.getRecipient();
//        recipient.setBalance(recipient.getBalance().add(transfer.getTransferAmount()));
//        customerRepository.save(recipient);

        customerRepository.incrementBalance(transfer.getTransferAmount(), transfer.getRecipient().getId());

        transferRepository.save(transfer);

        Optional<Customer> newSender = customerRepository.findById(transfer.getSender().getId());

        return newSender.get();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void remove(Long id) {

    }
}
