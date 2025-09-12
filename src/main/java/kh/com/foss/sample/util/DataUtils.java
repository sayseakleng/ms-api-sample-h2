package kh.com.foss.sample.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DataUtils {
    public void clear(final String key) {
        log.info("Your key: {}", key);
    }
}
