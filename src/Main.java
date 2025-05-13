import com.yourcompany.school.dao.StudentDao;
import com.yourcompany.school.dao.SubjectDao;
import com.yourcompany.school.dao.TeacherDao;
import com.yourcompany.school.model.Student;
import com.yourcompany.school.model.Subject;
import com.yourcompany.school.model.Teacher;
import com.yourcompany.school.service.TeacherService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            StudentDao studentDao = new StudentDao();
            TeacherDao teacherDao = new TeacherDao();
            SubjectDao subjectDao = new SubjectDao();
            TeacherService teacherService = new TeacherService();


            Subject math = new Subject("მათემატიკა");
            subjectDao.create(math);
            System.out.println("დამატებული საგანი: " + math);


            Teacher teacher1 = new Teacher("ანა", "ბერიძე", math.getId(), 1200.50);
            teacherDao.create(teacher1);
            System.out.println("დამატებული მასწავლებელი: " + teacher1);


            Student student1 = new Student("გიორგი", "ლომიძე", 10);
            studentDao.create(student1);
            System.out.println("დამატებული მოსწავლე: " + student1);


            String subjectName = teacherService.getSubjectNameByTeacherId(teacher1.getId());
            System.out.println("მასწავლებელი " + teacher1.getFirstName() + " ასწავლის: " + subjectName);


            List<Student> allStudents = studentDao.readAll();
            System.out.println("\nყველა მოსწავლე:");
            allStudents.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
