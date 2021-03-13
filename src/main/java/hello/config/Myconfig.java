package hello.config;

import hello.dao.RankDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Myconfig {
    @Bean
    public RankDao getRankDao() {
        return null;
    }
}
