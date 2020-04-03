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
public class Book {
    @NonNull
    protected int id;
    @NonNull
    protected String name;
    @NonNull
    private String description;
    @NonNull
    private LocalDate dateOfPublish;
    private Map<Integer, String> authors = new HashMap<>();
}
