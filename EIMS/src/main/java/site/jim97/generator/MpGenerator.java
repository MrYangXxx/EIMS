package site.jim97.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MpGenerator {

	// 生成代码里，注释的作者
	private static String author = "jim";
	// 代码生成输出的目录，可为项目路径的相对路径
	private static String outputDirectory = "src\\main\\java";
	// jdbc驱动
	private static String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	// 数据库连接地址
	private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/guns?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=UTC";
	// 数据库账号
	private static String jdbcUserName = "root";
	// 数据库密码
	private static String jdbcPassword = "123456";
	// 去掉表的前缀
	private static String[] removeTablePrefix = { "t_","sys_" };
	// 代码生成包含的表，可为空，为空默认生成所有
	private static String[] includeTables;
	// 代码生成排除的表，可为空，为空则不排除任何表
	private static String[] excludeTables;
	// 代码生成的类的父包名称
	private static String parentPackage = "site.jim97.blog.modular";
	// service是否生成接口，这个根据自己项目情况决定
	private static Boolean generatorInterface = true;
	//生成模板路径
	private static String serviceImplTemplatePath = "/templates/serviceImpl.java";
	private static String controllerTemplatePath = "/templates/controller.java";
	//实体是否用lombok
	private static boolean useLombok = true;
	/**
	 * <p>
	 * MySQL 生成演示
	 * </p>
	 */
	public static void main(String[] args) {
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir(outputDirectory);
		gc.setFileOverride(true);
		gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(false);// XML columList
		gc.setAuthor(author);
		
		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		if (generatorInterface) {
			gc.setServiceName("%sService");
			gc.setServiceImplName("%sServiceImpl");
			gc.setControllerName("%sController");
		} else {
			gc.setServiceName("%sService");
			gc.setServiceImplName("%sService");
			gc.setControllerName("%sController");
		}
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setDriverName(jdbcDriver);
		dsc.setUrl(jdbcUrl);
		dsc.setUsername(jdbcUserName);
		dsc.setPassword(jdbcPassword);
		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setCapitalMode(false);// 全局大写命名 ORACLE 注意设为true
		strategy.setTablePrefix(removeTablePrefix);// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setInclude(includeTables); // 需要生成的表
		strategy.setExclude(excludeTables); // 排除生成的表
		strategy.setEntityLombokModel(useLombok);//实体是否用lombok
		strategy.setSuperControllerClass("BaseController");
		// 公共字段填充
		// ArrayList<TableFill> tableFills = new ArrayList<>();
		// tableFills.add(new TableFill("CREATE_TIME", FieldFill.INSERT));
		// tableFills.add(new TableFill("UPDATE_TIME", FieldFill.UPDATE));
		// tableFills.add(new TableFill("CREATE_USER", FieldFill.INSERT));
		// tableFills.add(new TableFill("UPDATE_USER", FieldFill.UPDATE));
		// strategy.setTableFillList(tableFills);

		mpg.setStrategy(strategy);

		// 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
		// 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
		TemplateConfig tc = new TemplateConfig();
		tc.setController(controllerTemplatePath);
		tc.setServiceImpl(serviceImplTemplatePath);

		// 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
		mpg.setTemplate(tc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(parentPackage);
		pc.setModuleName("");
		pc.setXml("mapper.mapping");
		mpg.setPackageInfo(pc);

		if (generatorInterface) {
			pc.setServiceImpl("service.impl");
			pc.setService("service");
		} else {
			pc.setServiceImpl("service");
			pc.setService("service");
		}

		mpg.setPackageInfo(pc);

		// 执行生成
		mpg.execute();
	}
}
