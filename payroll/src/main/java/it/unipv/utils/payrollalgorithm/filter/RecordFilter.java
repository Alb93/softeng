package it.unipv.utils.payrollalgorithm.filter;

import java.util.Date;
import java.util.List;

public interface RecordFilter <T> {
	
	public List<T> filter(Date date, List<T> object);

}
