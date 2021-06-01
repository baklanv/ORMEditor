package ORM2Editor;

import ORM_Presenter.GraphPresenter;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxMultiplicity;

import java.util.Collection;

public class ORM_mxMultiplicity extends mxMultiplicity {
    GraphPresenter _graphPresenter;

    public ORM_mxMultiplicity() {
        super(true, null, null, null, 0, null, null, null, "", false);
    }

    public ORM_mxMultiplicity(GraphPresenter presenter) {
        super(true, null, null, null, 0, null, null, null, "", false);
        _graphPresenter = presenter;
    }

    @Override
    public String check(mxGraph graph, Object edge, Object source,
                        Object target, int sourceOut, int targetIn) {

        String result = "";

        if (source != null && target != null) {
            result = _graphPresenter.canEdgeConnected((mxCell) edge, (mxCell) source, (mxCell) target);
        }

        return result;
    }
}
