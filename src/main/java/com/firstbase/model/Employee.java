package com.firstbase.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//mark class as an Entity   
@Entity
// defining class name as Table name
@Table(name = "employees")
public class Employee {
	// Defining employee id as primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Name employeename;
	@Column(nullable = false)
	private String employeetitle;
	@OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Picture picture;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Name getEmployeename() {
		return employeename;
	}

	public void setEmployeename(Name employeename) {
		this.employeename = employeename;
	}

	public String getEmployeetitle() {
		return employeetitle;
	}

	public void setEmployeetitle(String employeetitle) {
		this.employeetitle = employeetitle;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

}
