package controller;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.Path;
import com.jfinal.json.FastJson;
import model.User;
import service.UserService;

import java.util.List;


@Path("/user")
public class UserController extends Controller {

    @Inject
    UserService service;

    public void index(){
        render("login.html");
    }

    public void student(){
        render("studentPage.html");
    }

    public void teacher(){
        render("teacherPage.html");
    }

    public void login(){
        System.out.println("进入login方法");
        String json = getRawData();
        System.out.println(json);
        User parse = FastJson.getJson().parse(json, User.class);
        List<User> users = service.login(parse);
        for (User user1 : users) {
            System.out.println(user1.toString());
        }
        if (users!= null){
            System.out.println("登录成功");
            System.out.println(parse);
            setSessionAttr("user",parse);
            renderJson("success");
        }
    }

    public void getPersonInfo(){
        System.out.println("进入getPersonInfo");
        User user = getSessionAttr("user");
        System.out.println(user);
        List<User> users = service.getByUserName(user.getUsername());
        renderJson(users);
    }

    //修改用户密码
    public void updatePassword(){
        System.out.println("进入updatePassword");
        String s = getRawData();
        User parse = FastJson.getJson().parse(s, User.class);
        int i = service.updatePassword(parse);
        if (i > 0){
            renderJson("success");
        } else {
            renderJson("error");
        }
    }
}
