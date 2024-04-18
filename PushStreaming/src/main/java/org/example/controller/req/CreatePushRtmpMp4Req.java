package org.example.controller.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePushRtmpMp4Req {

//    {
//        "userId":"123",
//            "streamName":"testName",
//            "parameters":{
//                 "test":"4567"
//             }
//    }

    @NotNull(message = "userId不能为空")
    private String userId;

    @NotNull(message = "username不能为空")
    private String username;

    @NotNull(message = "fileName不能为空")
    private String fileName;

    @NotNull(message = "fileMd5不能为空")
    private String fileMd5;

    //自代参数
    private HashMap<String, String> parameters;


}
