package uk.co.blackpepper.common.driver;

public interface Driver<T extends Driver<T>> {
	
	T show();
	
	boolean isVisible();
	
	boolean isVisibleInAnotherWindow();
}
