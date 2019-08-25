package com.zxczone.littlefox.pojo.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response picture and text message
 *
 * @author Jason Zhao
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ResNewsMessage extends ResMessage{

    private int ArticleCount;
    
    private List<Article> Articles;  
    
}
