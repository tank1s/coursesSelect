package config;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

public class MyGenerator {
    public static DataSource getDataSource() {
        String jdbcUrlString="jdbc:mysql://localhost:3306/courseonline?useSSL=false";
        String userName = "root";
        String password = "123456";

        DruidPlugin dp = new DruidPlugin(jdbcUrlString, userName, password);
        dp.start();
        return dp.getDataSource();
    }

    public static void main(String[] args) {
        //1 DataSource
        //2 base model package name
        String baseModelPackageName="model.base";
        //3 base model dir
        String baseModelOutputDir= PathKit.getWebRootPath()+"/../java/model/base";
        //4 modelPackageName
        String modelPackageName = "model";
        //5 modelOutputDir
        String modelOutputDir = baseModelOutputDir + "/..";
        //
        Generator gernerator= new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir,
                modelPackageName, modelOutputDir);

        gernerator.setDialect(new MysqlDialect());
        // dao
        gernerator.setGenerateDaoInModel(true);

        gernerator.setGenerateDataDictionary(true);

        gernerator.generate();
    }
}
