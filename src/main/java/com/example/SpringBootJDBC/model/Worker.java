package com.example.SpringBootJDBC.model;

public class Worker {
	private String name;
	private String ssn;
	private int rank;
	private String employing_state;
	
//	public Worker(String name, String ssn, int rank, String employing_state) {
//		super();
//		this.name = name;
//		this.ssn = ssn;
//		this.rank = rank;
//		this.employing_state = employing_state;
//	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getEmploying_state() {
		return employing_state;
	}
	public void setEmploying_state(String employing_state) {
		this.employing_state = employing_state;
	}
	
	
}
