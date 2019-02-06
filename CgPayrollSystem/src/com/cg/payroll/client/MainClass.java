package com.cg.payroll.client;

import com.cg.payroll.bean.Associate;
import com.cg.payroll.exceptions.AssociateDetailNotFoundException;
import com.cg.payroll.services.PayrollServices;
import com.cg.payroll.services.PayrollServicesImpl;

public class MainClass 
{
	public static void main(String[] args) throws AssociateDetailNotFoundException 
	{
         PayrollServices services = new PayrollServicesImpl();
         int associateId1 = services.acceptAssociateDetails("Ankita", "Dutta", "ank@gmail.com", "Fresher", "Analyst", "5464",100000, 500000, 21600, 15384, 5464376,"hdfc", "64647");
         System.out.println("Associate Id: " + associateId1);
         services.calculateNetSalary(associateId1);
         try {
         System.out.println(services.getAssociateDetails(associateId1));
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
		/*
		 * int associateId2 = services.acceptAssociateDetails("Subodra", "Banik",
		 * "sub@gmail.com", "Fresher", "Analyst", "5464",5363, 30000, 1000, 5000,
		 * 5464376,"hdfc", "64647"); System.out.println("Associate Id: " +
		 * associateId2); try { System.out.println(services.getAssociateDetails(102)); }
		 * catch(Exception e) { System.out.println(e); }
		 */
	}

}
