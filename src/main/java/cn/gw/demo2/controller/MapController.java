package cn.gw.demo2.controller;

import cn.gw.demo2.factory.MapConfig;
import cn.gw.demo2.factory.MapService;
import cn.gw.demo2.factory.MapServiceFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/map")
@Api("地图")
public class MapController {

    @Autowired
    private MapServiceFactory mapServiceFactory;
    @Autowired
    private MapConfig mapConfig;


    public MapService getMapService(String name) {
        if (StringUtils.isEmpty(name)) {
            name = mapConfig.getName();
        }
        return mapServiceFactory.getService(name);
    }


    @ApiOperation("系统默认服务提供方")
    @GetMapping("/send")
    public String send() {
        return getMapService(mapConfig.getName()).out();
    }

    //自主选择服务提供方
    @ApiOperation("自主选择服务提供方")
    @GetMapping("/send/v2")
    public String send(@RequestParam(required = false) String type) {
        return getMapService(type).out();
    }

}
