/**
 * 
 */
package cn.smthit.v4.framework.beetlsql.data;

import lombok.Data;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * @author Bean
 *
 */
@Data
@lombok.experimental.Accessors(chain = true)
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
}
