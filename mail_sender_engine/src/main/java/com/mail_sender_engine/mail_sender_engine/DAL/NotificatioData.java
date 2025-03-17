package com.mail_sender_engine.mail_sender_engine.DAL;
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
public class NotificatioData {
    private String connectInfo;
    private String name;
    private String subject;
    private String text;
}
