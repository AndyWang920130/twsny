package com.tswny.init.webservice;

import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;


@Service
public class TraditionalTranslateWebService {
    private final Logger log = LoggerFactory.getLogger(TraditionalTranslateWebService.class);

    private final String url = "http://ws.webxml.com.cn/WebServices/TraditionalSimplifiedWebService.asmx?wsdl";
    //命名空间
    private final String namespaceURI = "http://webxml.com.cn/";

    public String getTraditionalString(String simplifiedString){
        //方法名
        String method = "toTraditionalChinese";
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);
            //设置要调用的方法
            call.setOperationName(new QName(namespaceURI, method));
            //设置要返回的数据类型
            call.setReturnType(new QName(namespaceURI, method), String.class);
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(namespaceURI + method);
            //设置入参
            call.addParameter(new QName(namespaceURI, "sText"), Constants.XSD_STRING, javax.xml.rpc.ParameterMode.IN);

            //调用方法并传递参数
            String resultStr = (String) call.invoke(new Object[]{simplifiedString});
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getSimplifiedString(String traditionalString){
        //方法名
        String method = "toSimplifiedChinese";
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);
            //设置要调用的方法
            call.setOperationName(new QName(namespaceURI, method));
            //设置要返回的数据类型
            call.setReturnType(new QName(namespaceURI, method), String.class);
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(namespaceURI + method);
            //设置入参
            call.addParameter(new QName(namespaceURI, "sText"), Constants.XSD_STRING, javax.xml.rpc.ParameterMode.IN);

            //调用方法并传递参数
            String resultStr = (String) call.invoke(new Object[]{traditionalString});
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
