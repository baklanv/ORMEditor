package ORM2Editor;

import ORM_Presenter.GraphPresenter;
import com.mxgraph.swing.mxGraphComponent;
import org.vstu.nodelinkdiagram.*;
import org.vstu.orm2diagram.model.ORM_DiagramFactory;

import javax.swing.*;

public class ORM2Editor extends JFrame {

    ORM2Editor_mxGraph _graph;
    ClientDiagramModel _clientDiagramModel;
    GraphPresenter _graphPresenter;
    mxGraphComponent _graphComponent;

    public ORM2Editor(){
        super("ORM2Editor");

        MainDiagramModel mainModel = new MainDiagramModel(new ORM_DiagramFactory());
        _clientDiagramModel = mainModel.registerClient(new DiagramClient(){});
        _graph = new ORM2Editor_mxGraph(_graphPresenter);
        _graphComponent = new mxGraphComponent(_graph);
        _graphPresenter = new GraphPresenter(_graph, _clientDiagramModel, _graphComponent);

        _graph.setAllowDanglingEdges(false);
        _graph.setAllowLoops(false);

        _graphComponent.setConnectable(false);
        _graphComponent.setEnterStopsCellEditing(true);

        getContentPane().add(_graphComponent);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setVisible(true);
    }

    public static void main(String[] args) {

        ORM2Editor frame = new ORM2Editor();
    }
}
