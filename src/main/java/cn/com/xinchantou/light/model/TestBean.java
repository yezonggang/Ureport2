package cn.com.xinchantou.light.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;
/**
 * 声明一个类，相当于一个配置文件 bean → 注解@Component
 * 把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>
 */
@Component("testBean")
public class TestBean {
    /**
     * 方法必须包含三个参数：String，String，Map
     *
     * @return 集合类型 ，包含字段：id,name,salary
     */
    public List<Map<String, Object>> loadReportData(String dsName, String datasetName, Map<String, Object> parameters) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 1000; i++) {
            // 模拟数据
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("id", i);
            m.put("name", RandomStringUtils.random(10, true, false));
            m.put("salary", RandomUtils.nextInt() + i);
            list.add(m);
        }
        return list;
    }
    /**
     * @return 对象类型 ,类型：com.bstek.ureport.test.demo.User
     */
    public List<User> buildReport(String dsName, String datasetName, Map<String, Object> parameters) {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 1000; i++) {
            User m = new User();
            m.setId(i);
            m.setName(RandomStringUtils.random(10, true, false));
            m.setSalary(RandomUtils.nextInt() + i);
            list.add(m);
        }
        return list;
    }
}
