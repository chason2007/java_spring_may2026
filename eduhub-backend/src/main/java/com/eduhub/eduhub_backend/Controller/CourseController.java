package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Course> searchCourse(
            @RequestParam String code) {

        for (Course course : courseList) {
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return ResponseEntity.ok(course);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> addCourse(
            @RequestBody Course newCourse) {

        courseList.add(newCourse);

        return ResponseEntity.ok("Course Added Successfully");
    }

    @PutMapping("/{courseCode}")
    public ResponseEntity<String> updateCourse(
            @PathVariable String courseCode,
            @RequestBody Course updatedCourse) {

        for (Course course : courseList) {

            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {

                course.setSubjectName(updatedCourse.getSubjectName());
                course.setCredits(updatedCourse.getCredits());

                return ResponseEntity.ok("Course Updated Successfully");
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{courseCode}")
    public ResponseEntity<String> deleteCourse(
            @PathVariable String courseCode) {

        for (Course course : courseList) {

            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {

                courseList.remove(course);

                return ResponseEntity.ok("Course Deleted Successfully");
            }
        }

        return ResponseEntity.notFound().build();
    }
}