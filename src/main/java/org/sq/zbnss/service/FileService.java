package org.sq.zbnss.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.sq.zbnss.base.ResponseVo;
import org.sq.zbnss.entity.FilePo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;

public interface FileService extends IService<FilePo> {

    /**
     * 查询文件
     */
    List<FilePo> list(FilePo filePo);

    FilePo insert(FilePo filePo);

    boolean delete(int id);

    ResponseVo uploadOne(MultipartFile multipartFile, String sysId, String checkId, String testId,
                         String trainId, String roomId, String comId, String endpoint, HttpServletRequest request);

    ResponseVo uploadBatch(@RequestParam(value = "multipartFile", required = false) MultipartFile[] multipartFile,
                           String sysId, String checkId, String testId, String trainId, String roomId, String comId,
                           String endpoint, HttpServletRequest request, ExecutorService threadPoolExecutor);

    ResponseVo downloadFile(int id, String endpoint, HttpServletRequest request, HttpServletResponse response);

    ResponseVo getPreviewUrl(int id, String endpoint);
}
