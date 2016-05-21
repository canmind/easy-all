package com.easy.common.core.builder.freemarker;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.easy.common.core.log.Logger;
import com.easy.common.core.log.LoggerFactory;

public class TestFreeMarker {
	private static final Logger logger = LoggerFactory.getLogger(TestFreeMarker.class);
	public static void main(String[] args) {
		logger.info("test");
        // 1.创建目录
        Params params = XMLUtil.params;  
        FileUtil.initDirName(params);  
        // 2.生成文件  
        List<Tables> tables = XMLUtil.tableList;  
        for (Tables table : tables) {  
            System.out.println(table.getTableName());  
            String javaClassName = StringUtil.capitalize(StringUtil.dbNameToVarName(table.getTableName()));  
            Map<Object, Object> map = FreemarkerUtil.getTableInfo(table.getTableName());  
            map.put("title", params.getTitle());  
            map.put("author", params.getAuthor());  
            map.put("createTime", DateUtil.getToday());  
            map.put("project", params.getProject());  
            map.put("className", javaClassName);  
            map.put("voClassName", javaClassName);  
            map.put("javapackage", params.getJavapackage());  
            // 1.po  
            String poName = params.getOsdir() + File.separatorChar + "po" + File.separatorChar + javaClassName + "PO.java";  
            FreemarkerUtil.generateFile(poName, "javapo.ftl", map);  
            // 2.vo  
            String voName = params.getOsdir() + File.separatorChar + "vo" + File.separatorChar + javaClassName + "VO.java";  
            FreemarkerUtil.generateFile(voName, "javavo.ftl", map);  
            // 3.xml  
            String xmlName = params.getOsdir() + File.separatorChar + "xml" + File.separatorChar + javaClassName + ".xml";  
            FreemarkerUtil.generateFile(xmlName, "xml.ftl", map);  
        }  
    }  
}
