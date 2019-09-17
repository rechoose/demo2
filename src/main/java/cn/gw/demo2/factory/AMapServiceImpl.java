package cn.gw.demo2.factory;

import org.springframework.stereotype.Service;

@Service("a")
public class AMapServiceImpl implements MapService {
    @Override
    public String out() {
        return "a";
    }
}
