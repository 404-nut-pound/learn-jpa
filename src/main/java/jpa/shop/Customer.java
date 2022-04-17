package jpa.shop;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Customer {

//  @Id
//  private Long id;
//  private String name;
  @Id
  @GeneratedValue
  @Column(name = "customer_id")
  private Long id;
  private String name;
  private String city;
  private String street;
  private String zipcode;
  @OneToMany(mappedBy = "customer")
  private List<Order> orderList = Collections.emptyList();

}
