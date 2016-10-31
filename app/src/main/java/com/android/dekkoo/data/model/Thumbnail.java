package com.android.dekkoo.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Thumbnail {
    public Float aspectRatio;
    public Integer height;
    public Object name;
    public String url;
    public Integer width;
}