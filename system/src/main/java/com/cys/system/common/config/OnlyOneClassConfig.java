package com.cys.system.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;

public class OnlyOneClassConfig {
    public static final Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().create();
    public static final Gson gson1 = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
}
