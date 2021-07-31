package project1.memo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import project1.memo.controller.texts.TextsForm;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Texts {

    @Id
    @GeneratedValue
    @Column(name = "text_id")
    private Long id;

    @Column(name = "text_title", nullable = false)
    private String title;

    @Lob
    @Column(name = "text_describe")
    private String describe;

    @Column(name = "text_date", nullable = false)
    private String dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members;

    public static Texts createTexts(Members members, String title, String describe) {
        Texts texts = new Texts();
        if (texts.members != null) {
            texts.members.getMemos().remove(texts);
        }
        texts.title = title;
        texts.describe = describe;
        texts.members = members;
        texts.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        members.getMemos().add(texts);
        return texts;
    }

    public void setDescribe(String content){
        this.describe = content;
    }

    public void updateInfo(TextsForm dto){
        if (!dto.getTitle().isEmpty()){
            this.title = dto.getTitle();
        }
        if (!dto.getContent().isBlank()){
            this.describe = dto.getContent();
        }
    }
}
