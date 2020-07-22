package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ApiModelProperty(notes = "고객 이름", value = "홍길동")
	private String name;
	
	@ApiModelProperty(notes = "주소(도로명)", value = "서울시 강남구 대치1동")
	private String address;
	
	@ApiModelProperty(notes = "연락처(휴대폰)", value = "010-1111-1111")
	private String contact;
}
