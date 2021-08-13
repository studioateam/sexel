package net.aydini.sexel;

import net.aydini.sexel.annotation.SexelField;
import net.aydini.sexel.converter.ObjectToIntegerMaper;

public class Aydin {

	
	@SexelField(columnIndex = 0 ,headerTitle = "nam_h")
	private String name;
	
	
	@SexelField(columnIndex = 1 ,headerTitle = "family_h")
	private String family;
	
	
	@SexelField(columnIndex = 2 ,headerTitle = "age_h",converter = ObjectToIntegerMaper.class)
	private Integer age;
	


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFamily() {
		return family;
	}


	public void setFamily(String family) {
		this.family = family;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	@Override
	public String toString() {
		return "Aydin [name=" + name + ", family=" + family + ", age=" + age + "]";
	}


	
	


	
	
}
