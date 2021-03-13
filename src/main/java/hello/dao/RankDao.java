package hello.dao;

import hello.entity.RankItem;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankDao {

    @Autowired
    private SqlSession sqlSession;

    public List<RankItem> getRankList() {
        return sqlSession.selectList("MyMapper.selectRank");
    }
}
