package Student_Enrollment_and_Course_Registration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Registration {
    Map<String, Student> students;
    AVLTree courses;
    int idx;

    public Registration() {
        this.students = new HashMap<>();
        this.courses = new AVLTree();
        this.idx = 1000;
    }

    public void enroll(String studentID, String courseCode) {
        if (!students.containsKey(studentID)) {
            System.out.println("Oops! You are not registered as a student.");
            System.out.println("Please register as a student first!");
        } else {
            Student student = students.get(studentID);
            if (!courses.contains(courseCode)) {
                System.out.println("Invalid courseCode! The course does not exist.");
            } else if (student.coursesEnrolled.contains(courseCode)) {
                System.out.println("OOPS! You have already registered for this course.");
            } else {
                student.coursesEnrolled.add(courseCode);
                System.out.println("Enrollment successful.");
            }
        }
    }

    public void enrollAsNewStudent(String name) {
        String studentID = Integer.toString(idx);
        students.put(studentID, new Student(studentID, name));
        idx++;
        System.out.println("You have been successfully registered as a student.");
        System.out.println("Your Student ID is: " + studentID);
    }

    public void deregister(String studentID, String courseCode) {
        if (students.containsKey(studentID)) {
            Student student = students.get(studentID);
            if (student.coursesEnrolled.contains(courseCode)) {
                student.coursesEnrolled.remove(courseCode);
                System.out.println("Deregistration successful.");
            } else {
                System.out.println("Deregistration failed. Course not found in your schedule.");
            }
        } else {
            System.out.println("Deregistration failed. Student not found.");
        }
    }

    public int getNumberOfStudents() {
        return students.size();
    }
    public String getStudentNames() {
        StringBuilder names = new StringBuilder();
        for (Student student : students.values()) {
            names.append(student.name).append(", ");
        }
        
        if (names.length() > 0) {
            names.setLength(names.length() - 2);
        }
        return names.toString();
    }

    public void addNewCourse(String courseCode, String courseName) {
        if (!courses.contains(courseCode)) {
            Course newCourse = new Course(courseName);
            courses.root = courses.insert(courses.root, courseCode, newCourse);
            System.out.println("Course " + courseCode + " (" + courseName + ") added successfully.");
        } else {
            System.out.println("Course " + courseCode + " already exists.");
        }
    }


    public void courseData(String courseCode) {
        if (courses.contains(courseCode)) {
            System.out.println("Roster for Course " + courseCode + ":");
            AVLTree.Node courseNode = courses.search(courses.root, courseCode);
            if (courseNode != null) {
                Course course = courseNode.course;
                for (String studentID : course.studentsEnrolled) {
                    Student student = students.get(studentID);
                    System.out.println(studentID + " - " + student.name);
                }
            }
        } else {
            System.out.println("Invalid courseCode! The course does not exist.");
        }
    }

    public void studentSchedule(String studentID) {
        if (students.containsKey(studentID)) {
            Student student = students.get(studentID);
            System.out.println("Schedule for Student " + studentID + ":");
            for (String courseCode : student.coursesEnrolled) {
                if (courses.contains(courseCode)) {
                    String courseName = courses.search(courses.root, courseCode).course.name;
                    System.out.println(courseCode + " - " + courseName);
                } else {
                    System.out.println(courseCode + " - Unknown Course");
                }
            }
        } else {
            System.out.println("Oops! You are not registered as a student.");
            System.out.println("Please register as a student first!");
        }
    }

    public boolean hasScheduleConflict(String studentID, Set<String> newCourseSchedule) {
        if (students.containsKey(studentID)) {
            Student student = students.get(studentID);
            Set<String> studentSchedule = new HashSet<>(student.coursesEnrolled);
            return !Collections.disjoint(studentSchedule, newCourseSchedule);
        } else {
            System.out.println("Oops! You are not registered as a student.");
            System.out.println("Please register as a student first!");
            return false;
        }
    }

    public static void main(String[] args) {
        Registration registration = new Registration();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nWelcome to the Registration System");
            System.out.println("====================================");
            System.out.println("1. Enroll as a new student");
            System.out.println("2. Add a new course (only to be added by a professor)");
            System.out.println("3. Enroll in a course");
            System.out.println("4. View Schedule");
            System.out.println("5. Check Schedule Conflict");
            System.out.println("6. Deregister from a course");
            System.out.println("7. Number of students registered");
            System.out.println("8. Exit");
            System.out.print("Please select one from the following: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter your Name: ");
                    String studentName = scanner.next();
                    registration.enrollAsNewStudent(studentName);
                    break;
                case 2:
                    System.out.print("Enter the courseCode to be added: ");
                    String courseCode = scanner.next();
                    System.out.print("Enter the name of the course: ");
                    String courseName = scanner.next();
                    registration.addNewCourse(courseCode, courseName);
                    break;
                case 3:
                    System.out.print("Enter your studentID: ");
                    String studentID = scanner.next();
                    System.out.print("Enter the courseCode you want to register: ");
                    String enrollCourseCode = scanner.next();
                    registration.enroll(studentID, enrollCourseCode);
                    break;
                case 4:
                    System.out.print("Enter StudentID: ");
                    String viewStudentID = scanner.next();
                    registration.studentSchedule(viewStudentID);
                    break;
                case 5:
                    System.out.print("Enter your studentID: ");
                    String conflictStudentID = scanner.next();
                    System.out.print("Enter the courseCodes you want to check for conflict (comma-separated): ");
                    String[] conflictCourseCodes = scanner.next().split(",");
                    Set<String> newCourseSchedule = new HashSet<>(Arrays.asList(conflictCourseCodes));
                    boolean hasConflict = registration.hasScheduleConflict(conflictStudentID, newCourseSchedule);
                    if (hasConflict) {
                        System.out.println("You have a schedule conflict.");
                    } else {
                        System.out.println("You can register for these courses.");
                    }
                    break;
                case 6:
                    System.out.print("Enter your studentID: ");
                    String deregisterStudentID = scanner.next();
                    System.out.print("Enter the courseCode you want to deregister from: ");
                    String deregisterCourseCode = scanner.next();
                    registration.deregister(deregisterStudentID, deregisterCourseCode);
                    break;
                case 7:
                    int numberOfStudents = registration.getNumberOfStudents();
                    String studentNames = registration.getStudentNames();
                    System.out.println("Number of students registered: " + numberOfStudents);
                    System.out.println("Student Names: " + studentNames);
                    break;
                case 8:
                    System.out.println("Exiting the registration system.");
                    System.exit(0);
                default:
                    System.out.println("Invalid Selection!!");
                    break;
            }
        }
    }
}
