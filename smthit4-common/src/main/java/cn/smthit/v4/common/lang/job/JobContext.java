package cn.smthit.v4.common.lang.job;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author bean
 * @date 2023/9/24
 */
public class JobContext {
    private Map<String, Object> content = new HashMap<>();

    private StringBuffer logBuffer = new StringBuffer();

    @Getter
    @Setter
    private Date startTime;

    @Getter
    @Setter
    private Date endTime;

    public void put(String key, Object value) {
        content.put(key, value);
    }

    public Object get(String key) {
        return content.get(key);
    }

    public void appendLog(String log) {
        logBuffer.append(log).append("\n");
    }
}
