package com.avalonconsult;

import com.tinkerpop.blueprints.Graph;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Created by adam on 2/17/15.
 */
public interface GraphTupleProcessor {
    public void process(Graph g, TridentTuple tuple, TridentCollector collector);
}
