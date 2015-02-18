package com.avalonconsult;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.tuple.TridentTuple;

import java.util.List;

/**
 * Created by adam on 2/17/15.
 */
public class GraphUpdater extends BaseStateUpdater<GraphState> {

    private GraphTupleProcessor processor;

    public GraphUpdater(GraphTupleProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void updateState(GraphState state, List<TridentTuple> tuples, TridentCollector collector) {
        state.update(tuples, collector, this.processor);
    }
}
