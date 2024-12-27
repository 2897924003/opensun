package ss.springsecurity.cors;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

public class ACorsConfigurationSource {

    private static final CorsConfigurationSource INSTANCE;

    static {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("http://115.120.246.30");
        corsConfiguration.addAllowedOrigin("https://115.120.246.30");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("access_token");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        INSTANCE = urlBasedCorsConfigurationSource;
    }


    /**
     * 不应修改实例内部字段
     */

    public static CorsConfigurationSource getInstance() {
        return INSTANCE;
    }


}
