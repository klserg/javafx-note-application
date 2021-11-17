package com.javafx.exampl.dao;

import com.javafx.exampl.entity.Note;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NoteDao {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "mysql43";

    public static final String INSERT_QUERY = "INSERT INTO note_store(description, created_time) VALUES (?, ?)";
    public static final String SELECT_ALL_QUERY = "SELECT * FROM note_store";
    public static final String DELETE_QUERY = "DELETE FROM note_store WHERE id= ?";

    public Note create(Note note) throws DaoException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, note.getDescription());
            Timestamp timestamp = Timestamp.valueOf(note.getCreatedTime());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            note.setId(id);
            return note;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException("Failed to connect");
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Note> findAll() throws DaoException {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            List<Note> notes = new ArrayList<>();
            while (resultSet.next()) {
                String description = resultSet.getString("description");
                Timestamp createTime = resultSet.getTimestamp("created_time");
                int id = resultSet.getInt("id");

                Note note = new Note(id, description, createTime.toLocalDateTime());
                notes.add(note);
            }
            return notes;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException("Failed to find all");
        }
    }

    public void delete(int id) throws DaoException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException("Failed to delete");
        }
    }
}
