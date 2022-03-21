package com.example.persistence;

import javax.persistence.*;

import com.example.enums.Currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_conversion")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Conversion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "source_currency")
	@Enumerated(EnumType.STRING)
	private Currency sourceCurrency;

	@Column(name = "target_currency")
	@Enumerated(EnumType.STRING)
	private Currency targetCurrency;

	@Column(name = "source_amount")
	private BigDecimal sourceAmount;

	@Column(name = "converted_amount")
	private BigDecimal convertedAmount;

	@Column(name = "created_at", updatable = false)
	@CreationTimestamp
	@JsonIgnore
	private Timestamp createdAt;

}
