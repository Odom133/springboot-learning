package com.balancika.service;

import com.balancika.Exception.ResourceNotFoundException;
import com.balancika.entity.Customer;
import com.balancika.model.dto.CustomerDTO;
import com.balancika.model.request.CustomerRequest;
import com.balancika.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerDTO create(CustomerRequest request) {
        boolean duplicate = customerRepository.existsByName(request.getName());
        if (duplicate) {
            throw new DuplicateKeyException("Duplicate customer name: "+ request.getName());
        }
        return new CustomerDTO(customerRepository.save(Customer.builder()
                .name(request.getName())
                .description(request.getDescription())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build()));

    }

    @Transactional
    public CustomerDTO update(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        boolean duplicate = customerRepository.existsByNameAndId(request.getName(),id);
        if (duplicate) {
            throw new DuplicateKeyException("Duplicate customer name: "+ request.getName());
        }
        customer.setName(request.getName());
        customer.setDescription(request.getDescription());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        return new CustomerDTO(customerRepository.save(customer));
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }


    @Transactional(readOnly = true)
    public CustomerDTO getById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Transactional(readOnly = true)
    public Page<CustomerDTO> getAll(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customer -> CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .description(customer.getDescription())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build());
    }

}
