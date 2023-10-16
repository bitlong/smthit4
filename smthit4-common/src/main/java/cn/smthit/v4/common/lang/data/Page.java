/**
 * 
 */
package cn.smthit.v4.common.lang.data;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Bean
 * 和PageData的pageCount不一养，
 */
@Data
@lombok.experimental.Accessors(chain = true)
public class Page<T> implements Serializable {
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
