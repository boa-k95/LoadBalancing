package com.CHAT01.connector.rest.google.dlp.dto;

import com.CHAT01.service.google.dlp.dto.DeidentifyConfig;
import lombok.Data;
import com.CHAT01.model.InfoTypeEnum;

import java.util.LinkedList;
import java.util.List;

@Data
public class ContentServiceRequest {
    /*
       {
         "item":{
           "value":"My name is Alicia Abernathy, and my email address is aabernathy@example.com."
         },
         "deidentifyConfig":{
           "infoTypeTransformations":{
             "transformations":[
               {
                 "infoTypes":[
                   {
                     "name":"EMAIL_ADDRESS"
                   }
                 ],
                 "primitiveTransformation":{
                   "replaceConfig":{
                     "newValue":{
                       "stringValue":"[email-address]"
                     }
                   }
                 }
               }
             ]
           }
         },
         "inspectConfig":{
           "infoTypes":[
             {
               "name":"EMAIL_ADDRESS"
             }
           ]
         }
       }
   */
    private ContentServiceItem item;
    private DeidentifyConfig deidentifyConfig;
    private InspectConfig inspectConfig;

    public ContentServiceRequest(String userMessage, List<InfoTypeEnum> infoTypes) {
        item = new ContentServiceItem();
        item.setValue(userMessage);

        deidentifyConfig = new DeidentifyConfig(infoTypes);

        List<InfoTypeEnum> inspectInfoTypes = new LinkedList<>();
        for (InfoTypeEnum infoType : infoTypes) {
            if (!infoType.isCustom()) {
                inspectInfoTypes.add(infoType);
            }
        }
        inspectConfig = new InspectConfig(inspectInfoTypes);
    }
}
