
package com.android.dekkoo.data.model;

import javax.annotation.Generated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Generated("org.jsonschema2pojo")
public class Pagination {

    public Integer current;
    public Object previous;
    public Object next;
    public Integer perPage;
    public Integer pages;

}
