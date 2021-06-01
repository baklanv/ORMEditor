package ORM_Presenter;

import ORM2Editor.ORM2Editor_mxGraph;
import ORM_Event.ORM_EventListener;
import ORM_Event.ORM_EventObject;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.ClientDiagramModel;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.vstu.orm2diagram.model.ORM_ValueType;
import org.w3c.dom.Element;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphPresenter {
    ORM2Editor_mxGraph _graph;
    ClientDiagramModel _clientDiagramModel;
    mxGraphComponent _graphComponent;
    int _edgeType = -1;

    public GraphPresenter(@NotNull ORM2Editor_mxGraph graph, @NotNull ClientDiagramModel clientDiagramModel, @NotNull mxGraphComponent graphComponent) {
        _graph = graph;
        _graph.setGraphPresenter(this);

        _clientDiagramModel = clientDiagramModel;
        _graphComponent = graphComponent;

        _graph.addGraphListener(new edgeConnectedListener());
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

    private ElementPresenter getElementPresenter(DiagramElement element) {
        Collection<ElementPresenter> elementPresenterList = mxCellPresenterMap.values();
        for (ElementPresenter e : elementPresenterList) {
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

    public List<ElementPresenter> getCells(Class elementClass) {
        List<ElementPresenter> elementPresenters = new ArrayList<>();
        Collection<ElementPresenter> list = mxCellPresenterMap.values();
        for (ElementPresenter element : list) {
            if (element.getClass().equals(elementClass))
                elementPresenters.add(element);
        }
        return elementPresenters;
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

        //ORM_ValueType orm_valueType = _clientDiagramModel.createNode(ORM_ValueType.class);
        ValueTypePresenter valueTypePresenter = new ValueTypePresenter(this, pos);

        //_clientDiagramModel.commit();
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

    public void deleteElementPresenter(mxCell cell) {
        ElementPresenter elementPresenter = getPresenter(cell);
        if (elementPresenter != null) {
            elementPresenter.destroy();
        }
    }

    public void startCreatingEdgeBy_mxGraph(int edgeType) {

        _edgeType = edgeType;
        if (edgeType != 3)
            _graphComponent.setConnectable(true);

    }

    public void endCreatingEdgeBy_mxGraph() {

        _edgeType = -1;
        _graphComponent.setConnectable(false);
    }

    public String canEdgeConnected(mxCell edge, mxCell source, mxCell target) {
        String result = "";

        String edgeType = "";

        // проверять может ли эти source или target внутренные элементы BinaryRole

        ElementPresenter sourceEle = getPresenter(source);

        // System.out.println(((Element)target.getParent().getParent().getValue()).getAttribute("type"));;
        ElementPresenter targetEle = getPresenter(target);

        if (_edgeType == 1) {
            result = RoleAssociationPresenter.canConnect(GraphPresenter.this, sourceEle, targetEle);


        } else if (_edgeType == 2) {

            result = SubtypingPresenter.canConnect(GraphPresenter.this, sourceEle, targetEle);

        } else if (_edgeType == 3){

            result = ConstrainAssociationPresenter.canConnect(GraphPresenter.this, sourceEle, targetEle);
        }

        return result;
    }

    public String canChangeName(mxCell cell, String newLabel) {
        String result = "";
        ElementPresenter element = getPresenter(cell);

        if (element instanceof EntityTypePresenter) {

            result = EntityTypePresenter.canChangeName(GraphPresenter.this, newLabel);

        } else if (element instanceof ValueTypePresenter) {
            result = ValueTypePresenter.canChangeName(GraphPresenter.this, newLabel);
        }
        return result;
    }


    // класс контролирует создание дуги (Role Association/Subtyping) с помощью GUI
    private class edgeConnectedListener implements ORM_EventListener {

        mxCell tmpCell;

        @Override
        public void edgeConnected(ORM_EventObject e) {

            DiagramElement element = getORM_Element(e.get_mxCell());

            // Ничего дополнительно не делаем, если дуга уже существует/существовала в диаграмме
            if (element != null || _edgeType == 3) {
                return;
            }

            if (_edgeType == 1) {
                createRoleAssociationPresenter(e.get_mxCell());
            }
            else if (_edgeType == 2) {
                createSubtypingPresenter(e.get_mxCell());
            }
        }

        @Override
        public void processOfCreatingConstrainAssociation(ORM_EventObject e) {
            if (_edgeType == 3) {
                if (tmpCell == null) {
                    tmpCell = e.get_mxCell();
                }
                    else {
                    createConstrainAssociation(tmpCell, e.get_mxCell());
                    tmpCell = null;
                }
            }
            else{
                tmpCell = null;
            }
        }

        private void createSubtypingPresenter(mxCell cell) {

            SubtypingPresenter subtypePresenter = new SubtypingPresenter(GraphPresenter.this, cell);

            registerPresenter(subtypePresenter);
            endCreatingEdgeBy_mxGraph();
        }

        private void createRoleAssociationPresenter(mxCell cell) {
            RoleAssociationPresenter roleAssociationPresenter = new RoleAssociationPresenter(GraphPresenter.this, cell);
            registerPresenter(roleAssociationPresenter);
            endCreatingEdgeBy_mxGraph();
        }

        private void createConstrainAssociation(mxCell source, mxCell target) {
            ConstrainAssociationPresenter constrainAssociationPresenter = new ConstrainAssociationPresenter(GraphPresenter.this, source, target);
            String result = canEdgeConnected(constrainAssociationPresenter.get_mxCell(), tmpCell, target);
            registerPresenter(constrainAssociationPresenter);
            if (!result.equals("")){
                deleteElementPresenter(constrainAssociationPresenter.get_mxCell());
                JOptionPane.showMessageDialog(null, result);
            }
            endCreatingEdgeBy_mxGraph();
        }
    }
}
