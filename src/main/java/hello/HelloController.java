package hello;

import hello.dao.UserMapper;
import hello.entity.RankItem;
import hello.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class HelloController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RankService rankService;

    @RequestMapping("/")
    public ModelAndView model() throws IOException {
        List<RankItem> rankItems = rankService.getRanks();
        HashMap<String, Object> model = new HashMap<>();
        model.put("items", rankItems);
        return new ModelAndView("index", model);
    }

    @RequestMapping("/rankData")
    @ResponseBody
    public Object search() {
        return rankService.getRanks();
    }



}