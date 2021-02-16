package club.mrxiao.sf.test;

import club.mrxiao.sf.api.SfService;
import club.mrxiao.sf.api.impl.SfServiceImpl;
import club.mrxiao.sf.config.SfConfig;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.inject.Binder;
import com.google.inject.Module;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author <a href="https://github.com/mr-xiaoyu">xiaoyu</a>
 */
public class ApiTestModule implements Module {

    private final Log log = LogFactory.get(this.getClass().getName());
    private static final String TEST_CONFIG_XML = "tset-config.properties";

    @Override
    public void configure(Binder binder) {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(TEST_CONFIG_XML)) {
            if (inputStream == null) {
                throw new RuntimeException("测试配置文件【" + TEST_CONFIG_XML + "】未找到，请参照test-config-sample.properties文件生成");
            }
            Properties pro = new Properties();
            pro.load(inputStream);
            inputStream.close();

            SfConfig config = new SfConfig();

            config.setUrl(pro.getProperty("url"));
            config.setCard(pro.getProperty("card"));
            config.setCheck(pro.getProperty("check"));
            config.setCode(pro.getProperty("code"));

            SfService sfService = new SfServiceImpl();
            sfService.setConfig(config);
            binder.bind(SfService.class).toInstance(sfService);
            binder.bind(SfConfig.class).toInstance(config);
        } catch (IOException e) {
            this.log.error(e.getMessage(), e);
        }
    }
}
