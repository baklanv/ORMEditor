package ORM_Presenter;

import ORM2Editor.ORM2Editor_mxGraph;
import com.mxgraph.swing.mxGraphComponent;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.ClientDiagramModel;

public class GraphPresenter {
    ORM2Editor_mxGraph _graph;
    ClientDiagramModel _clientDiagramModel;
    mxGraphComponent _graphComponent;
    public GraphPresenter(@NotNull ORM2Editor_mxGraph graph, @NotNull ClientDiagramModel clientDiagramModel, @NotNull mxGraphComponent graphComponent){
        _graph = graph;
        _graph.setGraphPresenter(this);

        _clientDiagramModel = clientDiagramModel;
        _graphComponent = graphComponent;
    }

    public ORM2Editor_mxGraph getMxGraph() {
        return _graph;
    }
}
