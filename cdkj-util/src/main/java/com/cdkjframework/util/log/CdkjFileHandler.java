package com.cdkjframework.util.log;

import java.io.IOException;
import java.util.logging.FileHandler;

/**
 * @ProjectName: cdkjframework
 * @Package: com.cdkjframework.util.log
 * @ClassName: CdkjFileHandler
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class CdkjFileHandler extends FileHandler {
    /**
     * Construct a default <tt>FileHandler</tt>.  This will be configured
     * entirely from <tt>LogManager</tt> properties (or their default values).
     * <p>
     *
     * @throws IOException          if there are IO problems opening the files.
     * @throws SecurityException    if a security manager exists and if
     *                              the caller does not have <tt>LoggingPermission("control"))</tt>.
     * @throws NullPointerException if pattern property is an empty String.
     */
    public CdkjFileHandler() throws IOException, SecurityException {
        super();
    }


}

