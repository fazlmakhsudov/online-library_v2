package com.practice.library.entity;


import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Author {
    @NonNull
    protected int id;
    @NonNull
    protected String name;
    @NonNull
    private String secondName;
    @NonNull
    private LocalDate dateOfBirth;
    private Map<Integer, String> books = new HashMap<>();

}
