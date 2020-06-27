package com.example.studentservices.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentservices.exception.ResourceNotFoundException;
import com.example.studentservices.model.Student;
import com.example.studentservices.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping("/students")
	public List<Student> retrieveAllStudents(){
		return studentRepository.findAll();
	}
	
	@PostMapping("/students")
	public Student createStudent( @RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getEmployeeById(@PathVariable(value="id") long studentId) throws ResourceNotFoundException{
		Student student = studentRepository.findById(studentId).orElseThrow(()->new ResourceNotFoundException("studentId"));
		
		return ResponseEntity.ok().body(student);
		
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value="id") long studentId,@RequestBody Student studentDetails) throws ResourceNotFoundException{
		Student student = studentRepository.findById(studentId).orElseThrow(()->new ResourceNotFoundException("studentId"));
		
		student.setFirstName(studentDetails.getFirstName());
		student.setLastName(studentDetails.getLastName());
		student.setEmailId(studentDetails.getEmailId());
		studentRepository.save(student);
		
		return ResponseEntity.ok().body(student);
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable(value="id") long studentId) throws ResourceNotFoundException{
		 studentRepository.findById(studentId).orElseThrow(()->new ResourceNotFoundException("studentId"));
		
		studentRepository.deleteById(studentId);
		return ResponseEntity.ok().build();
	}

}
