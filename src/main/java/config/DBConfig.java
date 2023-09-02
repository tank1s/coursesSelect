package config;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import model.Course;
import model.Courseselection;
import model.User;


public class DBConfig extends JFinalConfig {
    public static void main(String[] args) {
        UndertowServer.start(DBConfig.class,80,true);
    }
    @Override
    public void configConstant(Constants constants) {
        // 配置开发模式，true 值为开发模式
        constants.setDevMode(true);
        // 配置依赖注入
        constants.setInjectDependency(true);
    }

    @Override
    public void configRoute(Routes routes) {
        // // 扫描仅会在controller包以及controller包的子包下进行
        routes.scan("controller.");
    }

    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins plugins) {
        String jdbcUrlString="jdbc:mysql://localhost:3306/courseonline?useSSL=false"; // 数据驱动
        String userName = "root";           //用户名
        String password = "123456";         //密码

        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrlString,userName,password);
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);

        activeRecordPlugin.setDevMode(true);
        activeRecordPlugin.setShowSql(true);

        activeRecordPlugin.addMapping("user", User.class);//完成映射
        activeRecordPlugin.addMapping("course", Course.class);//完成映射
        activeRecordPlugin.addMapping("courseselection", Courseselection.class);//完成映射
        plugins.add(druidPlugin);
        plugins.add(activeRecordPlugin);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    @Override
    public void configHandler(Handlers handlers) {

    }
}
