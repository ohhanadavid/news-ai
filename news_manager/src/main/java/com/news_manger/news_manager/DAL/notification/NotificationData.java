package com.news_manger.news_manager.DAL.notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString(includeFieldNames = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NotificationData {
    private String connectInfo;
    private String name;
    private String subject;
    private String text;
}
