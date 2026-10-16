package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		Scanner sc = new Scanner(System.in);

		
		System.out.println("=== TEST 1: departmentDao findById ====");

		Department department = departmentDao.findById(1);
		System.out.println(department);
		
		System.out.println("\n=== TEST 2: departmentDao findByDepartment ====");
		department = new Department();
		department.setName("Fashion");
		List<Department> list = departmentDao.findByDepartment(department);
		for(Department d : list) {
			System.out.println(d);
		}
		
		System.out.println("\n=== TEST 3: department findAll ====");
		list = departmentDao.findAll();
		for(Department obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 4: department insert ====");
		Department newDepartment = new Department(null, "Infantil");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id = " + newDepartment.getId());
		
		System.out.println("\n=== TEST 5: department Update ====");
		department = departmentDao.findById(2);
		department.setName("Religioso");
		departmentDao.update(department);
		System.out.println("Updated completed!");
		
		System.out.println("\n=== TEST 6: department Delete ====");
		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete complete!");

		
		
		sc.close();
	}
	

}
