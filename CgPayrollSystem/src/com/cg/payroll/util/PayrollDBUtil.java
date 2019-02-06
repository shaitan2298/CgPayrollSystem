package com.cg.payroll.util;

import java.util.HashMap;

import com.cg.payroll.bean.Associate;

public class PayrollDBUtil 
{
    public static HashMap<Integer, Associate> associates = new HashMap<>();
    
    private static int ASSOCIATE_ID_COUNTER = 100;
    public static int getASSOCIATE_ID_COUNTER()
    {
    	return ++ASSOCIATE_ID_COUNTER;
    }
}
