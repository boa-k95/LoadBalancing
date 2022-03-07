package com.CHAT01.service;

import com.intesasanpaolo.bear.service.BaseService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class GenerateRandomNumberService extends BaseService {

  public Integer createRandomNumberWithParams(List<String> text) {
      int randomNumberGenerated ;
             if(CollectionUtils.isEmpty(text) || text.size()==0){
            randomNumberGenerated =  RandomUtils.nextInt(0,300);
       }
       else {
           if (text.size()>0 && text.size()<2){
               randomNumberGenerated =  RandomUtils.nextInt(Integer.parseInt(text.get(0)),Integer.parseInt(text.get(1)));
           }
           else{
               randomNumberGenerated =RandomUtils.nextInt(0,Integer.parseInt(text.get(0)));
                }

        }

          return Integer.valueOf(randomNumberGenerated);



}

}