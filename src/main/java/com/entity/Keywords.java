package com.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @表名 keywords
 * Database Table:keywords to ClassName:Keywords
 */
@Entity
@Table(name = "keywords")
public class Keywords {

	/**
	 * 关键词
	 */
	@Column(name="maxnum")
	private String maxnum;
	@Column(name="fcolor", length = 10)
	private String fcolor;
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
			name = "minnum"
	)
	private String minnum;
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMinnum() {
		return this.minnum;
	}

	public void setMinnum(String minnum) {
		this.minnum = minnum;
	}



	public void setMaxnum(String value){
		this.maxnum = value;
	}


	public String getMaxnum() {
		return this.maxnum;
	}
	public void setFcolor(String value){
		this.fcolor = value;
	}
	public String getFcolor() {
		return this.fcolor;
	}






}
