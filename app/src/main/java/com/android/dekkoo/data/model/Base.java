
package com.android.dekkoo.data.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Generated("org.jsonschema2pojo")
public class Base {

    public List<Response> response = new ArrayList<>();
    public Pagination pagination;

}
