package de.podolak.microflowengine.graph.nodes;

/**
 * The default interface of a <code>NodeFactory</code>.
 * Implementations of <code>Node</code>s should also contain an implementation
 * of a <code>NodeFactory</code>. The main purpose of a <code>NodeFactory</code>
 * is its use during deserialization.
 * 
 * @author Dirk Podolak
 */
public interface NodeFactory {

    /**
     * Returns a <code>Node</code> from its textual representation.
     * 
     * @param nodeString textual representation of a <code>Node</code>
     * @return <code>Node</code>
     */
    Node getNodeFromString(String nodeString);
    
}
