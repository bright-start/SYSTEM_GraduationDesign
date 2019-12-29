package com.cys.system.common.config;

import com.google.gson.annotations.SerializedName;
import org.springframework.context.annotation.Configuration;

public class ConfigValue {
    @SerializedName(value = "a", alternate = {"defaultUserImage"})
    public String defaultUserImage;
    @SerializedName(value = "b", alternate = {"defaultShopImage"})
    public String defaultShopImage;

    public String getDefaultUserImage() {
        return defaultUserImage;
    }

    public void setDefaultUserImage(String defaultUserImage) {
        this.defaultUserImage = defaultUserImage;
    }

    public String getDefaultShopImage() {
        return defaultShopImage;
    }

    public void setDefaultShopImage(String defaultShopImage) {
        this.defaultShopImage = defaultShopImage;
    }
}
