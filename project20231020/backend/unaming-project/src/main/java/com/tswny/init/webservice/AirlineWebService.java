package com.tswny.init.webservice;

import com.tswny.init.webservice.dto.AirlineData;
import org.apache.axis.Constants;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Vector;

@Service
public class AirlineWebService {
    private final Logger log = LoggerFactory.getLogger(AirlineWebService.class);

    private final String url = "http://ws.webxml.com.cn/webservices/DomesticAirline.asmx?wsdl";
    //命名空间
    private final String namespaceURI = "http://WebXml.com.cn/";

    /**
     *
     * @param startCity
     * @param lastCity
     * @param dateString format 2024-01-31
     * @return
     */
    public Object getAirlineTime(String startCity, String lastCity, String dateString) {
        //方法名
        String method = "getDomesticAirlinesTime";
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);
            //设置要调用的方法
            call.setOperationName(new QName(namespaceURI, method));
            //设置要返回的数据类型
            call.setReturnType(XMLType.SOAP_DOCUMENT);
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(namespaceURI + method);
            //设置入参
            call.addParameter(new QName(namespaceURI, "startCity"), Constants.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(namespaceURI, "lastCity"), Constants.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName(namespaceURI, "theDate"), Constants.XSD_STRING, javax.xml.rpc.ParameterMode.IN);

            //调用方法并传递参数
            Document resultStr = (Document)call.invoke(new Object[]{startCity, lastCity, dateString});
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
