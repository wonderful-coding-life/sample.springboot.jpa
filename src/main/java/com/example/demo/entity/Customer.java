package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ApiModelProperty(position = 1, notes = "고객 아이디")
	private int id;
	
	@ApiModelProperty(position = 2, notes = "고객 이름")
	private String name;
	
	@ApiModelProperty(position = 3, notes = "주소(도로명)")
	private String address;
	
	@ApiModelProperty(position = 4, notes = "연락처(휴대폰)")
	private String contact;
}
