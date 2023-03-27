package org.sq.zbnss.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_attachment")
public class FilePo implements Serializable {
    private static final long serialVersionUID = 546174417406015956L;

    @ApiModelProperty(value = "文件id")
    private int id;

    @ApiModelProperty(value = "保存的文件名")
    private String uuid;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件路径")
    private String url;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "单位ID")
    private String companyId;

    @ApiModelProperty(value = "文件上传时间")
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date inputTime;

    @ApiModelProperty(value = "文件删除时间")
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date deleteTime;

    @ApiModelProperty(value = "机房id")
    private String machinerId;

    @ApiModelProperty(value = "培训id")
    private String trainId;
    @ApiModelProperty(value = "问题id")
    private String issueId;

    @ApiModelProperty(value = "备案id")
    private String systemId;

    @ApiModelProperty(value = "检查id")
    private String checkId;

    @ApiModelProperty(value = "上传人")
    private User inputUser;

    @ApiModelProperty(value = "删除人")
    private User deleteUser;

    @ApiModelProperty(value = "文件大小")
    private double size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getMachinerId() {
        return machinerId;
    }

    public void setMachinerId(String machinerId) {
        this.machinerId = machinerId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public User getInputUser() {
        return inputUser;
    }

    public void setInputUser(User inputUser) {
        this.inputUser = inputUser;
    }

    public User getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(User deleteUser) {
        this.deleteUser = deleteUser;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
