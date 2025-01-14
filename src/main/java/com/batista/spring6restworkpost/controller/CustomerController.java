package com.batista.spring6restworkpost.controller;


import com.batista.spring6restworkpost.model.Customer;
import com.batista.spring6restworkpost.service.CustomerService;
import com.sun.net.httpserver.Headers;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
//@AllArgsConstructor
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PatchMapping(value = "{requestId}")
    public ResponseEntity patchById(@PathVariable("requestId") UUID id, @RequestBody Customer customer) {

        customerService.patchById(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT) ;
    }

    @DeleteMapping(value = "{requestId}")
    public ResponseEntity deletebyId(@PathVariable("requestId") UUID id) {

        customerService.deleteById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity updateCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer customer) {

        customerService.updateCustomer(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomer() {
        return customerService.getCustomers();
    }



    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("id") UUID id) {
         return customerService.getCustomer(id);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Customer customer) {

        Customer cust = customerService.saveCustomer(customer);

        log.info ("Controller" + cust.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + cust.getId());


        return new ResponseEntity(cust, headers, HttpStatus.CREATED);

        

    }
      

   
}
