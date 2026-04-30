package de.sirswiperlpp.templatePlugin.SQL;

import de.sirswiperlpp.templatePlugin.Main.Main;
import de.sirswiperlpp.templatePlugin.Utils.Language;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL
{


    static Language language = new Language(new File(Main.getInstance().getDataFolder(), "lang.ini"));

    public static String host = Main.config.getString("sql.host");
    public static String port = Main.config.getString("sql.port");
    public static String username = Main.config.getString("sql.user");
    public static String password = Main.config.getString("sql.pwd");
    public static String database = Main.config.getString("sql.database");
    public static Connection con;
    private static final int KEEP_ALIVE_INTERVAL = 300000;


    public static void connect() {
        if (!isConnected()) {
            try {
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";
                con = DriverManager.getConnection(url, username, password);
                System.out.println(language.translateString("connection.established", database));

                // Start keep-alive thread
                new Thread(() -> {
                    while (true) {
                        try {
                            Thread.sleep(KEEP_ALIVE_INTERVAL);
                            if (isConnected()) {
                                con.createStatement().executeQuery("SELECT 1");
                            }
                        } catch (SQLException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // disconnect
    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                System.out.println(language.get("connection.dc"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // isConnected
    public static boolean isConnected() {
        return (con != null);
    }

    // getConnection
    public static Connection getConnection() {
        return con;
    }


}

