package com.dev.springcar.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private String dni;
	@NotNull
	private String name;
	@NotNull
	private String address;
	@NotNull
	private String phone;
	@NotNull
	private Date dob;
	@NotNull
	private String password;
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Rol rol;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<Car> cars = new HashSet<Car>();
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(@NotNull String dni, @NotNull String name, @NotNull String address, @NotNull String phone,
			@NotNull Date dob, @NotNull String password, @NotNull Rol rol) {
		super();
		this.dni = dni;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.dob = dob;
		this.password = password;
		this.rol = rol;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", dni='" + dni + '\'' +
				", name='" + name + '\'' +
				", address='" + address + '\'' +
				", phone='" + phone + '\'' +
				", dob=" + dob +
				", password='" + password + '\'' +
				", rol=" + rol +
				", cars=" + cars +
				'}';
	}
}
