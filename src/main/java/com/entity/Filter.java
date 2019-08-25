package com.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @表名 filter
 * Database Table:filter to ClassName:Filter
 */
@Entity
@Table(name = "filter")
public class Filter{
	@Id
	@GeneratedValue(
			generator = "system-uuid"
	)
	@GenericGenerator(
			name = "system-uuid",
			strategy = "uuid"
	)
	@Column(
			name = "ID",
			length = 32
	)
	private String id;
	@Column(
			name = "remarks"
	)
	private String remarks ;
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 过滤内容
	 */
	@Column(name="val", nullable=true, length = 100)
	private String val;
	/**
	 * 类型：1 符号 2 语气词
	 */
	@Column(name="types", nullable=true, length = 10)
	private String types;



	/*
	 * <p>过滤内容</p>
	 */
	public void setVal(String value){
		this.val = value;
	}

	/*
	 * <p>过滤内容</p>
	 */
	public String getVal() {
		return this.val;
	}


	/*
	 * <p>类型：1 符号 2 语气词</p>
	 */
	public void setTypes(String value){
		this.types = value;
	}

	/*
	 * <p>类型：1 符号 2 语气词</p>
	 */
	public String getTypes() {
		return this.types;
	}






}
