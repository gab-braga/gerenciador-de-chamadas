package model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Connect {

    final static private String url = "jdbc:mysql://127.0.0.1:3306";
    final static private String usuario = "root";
    final static private String senha = "root3306";

    static Connection connection = null;

    private static void openConnection() {
        try {
            connection = DriverManager.getConnection(url, usuario, senha);
        }
        catch(SQLException e) {
            System.err.println("ERROR (OPEN CONNECTION): " + e.getCause());
        }
    }

    private static void closeConnection() {
        try {
            connection.close();
        }
        catch(SQLException e) {
            System.err.println("ERROR (CLOSE CONNECTION): " + e.getCause());
        }
    }

    private static boolean useDataBase() {
        boolean flag = false;
        try {
            Statement statement = connection.createStatement();
            String sql = "USE calls;";
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            flag = false;
        }
        return flag;
    }

    private static boolean createDataBase() {
        boolean flag = false;
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS calls;";
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            flag = false;
        }
        return flag;
    }

    private static boolean createTableCall() {
        boolean flag = false;
        openConnection();
        try {
            if(createDataBase()) {
                if (useDataBase()) {
                    String sql = "CREATE TABLE IF NOT EXISTS callhistory(" +
                            "phone VARCHAR(20) PRIMARY KEY," +
                            "nameRegister VARCHAR(80) NOT NULL," +
                            "status VARCHAR(80) NOT NULL," +
                            "description VARCHAR(255) NOT NULL," +
                            "lastUpdate TIMESTAMP NOT NULL" +
                            ")" +
                            "ENGINE=InnoDB;";
                    Statement statement = connection.createStatement();
                    statement.execute(sql);
                    flag = true;
                } else {
                    System.err.println("ERROR (USE DATABASE)\n");
                    throw new SQLException();
                }
            }
            else {
                System.err.println("ERROR (CREATE DATABASE)\n");
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.err.println("ERROR (CREATE TABLE): " + e.getMessage());
        }
        closeConnection();
        return flag;
    }

    public static boolean addCall(Call call) {
        boolean flag = false;
        if(createTableCall()) {
            openConnection();
            try {
                if(useDataBase()) {
                    String sql = "INSERT INTO callhistory (phone, nameRegister, status, description, lastUpdate) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP());";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, call.getPhone());
                    statement.setString(2, call.getName());
                    statement.setString(3, call.getStatus());
                    statement.setString(4, call.getDescription());
                    statement.executeUpdate();
                    flag = true;
                }
                else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                System.err.println("ERRR (ADD CALL): " + e.getCause());
            }
            closeConnection();
        }
        return flag;
    }

    public static List<Call> queryAllCalls() {
        List<Call> calls = null;
        if(createTableCall()) {
            openConnection();
            try {
                if(useDataBase()) {
                    String sql = "SELECT * FROM callhistory ORDER BY nameRegister;";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    calls = getListCalls(statement.executeQuery());
                }
                else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                System.err.println("ERROR (QUERY ALL CALLS): " + e.getCause());
            }
            closeConnection();
        }
        return calls;
    }

    public static List<Call> queryCallsFiltered(String phone) {
        List<Call> calls = null;
        if(createTableCall()) {
            openConnection();
            try {
                if(useDataBase()) {
                    String sql = "SELECT * FROM callhistory WHERE phone LIKE '"+phone+"%' ORDER BY nameRegister;";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    calls = getListCalls(statement.executeQuery());
                }
                else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                System.err.println("ERROR (QUERY CALLS FILTERED): " + e.getCause());
            }
            closeConnection();
        }
        return calls;
    }

    public static List<Call> queryCallByPhone(String phone) {
        List<Call> calls = new ArrayList<Call>();
        if(createTableCall()) {
            openConnection();
            try {
                if(useDataBase()) {
                    String sql = "SELECT * FROM callhistory WHERE phone = ?;";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, phone);
                    calls = getListCalls(statement.executeQuery());
                }
                else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                System.err.println("ERROR (QUERY CALL BY PHONE): " + e.getCause());
            }
            closeConnection();
        }
        return calls;
    }

    public static boolean updateCall(Call call) {
        boolean flag = false;
        if(createTableCall()) {
            openConnection();
            try {
                if(useDataBase()) {
                    String sql = "UPDATE callhistory SET nameRegister = ?, status = ?, description = ?, lastUpdate = CURRENT_TIMESTAMP() WHERE phone = ?;";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, call.getName());
                    statement.setString(2, call.getStatus());
                    statement.setString(3, call.getDescription());
                    statement.setString(4, call.getPhone());
                    statement.executeUpdate();
                    flag = true;
                }
                else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                System.err.println("ERROR (UPDATE CALL): " + e.getCause());
            }
            closeConnection();
        }
        return flag;
    }

    public static boolean removeCall(String phone) {
        boolean flag = false;
        if(createTableCall()) {
            openConnection();
            try {
                if(useDataBase()) {
                    String sql = "DELETE FROM callhistory WHERE phone = ?;";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, phone);
                    statement.executeUpdate();
                    flag = true;
                }
                else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                System.err.println("ERROR (REMOVE CALL): " + e.getCause());
            }
            closeConnection();
        }
        return flag;
    }

    private static List<Call> getListCalls(ResultSet search) throws SQLException {
        List<Call> calls = new ArrayList<>();
        while(search.next()) {
            String phone = search.getString("phone");
            String name = search.getString("nameRegister");
            String status = search.getString("status");
            String description = search.getString("description");
            Timestamp timestamp = search.getTimestamp("lastUpdate");
            calls.add(new Call(name, phone, status, description, getTimestampUtil(timestamp)));
        }
        return calls;
    }

    protected static java.util.Date getTimestampUtil(java.sql.Timestamp timestamp) {
        Date datetime = new java.util.Date();
        try {
            SimpleDateFormat formatTimestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            datetime = formatTimestamp.parse(formatTimestamp.format(timestamp));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datetime;
    }
}
