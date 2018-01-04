package com.emirates.springboot.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="APP_TASK")
public class Task implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name="NAME", nullable=false)
	private String name;

	@Column(name="ESTIMATE", nullable=false)
	private Integer estimatedHours;

	@Column(name="ACTUAL", nullable=false)
	private double actualHours;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(Integer estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public double getActualHours() {
		return actualHours;
	}

	public void setActualHours(double actualHours) {
		this.actualHours = actualHours;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Task task = (Task) o;

		if (Double.compare(task.actualHours, actualHours) != 0) return false;
		if (id != null ? !id.equals(task.id) : task.id != null) return false;
		if (name != null ? !name.equals(task.name) : task.name != null) return false;
		return estimatedHours != null ? estimatedHours.equals(task.estimatedHours) : task.estimatedHours == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (estimatedHours != null ? estimatedHours.hashCode() : 0);
		temp = Double.doubleToLongBits(actualHours);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", estimatedHours=" + estimatedHours
				+ ", actualHours=" + actualHours + "]";
	}


}
