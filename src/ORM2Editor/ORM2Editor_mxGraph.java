package ORM2Editor;

import ORM_Event.ORM_EventListener;
import ORM_Event.ORM_EventObject;
import ORM_Presenter.ElementPresenter;
import ORM_Presenter.GraphPresenter;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.*;
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

    public void cellLabelChanged(Object cell, Object newValue,
                                 boolean autoSize) {

        String oldLabel = ((mxCell) cell).getValue().toString();

        if (!oldLabel.equals(newValue.toString())) {
            if (cell instanceof mxCell && newValue != null) {

                String newLabel = newValue.toString();

                String canChangeName = _graphPresenter.canChangeName((mxCell) cell, newLabel);

                if (canChangeName.isEmpty()) {
                    ((mxCell) cell).setValue(newLabel);
                    super.cellLabelChanged(cell, newValue, autoSize);
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, canChangeName);
                    ((mxCell) cell).setValue(oldLabel);
                    super.cellLabelChanged(cell, oldLabel, autoSize);
                }
            }
        }
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

    public void fireprocessOfCreatingConstrainAssociation(ORM_EventObject e) {

        for (ORM_EventListener obj : _mxGraphListener) {
            obj.processOfCreatingConstrainAssociation(e);
        }
    }
}
