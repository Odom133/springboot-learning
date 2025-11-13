package com.balancika.model.dto;

import com.balancika.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;
    private String name;
    private String description;
    private String email;
    private String phone;

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.description = customer.getDescription();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
    }

}
