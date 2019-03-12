package controller;

import consts.Path;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.CoursePic;
import pojo.WxResultJson;
import service.CoursePicService;
import utils.JsonUtils;
import utils.LoggerUtlis;
import utils.PicUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional(rollbackFor = { Exception.class })
public class CoursePicController {
    Logger logger=LoggerUtlis.getLogger(CoursePicController.class);
    
    @Autowired
    CoursePicService coursePicService;

    @RequestMapping("/select-picture-show")
    @ResponseBody
    public String showCoursePic(@RequestParam(name = "ids",defaultValue = "0") int ids){
//        TODO try catch
        List<CoursePic> coursePicList = coursePicService.selectCoursePicByCourseId(ids);
        return JsonUtils.objectToJson(coursePicList);
    }

    @RequestMapping(value = {"/mapping-picture-add"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String add(String courseId) throws Exception{
        try {
            for (int i = 0; i < FileuploadController.fileNmaeList.size(); i++) {
                String originalName = FileuploadController.fileNmaeList.get(i);
                String newImgName = PicUtils.getNewCourseImageName(originalName);
                PicUtils.rename(originalName, newImgName);
                coursePicService.insert(new CoursePic(null, Integer.valueOf(courseId), "static/images/" + newImgName));
            }
            FileuploadController.fileNmaeList = new ArrayList<String>();
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-picture-add-"+e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0,"添加错误"));
        }
        return JsonUtils.objectToJson(new WxResultJson(1,""));
    }

    @RequestMapping(value = {"/mapping-picture-del"},produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String del(String perId, String[] ids) throws Exception {
        try {
            int result = 0;
            if (perId != null) {
                CoursePic coursePic = coursePicService.selectById(Integer.valueOf(perId));
                result += coursePicService.delById(Integer.valueOf(perId));
                File file=new File(Path.getImagesPath()+"/"+coursePic.getPic());
                if (file.exists()&&file.isFile())
                    file.delete();
            }
            else
                for (int i = 0; i < ids.length; i++) {
                    CoursePic coursePic = coursePicService.selectById(Integer.valueOf(ids[i]));
                    result += coursePicService.delById(Integer.valueOf(ids[i]));
                    File file=new File(Path.getImagesPath()+"/"+coursePic.getPic());
                    if (file.exists()&&file.isFile())
                        file.delete();
                }

            if (result == 1 || ids == null || result == ids.length)
                return JsonUtils.objectToJson(new WxResultJson(1,""));
            else
                return JsonUtils.objectToJson(new WxResultJson(0,"删除错误"));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("------mapping-picture-del-"+e.getMessage());
            return JsonUtils.objectToJson(new WxResultJson(0,"删除错误"));
        }
    }
}
