package mchat;


import java.sql.*;
import java.util.ArrayList;

public class MessageModel {
    private int id;
    private String message;
    private Timestamp date;
    private String type;
    private boolean mark;
    private int user_id_s;
    private int user_id_r;

    public MessageModel() {
    }

    public MessageModel(String message, String type, boolean mark, int user_id_s, int user_id_r) {
        this.message = message;
        this.date = date;
        this.type = type;
        this.mark = mark;
        this.user_id_s = user_id_s;
        this.user_id_r = user_id_r;
    }

    public MessageModel(String message, Timestamp date, String type, boolean mark, int user_id_s, int user_id_r) {
        this.message = message;
        this.date = date;
        this.type = type;
        this.mark = mark;
        this.user_id_s = user_id_s;
        this.user_id_r = user_id_r;
    }

    public MessageModel(int id, String message, Timestamp date, String type, boolean mark, int user_id_s, int user_id_r) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.type = type;
        this.mark = mark;
        this.user_id_s = user_id_s;
        this.user_id_r = user_id_r;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public int getUser_id_s() {
        return user_id_s;
    }

    public void setUser_id_s(int user_id_s) {
        this.user_id_s = user_id_s;
    }

    public int getUser_id_r() {
        return user_id_r;
    }

    public void setUser_id_r(int user_id_r) {
        this.user_id_r = user_id_r;
    }

