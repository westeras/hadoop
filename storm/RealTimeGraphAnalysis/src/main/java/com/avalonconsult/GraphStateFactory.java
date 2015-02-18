package com.avalonconsult;

import backtype.storm.task.IMetricsContext;
import com.tinkerpop.blueprints.Graph;
import storm.trident.state.State;
import storm.trident.state.StateFactory;

import java.util.Map;

/**
 * Created by adam on 2/17/15.
 */
public class GraphStateFactory implements StateFactory {

    private GraphFactory factory;

    public GraphStateFactory(GraphFactory factory) {
        this.factory = factory;
    }

    @Override
    public State makeState(Map conf, IMetricsContext iMetricsContext, int i, int i1) {
        Graph graph = this.factory.makeGraph(conf);
        State state = new GraphState(graph);
        return state;
    }
}
