package com.yourcompany.school.dao;

import com.yourcompany.school.database.DatabaseConnection;
import com.yourcompany.school.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDao {

    public void create(Subject subject) throws SQLException {
        String sql = "INSERT INTO subjects (subject_name) VALUES (?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, subject.getSubjectName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                subject.setId(generatedKeys.getInt(1));
            }
        }
    }

    public Subject read(int id) throws SQLException {
        String sql = "SELECT id, subject_name FROM subjects WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Subject(
                        resultSet.getInt("id"),
                        resultSet.getString("subject_name")
                );
            }
            return null;
        }
    }

    public List<Subject> readAll() throws SQLException {
        String sql = "SELECT id, subject_name FROM subjects";
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                subjects.add(new Subject(
                        resultSet.getInt("id"),
                        resultSet.getString("subject_name")
                ));
            }
            return subjects;
        }
    }

    public void update(Subject subject) throws SQLException {
        String sql = "UPDATE subjects SET subject_name = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, subject.getSubjectName());
            preparedStatement.setInt(2, subject.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM subjects WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
