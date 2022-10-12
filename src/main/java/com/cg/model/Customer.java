package com.cg.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    @NotEmpty(message = "Vui lòng nhập tên đầy đủ")
    @Size(min = 10, max = 50, message = "Họ tên có độ dài nằm trong khoảng 10-50 ký tự")
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^[0][0-9]{9}$", message = "Vui lòng nhập đúng định dạng số điện thoại")
    private String phone;
    private String address;

    @Column(precision = 12, scale = 0, nullable = false, updatable = false)
    private BigDecimal balance;


    @OneToMany
    private List<Deposit> deposits;


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", balance=" + balance +
                '}';
    }

}
