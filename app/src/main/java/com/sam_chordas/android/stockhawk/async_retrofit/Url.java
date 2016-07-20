
package com.sam_chordas.android.stockhawk.async_retrofit;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Url {

    @SerializedName("execution-start-time")
    @Expose
    private String execution_start_time;
    @SerializedName("execution-stop-time")
    @Expose
    private String execution_stop_time;
    @SerializedName("execution-time")
    @Expose
    private String execution_time;
    @SerializedName("content")
    @Expose
    private String content;

    /**
     * 
     * @return
     *     The execution_start_time
     */
    public String getExecution_start_time() {
        return execution_start_time;
    }

    /**
     * 
     * @param execution_start_time
     *     The execution-start-time
     */
    public void setExecution_start_time(String execution_start_time) {
        this.execution_start_time = execution_start_time;
    }

    /**
     * 
     * @return
     *     The execution_stop_time
     */
    public String getExecution_stop_time() {
        return execution_stop_time;
    }

    /**
     * 
     * @param execution_stop_time
     *     The execution-stop-time
     */
    public void setExecution_stop_time(String execution_stop_time) {
        this.execution_stop_time = execution_stop_time;
    }

    /**
     * 
     * @return
     *     The execution_time
     */
    public String getExecution_time() {
        return execution_time;
    }

    /**
     * 
     * @param execution_time
     *     The execution-time
     */
    public void setExecution_time(String execution_time) {
        this.execution_time = execution_time;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

}
