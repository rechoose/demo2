package cn.gw.demo2.factory;

import org.springframework.stereotype.Service;

@Service("b")
public class BMapServiceImpl implements MapService {
    @Override
    public String out() {
        return "b";
    }
}
