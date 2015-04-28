package com.dfc.springmvc.common;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * ��װ��ҳ��Ϣ
 * 
 * @author dongdong
 * 
 */
@Service
public class PageInfo<E> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pageSize;// ÿҳ��ʾ����

	private int totalPages;// �ܵ�ҳ��

	private int totalRecords;// ����������

	private int currentPage;// ��ǰҳ

	@SuppressWarnings("unused")
	private int fromRecord;// �ӵڼ�����ʼ��ȡ����

	public PageInfo() {
		this(1);
	}

	public int getTotalPages() {
		// ��ҳ�㷨��������ҳ��
		totalPages = (totalRecords + pageSize - 1) / pageSize;
		return totalPages;
	}

	public PageInfo(int currentPage) {
		// Ĭ��ÿҳ��ʾ10����¼
		this(currentPage, 10);
	}

	public PageInfo(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		if (pageSize > 0)
			this.pageSize = pageSize;
		// ������
		if (this.currentPage < 1)
			this.currentPage = 1;
	}

	public int getFromRecord() {
		// ����ӵڼ�����ȡ����
		return (currentPage - 1) * pageSize;
	}

	public void setFromRecord(int fromRecord) {
		this.fromRecord = fromRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}