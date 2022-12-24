package cn.msdnicrosoft.commandbuttons.data;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class CommandItem {
    private String displayName;
    private String raw;
}
