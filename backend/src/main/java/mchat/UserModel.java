package mchat;

import com.sun.javafx.sg.prism.NGShape;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel extends Model {
    private int id;
    private String alias;
    private String name;
    private String password;
    private String status;
    private String email;
    public UserModel(){
    }

    public UserModel(String alias, String name, String password, String status, String email) {
        this.alias = alias;
        this.name = name;
        this.password = password;
        this.status = status;
        this.email = email;
    }

    public UserModel(int id, String alias, String name, String password, String status, String email) {
        this.id = id;
        this.alias = alias;
        this.name = name;
        this.password = password;
        this.status = status;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserModel insert(UserModel model){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("INSERT INTO user (alias,name,status," +
                    "password,email) VALUE (?,?,?,?,?)");
            preparedStatement.setString(1,model.getAlias());
            preparedStatement.setString(2,model.getName());
            preparedStatement.setString(3,model.getStatus());
            preparedStatement.setString(4,model.getPassword());
            preparedStatement.setString(5,model.getEmail());
            preparedStatement.executeUpdate();
            return UserModel.selectByName(model.name);
        } catch (SQLException e) {
            return null;
        }finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static UserModel selectById(int id){
        UserModel model = null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM user WHERE id = ?");
            preparedStatement.setInt(1,id);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                model = new UserModel();
                model.setId(resultSet.getInt("id"));
                model.setAlias(resultSet.getString("alias"));
                model.setName(resultSet.getString("name"));
                model.setPassword(resultSet.getString("password"));
                model.setStatus(resultSet.getString("status"));
                model.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
           model=null;
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }
    public static UserModel selectByName(String name){
        UserModel model = null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM user WHERE name = ?");
            preparedStatement.setString(1,name);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                model = new UserModel();
                model.setId(resultSet.getInt("id"));
                model.setAlias(resultSet.getString("alias"));
                model.setName(resultSet.getString("name"));
                model.setPassword(resultSet.getString("password"));
                model.setStatus(resultSet.getString("status"));
                model.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            model=null;
            e.printStackTrace();
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }
    public static UserModel selectByEmail(String email){
        UserModel model = null;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM user WHERE email = ?");
            preparedStatement.setString(1,email);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                model = new UserModel();
                model.setId(resultSet.getInt("id"));
                model.setAlias(resultSet.getString("alias"));
                model.setName(resultSet.getString("name"));
                model.setPassword(resultSet.getString("password"));
                model.setStatus(resultSet.getString("status"));
                model.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            model=null;
            e.printStackTrace();
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }
    public static UserModel update(UserModel model){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("UPDATE user SET alias=?,name=?,status=?," +
                    "password=?,email=? WHERE id = ?");
            preparedStatement.setString(1,model.getAlias());
            preparedStatement.setString(2,model.getName());
            preparedStatement.setString(3,model.getStatus());
            preparedStatement.setString(4,model.getPassword());
            preparedStatement.setInt(6,model.getId());
            preparedStatement.setString(5,model.getEmail());
            preparedStatement.executeUpdate();
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean delete(int id){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM user WHERE id = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
