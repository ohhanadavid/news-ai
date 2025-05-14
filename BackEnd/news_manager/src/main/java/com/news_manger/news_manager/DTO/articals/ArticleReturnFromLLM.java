package com.news_manger.news_manager.DTO.articals;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleReturnFromLLM {
    private String url;
    private String title;
    private String summary;
    private String videoUrl;
    //@JsonDeserialize(using= ArticalDataTimeFormatter.class)
    private String pubDate;


    public ArticleReturnFromLLM(Article artical){
        this.url=artical.getLink();
        this.title=artical.getTitle();
        this.videoUrl=artical.getVideo_url();
        this.summary="";
        this.pubDate=artical.getPubDate();


    }

    @Override
    public String toString (){
        StringBuilder ans = new StringBuilder().append("\uD83D\uDCF0  url: ").append(url).append("\n")
                .append("   \uD83D\uDCCB title: ").append(title).append("\n")
                .append("   📝 summary: ").append(summary).append("\n");

        if (videoUrl != null && !videoUrl.isEmpty()) {
            ans.append("   \uD83C\uDFA5 video url: ").append(videoUrl).append("\n");
        }

        ans.append("    \uD83D\uDCC5 publish date: ").append(pubDate).append("\n\n").append("⭐ ⭐ ⭐ \n\n");

        return ans.toString();
    }
}
