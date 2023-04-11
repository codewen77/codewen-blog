package test1;

import com.codewen.utils.JwtUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author codewen77
 * @date 2022-09-01
 */
public class MyTesst {

    @Test
    public void test1() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "123");
        String token = JwtUtil.getToken(map);
        System.out.println(token);
        if (JwtUtil.verify(token) == null) {
            System.out.println("!!!!");
        }
        System.out.print(JwtUtil.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjIwMTc3NjUsImlhdCI6MTY2MjAxNzU4NSwiYWNjb3VudCI6ImFkbWluIn0.OD45nB6dHvSWH9H1spW-Js9eKgWaLuo4sDjCHhoP_m0"));
    }
}
