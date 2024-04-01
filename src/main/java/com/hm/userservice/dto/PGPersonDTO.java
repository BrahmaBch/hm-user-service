package com.hm.userservice.dto;

public class PGPersonDTO {
	private Long id;
	private Integer age;
	private String firstName;
	private String lastName;
    private AddressDTO addressDto;
	public PGPersonDTO() {
	}
	public PGPersonDTO(Long id, Integer age, String firstName, String lastName, AddressDTO addressDto) {
		super();
		this.id = id;
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressDto = addressDto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public AddressDTO getAddressDto() {
		return addressDto;
	}
	public void setAddressDto(AddressDTO addressDto) {
		this.addressDto = addressDto;
	}

    

}
