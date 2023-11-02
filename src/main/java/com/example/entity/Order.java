package com.example.entity;

import com.example.coverter.OrderStatusConverter;
import com.example.enums.OrderStatusVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "order_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "update orders  set deleted = true where order_number = ?")
@Where(clause = "deleted = false")
public class Order implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer orderNumber;

	private LocalDate orderDate;

	private LocalDate shippedDate;

	@Convert(converter = OrderStatusConverter.class)
	@Column(name = "status", columnDefinition = "ENUM('CREATED', 'ORDERED', 'CANCELED', 'PENDING', 'SHIPPED', 'DELIVERED')")
	private OrderStatusVo orderStatusVo;

	@Size(max = 500, message = "Comments cannot be more than 500 characters")
	@NotBlank(message = "To place Order comment must required")
	private String comments;

	private Integer customerNumber;

	//@JsonIgnore
	@ManyToOne//(cascade ={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
	@JoinColumn(name = "fk_customerNumber")
	private Customer customer;

	@OneToMany(mappedBy = "order", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<OrderDetails> orderDetails;

	@JsonIgnore
	private boolean deleted = Boolean.FALSE;
}

















