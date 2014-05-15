package com.excilys.computerdatabase.mapper;

import java.util.List;

/**
 * @param: <V> - Type of output objects from the combined mapper. May be the
 *         same type as <R>.
 * @author excilys
 * 
 */
public interface Mapper<T, R> {

	/**
	 * Map the provided input object to an appropriate output object.
	 * 
	 * @Parameters: t - the input object to be mapped.
	 * @Returns: the mapped output object.
	 */
	R map(T t);

	List<R> map(List<T> t);

	/**
	 * Reverse map() process
	 * 
	 * @param r
	 * @return
	 */
	T invert(R r);

	List<T> invert(List<R> r);
}
