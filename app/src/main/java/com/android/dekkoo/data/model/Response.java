
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
public class Response {
    public String id;
    public String createdAt;
    public Object friendlyTitle;
    public String siteId;
    public String title;
    public String updatedAt;
    public List<String> values = new ArrayList<>();
    public String description;
    public String created_at;
    public String published_at;
    public String site_id;
    public Boolean subscription_required;
    public String short_description;
    public String youtube_id;
    public String vimeo_id;
    public String transcoded;
    public Boolean active;
    public Boolean mature_content;
    public Boolean rental_required;
    public List<Thumbnail> thumbnails = new ArrayList<Thumbnail>();
}
