package com.example.demo.controller;

import com.example.demo.model.Faculty;
import com.example.demo.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    @Autowired
    private FacultyRepository facultyRepository;

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @GetMapping
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        return faculty.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty facultyDetails) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            Faculty existingFaculty = faculty.get();
            existingFaculty.setFirstName(facultyDetails.getFirstName());
            existingFaculty.setLastName(facultyDetails.getLastName());
            existingFaculty.setDob(facultyDetails.getDob());
            existingFaculty.setAddress(facultyDetails.getAddress());
            existingFaculty.setEmployeeId(facultyDetails.getEmployeeId());
            existingFaculty.setDepartments(facultyDetails.getDepartments());
            return ResponseEntity.ok(facultyRepository.save(existingFaculty));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
