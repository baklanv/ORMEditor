package ORM2Editor;

import ORM_Event.ORM_EventListener;
import ORM_Event.ORM_EventObject;
import ORM_Presenter.GraphPresenter;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import java.util.ArrayList;
import java.util.List;

public class ORM2Editor_mxGraph extends mxGraph {

    private GraphPresenter _graphPresenter;

    public ORM2Editor_mxGraph(GraphPresenter graphPresenter) {
        _graphPresenter = graphPresenter;
    }

    public GraphPresenter getGraphPresenter() {
        return _graphPresenter;
    }

    public void setGraphPresenter(GraphPresenter graphPresenter) {
        _graphPresenter = graphPresenter;
    }

    // переопределенный метод mxGraph при переименовании узлов
    public void cellLabelChanged(Object cell, Object newValue, boolean autoSize) {
        if (cell instanceof mxCell && newValue != null) {
            String newLabel = newValue.toString();
            _graphPresenter.changeName((mxCell) cell, newLabel);
            super.cellLabelChanged(cell, newValue, autoSize);
        }
    }

    // переопределенный метод mxGraph для выделения узлов
    public boolean isCellSelectable(Object cell) {
        if (_graphPresenter.getMxGraph().getDefaultParent() != ((mxCell) cell).getParent()) {
            return false;
        }
        return super.isCellSelectable(cell);
    }

    private List<ORM_EventListener> _mxGraphListener = new ArrayList<>();

    public void addGraphListener(ORM_EventListener l) {
        _mxGraphListener.add(l);
    }

    public void removeGraphListener(ORM_EventListener l) {
        _mxGraphListener.remove(l);
    }

    public void fireConnectEdge(ORM_EventObject e) {

        for (ORM_EventListener obj : _mxGraphListener) {
            obj.edgeConnected(e);
        }
    }

    public void fireProcessOfCreatingConstrainAssociation(ORM_EventObject e) {
        for (ORM_EventListener obj : _mxGraphListener) {
            obj.processOfCreatingConstrainAssociation(e);
        }
    }
}
