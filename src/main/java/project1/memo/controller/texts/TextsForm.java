package project1.memo.controller.texts;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TextsForm {
    String title;
    String content;
}
