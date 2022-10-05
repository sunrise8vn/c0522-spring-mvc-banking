package com.cg.controller;


import com.cg.model.Customer;
import com.cg.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ModelAndView showListPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customer/list");

        Customer customer = new Customer();
        customer.setFullName("NVA");
        customer.setEmail("nva@co.cc");
        customer.setPhone("2345");
        customer.setAddress("28 NTP");
        customer.setBalance(new BigDecimal(0L));
        Customer newCustomer = customerService.save(customer);

        System.out.println(newCustomer);

        return modelAndView;
    }
}
