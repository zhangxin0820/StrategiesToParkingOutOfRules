package com.zx.model;

import java.sql.Date;

public class Subincident {

	private Integer sub_id;
	private Integer sub_event_id;
	private Integer have_person_id;
	private Date car_date;
	private String car_id;
	private String car_type;

	public Integer getSub_id() {
		return sub_id;
	}

	public void setSub_id(Integer sub_id) {
		this.sub_id = sub_id;
	}

	public Integer getSub_event_id() {
		return sub_event_id;
	}

	public void setSub_event_id(Integer sub_event_id) {
		this.sub_event_id = sub_event_id;
	}

	public Integer getHave_person_id() {
		return have_person_id;
	}

	public void setHave_person_id(Integer have_person_id) {
		this.have_person_id = have_person_id;
	}

	public Date getCar_date() {
		return car_date;
	}

	public void setCar_date(Date car_date) {
		this.car_date = car_date;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

}
