package ORM_Presenter;

import ORM2Editor.ORM2Editor_mxGraph;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.ClientDiagramModel;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.vstu.orm2diagram.model.ORM_ValueType;

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

    public EntityTypePresenter createEntityTypePresenter(Point pos) {

        ORM_EntityType orm_entityType = _clientDiagramModel.createNode(ORM_EntityType.class);
        EntityTypePresenter entityPresenter = new EntityTypePresenter(this, pos, orm_entityType);

        _clientDiagramModel.commit();
        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(entityPresenter); // надо ли?

        return entityPresenter;
    }

    public ValueTypePresenter createValueTypePresenter(Point pos) {

        ORM_ValueType orm_valueType = _clientDiagramModel.createNode(ORM_ValueType.class);
        ValueTypePresenter valueTypePresenter = new ValueTypePresenter(this, pos, orm_valueType);

        _clientDiagramModel.commit();
        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(valueTypePresenter);

        return valueTypePresenter;
    }

    public UnaryPredicatePresenter createUnaryPredicatePresenter(Point pos) {

        //ORM_ValueType orm_valueType = _clientDiagramModel.createNode(ORM_ValueType.class);
        UnaryPredicatePresenter unaryPredicatePresenter = new UnaryPredicatePresenter(this, pos);

        //_clientDiagramModel.commit();
        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(unaryPredicatePresenter);

        return unaryPredicatePresenter;
    }

    public BinaryPredicatePresenter createBinaryPredicatePresenter(Point pos) {

        //ORM_ValueType orm_valueType = _clientDiagramModel.createNode(ORM_ValueType.class);
        BinaryPredicatePresenter binaryPredicatePresenter = new BinaryPredicatePresenter(this, pos);

        //_clientDiagramModel.commit();
        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(binaryPredicatePresenter);

        return binaryPredicatePresenter;
    }

    public ExclusionConstraintPresenter createExclusionConstraintPresenter(Point pos) {

        //ORM_ValueType orm_valueType = _clientDiagramModel.createNode(ORM_ValueType.class);
        ExclusionConstraintPresenter exclusionConstraintPresenter = new ExclusionConstraintPresenter(this, pos);

        //_clientDiagramModel.commit();
        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(exclusionConstraintPresenter);

        return exclusionConstraintPresenter;
    }

    public InclusiveOrConstraintPresenter createInclusiveOrConstraintPresenter(Point pos) {

        //ORM_ValueType orm_valueType = _clientDiagramModel.createNode(ORM_ValueType.class);
        InclusiveOrConstraintPresenter inclusiveOrConstraintPresenter = new InclusiveOrConstraintPresenter(this, pos);

        //_clientDiagramModel.commit();
        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(inclusiveOrConstraintPresenter);

        return inclusiveOrConstraintPresenter;
    }

    public ExclusiveOrConstraintPresenter createExclusiveOrConstraintPresenter(Point pos) {

        //ORM_ValueType orm_valueType = _clientDiagramModel.createNode(ORM_ValueType.class);
        ExclusiveOrConstraintPresenter exclusiveOrConstraintPresenter = new ExclusiveOrConstraintPresenter(this, pos);

        //_clientDiagramModel.commit();
        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(exclusiveOrConstraintPresenter);

        return exclusiveOrConstraintPresenter;
    }

    public SubtypingPresenter createSubtypingPresenter() {

        SubtypingPresenter subtypePresenter = new SubtypingPresenter(this);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(subtypePresenter);

        return subtypePresenter;
    }
}
