package com.cg.payroll.services;

import java.util.List;

import com.cg.payroll.bean.Associate;
import com.cg.payroll.bean.BankDetails;
import com.cg.payroll.bean.Salary;
import com.cg.payroll.daoservices.AssociateDAO;
import com.cg.payroll.daoservices.AssociateDAOImpl;
import com.cg.payroll.exceptions.AssociateDetailNotFoundException;
import com.cg.payroll.util.PayrollDBUtil;

public class PayrollServicesImpl implements PayrollServices
{
     private AssociateDAO associateDAO = new AssociateDAOImpl();
	@Override
	public int acceptAssociateDetails(String firstName, String lastName, String emailId, String department,
			String designation, String pancard, int yearlyInvestmentunder80C, int basicSalary, int epf, int companyPf,
			int accountNumber, String bankName, String ifscCode) 
	{
		Associate associate = new Associate(yearlyInvestmentunder80C, firstName, lastName, department, designation, pancard, emailId,new Salary(basicSalary, epf,companyPf), new BankDetails(accountNumber,bankName,ifscCode));
		associate = associateDAO.save(associate);
		/*Associate associate = new Associate(yearlyInvestmentunder80C, firstName, lastName, department, designation, pancard, emailId, new Salary(basicSalary, epf,companyPf), new BankDetails(accountNumber,bankName,ifscCode));
		associate = associateDAO.save(associate);*/
		return associate.getAssociateId();
		
	}

	@Override
	public int calculateNetSalary(int associateId) throws AssociateDetailNotFoundException {
		Associate associate = getAssociateDetails(associateId);
		/*
		 * Salary sal = new Salary(); if(associate.salary.getBasicSalary() <= 250000)
		 * return associate.salary.getBasicSalary();
		 * 
		 * associate.salary.setHra((associate.salary.getBasicSalary()*30)/100);
		 * associate.salary.setConveyenceAllowance((associate.salary.getBasicSalary()*30
		 * )/100);
		 * associate.salary.setOtherAllowance((associate.salary.getBasicSalary()*25)/100
		 * );
		 * associate.salary.setPersonalAllowance((associate.salary.getBasicSalary()*20)/
		 * 100);
		 * associate.salary.setNetSalary(associate.salary.getBasicSalary()+associate.
		 * salary.getHra()+associate.salary.getConveyenceAllowance()+
		 * associate.salary.getOtherAllowance() +
		 * associate.salary.getPersonalAllowance() + associate.salary.getEpf() +
		 * associate.salary.getCompanyPf());
		 * associate.salary.setGrossSalary((associate.salary.getNetSalary() )*12); int
		 * taxable_income = sal.getGrossSalary() -
		 * associate.getYearlyInvestmentUnder80C() - sal.getHra() -
		 * sal.getConveyenceAllowance() - sal.getPersonalAllowance() -
		 * sal.getOtherAllowance(); if(associate.salary.getBasicSalary() > 250000 &&
		 * associate.salary.getBasicSalary() <= 500000) {
		 * 
		 * } if(associate.salary.getBasicSalary() > 500000 &&
		 * associate.salary.getBasicSalary() < 1000000) { int income_tax =
		 * (0*250000)/100 + (10*(taxable_income - 250000));
		 * 
		 * } int netSalary = sal.getGrossSalary();
		 */
		int netSalary;
		if(associate == null)
			throw new AssociateDetailNotFoundException("Associate Details Not Found for Id " + associateId);
		int basicSalary = associate.salary.getBasicSalary();
		int hra =((associate.salary.getBasicSalary()*30)/100); 
		int conveyenceSalary = (associate.salary.getBasicSalary()*30/100);
		int otherAllowance = (associate.salary.getBasicSalary()*25/100);
		int personalAllowance = (associate.salary.getBasicSalary()*20/100);
		int monthlyGrossSalary = basicSalary + hra + conveyenceSalary + otherAllowance + personalAllowance + associate.salary.getCompanyPf()
		+ associate.salary.getEpf();
		int annualGrossSalary = monthlyGrossSalary * 12;
		int investment = associate.getYearlyInvestmentUnder80C()+associate.salary.getCompanyPf()+associate.salary.getEpf();
		if(investment >= 1500000)
			investment = 1500000;
		if(annualGrossSalary < 250000)
			netSalary = annualGrossSalary - associate.salary.getEpf() - associate.salary.getCompanyPf();
		else if(annualGrossSalary >= 250000 && annualGrossSalary < 500000)
			netSalary = (int) (annualGrossSalary -((annualGrossSalary-investment)*0.1) - associate.salary.getEpf() - associate.salary.getCompanyPf());
		else if(annualGrossSalary >= 500000 && annualGrossSalary < 1000000)
		{
			int t2 = (int)((annualGrossSalary - 500000)*0.2);
			int t1 = (int)((250000 - investment) *0.1);
			netSalary = annualGrossSalary - t1 - t2 - associate.salary.getCompanyPf() - associate.salary.getEpf();
		}
		else 
		{
			int t3 = (int)((annualGrossSalary - 1000000) *0.3);
			int t2 = 1000000;
			int t1 = (int)((250000 - investment) *0.1);
			netSalary = annualGrossSalary - t1 - t2 -t3- associate.salary.getCompanyPf() - associate.salary.getEpf();
		}
			return associate.getSalary().setNetSalary(netSalary);
	}

	@Override
	public Associate getAssociateDetails(int associateId) throws AssociateDetailNotFoundException {
		Associate associate = associateDAO.findOne(associateId);
		if(associate == null) 
		    throw new AssociateDetailNotFoundException("Associate Details Not Found for Id " + associateId);
		return associate;
	}

	@Override
	public List<Associate> getAllAssociateDetails() {
		return associateDAO.findAll();
	}
     
}
