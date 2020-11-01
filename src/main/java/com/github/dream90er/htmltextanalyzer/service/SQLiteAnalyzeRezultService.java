package com.github.dream90er.htmltextanalyzer.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.dream90er.htmltextanalyzer.entity.AnalyzeResult;

public class SQLiteAnalyzeRezultService implements AnalyzeResultService {

    private static final String CREATE_RESULT_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS results ("
        + " id integer PRIMARY KEY,"
        + " url TEXT NOT NULL,"
        + " result TEXT NOT NULL,"
        + " created_at DATE DEFAULT (DATETIME('now','localtime'))"
        + ");";

    private static final String INSERT_RESULT_STATEMENT = "INSERT INTO results (url, result) VALUES(?,?)";

    private final String sQLiteConnectionString;

    private boolean initialized = false;

    protected SQLiteAnalyzeRezultService(String sQLiteConnectionString) {
        this.sQLiteConnectionString = sQLiteConnectionString;
    }

    @Override
    public void saveAnalyzeResult(AnalyzeResult analyzeResult) {
        if (!isInitialized()) {
            //TODO
            return;
        }
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(INSERT_RESULT_STATEMENT)) {
            pstmt.setString(1, analyzeResult.getPageUrl());
            pstmt.setString(2, analyzeResult.getResultMapAsString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServiceException("An exception occurred while adding result to the database", e);
        }
    }

    private Connection connect() {
        try {
            return DriverManager.getConnection(sQLiteConnectionString);
        } catch (SQLException e) {
            throw new ServiceException("An exception occurred while connecting to the database", e);
        }
    }

    public void createResultTable() {
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_RESULT_TABLE_STATEMENT);
            setInitialized();
        } catch (SQLException e) {
            throw new ServiceException("An exception occurred while initilizing database", e);
        }
    }

    private boolean isInitialized () {
        return initialized;
    }

    private void setInitialized() {
        initialized = true;
    }

    public static SQLiteAnalyzeRezultService getInstance(String sQLiteConnectionString) {
        SQLiteAnalyzeRezultService sQLiteAnalyzeRezultService = 
            new SQLiteAnalyzeRezultService(sQLiteConnectionString);
        try {
            sQLiteAnalyzeRezultService.createResultTable();
        } catch (ServiceException e) {
            //TODO
        }
        return sQLiteAnalyzeRezultService;
    }

}
