package com.notes.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SortObjects {
	
	public static void main(String[] args) {
		
		Employee employee1 = new Employee(1, "Mohan", "Bangalore");
		Employee employee1b = new Employee(1, "Kumar", "Bangalore");
		Employee employee2 = new Employee(2, "Sachin", "Mumbai");
		Employee employee3 = new Employee(4, "Dhoni", "Ranchi");
		Employee employee4 = new Employee(3, "Ganguly", "Kolkata");
		
		List<Employee> employees = new ArrayList<>();
		employees.add(employee1);
		employees.add(employee1b);
		employees.add(employee2);
		employees.add(employee3);
		employees.add(employee4);
		
		System.out.println("Sort objects");
		List<Employee> sortedEmployees = employees.stream()
													.filter(Objects::nonNull)
													.sorted(Comparator.comparing(Employee::getId))
													.collect(Collectors.toList());
		sortedEmployees.forEach(t -> System.out.println(t.toString()));
		
		System.out.println("\nSort objects in reverse order");
		List<Employee> sortedEmployeesReversed = employees.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(Employee::getId).reversed())
				.collect(Collectors.toList());
		sortedEmployeesReversed.forEach(t -> System.out.println(t.toString()));
		
		System.out.println("\nTodo: Sort first based on id and then based on name?");
		List<Employee> sortedEmployees2 = employees.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(Employee::getId))
				// .thenComparing(Employee::getName))
                .toList();
		sortedEmployees2.forEach(t -> System.out.println(t.toString()));
	}

}
