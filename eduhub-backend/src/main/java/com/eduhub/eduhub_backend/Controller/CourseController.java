package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.model.Course;
import com.eduhub.eduhub_backend.exception.CourseNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    static List<Course> courseList = new ArrayList<>();
    static {
        courseList.add(new Course("CS101", "Java Programming", 4));
        courseList.add(new Course("CS102", "Data Structures", 3));
        courseList.add(new Course("CS103", "Database Management", 4));
        courseList.add(new Course("CS104", "Operating Systems", 3));
        courseList.add(new Course("CS105", "Computer Networks", 3));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("/{courseCode}")
    public ResponseEntity<Course> getCourseByCode(
            @PathVariable String courseCode) {

        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return ResponseEntity.ok(course);
            }
        }

        throw new CourseNotFoundException("Course with code " + courseCode + " not found");
    }

    @GetMapping("/search")
    public ResponseEntity<Course> searchCourse(
            @RequestParam String code) {

        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return ResponseEntity.ok(course);
            }
        }

        throw new CourseNotFoundException("Course with code " + code + " not found");
    }

    @PostMapping
    public ResponseEntity<String> addCourse(
            @RequestBody Course newCourse) {

        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(newCourse.getCourseCode())) {
                throw new IllegalArgumentException("Course with code " + newCourse.getCourseCode() + " already exists");
            }
        }

        courseList.add(newCourse);

        return ResponseEntity.ok("Course Added Successfully");
    }

    @PutMapping("/{courseCode}")
    public ResponseEntity<String> updateCourse(
            @PathVariable String courseCode,
            @RequestBody Course updatedCourse) {

        Optional<Course> courseOptional = courseList.stream()
                .filter(c -> c.getCourseCode().equalsIgnoreCase(courseCode))
                .findFirst();

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.setSubjectName(updatedCourse.getSubjectName());
            course.setCredits(updatedCourse.getCredits());
            return ResponseEntity.ok("Course Updated Successfully");
        }

        throw new CourseNotFoundException("Course with code " + courseCode + " not found");
    }

    @DeleteMapping("/{courseCode}")
    public ResponseEntity<String> deleteCourse(
            @PathVariable String courseCode) {

        Iterator<Course> iterator = courseList.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                iterator.remove();
                return ResponseEntity.ok("Course Deleted Successfully");
            }
        }

        throw new CourseNotFoundException("Course with code " + courseCode + " not found");
    }
}