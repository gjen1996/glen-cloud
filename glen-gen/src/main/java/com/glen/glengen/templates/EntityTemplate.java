package com.glen.glengen.templates;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-17:00
 * @Description
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.DateUtils;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.util.ContentOperationUtil;
import com.glen.glengen.util.FileOperationUtil;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author Glen
 * @create 2020/9/10 17:00
 * @Description
 */
@Slf4j
public class EntityTemplate {

    public static R EntityTemplateWriteFile(JSONObject params) {
        log.info("EntityTemplateWriteFile---params" + "---" + params);

        StringBuffer content = new StringBuffer("package " + params.getString("packageName") +";"+"\n");
        content.append("/**\n" +
                " * @author " + params.getString("author") + "\n" +
                " * @create " + DateUtils.currentTime() + "\n" +
                " * @Description " + params.getString("Description") + "\n" +
                " */\n\n");
        content.append("import com.fasterxml.jackson.annotation.JsonFormat;\n" +
                "import io.swagger.annotations.ApiModel;\n" +
                "import io.swagger.annotations.ApiModelProperty;\n" +
                "import lombok.Data;\n" +
                "\n" +
                "import javax.persistence.*;\n" +
                "import java.util.Date;\n\n");
        content.append("@Data\n" +
                "@Entity\n" +
                "@Table(name= \"" + FileOperationUtil.tableName(params.getString("className")) + "\")\n");
        content.append("public class " + FileOperationUtil.className(params.getString("className"), true) + "{\n");
        //循环输出变量
        log.info("params" + params.getString("vars"));
        JSONArray array = params.getJSONArray("vars");
        log.info("array:" + array);
        for (int i = 0; i < array.size(); i++) {
            JSONObject jo = array.getJSONObject(i);
            content.append("    /**\n" +
                    "     * " + jo.getString("note") + "\n" +
                    "     */\n");
            if ("1".equals(jo.getString("isPrimaryKey"))) {
                content.append("    @Id\n" + "    @GeneratedValue(strategy= GenerationType.AUTO)\n");
            }
            if("Date".equals(jo.getString("varTpye"))){
                content.append("    @JsonFormat(pattern = \"" + jo.getString("pattern")+ "\")\n");
            }
            content.append("    private " + jo.getString("varTpye") + " " + jo.getString("varName") + ";\n\n");
        }
        content.append("}");
        log.info("content:---" + content.toString());
        ContentOperationUtil.contentOperation(params,content);
        return R.ok().put("msgDetials","文件生成成功！");
    }
}