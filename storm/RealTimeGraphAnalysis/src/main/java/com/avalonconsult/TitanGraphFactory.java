package com.avalonconsult;

import com.thinkaurelius.titan.core.TitanFactory;
import com.tinkerpop.blueprints.Graph;
import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by adam on 2/17/15.
 */
public class TitanGraphFactory implements GraphFactory, Serializable {

    public static final String STORAGE_BACKEND = "titan.storage.backend";
    public static final String STORAGE_HOSTNAME = "titan.storage.hostname";

    @Override
    public Graph makeGraph(Map conf) {
        Configuration graphConf = new BaseConfiguration();
        graphConf.setProperty("storage.backend", conf.get(STORAGE_BACKEND));
        graphConf.setProperty("storage.hostname", conf.get(STORAGE_HOSTNAME));
        return TitanFactory.open(graphConf);
    }
}
