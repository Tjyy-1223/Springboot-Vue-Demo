package com.example.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2b8", "root", "991116")
                .globalConfig(builder -> {
                    builder.author("tjyy") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/tianjiangyu/MyStudy/Java体系/10_Springboot-Vue/springboot-project/springboot/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/tianjiangyu/MyStudy/Java体系/10_Springboot-Vue/springboot-project/springboot/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.addInclude("sys_menu"); // 设置需要生成的表名
//                            .addTablePrefix("_2"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
