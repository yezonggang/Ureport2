package cn.com.xinchantou.light.report.config;

import java.io.*;
import java.util.*;
import javax.servlet.ServletContext;

import cn.com.xinchantou.light.mapper.LtUreportFileMapper;
import cn.com.xinchantou.light.model.LtUreportFile;
import cn.com.xinchantou.light.uap.common.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;

/**
 *  @author Jason.zhu
 *  @since 2017年2月11日
 *  将报表存储在数据库中
 *
 */
@Slf4j
@Component
public class DatabaseReportProvider implements ReportProvider {

    private static final String NAME = "服务器数据系统";

    private String prefix = "db:";
    private String fileStoreDir;
    private boolean disabled;


    @Autowired
    private LtUreportFileMapper ltUreportFileMapper;
    /**
     * 根据报表名加载报表文件
     * @param file 报表名称
     * @return 返回的InputStream
     */
    @Override
    public InputStream loadReport(String file) {
        String xmlName = file.substring(3);
        LtUreportFile ureportFile = ltUreportFileMapper.selectByName(getCorrectName(xmlName));
        byte[] content = ureportFile.getContent();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
        return inputStream;
    }

    /**
     * 获取没有前缀的文件名
     * @param name
     * @return
     */
    private String getCorrectName(String name){
        if(name.startsWith(prefix)){
            name = name.substring(prefix.length(), name.length());
        }
        return name;
    }

    /**
     * 根据报表名，删除指定的报表文件
     * @param file 报表名称
     */
    @Override
    public void deleteReport(String file) {
        ltUreportFileMapper.deleteReportFileByName(getCorrectName(file));
    }
    /**
     * 获取所有的报表文件
     * @return 返回报表文件列表
     */
    @Override
    public List<ReportFile> getReportFiles() {
        List<LtUreportFile> list = ltUreportFileMapper.queryReportFileList();
        List<ReportFile> reportList = new ArrayList<>();
        for (LtUreportFile ltUreportFile : list) {
            reportList.add(new ReportFile(ltUreportFile.getReportName(), ltUreportFile.getUpdateDate()));
        }
        return reportList;
    }

    /**
     * @return 返回存储器名称
     */
    @Override
    public String getName() {
        return NAME;
    }
    /**
     * 保存报表文件
     * @param file 报表名称
     * @param content 报表的XML内容
     */
    @Override
    public void saveReport(String file, String content) {
        file = getCorrectName(file);
        LtUreportFile ltUreportFile = ltUreportFileMapper.selectByName(file);
        Date currentDate = new Date();
        if(ltUreportFile == null){
            ltUreportFile = new LtUreportFile();
            ltUreportFile.setReportName(file);
            ltUreportFile.setContent(content.getBytes());
            ltUreportFile.setCreateDate(currentDate);
            ltUreportFile.setCreateBy(UserUtil.getUserCode());
            ltUreportFile.setUpdateDate(currentDate);
            ltUreportFile.setUpdateBy(UserUtil.getUserCode());
            ltUreportFileMapper.insertSelective(ltUreportFile);
        }else{
            ltUreportFile.setContent(content.getBytes());
            ltUreportFile.setUpdateDate(currentDate);
            ltUreportFile.setCreateBy(UserUtil.getUserCode());
            ltUreportFileMapper.updateByPrimaryKeySelective(ltUreportFile);
        }

    }
    /**
     * @return 返回是否禁用
     */
    @Override
    public boolean disabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setFileStoreDir(String fileStoreDir) {
        this.fileStoreDir = fileStoreDir;
    }

    /**
     * @return 返回报表文件名前缀
     */
    @Override
    public String getPrefix() {
        return prefix;
    }
}