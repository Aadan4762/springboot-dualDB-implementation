package com.db.implementation.controller;

import java.util.List;
import java.util.Optional;


import com.db.implementation.builder.ObjectBuilder;
import com.db.implementation.entity1.Student;
import com.db.implementation.entity1.StudentDTO;
import com.db.implementation.entity2.Teacher;
import com.db.implementation.entity2.TeacherDTO;
import com.db.implementation.repo1.StudentRepository;
import com.db.implementation.repo2.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StudentTeacherRestController {

	@Autowired
	StudentRepository studentRepo;

	@Autowired
	TeacherRepository teacherRepo;

	@PostMapping("/saveStudent")
	public Student saveStudent(@RequestBody StudentDTO studentDTO) {
		Student student = ObjectBuilder.createStudentFromStudentDTO(studentDTO);
		return studentRepo.save(student);
	}

	@PostMapping("/saveTeacher")
	public Teacher saveTeacher(@RequestBody TeacherDTO teacherDTO) {
		Teacher teacher = ObjectBuilder.createTeacherFromTeacherDTO(teacherDTO);
		return teacherRepo.save(teacher);
	}

	@GetMapping("/getAllStudents")
	public List<Student> getAllStudents() {
		return studentRepo.findAll();
	}

	@GetMapping("/getAllTeachers")
	public List<Teacher> getAllTeachers() {
		return teacherRepo.findAll();
	}

	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id) {
		Optional<Student> student = studentRepo.findById(id);
		return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/getTeacherById/{id}")
	public ResponseEntity<Teacher> getTeacherById(@PathVariable int id) {
		Optional<Teacher> teacher = teacherRepo.findById(id);
		return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/deleteStudentById/{id}")
	public ResponseEntity<?> deleteStudentById(@PathVariable int id) {
		if (studentRepo.existsById(id)) {
			studentRepo.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/deleteTeacherById/{id}")
	public ResponseEntity<?> deleteTeacherById(@PathVariable int id) {
		if (teacherRepo.existsById(id)) {
			teacherRepo.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/updateStudentById/{id}")
	public ResponseEntity<Student> updateStudentById(@PathVariable int id, @RequestBody StudentDTO studentDTO) {
		Optional<Student> optionalStudent = studentRepo.findById(id);
		if (optionalStudent.isPresent()) {
			Student existingStudent = optionalStudent.get();
			existingStudent.setName(studentDTO.getName());
			existingStudent.setSchoolName(studentDTO.getSchoolName());
			existingStudent.setStandard(studentDTO.getStandard());
			studentRepo.save(existingStudent);
			return ResponseEntity.ok(existingStudent);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/updateTeacherById/{id}")
	public ResponseEntity<Teacher> updateTeacherById(@PathVariable int id, @RequestBody TeacherDTO teacherDTO) {
		Optional<Teacher> optionalTeacher = teacherRepo.findById(id);
		if (optionalTeacher.isPresent()) {
			Teacher existingTeacher = optionalTeacher.get();
			existingTeacher.setName(teacherDTO.getName());
			existingTeacher.setSubject(teacherDTO.getSubject());
			teacherRepo.save(existingTeacher);
			return ResponseEntity.ok(existingTeacher);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
