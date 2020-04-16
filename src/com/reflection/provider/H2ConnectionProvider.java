package com.reflection.provider;

import com.reflection.annotations.Provides;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class H2ConnectionProvider {
    @Provides
    public Connection buildConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:C:\\Users\\mostafa\\IdeaProjects\\javaReflection\\lib\\h2-1.4.197.jar", "sa", "");
    }
}
