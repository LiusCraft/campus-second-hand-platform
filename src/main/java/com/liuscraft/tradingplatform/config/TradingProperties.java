package com.liuscraft.tradingplatform.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @author LiusCraft
 * @date 2023/3/29 11:41
 */
@Component
@Data
public class TradingProperties {
    private static TradingProperties instance;

    public static TradingProperties getInstance() {
        return instance;
    }

    public TradingProperties(){
        instance = this;
    }
    private String saveImgLocation;
    @Value("${good.saveimg}")
    public void setSaveImgLocation(String saveImgLocation) {
        System.out.println("当前目录"+System.getProperty("user.dir"));
        File file;
        if (!StringUtils.hasLength(saveImgLocation)){
            saveImgLocation = System.getProperty("user.dir")+"/img";
            file = new File(saveImgLocation);
            if (!file.exists() && !file.mkdir()) {
                throw new RuntimeException("根据运行路径创建图片存储路径文件夹失败,请手动");
            }
        }else {
            file = new File(saveImgLocation);
        }
        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("请配置正确的商品图片存储路径,它需要是文件夹路径");
        }
        this.saveImgLocation = saveImgLocation;
    }
}
