package ra.accademy.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;


@Configuration // đây là lớp cấu hình
@EnableWebMvc // cho phép bật cấu hình MVC
@ComponentScan(basePackages = "ra.accademy")// phát hiện component :  @Component , @Controller, @Service, @Repository
public class MVCConfig implements WebMvcConfigurer, ApplicationContextAware {
    private  ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // khởi tạo bean
    // thymeleaf config
    // cau hình thymeleaf
    @Bean // thành phần cốt lõi của ưng dụng, ko phải do lập trình viên tạo ra, nó hoàn toàn được tạo và quản lí bởi spring IOC container
    // Dependences injection : tiêm phụ thuộc : tiêm bean vào vị trí cần dùng , spring IOC : tiêu chuẩn thiết kế : nguyên lí đảo ngược điều khiển  + design pattern : Singleton I
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setContentType("UTF-8");
        return viewResolver;
    }
    // cấu hình file upload
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver()  {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(52428800); // 50MB
        return resolver;
    }

    // cấu hình dường dẫn
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**","/css/**","/js/**","/img/**")
                // thì  sẽ khớp với địa chỉ đc chỉ định
                .addResourceLocations("/uploads/","/static/css/","/static/js/","/static/img/");

        // /css/style.css ~ /static/css/style.css
        // webapp : localhost:8080/static/css/style.css
    }

}