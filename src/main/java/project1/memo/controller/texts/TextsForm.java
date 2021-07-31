package project1.memo.controller.texts;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextsForm {
    String title;
    String content;
}
