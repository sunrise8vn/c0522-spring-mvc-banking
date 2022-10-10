package com.cg.service.customer;


import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
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
    public Customer deposit(Customer customer, Deposit deposit) {
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal transactionAmount = deposit.getTransactionAmount();
        BigDecimal newBalance = currentBalance.add(transactionAmount);

        try {
            customer.setBalance(newBalance);
            Customer newCustomer = customerRepository.save(customer);

            deposit.setId(0L);
            deposit.setCustomer(customer);
            depositRepository.save(deposit);

            return newCustomer;
        } catch (Exception e) {
            e.printStackTrace();
            customer.setBalance(currentBalance);
            return customer;
        }
    }

    @Override
    public Customer transfer(Transfer transfer) {
        Customer sender = transfer.getSender();
        sender.setBalance(sender.getBalance().subtract(transfer.getTransactionAmount()));
        customerRepository.save(sender);

        Customer recipient = transfer.getRecipient();
        recipient.setBalance(recipient.getBalance().add(transfer.getTransferAmount()));
        customerRepository.save(recipient);



//        if (currentBalanceSender.compareTo(transactionAmount) < 0) {
//            return sender;
//        }

//        BigDecimal newBalance = currentBalance.add(transactionAmount);

        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void remove(Long id) {

    }
}
