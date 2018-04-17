package com.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.demo.common.Developer;
import com.demo.common.IopConst;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;




/**
 * @author zWX418686 properties解密
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException
    {
        String isEncrypt = props.getProperty("isEncrypt");
        DESTool desUtil = new DESTool(IopConst.DESKEY);
        FileOutputStream out = null;
        try
        {
            String localPassword = props.getProperty("localpassword");
            String bdiPassword = props.getProperty("bdipassword");
            
            if ("no".equals(isEncrypt))
            {
                //密码加密并将标志位置为yes
                props.setProperty("localpassword", desUtil.encryptStr(localPassword));
                props.setProperty("bdipassword", desUtil.encryptStr(bdiPassword));
                props.setProperty("isEncrypt", "yes");
                localPassword = props.getProperty("localpassword");
                bdiPassword = props.getProperty("bdipassword");
                URL resource = this.getClass().getClassLoader().getResource("resources/jdbc.properties");
                File file = new File(resource.toURI());
                out = new FileOutputStream(file);
                props.store(out, null);
                out.close();
            }
            props.setProperty("localpassword", desUtil.decryptStr(localPassword));
            props.setProperty("bdipassword", desUtil.decryptStr(bdiPassword));
        }
        catch (Exception e)
        {
            Developer.error("EncryptPropertyPlaceholderConfigurer :", e.getMessage());
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    Developer.error("EncryptPropertyPlaceholderConfigurer :", e.getMessage());
                }
            }
        }
        super.processProperties(beanFactory, props);
    }

}