    public static MessageModel insert(MessageModel model) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Model.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO message (message,date,type," +
                    "mark,user_id_s,user_id_r) VALUE (?,?,?,?,?,?)");
            preparedStatement.setString(1, model.getMessage());
            preparedStatement.setTimestamp(2, model.getDate());
            preparedStatement.setString(3, model.getType());
            preparedStatement.setBoolean(4, model.isMark());
            preparedStatement.setInt(5, model.getUser_id_s());
            preparedStatement.setInt(6, model.getUser_id_r());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT * FROM message ORDER by id DESC Limit 0,1");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                model = new MessageModel();
                model.setId(resultSet.getInt("id"));
                model.setMessage(resultSet.getString("message"));
                model.setDate(resultSet.getTimestamp("date"));
                model.setUser_id_r(resultSet.getInt("user_id_r"));
                model.setUser_id_s(resultSet.getInt("user_id_s"));
                model.setMark(resultSet.getBoolean("mark"));
                model.setType(resultSet.getString("type"));
            }
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static MessageModel selectById(int id) {
        MessageModel model = new MessageModel();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Model.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM message WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                model.setId(resultSet.getInt("id"));
                model.setMessage(resultSet.getString("message"));
                model.setDate(resultSet.getTimestamp("date"));
                model.setType(resultSet.getString("type"));
                model.setMark(resultSet.getBoolean("mark"));
                model.setUser_id_s(resultSet.getInt("user_id_s"));
                model.setUser_id_r(resultSet.getInt("user_id_r"));
            }
        } catch (SQLException e) {
            model = null;
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    public static MessageModel selectByDate(Timestamp date) {
        MessageModel model = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Model.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM message WHERE date = ?");
            preparedStatement.setTimestamp(1, date);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                model = new MessageModel();
                model.setId(resultSet.getInt("id"));
                model.setMessage(resultSet.getString("message"));
                model.setDate(resultSet.getTimestamp("date"));
                model.setType(resultSet.getString("type"));
                model.setMark(resultSet.getBoolean("mark"));
                model.setUser_id_s(resultSet.getInt("user_id_s"));
                model.setUser_id_r(resultSet.getInt("user_id_r"));
            }
        } catch (SQLException e) {
            model = null;
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    public static ArrayList<MessageModel> selectAllByDate(Timestamp date, int m_id, int f_id) {
        ArrayList a_model = new ArrayList();
        MessageModel model = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Model.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM message WHERE (date > ?) and ((user_id_s = ? and user_id_r=?)or(user_id_s = ? and user_id_r=?) )");
            preparedStatement.setTimestamp(1, date);
            preparedStatement.setInt(2, m_id);
            preparedStatement.setInt(3, f_id);
            preparedStatement.setInt(4, f_id);
            preparedStatement.setInt(5, m_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                model = new MessageModel();
                model.setId(resultSet.getInt("id"));
                model.setMessage(resultSet.getString("message"));
                model.setDate(resultSet.getTimestamp("date"));
                model.setType(resultSet.getString("type"));
                model.setMark(resultSet.getBoolean("mark"));
                model.setUser_id_s(resultSet.getInt("user_id_s"));
                model.setUser_id_r(resultSet.getInt("user_id_r"));
                a_model.add(model);
            }
        } catch (SQLException e) {
            a_model = null;
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return a_model;
    }

    public static ArrayList<MessageModel> selectByMark(int s, int r) {
        ArrayList a_model = new ArrayList();
        MessageModel model = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Model.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM message WHERE (mark = 1) and ((user_id_s = ? and user_id_r=?)or(user_id_s = ? and user_id_r=?) )");
            preparedStatement.setInt(1, s);
            preparedStatement.setInt(2, r);
            preparedStatement.setInt(3, r);
            preparedStatement.setInt(4, s);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                model = new MessageModel();
                model.setId(resultSet.getInt("id"));
                model.setMessage(resultSet.getString("message"));
                model.setDate(resultSet.getTimestamp("date"));
                model.setType(resultSet.getString("type"));
                model.setMark(resultSet.getBoolean("mark"));
                model.setUser_id_s(resultSet.getInt("user_id_s"));
                model.setUser_id_r(resultSet.getInt("user_id_r"));
                a_model.add(model);
            }
        } catch (SQLException e) {
            a_model = null;
            e.printStackTrace();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return a_model;
    }

    public static MessageModel update(MessageModel model) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Model.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE message SET message=?,date=?,type=?," +
                    "mark=?,user_id_s=?,user_id_r=? WHERE id = ?");
            preparedStatement.setString(1, model.getMessage());
            preparedStatement.setTimestamp(2, model.getDate());
            preparedStatement.setString(3, model.getType());
            preparedStatement.setBoolean(4, model.isMark());
            preparedStatement.setInt(7, model.getId());
            preparedStatement.setInt(5, model.getUser_id_s());
            preparedStatement.setInt(6, model.getUser_id_r());
            preparedStatement.executeUpdate();
            return model;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean delete(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Model.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM message WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<MessageModel>  selectAllById(int id, int user_id_s, int user_id_r) {
        ArrayList a_model = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Model.getConnection();
            if (id == -1) {
                preparedStatement = connection.prepareStatement("SELECT * FROM message where ((user_id_s =? and user_id_r=?) or (user_id_s =? and user_id_r=?))");
                preparedStatement.setInt(1, user_id_s);
                preparedStatement.setInt(2, user_id_r);
                preparedStatement.setInt(3, user_id_r);
                preparedStatement.setInt(4, user_id_s);
            } else {
                preparedStatement = connection.prepareStatement("SELECT * FROM message where id >? and ((user_id_s =? and user_id_r=?) or (user_id_s =? and user_id_r=?)) ");
                preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, user_id_s);
                preparedStatement.setInt(3, user_id_r);
                preparedStatement.setInt(4, user_id_r);
                preparedStatement.setInt(5, user_id_s);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MessageModel model = new MessageModel();
                model.setId(resultSet.getInt("id"));
                model.setMessage(resultSet.getString("message"));
                model.setDate(resultSet.getTimestamp("date"));
                model.setType(resultSet.getString("type"));
                model.setMark(resultSet.getBoolean("mark"));
                model.setUser_id_s(resultSet.getInt("user_id_s"));
                model.setUser_id_r(resultSet.getInt("user_id_r"));
                a_model.add(model);
            }
        } catch (SQLException e) {
            a_model = null;
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return a_model;
    }
}
