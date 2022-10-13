## _DBTransaction_ class manual
### The following is an example of using all the methods of this class in Java.

```java
// User class

DBTransaction tx = new DBTransaction();
if (tx.registerUser("admin", "1234567p")) System.out.println("Signed up");   // register new user; return true or false
else System.out.println("This login is existing in db");
tx.printUsers(0); // show all users (id, login, password)   // userId=0 to show all users, type specified userId to get one user with this ID

if (tx.signIn("usr1", "qwerty")) {  // sign in method; return true or false
    System.out.println("Signed in");
} else {
    System.out.println("Incorrect pass");
}

tx.changePassword(1, "wasd000");    // change user's password; user is searched for by his id; return true or false



//Student class

tx.addStudent("Kowalski", "Jan", "4c", "2022/2023");    // add student to the db; return true or false
tx.printStudents(); // show all students (id, name, surname, studentGroup, schoolYear)

List<Student> y = tx.getStudents_byId(2);  // return list of students by specified id; put 0 to get all students
for (Student s : y){
    System.out.println(s.getName());
}

tx.getStudents_byName("Jan");   // return list of students by name
tx.getStudents_bySurname("Nowak");   // return list of students by surname
tx.getStudents_byGroup("4c");   // return list of students by students' group
tx.getStudents_bySchoolYear("2022/2023");   // return list of students by school year

tx.editStudent(2,"Nowakowska", "Anna", "3e", "2022/2023");  // edit student in db; return true or false



//Grade class

tx.addGrade(5, "math", "exam", "", 2, 1);   // add grade to the db; return true or false
tx.editGrade(1, 4.5, "math", "exam", "corrected", 2, 1); // edit grade in db; return true or false
tx.deleteGrade(2);  // delete grade by gradeId; return true or false
tx.getGrades_byId(1);   // return list of grades by gradeId
// WARNING: all below getGrades_ methods return results for specified school year (school year must be in format "0000/0000")
tx.getGrades_byStudentId(1, "2022/2023");   // return list of grades by studentId
tx.getGrades_byUserId(1, "2022/2023");   // return list of grades by userId
tx.getGrades_bySubject("math", "2022/2023");   // return list of grades by subject
tx.getGrades_byStudentGroup_nSubject("math", "4c", "2022/2023");   // return list of grades by subject and students' group
```