package com.yourcompany.school.dao;

import com.yourcompany.school.database.DatabaseConnection;
import com.yourcompany.school.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao {

    public void create(Teacher teacher) throws SQLException {
        String sql = "INSERT INTO teachers (first_name, last_name, subject, salary) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setInt(3, teacher.getSubjectId());
            preparedStatement.setDouble(4, teacher.getSalary());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                teacher.setId(generatedKeys.getInt(1));
            }
        }
    }

    public Teacher read(int id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, subject, salary FROM teachers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Teacher(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("subject"),
                        resultSet.getDouble("salary")
                );
            }
            return null;
        }
    }

    public List<Teacher> readAll() throws SQLException {
        String sql = "SELECT id, first_name, last_name, subject, salary FROM teachers";
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                teachers.add(new Teacher(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("subject"),
                        resultSet.getDouble("salary")
                ));
            }
            return teachers;
        }
    }

    public void update(Teacher teacher) throws SQLException {
        String sql = "UPDATE teachers SET first_name = ?, last_name = ?, subject = ?, salary = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setInt(3, teacher.getSubjectId());
            preparedStatement.setDouble(4, teacher.getSalary());
            preparedStatement.setInt(5, teacher.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM teachers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
