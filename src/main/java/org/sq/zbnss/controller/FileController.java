package org.sq.zbnss.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import io.minio.messages.Upload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sq.zbnss.base.ResponseVo;
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
import java.util.*;
import java.util.concurrent.ExecutorService;

@Api(value = "文件管理", tags = "文件管理")
@RestController
@CrossOrigin
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    public FileService fileSerivce;
    @Autowired
    private UserService userService;

    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Autowired
    @Qualifier("threadPoolExecutor")
    private ExecutorService threadPoolExecutor;

    /**
     * 查询文件
     */
    @GetMapping("/list")
    public ResponseVo list(FilePo filePo){
        List<FilePo> filePos = fileSerivce.list(filePo);
        return ResultUtil.success("",filePos);
    }


    /**
     * 单文件上传
     */
    @ApiOperation(value = "单文件上传", tags = "文件管理")
    @PostMapping("/addOne")
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo addOne(@RequestPart(value = "multipartFile",required = false) MultipartFile multipartFile,
                             @RequestParam(value = "sysId", required = false) String sysId,
                             @RequestParam(value = "checkId",required = false) String checkId,
                             @RequestParam(value = "testId",required = false) String testId,
                             @RequestParam(value = "trainId",required = false) String trainId,
                             @RequestParam(value = "roomId",required = false) String roomId,
                             @RequestParam(value = "comId",required = true) String comId,
                             HttpServletRequest request){

        return fileSerivce.uploadOne(multipartFile,sysId,checkId,testId,trainId,roomId,comId,endpoint,request);
    }

    /**
     * 批量上传文件

     */
    @ApiOperation(value = "批量文件上传", tags = "文件管理")
    @PostMapping("/addBatch")
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo addBatch(@RequestParam(value = "multipartFile",required = false) MultipartFile[] multipartFile,
                               @RequestParam(value = "sysId", required = false) String sysId,
                               @RequestParam(value = "checkId",required = false) String checkId,
                               @RequestParam(value = "testId",required = false) String testId,
                               @RequestParam(value = "trainId",required = false) String trainId,
                               @RequestParam(value = "roomId",required = false) String roomId,
                               @RequestParam(value = "comId",required = true) String comId,
                               HttpServletRequest request){
        return fileSerivce.uploadBatch(multipartFile,sysId,checkId,testId,trainId,roomId,comId,endpoint,request,threadPoolExecutor);
    }

    @ApiOperation(value = "文件下载", tags = "文件管理")
    @PostMapping("/download")
    public ResponseVo downloadFile(@RequestParam(value = "id") int id, HttpServletRequest request, HttpServletResponse response){
        return fileSerivce.downloadFile(id,endpoint,request,response);
    }


    @ApiOperation(value = "文件预览", tags = "文件管理")
    @PostMapping("/preview")
    public ResponseVo getPreviewUrl(@RequestParam(value = "id") int id){
        return this.fileSerivce.getPreviewUrl(id,endpoint);
    }

    /**
     * 重命名
     */
    @GetMapping("/rename/{id}/{name}")
    public ResponseVo rename(@PathVariable Long id,@PathVariable String name){
        boolean update = fileSerivce.update(new LambdaUpdateWrapper<FilePo>()
                .eq(FilePo::getId, id)
                .set(FilePo::getFileName, name));
        return update == true ? ResultUtil.success() : ResultUtil.success();
    }

    /**
     * 删除文件
     */
    @GetMapping("/del/{id}")
    public ResponseVo del(@PathVariable Long id){
        //查询云存储中文件名
        FilePo byId = fileSerivce.getById(id);
        try {
            MinioUtils.removeObject(bucketName,byId.getFileName());
            fileSerivce.removeById(id);
        } catch (Exception e) {
            throw new RuntimeException("删除文件失败请重试！");
        }
        return ResultUtil.success("文件删除成功");
    }
    /**
     * 计算文件大小MB
     *
     * @param lenth 文件字节长度
     * @return 结果 MB
     */
    private String getMB(Long lenth) {
        return CalculationUtil.getCalculationUtil(CalculationUtil.getCalculationUtil(lenth, 1024, 3).divide(), 1024, 3).divide();
    }

    public User getLoginUser(HttpServletRequest request){
        String tokenHeader = request.getHeader("Authorization");
        String[] tokens = tokenHeader.split(" ");
        String token = tokens[1];
        String userId = JWTUtil.getUserId(token);
        User loginUser = userService.getUserByUserId(userId);
        return loginUser;
    }
}
