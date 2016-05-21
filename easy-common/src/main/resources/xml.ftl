<?xml version="1.0" encoding="UTF-8"?>  
  
<!DOCTYPE mapper PUBLIC  
    "-//mybatis.org//DTD Mapper 3.0//EN"  
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">  
      
<mapper namespace="${project?uncap_first}${className}DAO">  
    <resultMap id="${className?uncap_first}_base" type="${javapackage}.po.${className}PO">  
        <#list columns as propertyName>  
          <result property="${propertyName}"${""?right_pad(maxJavaPropertyLength - propertyName?length," ")} column="${mybatisColumns[propertyName_index]}"${""?right_pad(maxColumnLength - mybatisColumns[propertyName_index]?length," ")} jdbcType="${mybatisTypes[propertyName_index]}" />  
        </#list>  
    </resultMap>  
</mapper>