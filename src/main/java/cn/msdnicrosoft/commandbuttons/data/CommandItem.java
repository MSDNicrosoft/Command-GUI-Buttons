package cn.msdnicrosoft.commandbuttons.data;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class CommandItem {
    @Setter
    private String displayName;
    private ArrayList<String> raw;
}
