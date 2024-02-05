package Student_Enrollment_and_Course_Registration;

import java.util.*;

class Student {
    String studentID;
    String name;
    List<String> coursesEnrolled;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.coursesEnrolled = new ArrayList<>();
    }
}

class Course {
    String name;
    List<String> studentsEnrolled;

    public Course(String name) {
        this.name = name;
        this.studentsEnrolled = new ArrayList<>();
    }
}

class AVLTree {
    class Node {
        String courseCode;
        Course course;
        Node left;
        Node right;
        int height;

        public Node(String courseCode, Course course) {
            this.courseCode = courseCode;
            this.course = course;
            this.height = 1;
        }
    }

    Node root;

    public int height(Node node) {
        return (node != null) ? node.height : 0;
    }

    public int getBalance(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    public Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    public Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public Node insert(Node node, String courseCode, Course course) {
        if (node == null)
            return new Node(courseCode, course);

        if (courseCode.compareTo(node.courseCode) < 0)
            node.left = insert(node.left, courseCode, course);
        else if (courseCode.compareTo(node.courseCode) > 0)
            node.right = insert(node.right, courseCode, course);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1) {
            if (courseCode.compareTo(node.left.courseCode) < 0) {
                return rightRotate(node);
            } else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        if (balance < -1) {
            if (courseCode.compareTo(node.right.courseCode) > 0) {
                return leftRotate(node);
            } else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    public void inOrderTraversal(Node node, List<String> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.courseCode);
            inOrderTraversal(node.right, result);
        }
    }

    public List<String> getCourses() {
        List<String> courses = new ArrayList<>();
        inOrderTraversal(root, courses);
        return courses;
    }

    public boolean contains(String courseCode) {
        return search(root, courseCode) != null;
    }

    public Node search(Node node, String courseCode) {
        if (node == null)
            return null;

        if (courseCode.equals(node.courseCode))
            return node;
        else if (courseCode.compareTo(node.courseCode) < 0)
            return search(node.left, courseCode);
        else
            return search(node.right, courseCode);
    }
}









