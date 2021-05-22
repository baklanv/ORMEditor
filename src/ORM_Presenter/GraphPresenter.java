package ORM_Presenter;

import ORM2Editor.ORM2Editor_mxGraph;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.ClientDiagramModel;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.nodelinkdiagram.util.Point;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    // ----- Соответсвие между ячейкой из mxGraph и презентером элемента диаграммы -------------
    private final Map<mxCell, ElementPresenter> mxCellPresenterMap = new HashMap<>();

    private void registerPresenter(@NotNull ElementPresenter presenter) {

        mxCellPresenterMap.put(presenter.get_mxCell(), presenter);
    }

    private void unregisterPresenter(@NotNull ElementPresenter presenter) {
        mxCellPresenterMap.remove(presenter.get_mxCell(), presenter);
    }

    private ElementPresenter getElementPresenter(DiagramElement element){
        Collection<ElementPresenter> elementPresenterList = mxCellPresenterMap.values();
        for (ElementPresenter e : elementPresenterList){
            if (e.getORM_Element() == element)
                return e;
        }
        return null; //  не очень
    }

    public ElementPresenter getPresenter(@NotNull mxCell cell) {

        return mxCellPresenterMap.get(cell);
    }

    public DiagramElement getORM_Element(@NotNull mxCell cell) {

        ElementPresenter presenter = getPresenter(cell);

        return presenter != null ? presenter.getORM_Element() : null;
    }

    public EntityTypePresenter createEntityTypePresenter(String name, Point pos) {

        EntityTypePresenter entityPresenter = new EntityTypePresenter(this, pos);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(entityPresenter);

        return entityPresenter;
    }

    public ValueTypePresenter createValueTypePresenter(String name, Point pos) {

        ValueTypePresenter valueTypePresenter = new ValueTypePresenter(this, pos);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(valueTypePresenter);

        return valueTypePresenter;
    }

    public SubtypingPresenter createSubtypingPresenter() {

        SubtypingPresenter subtypePresenter = new SubtypingPresenter(this);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(subtypePresenter);

        return subtypePresenter;
    }
}
