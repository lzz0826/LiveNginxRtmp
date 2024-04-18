package org.example.controller.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPushRtmpReq {

    @NotNull(message = "userId不能为空")
    private String userId;

    @NotNull(message = "username不能为空")
    private String username;
}
