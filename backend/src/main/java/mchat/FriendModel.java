package mchat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendModel {
    private int id;
    private int sender;
    private int receiver;
    private Timestamp date;
    private int status;

    public FriendModel() {
    }

    public FriendModel(int sender, int receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static  FriendModel insert(FriendModel model){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("select * from friend WHERE sender = ? and receiver =?");
            preparedStatement.setInt(1,model.getSender());
            preparedStatement.setInt(2,model.getReceiver());
            resultSet=preparedStatement.executeQuery();
            FriendModel model_tmp=null;
            while(resultSet.next()){
                model_tmp = new FriendModel();
                model_tmp.setId(resultSet.getInt("id"));
                model_tmp.setReceiver(resultSet.getInt("receiver"));
                model_tmp.setSender(resultSet.getInt("sender"));
                model_tmp.setStatus(resultSet.getInt("status"));
                model_tmp.setDate(resultSet.getTimestamp("date_create"));
            }
            if (model_tmp==null){
                preparedStatement=connection.prepareStatement("INSERT INTO friend (sender,receiver," + "status) VALUE (?,?,?)");
                preparedStatement.setInt(1,model.getSender());
                preparedStatement.setInt(2,model.getReceiver());
                preparedStatement.setInt(3,3);
                preparedStatement.executeUpdate();
                preparedStatement=connection.prepareStatement("select * from friend order by id desc Limit 1");
                resultSet=preparedStatement.executeQuery();
                while(resultSet.next()){
                    model=new FriendModel();
                    model.setId(resultSet.getInt("id"));
                    model.setReceiver(resultSet.getInt("receiver"));
                    model.setSender(resultSet.getInt("sender"));
                    model.setStatus(resultSet.getInt("status"));
                    model.setDate(resultSet.getTimestamp("date_create"));
                }
                return model;
            }
            else{
                return null;
            }

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
    public static ArrayList<UserModel> selectFriend(int s){
        ArrayList a_model=new ArrayList();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        ArrayList<Integer> tmp = new ArrayList();
        ArrayList<UserModel> userModels = new ArrayList();
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("select user.* \n" +
                    "from friend \n" +
                    "\tinner join user\n" +
                    "\ton friend.receiver = user.id\n" +
                    "    where friend.sender = ? and friend.status = 1\n" +
                    "union\n" +
                    "select user.*\n" +
                    "from friend \n" +
                    "\tinner join user\n" +
                    "\ton friend.sender = user.id\n" +
                    "    where friend.receiver = ? and friend.status = 1\n" +
                    "\n");
            preparedStatement.setInt(1,s);
            preparedStatement.setInt(2,s);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                UserModel userModel = new UserModel(resultSet.getInt("id"),resultSet.getString("alias"),
                        resultSet.getString("name"),resultSet.getString("password"),resultSet.getString("status"),resultSet.getString("email"));
                userModels.add(userModel);
            }
        } catch (SQLException e) {
            userModels=null;
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
        return userModels;
    }
    public static ArrayList<UserModel> selectRequesting(int s){
        ArrayList a_model=new ArrayList();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        ArrayList<Integer> tmp = new ArrayList();
        ArrayList<UserModel> userModels = new ArrayList();
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("select user.* \n" +
                    "from friend \n" +
                    "\tinner join user\n" +
                    "\ton friend.receiver = user.id\n" +
                    "    where friend.sender = ? and friend.status = 3\n");
            preparedStatement.setInt(1,s);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                UserModel userModel = new UserModel(resultSet.getInt("id"),resultSet.getString("alias"),
                        resultSet.getString("name"),resultSet.getString("password"),resultSet.getString("status"),resultSet.getString("email"));
                userModels.add(userModel);
            }
        } catch (SQLException e) {
            userModels=null;
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
        return userModels;
    }
    public static ArrayList<UserModel> selectRequested(int s){
        ArrayList a_model=new ArrayList();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        ArrayList<Integer> tmp = new ArrayList();
        ArrayList<UserModel> userModels = new ArrayList();
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement(
                    "select user.*\n" +
                    "from friend \n" +
                    "\tinner join user\n" +
                    "\ton friend.sender = user.id\n" +
                    "    where friend.receiver = ? and friend.status = 3");
            preparedStatement.setInt(1,s);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                UserModel userModel = new UserModel(resultSet.getInt("id"),resultSet.getString("alias"),
                        resultSet.getString("name"),resultSet.getString("password"),resultSet.getString("status"),resultSet.getString("email"));
                userModels.add(userModel);
            }
        } catch (SQLException e) {
            userModels=null;
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
        return userModels;
    }
    public static FriendModel update(int s,int r ,int status){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        FriendModel friendModel=new FriendModel();
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("UPDATE friend SET status=? WHERE sender = ? and receiver=?");
            preparedStatement.setInt(1,status);
            preparedStatement.setInt(2,s);
            preparedStatement.setInt(3,r);
            preparedStatement.executeUpdate();
            preparedStatement=connection.prepareStatement("select * from friend WHERE sender = ? and receiver=?");
            preparedStatement.setInt(1,s);
            preparedStatement.setInt(2,r);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                friendModel.setId(resultSet.getInt("id"));
                friendModel.setSender(resultSet.getInt("sender"));
                friendModel.setReceiver(resultSet.getInt("receiver"));
                friendModel.setDate(resultSet.getTimestamp("date_create"));
                friendModel.setStatus(resultSet.getInt("status"));
            }
            return friendModel;
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
    public static ArrayList<UserModel> selectNotFriend(int id){
        ArrayList<UserModel> userModels =new ArrayList<UserModel>();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection=Model.getConnection();
            preparedStatement=connection.prepareStatement("select * from user where id != ? and (id not in(select sender from friend where receiver = ? and (status = 1 or status =3))\n" +
                    " and id not in(select receiver from friend where sender = ? and (status = 1 or status =3))) ");
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,id);
            preparedStatement.setInt(3,id);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                UserModel userModel=new UserModel(resultSet.getInt("id"),resultSet.getString("alias"), resultSet.getString("name"),
                        resultSet.getString("password"), resultSet.getString("status"), resultSet.getString("email"));
                userModels.add(userModel);
            }

        } catch (SQLException e) {
            userModels=null;
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
        return userModels;
    }
}

