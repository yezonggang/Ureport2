package cn.com.xinchantou.light.report.config;

import cn.com.xinchantou.light.multids.context.DynamicDataSource;
import com.bstek.ureport.definition.datasource.BuildinDatasource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 内置数据源
 */
@Slf4j
@Component
public class LightBuildinDatasource implements BuildinDatasource {

    @Autowired
    private DynamicDataSource ds;

    @Override
    public String name() {
        return "内置默认数据源";
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            log.error("获取内置数据源失败");
        }
        return connection;
    }
}
