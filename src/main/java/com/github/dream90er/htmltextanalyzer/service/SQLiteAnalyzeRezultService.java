package com.github.dream90er.htmltextanalyzer.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;

/**
 * Implementation of {@link AnalyzeResultService} that uses SQLite as underlying database.
 * 
 * @author Sychev Alexey 
 */ 
public class SQLiteAnalyzeRezultService implements AnalyzeResultService {

    private static final String CREATE_RESULT_TABLE_STATEMENT = 
        "CREATE TABLE IF NOT EXISTS results ("
        + " id integer PRIMARY KEY,"
        + " url TEXT NOT NULL,"
        + " result TEXT NOT NULL,"
        + " created_at DATE DEFAULT (DATETIME('now','localtime'))"
        + ");";

    private static final String INSERT_RESULT_STATEMENT = 
        "INSERT INTO results (url, result) VALUES(?,?)";

    private final String sQLiteConnectionString;

    protected SQLiteAnalyzeRezultService(String sQLiteConnectionString) {
        this.sQLiteConnectionString = sQLiteConnectionString;
    }

    @Override
    public void saveAnalyzeResult(AnalyzeResult analyzeResult) {
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(INSERT_RESULT_STATEMENT)) {
            pstmt.setString(1, analyzeResult.getPageUrl());
            pstmt.setString(2, analyzeResult.getResultMapAsString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceException(
                "An exception occurred while adding result to the database", e);
        }
    }

    //Connect to a database
    private Connection connect() {
        try {
            return DriverManager.getConnection(sQLiteConnectionString);
        } catch (SQLException e) {
            throw new ServiceException(
                "An exception occurred while connecting to the database", e);
        }
    }

    //Create table for AnalyzeResult entity
    private void createResultTable() {
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_RESULT_TABLE_STATEMENT);
        } catch (SQLException e) {
            throw new ServiceException(
                "An exception occurred while initilizing database", e);
        }
    }

    /**
     * Get a {@code SQLiteAnalyzeRezultService} instance with initialized database.
     * 
     * @param sQLiteConnectionString database URL
     * @return {@code SQLiteAnalyzeRezultService} instance
     * @throws ServiceException if can't initialize database
     */
    public static SQLiteAnalyzeRezultService getInstance(String sQLiteConnectionString) {
        SQLiteAnalyzeRezultService sQLiteAnalyzeRezultService = 
            new SQLiteAnalyzeRezultService(sQLiteConnectionString);
        sQLiteAnalyzeRezultService.createResultTable();
        return sQLiteAnalyzeRezultService;
    }

}
