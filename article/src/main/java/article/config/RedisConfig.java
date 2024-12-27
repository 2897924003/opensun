package article.config;

import article.domain.Article;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Article> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Article> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 配置键和值的序列化器
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Article.class));

        return template;
    }

    @Bean
    public RedisTemplate<String, Long> redisRankTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 配置键的序列化器为字符串
        template.setKeySerializer(new StringRedisSerializer());

        // 配置值的序列化器为 GenericToStringSerializer（适用于 Long 类型）
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return template;
    }
}
