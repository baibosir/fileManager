package org.sq.zbnss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.dao.FileDao;
import org.sq.zbnss.entity.FilePo;
import org.sq.zbnss.entity.User;
import org.sq.zbnss.service.FileService;
import org.sq.zbnss.service.UserService;
import org.sq.zbnss.task.UploadTask;
import org.sq.zbnss.uitl.CalculationUtil;
import org.sq.zbnss.uitl.JWTUtil;
import org.sq.zbnss.uitl.MinioUtils;
import org.sq.zbnss.uitl.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class FileServiceImpl extends ServiceImpl<FileDao, FilePo> implements FileService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserService userService;

    /**
     * 查询文件文件
     */
    @Override
    public List<FilePo> list(FilePo filePo) {
        if(filePo == null){
           return null;
        }
        return fileDao.selectAttachment(filePo);
    }


    @Override
    public FilePo insert(FilePo filePo){
        int res = fileDao.insert(filePo);
        if(res == 0){
            return null;
        }
        return fileDao.selectAttachment(filePo).get(0);
    }

    @Override
    public boolean delete(int id){
        int res = fileDao.deleteById(id);
        if(res == 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 单文件上传
     * @param multipartFile 文件
     * @param sysId 备案id
     * @param checkId 检查id
     * @param testId  测评id
     * @param trainId 培训id
     * @param roomId 机房id
     * @param comId 单位id
     * @param endpoint minio
     * @param request 请求体
     * @return 上传结果
     */
    @Override
    public ResponseVo uploadOne(MultipartFile multipartFile, String sysId, String checkId, String testId,
                                String trainId, String roomId, String comId, String endpoint, HttpServletRequest request){

        if(comId == null || "".equals(comId.trim())){
            ResultUtil.error("参数单位ID必传（comId），请检查参数");
        }
        String type = "com";
        String dir = "";
        if(roomId != null && !"".equals(roomId.trim())){
            type = "machine_room";
            dir= roomId;
        }else if(trainId != null && !"".equals(trainId.trim())){
            type = "train";
            dir= trainId;
        } else if(checkId != null && !"".equals(checkId.trim())){
            type = "check";
            dir= checkId;
        }else if(sysId != null && !"".equals(sysId.trim())){
            type = "record";
            dir= sysId;
        }else if(testId != null && !"".equals(testId.trim())){
            type = "appraisal";
            dir= testId;
        }
        StringBuffer Sb = new StringBuffer();
        String fileDir = "";
        if("com".equals(type)){
            fileDir = type;
        }else{
            fileDir = new StringBuffer().append(type).append("/").append(dir).toString();
        }

        try {
            //判断bucket是否存在，不存在创景
            if(!MinioUtils.bucketExists(comId)){
                MinioUtils.createBucket(comId);
                MinioUtils.putDirObject(comId,fileDir+"/");
            }
            //判断文件夹是否存在
            if(!MinioUtils.doesFolderExist(comId,fileDir)){
                MinioUtils.putDirObject(comId,fileDir+"/");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("minio 操作失败"+e.getMessage());
        }

        String fileName = multipartFile.getOriginalFilename();
        //设置文件code uuid + 文件后缀
        String splitStr [] = fileName.split("\\.");
        String code = UUID.randomUUID().toString().replaceAll("-", "") + "." + splitStr[splitStr.length - 1];

        FilePo filePo = new FilePo();
        filePo.setFileType(splitStr[splitStr.length - 1]);
//        //设置文件名
        filePo.setFileName(fileName);
//        //云存储中文件名
        filePo.setUuid(code);
//        //设置路径
        filePo.setUrl(endpoint+"/"+comId+"/"+fileDir+"/"+code);
//        //设置文件大小
        filePo.setSize(Double.parseDouble(getMB(multipartFile.getSize())));

        filePo.setCompanyId(comId);
        filePo.setCheckId(checkId);
        filePo.setTrainId(trainId);
        filePo.setSystemId(sysId);
        filePo.setMachinerId(roomId);
        filePo.setInputUser(getLoginUser(request));

        try {

            MinioUtils.putObject(comId,multipartFile,fileDir+"/"+code);
        } catch (Exception e) {
            return  ResultUtil.error("文件上上传失败"+e.getMessage());
        }
        //插入文件信息
        save(filePo);
        return ResultUtil.success("文件上传成功");
    }

    /**
     *
     * @param multipartFile 文件列表
     * @param sysId 备案id
     * @param checkId 检查id
     * @param testId  测评id
     * @param trainId 培训id
     * @param roomId 机房id
     * @param comId 单位id
     * @param endpoint minio
     * @param request 请求体
     * @param threadPoolExecutor 线程池
     * @return 结果
     */
    @Override
    public ResponseVo uploadBatch(MultipartFile[] multipartFile,
                                  String sysId, String checkId, String testId, String trainId, String roomId, String comId,
                                  String endpoint, HttpServletRequest request, ExecutorService threadPoolExecutor){
        if(comId == null || "".equals(comId.trim())){
            ResultUtil.error("参数单位ID必传（comId），请检查参数");
        }
        String type = "com";
        String dir = "";
        if(roomId == null && "".equals(roomId.trim())){
            type = "machine_room";
            dir= roomId;
        }else if(trainId == null && "".equals(trainId.trim())){
            type = "train";
            dir= trainId;
        } else if(checkId == null && "".equals(checkId.trim())){
            type = "check";
            dir= checkId;
        }else if(sysId == null && "".equals(sysId.trim())){
            type = "record";
            dir= sysId;
        }else if(testId == null && "".equals(testId.trim())){
            type = "appraisal";
            dir= testId;
        }
        StringBuffer Sb = new StringBuffer();
        String fileDir = "";
        if("com".equals(type)){
            fileDir = type;
        }else{
            fileDir = new StringBuffer().append(type).append("/").append(dir).toString();
        }

        try {
            //判断bucket是否存在，不存在创景
            if(!MinioUtils.bucketExists(comId)){
                MinioUtils.createBucket(comId);
                MinioUtils.putDirObject(comId,fileDir+"/");
            }
            //判断文件夹是否存在
            if(!MinioUtils.doesFolderExist(comId,fileDir)){
                MinioUtils.putDirObject(comId,fileDir+"/");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("minio 操作失败"+e.getMessage());
        }

        List<FilePo> filePos = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            String fileName = file.getOriginalFilename();
            //设置文件code uuid + 文件后缀
            String splitStr [] = fileName.split("\\.");
            String code = UUID.randomUUID().toString().replaceAll("-", "") + "." + splitStr[splitStr.length - 1];
            FilePo filePo = new FilePo();
            filePo.setFileType(splitStr[splitStr.length - 1]);
            filePo.setFileName(fileName);
            filePo.setUuid(code);
            filePo.setUrl(endpoint+"/"+comId+"/"+fileDir+"/"+code);
            filePo.setCompanyId(comId);
            filePo.setCheckId(checkId);
            filePo.setTrainId(trainId);
            filePo.setSystemId(sysId);
            filePo.setMachinerId(roomId);
            filePo.setInputUser(getLoginUser(request));
            //设置文件大小
            filePo.setSize(Double.parseDouble(getMB(file.getSize())));
            filePos.add(filePo);
            //开线程上传 （目前没做日志）
            threadPoolExecutor.submit(new UploadTask(comId,file,fileDir+"/"+code));
        }
        //插入文件信息
        saveBatch(filePos);
        return ResultUtil.success();
    }

    public FilePo getFileInfoById(int id){
       return fileDao.queryById(id);
    }

    /**
     * 文件下载
     * @param id 文件id
     * @param endpoint minio
     * @param request 请求
     * @param response 应答
     * @return 结果
     */
    @Override
    public ResponseVo downloadFile(int id, String endpoint, HttpServletRequest request, HttpServletResponse response){
        if(id <= 0){
            return ResultUtil.error("id错误");
        }
        FilePo filePo =getFileInfoById(id);
        if(filePo == null){
            return ResultUtil.error("文件不存在");
        }
        String url = filePo.getUrl();
        String comId = filePo.getCompanyId();
        if(comId == null || "".equals(comId)){
            return ResultUtil.error("单位不能为空");
        }
        if(url == null || "".equals(url)){
            return ResultUtil.error("文件url,应当由minio地址/bucket/目录/文件名组层");
        }
        try{
            MinioUtils.downloadFile(comId,url.replaceAll(endpoint+"/"+comId+"/",""),request,response);
        }catch (Exception e){
            return ResultUtil.error("文件加载失败\n"+e.getMessage());
        }

        return ResultUtil.success("文件加载成功");
    }

    /**
     * 生成文件预览地址
     * @param id 文件id
     * @param endpoint
     * @return
     */
    @Override
    public ResponseVo getPreviewUrl(int id,String endpoint){
        if(id <= 0){
            return ResultUtil.error("id错误");
        }
        FilePo filePo =getFileInfoById(id);
        if(filePo == null){
            return ResultUtil.error("文件不存在");
        }
        String url = filePo.getUrl();
        String comId = filePo.getCompanyId();
        if(comId == null || "".equals(comId)){
            return ResultUtil.error("单位不能为空");
        }
        if(url == null || "".equals(url)){
            return ResultUtil.error("文件url,应当由minio地址/bucket/目录/文件名组层");
        }
        String type = filePo.getFileType();
        if(type == null || "".equals(type.trim())){
            return ResultUtil.error("文件类型未知，无法预览");
        }
        String preType ="";
        if(type.equals("gif")){
            preType ="image/gif";
        }else if(type.equals("jpeg")){
            preType ="image/jpeg";
        }else if(type.equals("jpg")){
            preType ="image/jpg";
        }else if(type.equals("png")){
            preType ="image/png";
        }
        else if(type.equals("pdf")){
            preType ="application/pdf";
        }else{
            return ResultUtil.error("文件不可预览");
        }
        String objName = url.replaceAll(endpoint+"/"+comId+"/","");
        try{
           boolean result = MinioUtils.doesObjectExist(comId,objName);
           if(result){
                String preUrl = MinioUtils.getPreviewUrl(comId,objName,preType,1, TimeUnit.DAYS);
               return ResultUtil.success("文件预览路径获取成功",preUrl);
           }else{
               return ResultUtil.error("文件不存在");
           }
        }catch (Exception e){
            return ResultUtil.error("文件预览地址生成失败");
        }
    }


    public User getLoginUser(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String[] tokens = tokenHeader.split(" ");
        String token = tokens[1];
        String userId = JWTUtil.getUserId(token);
        User loginUser = userService.getUserByUserId(userId);
        return loginUser;
    }

    private String getMB(Long lenth) {
        return CalculationUtil.getCalculationUtil(CalculationUtil.getCalculationUtil(lenth, 1024, 3).divide(), 1024, 3).divide();
    }
}
