package com.notes.equalsHashcode;

import java.util.Objects;

public class Student {
	
    private final int id;
    private String name;
    private String major;
    
    public Student(int id, String name, String major) {
    	
        this.id = id;
        this.name = name;
        this.major = major;
    }

    /*
     * auto-generated equals and hashCode
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student student = (Student) obj;
		return id == student.id 
						&& Objects.equals(major, student.major) 
						&& Objects.equals(name, student.name);
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(id, major, name);
	}
	*/
    
    @Override
    public boolean equals(Object obj) {
    	
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        Student student = (Student) obj;
        return id == student.id;  // Only compare by ID
    }
    
    @Override
    public int hashCode() {
    	
        return Objects.hash(id);  // Generate hash only from ID
    }
}


