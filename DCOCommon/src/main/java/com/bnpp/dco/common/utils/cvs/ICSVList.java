package com.bnpp.dco.common.utils.cvs;

public interface ICSVList {

	String CSV_SEPARATOR = ";";
	
	String CSV_LR = "\r\n";

	String toCSV() throws IllegalArgumentException, IllegalAccessException;
}
