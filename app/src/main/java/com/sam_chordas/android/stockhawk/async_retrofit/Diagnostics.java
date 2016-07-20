
package com.sam_chordas.android.stockhawk.async_retrofit;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Diagnostics {

    @SerializedName("url")
    @Expose
    private List<Url> url = new ArrayList<Url>();
    @SerializedName("publiclyCallable")
    @Expose
    private String publiclyCallable;
    @SerializedName("cache")
    @Expose
    private List<Cache> cache = new ArrayList<Cache>();
    @SerializedName("query")
    @Expose
    private List<Query_> query = new ArrayList<Query_>();
    @SerializedName("javascript")
    @Expose
    private Javascript javascript;
    @SerializedName("user-time")
    @Expose
    private String user_time;
    @SerializedName("service-time")
    @Expose
    private String service_time;
    @SerializedName("build-version")
    @Expose
    private String build_version;

    /**
     * 
     * @return
     *     The url
     */
    public List<Url> getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(List<Url> url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The publiclyCallable
     */
    public String getPubliclyCallable() {
        return publiclyCallable;
    }

    /**
     * 
     * @param publiclyCallable
     *     The publiclyCallable
     */
    public void setPubliclyCallable(String publiclyCallable) {
        this.publiclyCallable = publiclyCallable;
    }

    /**
     * 
     * @return
     *     The cache
     */
    public List<Cache> getCache() {
        return cache;
    }

    /**
     * 
     * @param cache
     *     The cache
     */
    public void setCache(List<Cache> cache) {
        this.cache = cache;
    }

    /**
     * 
     * @return
     *     The query
     */
    public List<Query_> getQuery() {
        return query;
    }

    /**
     * 
     * @param query
     *     The query
     */
    public void setQuery(List<Query_> query) {
        this.query = query;
    }

    /**
     * 
     * @return
     *     The javascript
     */
    public Javascript getJavascript() {
        return javascript;
    }

    /**
     * 
     * @param javascript
     *     The javascript
     */
    public void setJavascript(Javascript javascript) {
        this.javascript = javascript;
    }

    /**
     * 
     * @return
     *     The user_time
     */
    public String getUser_time() {
        return user_time;
    }

    /**
     * 
     * @param user_time
     *     The user-time
     */
    public void setUser_time(String user_time) {
        this.user_time = user_time;
    }

    /**
     * 
     * @return
     *     The service_time
     */
    public String getService_time() {
        return service_time;
    }

    /**
     * 
     * @param service_time
     *     The service-time
     */
    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    /**
     * 
     * @return
     *     The build_version
     */
    public String getBuild_version() {
        return build_version;
    }

    /**
     * 
     * @param build_version
     *     The build-version
     */
    public void setBuild_version(String build_version) {
        this.build_version = build_version;
    }

}
