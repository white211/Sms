package cn.white.controller;

import cn.white.utils.GetMessageCode;
import org.omg.CORBA.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by @author white
 *
 * @date 2017-11-01 14:01
 */
@Controller
public class SendSms {
    @RequestMapping(value = "/SendNum")
    public Map<String, Object> SendNum(String telephone) {
//        System.out.println(telephone);
        Map<String, Object> map = new HashMap<String, Object>();
        String code = GetMessageCode.getCode(telephone);
//        System.out.println(code);
        map.put("result", code);
        return map;
    }
}
