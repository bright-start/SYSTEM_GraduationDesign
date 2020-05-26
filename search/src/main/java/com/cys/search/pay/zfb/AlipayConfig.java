package com.cys.search.pay.zfb;

/**
 * @author zhaoliancan
 * @description 配置类
 * @create 2019-08-08 18:50
 */

public class AlipayConfig {

    // 作为身份标识的应用ID
    public static String app_id = "2016102200736706";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key  = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDZorft4W983JPJE8YM1biR28k7dQ9b7NthwovNKHcbruZYgOWX08u1WCRLc5jvYYP04fpW69p4t1CokFVVGzHDzg4MZdG+gjf4De2B4w00c7pKDe4ZO9rJyxy19I0gANGy4BU8ovr1+k/gpsh58T+TVpScnmy6/pYhQBlh6k+gr7JOm7iwOhKAtEbOv8On5tq6J0jqnggiZfgvgdOdmEVEh/DP8qLk/WY85w8ji4Iq1ly5Z8cbMSicU6yjasvvATqwt63wTk9AevivahM4WySFwEaBHS9mIuRPMInMAFzTfXCe3U8fYy9XqJEoOA5h0jUTzO5hFKXOsw4xh7yqZBNPAgMBAAECggEANtwVkRkqiUbw2dbqLYISLHJCn4I6hOmHw3TO2MBChe/okJatSUBSMTwuqfZRrsXeBXSRLif1wkgzRMpdOD7KpJjkYauo22Tnxtd77ynUwkwJwXERe7riw8s3SYaDrHWIZpalw8sxh5+5Ut+32UU+yV0hyPkGX3ydS3Rk8ZGm0NIB6vBo2w8qN3mBloToJHnDPUsEWWhPkephwpvNrUZ0XPP//xYo8JxhII8yZxBSZXroeRZXgfdCqTq//9vQiHeFnSuGL0qE+UKPSIedPfWDLYgl9QqThJpTZiB5yvzGBp63+bznKMhZu2wR3rsoAVZ4eJi1uPS0XAiHKWrIuIz7QQKBgQD5TqJv9B6O+tP42TsvzOvK+TWR49PvrHsZTRawOM9FC1ECYwYjDKvyz4DSBDg4X+rooaToZ5DTeAC1uBzCdw9JCfppXhLG91PkkaQPmIXg5VA6bIHS+Hapjm2dBh/ChHtOUWKYmSaSf3rUSN/UmsQYpPuWUy4N76YFQtsyEowzkwKBgQDfemvGQ3vB5sOG3H/DcFYPhTeb7qq3O57xhtd8BCXUaLCYGAhEutrIpI+5HsmIrhOHH1eAep5mvPjxTM+Unx56sMNqFz4R1b4w1GZX1pXlu8ih4YXIDEPYj5vgEwJuXs2XknSf36CeRg0NKYZ8hzvUDF3UP1PabqhSK0QAMJxu1QKBgQCbn9iKZisFOdJETuZBanvuUi58iWls/ZBCNPrquiu4f93rzYCxNGHJynoxn2yw1D9BRFY6tRuUxh47UViyk7u47j+gspaqGQ4HalqtWZqIztyL+9v2pB7+rOgD0yTUOfr24prXgxKu9GDqTytZ/dpXP/XE2tuu22AZfKhz01wtfQKBgDAD/J4Hp9J8g2w5ehYCz+rbw5v0RqfB3p+kSi4yMfKWX8L9/uDNiO5tNRggPkroo/d3S3SmY9xNI+ushjk3ivPvgL0Nq19nnkqdiDO9Pk2y8COf8f+OaxFfoa0eSHpwUhoL9JTZFoiLIoi+cE/XOoqP8aUFyj13YyDsfNrRlvrVAoGAYWej+QbIOSpL+ihnhTmdsyUPeA71b9GnP2H4ZFvqqas+tOqg2o59dAXu43zHQpQuPf3TinptaP0xhwPOcSpVxdfc1cKKF++ZaZiQEF0JClgv6w3WjUP0bxpfRbtfHq2ovIRY7AxMHQ9Do4FUMKYJXZBQLzrUELPFPd5j5EwTQBs=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1tGS85vzI2pV5mCfCBXNGHlgEdY9GcfDhkiPPN+sBxVqwpHaBda0u6WKRZgBO7bjSDO97Wunx80EdyeYBwIK3UQMkSGQ+9CUulr97VAfjBZYMN3AK4UJbI+WFredExuWtkPpWWU/tOUniK917/bYkGVCDrAKoEJDvXb4/k7tgilLMkHFCt5MaJj3pSqUjtQ1sTm5wS9fsEIMB0GBidwDRKkL9DkwaCA+fZB17HKPQhpqFZazs8qCdDEAjHzWV2BFBkLnqUWwMpx1dg/utdzgcfVy0JBGhg6QxKZt36jmaXOVi2nAkjmadqbqbqo4yTFdgA8LJH538r0FSmOOdleRtwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.cys.com:9200/search/search/html/pay_success.html";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://www.cys.com:9200/search/pay";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}


