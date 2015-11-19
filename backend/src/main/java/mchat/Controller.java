package mchat;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class Controller {

    //create user with param
    //return status
    //true or false
    @RequestMapping(value = "/regis/{email}/{username}/{name}/{pass}", method= RequestMethod.GET)
    public String register(@PathVariable("email") String email,
                              @PathVariable("username") String username,
                              @PathVariable("name") String name,
                              @PathVariable("pass") String pass
                              ) throws JSONException{
        String str,status = "";
        int userid = 0;
        if(UserModel.selectByName(username) == null){
            UserModel user = UserModel.insert(new UserModel(name, username, pass, "regis", email));
            if(user != null){
                str = "true";
                status = "complete";
                userid = user.getId();
            }
            else{
                str = "false";
                status = "connection error";
            }
        }
        else{
            str = "false";
            status = "exist username";
        }
        JSONObject tmp = new JSONObject();
        tmp.put("response", str);
        tmp.put("status", status);
        tmp.put("userid", userid);
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp);
        return output.toString();
    }

    @RequestMapping(value = "/edit/{id}/{name}/{email}", method= RequestMethod.GET)
    public String editUser(@PathVariable("id") int userid,
                           @PathVariable("name") String name,
                           @PathVariable("email") String email)throws JSONException{
        UserModel edituser = UserModel.selectById(userid);
        String status = "false";
        String str = userid + "not found";
        if(edituser != null){
            edituser.setAlias(name);
            edituser.setEmail(email);
            str = userid + "user found";
            if(UserModel.update(edituser) != null){
                str = userid + "was edited";
                status = "true";
            }
        }
        JSONObject tmp = new JSONObject();
        tmp.put("response", str);
        tmp.put("status", status);
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp);
        return output.toString();
    }

    //check validate of param
    //return true false
    @RequestMapping(value = "/login/{username}/{pass}", method= RequestMethod.GET)
    public String login(@PathVariable("username") String username,
                        @PathVariable("pass") String pass) throws JSONException {
        //check exist
        String str,status = "";
        UserModel user = UserModel.selectByName(username);
        if(user != null){
            if(user.getPassword().equals(pass)){
                str = "true";
                status = "validate";
            }
            else{
                str = "false";
                status = "invalid password";
            }
        }
        else{
            str = "false";
            status = "invalid username";

        }
        JSONObject tmp = new JSONObject();
        tmp.put("response", str);
        tmp.put("status", status);
        tmp.put("userid", user.getId());
        tmp.put("username", user.getName());
        tmp.put("alias", user.getAlias());
        tmp.put("email", user.getEmail());
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp);
        return output.toString();
    }

    //add ftiend
    @RequestMapping(value = "/addfriend/{userid1}/{userid2}", method= RequestMethod.GET)
    public String addFriend(@PathVariable("userid1") int userid1,
                            @PathVariable("userid2") int userid2) throws JSONException {
        FriendModel friendtmp = FriendModel.insert(new FriendModel(userid1, userid2));
        String status = "false";
        if(friendtmp != null){
            status = "true";
        }
        JSONObject tmp = new JSONObject();
        tmp.put("status", status);
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp);
        return output.toString();
    }

    @RequestMapping(value = "/acceptfriend/{userid1}/{userid2}", method= RequestMethod.GET)
    public  String acceptFriend(@PathVariable("userid1") int userid1,
                                @PathVariable("userid2") int userid2) throws JSONException  {
        FriendModel friendtmp = FriendModel.update(userid1, userid2, 1);
        String status = "false";
        if(friendtmp != null){
            status = "true";
        }
        JSONObject tmp = new JSONObject();
        tmp.put("status", status);
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp);
        return output.toString();
    }

    @RequestMapping(value = "/rejectfriend/{userid1}/{userid2}", method= RequestMethod.GET)
    public  String rejectFriend(@PathVariable("userid1") int userid1,
                                @PathVariable("userid2") int userid2) throws JSONException  {
        FriendModel friendtmp = FriendModel.update(userid1, userid2, 2);
        String status = "false";
        if(friendtmp != null){
            status = "true";
        }
        JSONObject tmp = new JSONObject();
        tmp.put("status", status);
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp);
        return output.toString();
    }


    //return friend of userid
    @RequestMapping(value = "/getfriend/{userid}", method= RequestMethod.GET)
    public String getFriendList(@PathVariable("userid") int userid) throws JSONException {
        List<UserModel> friend = FriendModel.selectFriend(userid);
        List<UserModel> notfriend = FriendModel.selectNotFriend(userid);
        List<UserModel> requestingfriend = FriendModel.selectRequesting(userid);
        List<UserModel> requestedfriend = FriendModel.selectRequested(userid);
        List<JSONObject> friend_list = new ArrayList<JSONObject>();
        List<JSONObject> notfriend_list = new ArrayList<JSONObject>();
        List<JSONObject> requestingfriend_list = new ArrayList<JSONObject>();
        List<JSONObject> requestedfriend_list = new ArrayList<JSONObject>();
        for(Iterator<UserModel> iter = friend.iterator(); iter.hasNext();){
            JSONObject tmp = new JSONObject();
            UserModel user = iter.next();
            tmp.put("userid", user.getId());
            tmp.put("alias", user.getAlias());
            tmp.put("email", user.getEmail());
            friend_list.add(tmp);
        }
        for(Iterator<UserModel> iter = notfriend.iterator(); iter.hasNext();){
            JSONObject tmp = new JSONObject();
            UserModel user = iter.next();
            tmp.put("userid", user.getId());
            tmp.put("alias", user.getAlias());
            tmp.put("email", user.getEmail());
            notfriend_list.add(tmp);
        }
        for(Iterator<UserModel> iter = requestingfriend.iterator(); iter.hasNext();){
            JSONObject tmp = new JSONObject();
            UserModel user = iter.next();
            tmp.put("userid", user.getId());
            tmp.put("alias", user.getAlias());
            tmp.put("email", user.getEmail());
            requestingfriend_list.add(tmp);
        }
        for(Iterator<UserModel> iter = requestedfriend.iterator(); iter.hasNext();){
            JSONObject tmp = new JSONObject();
            UserModel user = iter.next();
            tmp.put("userid", user.getId());
            tmp.put("alias", user.getAlias());
            tmp.put("email", user.getEmail());
            requestedfriend_list.add(tmp);
        }
        JSONObject tmp_list = new JSONObject();
        tmp_list.put("friend", friend_list);
        tmp_list.put("notfriend", notfriend_list);
        tmp_list.put("requesting", requestingfriend_list);
        tmp_list.put("requested", requestedfriend_list);
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp_list);
        return output.toString();
    }


    @RequestMapping(value = "/sendmessage/{userid1}/{userid2}/{message}", method= RequestMethod.GET)
    public String sendMessage(@PathVariable("userid1") int userid1,
                              @PathVariable("userid2") int userid2,
                              @PathVariable("message") String message) throws JSONException {
        java.sql.Timestamp dates = new java.sql.Timestamp(System.currentTimeMillis());
        MessageModel send_message = MessageModel.insert(new MessageModel(message,dates ,"normal",false,userid1,userid2));
        String str,status = "";
        int msgid = -1;
        if(send_message != null){
            str = "true";
            status = "sent";
            msgid = send_message.getId();
        }
        else{
            str = "false";
            status = "send fail";
        }
        JSONObject tmp = new JSONObject();
        JSONObject output = new JSONObject();
        tmp.put("response", str);
        tmp.put("status", status);
        tmp.put("id", msgid);
        if(send_message != null){
            tmp.put("date", send_message.getDate());
        }
        else{
            tmp.put("date", dates);
        }
        output.put("resultObject", tmp);
        return output.toString();
    }

    @RequestMapping(value = "/loadmessage/{userid1}/{userid2}/{msgid}", method= RequestMethod.GET)
    public String loadMessage(@PathVariable("userid1") int userid1,
                              @PathVariable("userid2") int userid2,
                              @PathVariable("msgid") int msgid) throws JSONException {
        List<MessageModel> message = MessageModel.selectAllById(msgid, userid1, userid2);
        List<JSONObject> message_list = new ArrayList<JSONObject>();
        for(Iterator<MessageModel> iter = message.iterator(); iter.hasNext();){
            MessageModel msg = iter.next();
            JSONObject tmp = new JSONObject();
            tmp.put("id", msg.getId());
            tmp.put("text", msg.getMessage());
            tmp.put("sender", msg.getUser_id_s());
            tmp.put("reciever", msg.getUser_id_r());
            tmp.put("marked", msg.isMark());
            tmp.put("date", msg.getDate());
            message_list.add(tmp);
        }
        JSONObject msg_list = new JSONObject();
        msg_list.put("message", message_list);
        if(message_list.size() > 0){
            msg_list.put("response", "true");
        }
        else{
            msg_list.put("response", "false");
        }
        JSONObject output = new JSONObject();
        output.put("resultObject", msg_list);
        return output.toString();

    }


        @RequestMapping(value = "/loadmarked/{userid1}/{userid2}", method= RequestMethod.GET)
    public String getMarked(@PathVariable("userid1") int userid1,
                            @PathVariable("userid2") int userid2) throws JSONException {
       List<MessageModel> message = MessageModel.selectByMark(userid1, userid2);
       List<JSONObject> message_list = new ArrayList<JSONObject>();
       for(Iterator<MessageModel> iter = message.iterator(); iter.hasNext();){
           MessageModel msg = iter.next();
           JSONObject tmp = new JSONObject();
           tmp.put("id", msg.getId());
           tmp.put("text", msg.getMessage());
           tmp.put("sender", msg.getUser_id_s());
           tmp.put("reciever", msg.getUser_id_r());
           tmp.put("marked", msg.isMark());
           tmp.put("date", msg.getDate());
           message_list.add(tmp);
       }
       JSONObject msg_list = new JSONObject();
       msg_list.put("message", message_list);
       if(message_list.size() > 0){
           msg_list.put("response", "true");
       }
       else{
           msg_list.put("response", "false");
       }
       JSONObject output = new JSONObject();
       output.put("resultObject", msg_list);
       return output.toString();
    }

    @RequestMapping(value = "/togglemarked/{id}", method= RequestMethod.GET)
    public String toggleMarked(@PathVariable("id") int id) throws JSONException {
        MessageModel msg = MessageModel.selectById(id);
        msg.setMark(!msg.isMark());
        String str,status = "";
        if(MessageModel.update(msg) != null){
            str = "true";
            if(msg.isMark()){
                status = "marked";
            }
            else{
                status = "unmarked";
            }
        }
        else{
            str = "false";
            status = "error";
        }
        JSONObject tmp = new JSONObject();
        tmp.put("response", str);
        tmp.put("status", status);
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp);
        return output.toString();
    }

    @RequestMapping(value = "/forgot/{email}",  method= RequestMethod.GET)
    public String forgotPassword(@PathVariable("email") String email) throws MessagingException,JSONException {
        UserModel usertmp = UserModel.selectByEmail(email.toString()+".com");
        String status = "false";
        String str = "Invalid email";
        if(usertmp != null){
            String detail = "Hi, "+usertmp.getAlias()+"\r\nThis is your account detail.\r\n\r\n";
            detail += "------------------------------------------------------------------------\r\n";
            detail += "ID       : "+usertmp.getName()+"\r\n";
            detail += "Password : "+usertmp.getPassword()+"\r\n\r\n";
            detail += "------------------------------------------------------------------------\r\n";
            detail += "Please keep this information for your advantage.\r\n";
            detail += "Thanks and Regards, MChat.";
            EmailService mailer = new EmailService();
            mailer.sendMail(usertmp.getEmail(), "Forgot your password.", detail);
            status = "true";
            str = "Email sent";
        }
        JSONObject tmp = new JSONObject();
        tmp.put("response", str);
        tmp.put("status", status);
        JSONObject output = new JSONObject();
        output.put("resultObject", tmp);
        return output.toString();
    }

}
