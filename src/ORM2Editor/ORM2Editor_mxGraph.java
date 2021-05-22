package ORM2Editor;

import ORM_Presenter.GraphPresenter;
import com.mxgraph.view.mxGraph;

public class ORM2Editor_mxGraph extends mxGraph {

    private GraphPresenter _graphPresenter;

    public ORM2Editor_mxGraph(GraphPresenter graphPresenter)
    {
        _graphPresenter = graphPresenter;
    }

    public GraphPresenter getGraphPresenter() {
        return _graphPresenter;
    }

    public void setGraphPresenter(GraphPresenter graphPresenter) {
        _graphPresenter = graphPresenter;
    }
}
