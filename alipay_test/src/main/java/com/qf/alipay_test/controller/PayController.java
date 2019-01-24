package com.qf.alipay_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author ken
 * @Date 2019/1/24
 * @Version 1.0
 */
@Controller
public class PayController {

    @RequestMapping("/pay")
    public void pay(HttpServletResponse response) throws IOException {
//        //调用支付宝进行支付
//        AlipayClient alipayClient = new DefaultAlipayClient(
//                "https://openapi.alipaydev.com/gateway.do",
//                "2016073000127352",
//                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCNg00STOHQ6APWMmCxn+e+ewixIw5oAy9zOmIt4rOqRIbG4mug2rld96gIevy1eVsisYtsP2ZTjsyC5M3Yr+Lm51S77nbn1C4YOW925RgCvSqXrJg7l7HA9ytJf/plN8NaYGEmMJSmuC772R/rpXfkxNI5iWByGVf8toMXHLAL+rDw0+45rOlWSWp5bAfizBEDtj8WkdvyW/FpYs0bddL1C72hmula/7W7xLHKgd/QLyLb2y/zRMLCoyG7/lYzsa/3T4ZDrlR4lgU9j+G7izq1ygM2N2fKxFroVduKhYnMalMiIAJO+umWVeEGX773qeQ6dV7surId51GEHuUzuhiLAgMBAAECggEAfZpx+PFGaT7V0POMAXxVnCrl7xuXeNiC8Dp81A9oupLyMWrcbrxUVUQw374RuR1Kt8NwZZbsQvP3L6LX1iFfOuVOvzOMVH5un0+zUItMwXmXaWJnCFW/DvATerxdyiMxgHEVKWlrN4M5KI5yF50ZinLryOfXM86s2wA/4G61DxuUnTzps8Vu8LUa4c4PmNQhYumNqjEwK3pkCu9vOP8GIY/CvnIQaLJ+ErTh6S5KE+QIib5dbAQcXAMB3i2J12q91tvwiP8R5tizTqO+gs33bUiy/GKQFvaLT1yNTrlbiGeSAq1ZoEWkdlglMlHBps2A8v3MvzTfIomC/zMonzmXEQKBgQDDXPpYMlzeGQgL4UcrVa3pl18jk9y+YhmpO61Ff0vpYyQTgaZVeIJLj2nSR8zpebjYN3HDFKhB0yO0piJE0je9mJ0uTLo37juEMkfwWQUoEvE2n5eM0G+vHKqLHiBjOfX07uj1/wgOHXnW29hkziuh94gDeYTi5pdG5setHOisCQKBgQC5b4bDtAYS3HOQes2P54ly3Uu6eYcXy5lf9b+o+ezf8UP//KEwUXg2u93LeQGaoAl4p8Uqyq+RJw7yWI8XJ7BmGXzsQ7DLcbN4EIkaXmFb4D6GwgCbzdRqtKX5C5xHzgsRjBquuRLM+/LTV1kwyB3dkKiANPgNtCFPQx/h0LNs8wKBgQDAVB2ljFc+03fHck3FTME88SfZd6zexOcsYzLO82ZBarfOeFnPVSc4ygRM3yDNTWb7fYICoqiOvRUuuEuOY0I65i7/sMu3WlK/b1zBMnJQJG3R78Wb/GunAlqnQM5zwVDxg+5Wl29PrhcPvjlH7GyqNG1ztkNrucV+KJ61P3uUOQKBgFTdVpawB+uP2o+vs+387+mSn3q67XmVYx8Ij+tcXezrNMytqHM9hb++4LWjLnm+bjc6yMBZvFm4v11n7CyTrGY9me9i10F0SsdTAfwAX5w1l0gC91ZWr7UZ8a91gdNW17fzH68A4jrPv4S7QAVUQF7LOmJaW3+iBXCJ7shgxAzDAoGBAL8qyhgUc4R/gXMY/prv7OWQwPGS+9rI+ZQNpANpb4EUNMV+7liyBQqRfEiyjfkpPc0TVbLkdfp0k+KCucBo7SZyfaO/OAsbh1KkjIAfvKqAn+oNCDqkvaCgkzYKkSPn/7kIkVjTuf2y2eGlcq/6Af62UYzP6urjSdrLht2482ai",
//                "json",
//                "UTF-8",
//                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx+n+pEqTVi27mtj3CuUQXi2ixqeeTwE/0tGrW+sg6xtfajvJV67GYf2zzNxxBV0TYfhdbi70VI3DftEijg7GSNKoOilAu2DKQFqidnSxmN1Es1oRTaiaehqm1Uzs2uswpzBVR21iygLHujwthC8kNkMgxVFkjbE/qTn7z5wlsailtg6wF+hY3BcDCiaLyVLjEDngmrLyLXPLenjAuvXq20h9zV7CW9HXuhpPBDfsn4fv5TjgEl1smjJNr4O/VxICKDNPsvrCyNXhfroK9PEFFwH+4IWGeBUJAP2cSufNU0OA+UH+2xQnaR8Cz30QIgIslckBGuXQZvxqaY2mMMz14QIDAQAB",
//                "RSA2"); //获得初始化的AlipayClient
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
//        alipayRequest.setReturnUrl("http://www.baidu.com");//同步请求，当用户支付完成，页面如何跳转
//        alipayRequest.setNotifyUrl("http://www.baidu.com");//异步请求，支付成功与否根据异步请求决定
//        //支付参数
//        alipayRequest.setBizContent("{" +
//                "    \"out_trade_no\":\"" + UUID.randomUUID().toString() + "\"," +
//                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
//                "    \"total_amount\":10086.89," +
//                "    \"subject\":\"IphoneX 128G\"," +
//                "    \"body\":\"IphoneX 128G\"," +
//                "    \"extend_params\":{" +
//                "    \"sys_service_provider_id\":\"2088511833207846\"" +
//                "    }"+
//                "  }");//填充业务参数
//
//        //按照客户端的参数，以及支付的参数，会自动生成一个支付页面
//        String form="";
//        try {
//            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        response.setContentType("text/html;charset=utf-8");
//        response.getWriter().write(form);//直接将完整的表单html输出到页面
//        response.getWriter().flush();
//        response.getWriter().close();
    }
}
