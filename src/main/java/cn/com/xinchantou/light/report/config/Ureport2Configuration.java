package cn.com.xinchantou.light.report.config;

import com.bstek.ureport.UReportPropertyPlaceholderConfigurer;
import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ImportResource("classpath:ureport-console-context.xml")
public class Ureport2Configuration {

    @Bean
    public ServletRegistrationBean ureportServlet(){
        ServletRegistrationBean bean =
                new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
        return bean;
    }

//    <bean id="propertyConfigurer" parent="ureport.props">
//        <property name="location">
//            <value>/WEB-INF/config.properties</value>
//        </property>
//    </bean>

    @Bean
    public UReportPropertyPlaceholderConfigurer propertyConfigurer(){
        UReportPropertyPlaceholderConfigurer configurer =
                new UReportPropertyPlaceholderConfigurer();
        Resource resource = new ClassPathResource("config.properties");
        configurer.setLocation(resource);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }


}
