package de.sirswiperlpp.realeconomy.Provider;

import de.sirswiperlpp.realeconomy.SQL.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EcoProvider
{

    public static void createEcoTable() throws SQLException
    {
        PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS eco_profiles (UUID VARCHAR(36), coins int, shards int, PRIMARY KEY (UUID))");
        ps.executeUpdate();
    }

    public static void registerPlayer(UUID uuid) throws SQLException {
        try {
            String query = "INSERT IGNORE INTO eco_profiles (UUID, coins, shards) VALUES (?,?,?)";

            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, uuid.toString());
                statement.setInt(2, 300);
                statement.setInt(3, 0);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer readCoinsFromSQL(UUID uuid) throws SQLException {
        try {
            String query = "SELECT coins FROM eco_profiles WHERE UUID = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, uuid.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("coins");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return -1;
    }

    public static Integer readShardsFromSQL(UUID uuid) throws SQLException {
        try {
            String query = "SELECT shards FROM eco_profiles WHERE UUID = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setString(1, uuid.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("shards");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return -1;
    }

    public static void updateCoins(UUID uuid, int coins) throws SQLException {
        try {
            String query = "UPDATE eco_profiles SET coins = ? WHERE UUID = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setInt(1, coins);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void updateShards(UUID uuid, int shards) throws SQLException {
        try {
            String query = "UPDATE eco_profiles SET shards = ? WHERE UUID = ?";
            try (PreparedStatement statement = MySQL.getConnection().prepareStatement(query)) {
                statement.setInt(1, shards);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
