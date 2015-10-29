package com.zxczone.wechat.pojo.response;

import java.util.List;

/**
 * Response picture and text message
 *
 * @author Jason Zhao
 */
public class ResNewsMessage extends ResMessage{

    private int ArticleCount;
    
    private List<Article> Articles;  
    
    public int getArticleCount() {  
        return ArticleCount;  
    }  
  
    public void setArticleCount(int articleCount) {  
        ArticleCount = articleCount;  
    }  
  
    public List<Article> getArticles() {  
        return Articles;  
    }  
  
    public void setArticles(List<Article> articles) {  
        Articles = articles;  
    }  
    
}
