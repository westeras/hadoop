package com.avalonconsult;

import com.tinkerpop.blueprints.Graph;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by adam on 2/17/15.
 */
public interface GraphFactory {
    public Graph makeGraph(Map conf);
}
