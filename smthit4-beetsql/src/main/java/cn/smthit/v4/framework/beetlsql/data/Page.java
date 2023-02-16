/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data;

import java.util.Collections;
import java.util.List;

/**
 * @author Bean
 *
 */
public class Page<T> {
	private int currentPage;

	private long total;
	private List<T> rows;
	private int totalPages;
	
	public Page() {
		this.total = 0;
		this.totalPages = 0;
		this.rows = Collections.emptyList();
	}
	
	public Page(int currentPage, int totalPages, long total, List<T> rows) {
		this.total = total;
		this.currentPage = currentPage;
		this.rows = rows;
		this.totalPages = totalPages;
	}
	
	public long getTotal() {
		return total;
	}

	public Page<T> setTotal(long total) {
		this.total = total;
		return this;
	}

	public List<T> getRows() {
		return rows;
	}

	public Page<T> setRows(List<T> rows) {
		this.rows = rows;
		return this;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public Page<T> setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		return this;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPage to set
	 */
	public Page<T> setTotalPages(int totalPages) {
		this.totalPages = totalPages;
		return this;
	}	
}
