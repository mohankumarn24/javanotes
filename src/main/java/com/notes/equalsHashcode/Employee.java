package com.notes.equalsHashcode;

import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private String department;
    
    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
    
    // Overriding equals()
    @Override
    public boolean equals(Object o) {
        // 1. Check if same object reference
        if (this == o) return true;
        
        // 2. Check if null or different class
        if (o == null || getClass() != o.getClass()) return false;
        
        // 3. Cast to Employee
        Employee employee = (Employee) o;
        
        // 4. Compare significant fields
        return id == employee.id && 
               Objects.equals(name, employee.name) && 
               Objects.equals(department, employee.department);
    }
    
    // Overriding hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(id, name, department);
    }
}
