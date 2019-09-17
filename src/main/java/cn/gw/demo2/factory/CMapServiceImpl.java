package cn.gw.demo2.factory;

import org.springframework.stereotype.Service;

@Service("c")
public class CMapServiceImpl implements MapService {
    @Override
    public String out() {
        return "c";
    }
}
