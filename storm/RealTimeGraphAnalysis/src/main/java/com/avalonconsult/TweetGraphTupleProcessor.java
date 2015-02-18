package com.avalonconsult;

import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adam on 2/17/15.
 */
public class TweetGraphTupleProcessor implements GraphTupleProcessor, Serializable {
    @Override
    public void process(Graph g, TridentTuple tuple, TridentCollector collector) {
        Long timestamp = tuple.getLong(0);
        JSONObject json = (JSONObject)tuple.get(1);

        Vertex user = findOrCreateUser(g, (String)json.get("user"), (String)json.get("name"));
        JSONArray hashtags = (JSONArray)json.get("hashtags");
        for (int i = 0; i < hashtags.size(); i++) {
            Vertex v = findOrCreateVertex(g, "hashtag", ((String)hashtags.get(i)).toLowerCase());
            createEdgeAtTime(g, user, v, "mentions", timestamp);
        }
    }

    private void createEdgeAtTime(Graph g, Vertex user, Vertex v, String label, Long timestamp) {
        Edge newEdge = g.addEdge(null, user, v, label);
        newEdge.setProperty("timestamp", timestamp);
    }

    private Vertex findOrCreateVertex(Graph g, String property, String value) {
        ArrayList<Vertex> vertices = Lists.newArrayList(g.getVertices(property, value));
        Vertex vertex;
        if (vertices.size() == 0) {
            vertex = g.addVertex(null);
            vertex.setProperty("type", "hashtag");
            vertex.setProperty(property, value);
        } else {
            vertex = vertices.get(0);
        }
        return vertex;
    }

    private Vertex findOrCreateUser(Graph g, String user, String name) {
        ArrayList<Vertex> vertices = Lists.newArrayList(g.getVertices("user", user));
        Vertex vertex;
        if (vertices.size() == 0) {
            vertex = g.addVertex(null);
            vertex.setProperty("type", "user");
            vertex.setProperty("user", user);
            vertex.setProperty("name", name);
        } else {
            vertex = vertices.get(0);
        }

        return vertex;
    }
}
