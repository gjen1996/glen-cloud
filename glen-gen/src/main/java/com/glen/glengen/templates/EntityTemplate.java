package com.glen.glengen.templates;/**
 * @author Glen
 * @create 2020- 09-2020/9/10-17:00
 * @Description
 */

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.DateUtils;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.util.FileOperationUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Glen
 * @create 2020/9/10 17:00
 * @Description
 */
@Slf4j
public class EntityTemplate {

    public static R EntityTemplateWriteFile(JSONObject params) {
        log.info("EntityTemplateWriteFile---params" + "---" + params);
        FileOutputStream fop = null;
        File file;
        StringBuffer content = new StringBuffer("package " + params.getString("packageName") + "\n");
        content.append("/**\n" +
                " * @author" +params.getString("author")+"\n"+
                " * @create " + DateUtils.currentTime()+"\n"+
                " * @Description" + params.getString("Description") + "\n"+
                " */");
        content.append("import com.fasterxml.jackson.annotation.JsonFormat;\n" +
                "import io.swagger.annotations.ApiModel;\n" +
                "import io.swagger.annotations.ApiModelProperty;\n" +
                "import lombok.Data;\n" +
                "\n" +
                "import javax.persistence.*;\n" +
                "import java.util.Date;\n\n\n\n");
        content.append("@Data\n" +
                "@Entity\n" +
                    "@Table(name=\"" + FileOperationUtil.tableName(params.getString("className")) + "\")");

        log.info("content:---" + content.toString());
        try {

            file = new File(params.getString("fileUrl"), params.getString("className"));
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes = content.toString().getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return R.ok();
    }
}
