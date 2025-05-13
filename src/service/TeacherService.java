package com.yourcompany.school.service;

import com.yourcompany.school.dao.SubjectDao;
import com.yourcompany.school.dao.TeacherDao;
import com.yourcompany.school.model.Subject;
import com.yourcompany.school.model.Teacher;

import java.sql.SQLException;

public class TeacherService {
    private final TeacherDao teacherDao;
    private final SubjectDao subjectDao;

    public TeacherService() {
        this.teacherDao = new TeacherDao();
        this.subjectDao = new SubjectDao();
    }

    public String getSubjectNameByTeacherId(int teacherId) throws SQLException {
        Teacher teacher = teacherDao.read(teacherId);
        if (teacher != null) {
            Subject subject = subjectDao.read(teacher.getSubjectId());
            if (subject != null) {
                return subject.getSubjectName();
            } else {
                return "საგანი ვერ მოიძებნა მასწავლებლისთვის ID: " + teacherId;
            }
        } else {
            return "მასწავლებელი ვერ მოიძებნა ID: " + teacherId;
        }
    }
}
