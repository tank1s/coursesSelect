package service;

import com.jfinal.plugin.activerecord.Db;
import model.User;

import java.util.List;

public class UserService {
    public static final User dao = new User().dao();
    public List<User> login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        return dao.find("select * from user where username = '" + username + "' and password = '" + password + "'");
    }

    public List<User> getByUserName(String username){
        return  dao.find("select * from user where username = '" + username + "'");
    }

    public int  updatePassword(User parse) {
       return Db.update("update user set password = '" + parse.getPassword() + "'" +
                " where uid = '" + parse.getUid() + "'");
    }
}
