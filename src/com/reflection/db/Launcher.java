package com.reflection.db;

import org.h2.tools.Server;

import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) throws SQLException {
        Server.main();
        System.out.println("db launched");
    }
}
