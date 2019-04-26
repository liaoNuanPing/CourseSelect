package controller;

import enums.RegisterCodeEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.DatatablePage;
import pojo.RegisterCode;
import pojo.WxResultJson;
import service.RegisterCodeService;
import utils.ConnectDB;
import utils.EncodingUtils;
import utils.JsonUtils;
import utils.LoggerUtlis;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class RegisterCodeController {

    Logger logger= LoggerUtlis.getLogger(RegisterCodeController.class);


    @Autowired
    RegisterCodeService registerCodeService;


    @RequestMapping(value = {"loadRegisterCode"}, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String showCourse(HttpServletRequest request) {
        List<RegisterCode> registerCodeList = new ArrayList<RegisterCode>();
        String orderColumn = request.getParameter("order[0][column]");
        String order = request.getParameter("order[0][dir]");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        DatatablePage<RegisterCode> data = new DatatablePage<RegisterCode>();
        //search中文编码处理
        String search = "";
        try {
            search = EncodingUtils.useToSearch(search, request);
            Connection conn = ConnectDB.getConnection();

            String orderString3 = "order by " + RegisterCodeEnum.getNameByIndex(Integer.valueOf(orderColumn)) + " " + order + " ";
            String sql3 =
                    "select * " +
                            "from register_code " +
                            "where CONCAT(code,disable) " +
                            "LIKE '%" + search + "%' " +
                            orderString3 +
                            " LIMIT " + start + " ," + length;


//              查找数据库
            Statement pstm = conn.createStatement();
            ResultSet rs = pstm.executeQuery(sql3);
            while (rs.next()) {
                RegisterCode registerCode = new RegisterCode(
                        rs.getString("code"),
                        rs.getString("disable")
                );//end CourseShow
                registerCodeList.add(registerCode);
            }//end while

//            过滤后的总记录数
            data.setRecordsFiltered(registerCodeService.countAll());
//            总记录数
//            data.setRecordsTotal(perClassCourseService.countAll()-1);
        } catch (Exception e) {
            logger.error("loadRegisterCode-" + e.getMessage());
        }

//        draw: 表示请求次数
        data.setDraw(Integer.valueOf(request.getParameter("draw")) + 1);

//        data: 具体的数据对象数组
        data.setData(registerCodeList);
        return JsonUtils.objectToJson(data);

    }

    @RequestMapping(value = {"/mapping-register-code-del"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String del(String perId, String[] ids) {
        try {
            if (perId != null) {
                registerCodeService.delById(perId);

            } else {
                for (int i = 0; i < ids.length; i++)
                    registerCodeService.delById(ids[i]);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonUtils.objectToJson(new WxResultJson(0,"删除失败"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1,"success"));

    }

    /**
     * 添加注册码
     */
    @RequestMapping(value = {"mapping-register-code-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String perclassCourseAdd(HttpServletRequest request) throws Exception {

        try {
            Integer number = Integer.valueOf(request.getParameter("number"));
            //选取有效注册码
            List<RegisterCode> registerCodeList = registerCodeService.selectEnableCode();
            //list转map
            Map<String, RegisterCode> codeMap = registerCodeList.stream().collect(
                    Collectors.toMap(
                            e -> e.getCode(),
                            e -> e,
                            (e1, e2) -> e1)
            ); // Merge Function

            //生成注册码
            out:
            for (int i = 0; i < number; ) {
                String uuid = UUID.randomUUID().toString().replace("-", "");

                for (int j = 0; j < 30; j += 5) {
                    String code = uuid.substring(j, j + 5);
                    if (!codeMap.containsKey(code)) {
                        registerCodeService.insert(new RegisterCode(code, "false"));
                        i++;
                        if (i >= number)
                            break out;
                    } else {
                        System.out.println(code + "重复");
                    }

                }
            }
            return JsonUtils.objectToJson(new WxResultJson(1, "success"));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonUtils.objectToJson(new WxResultJson(0, e.getMessage()));

        }
    }

}
