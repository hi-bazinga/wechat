package com.zxczone.littlefox.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request Text Message
 *
 * @author Jason Zhao
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ReqTextMessage extends ReqMessage{

    private String Content;

}
