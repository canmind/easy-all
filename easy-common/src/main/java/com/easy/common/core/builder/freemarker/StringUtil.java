package com.easy.common.core.builder.freemarker;
/**
 * 
 * @author haifeng.chf
 *
 */
public class StringUtil {
	/** 
     * 首字母大写 
     * @param str 
     * @return 
     */  
    public static String capitalize(String str) {  
        if (null == str) {  
            return null;  
        } else if ("".equals(str)) {  
            return str;  
        }  
        return Character.toTitleCase(str.charAt(0)) + str.substring(1);  
    }  
    /** 
     * 将表中列名去下划线且下划线后首字母大写其他字母小写 
     * @param columnName 表中列名 
     * @return java类属性名 
     */  
    public static String dbNameToVarName(String columnName) {  
        if (columnName == null) {  
            return null;  
        }  
        StringBuilder fieldName = new StringBuilder();  
        boolean toUpper = false;  
        for (int i = 0; i < columnName.length(); i++) {  
            char ch = columnName.charAt(i);  
            if (ch == '_') {  
                toUpper = true;  
            } else if (toUpper == true) {  
                fieldName.append(Character.toUpperCase(ch));  
                toUpper = false;  
            } else {  
                fieldName.append(Character.toLowerCase(ch));  
            }  
        }  
        return fieldName.toString();  
    }  
    /** 
     * 将数据库类型转换成java类型 
     * @param columnType 数据库类型 
     * @return java类型 
     */  
    public static String dbTypeToJavaType(String columnType) {  
        String type = "";  
        if (columnType == null || columnType.trim().equals("")) {  
            return null;  
        }  
        if (columnType.equals("VARCHAR")) {  
            type = "String";  
        } else if (columnType.equals("DATE")) {  
            type = "java.util.Date";  
        } else if (columnType.equals("DATETIME")) {  
            type = "java.util.Date";  
        } else if (columnType.equals("CHAR")) {  
            type = "String";  
        } else if (columnType.equals("INT")) {  
            type = "Integer";  
        } else if (columnType.equals("INT UNSIGNED")) {  
            type = "Integer";  
        } else if (columnType.equals("TEXT")) {  
            type = "String";  
        } else if (columnType.equals("MEDIUMBLOB")) {  
            type = "byte[]";  
        } else if (columnType.equals("DECIMAL")) {  
            type = "java.math.BigDecimal";  
        } else {  
            System.out.println("columnType[" + columnType + "]");  
            type = null;  
        }  
        return type;  
    }  
    /** 
     * 将数据库类型转换成mybatis配置文件类型 
     * @param sqlTypeName 数据库类型 
     * @return mybatis配置文件类型 
     */  
    public static String mybatisType(String sqlTypeName){  
        String type = "";  
        if (sqlTypeName == null || sqlTypeName.trim().equals("")) {  
            return null;  
        }  
        if(sqlTypeName.equals("VARCHAR") || sqlTypeName.equals("TEXT")){  
            type = "VARCHAR";  
        }else if(sqlTypeName.equals("TINYBLOB") || sqlTypeName.equals("BLOB") || sqlTypeName.equals("MEDIUMBLOB")){  
            type = "BLOB";  
        } else if (sqlTypeName.equals("CHAR")) {  
            type = "CHAR";  
        } else if (sqlTypeName.equals("INT")) {  
            type = "INTEGER";  
        } else if (sqlTypeName.equals("DATETIME") || sqlTypeName.equals("DATE")) {  
            type = "TIMESTAMP";  
        } else if (sqlTypeName.equals("DECIMAL")) {  
            type = "DECIMAL";  
        } else if (sqlTypeName.equals("INT UNSIGNED")) {  
            type = "INTEGER";  
        } else {  
            System.out.println("sqlTypeName[" + sqlTypeName + "]");  
            type = null;  
        }  
        return type;  
    }  
    public static String getFileName(String tableName, String flag){  
        String retName;  
        switch(flag){  
        case "po"  : retName = tableName + "_po"; break;  
        case "vo"  : retName = tableName + "_vo"; break;  
        default : retName = null;  
        }  
        return retName;  
    }  
}
