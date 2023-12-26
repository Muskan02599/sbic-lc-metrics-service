package com.inno;

public class Employee {
	private Integer empno;
	private  String ename;
	private  String job;
	private  Double sal;
	private  Integer deptno=10;
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Double getSal() {
		return sal;
	}
	public void setSal(Double sal) {
		this.sal = sal;
	}
	public Integer getDeptno() {
		return deptno;
	}
	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}

public Employee() {
	// TODO Auto-generated constructor stub
}
public Employee(Integer empno, String ename, Double sal) {
	super();
	this.empno = empno;
	this.ename = ename;
	this.job = "Developer";
	this.sal = sal;
	this.deptno = 10;
}


}
