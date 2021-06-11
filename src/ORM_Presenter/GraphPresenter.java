package ORM_Presenter;

import ORM2Editor.ORM2Editor_mxGraph;
import ORM_Event.ORM_EventListener;
import ORM_Event.ORM_EventObject;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.ClientDiagramModel;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.nodelinkdiagram.statuses.ValidationLevel;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.*;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class GraphPresenter{
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

    public ElementPresenter getElementPresenter(DiagramElement element) {
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

    public void createEntityTypePresenter(Point pos) {

        ORM_EntityType entityType = _clientDiagramModel.createNode(ORM_EntityType.class);
        EntityTypePresenter entityPresenter = new EntityTypePresenter(this, pos, entityType);
        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(entityPresenter);

        _clientDiagramModel.commit();
    }

    public void createValueTypePresenter(Point pos) {

        ORM_ValueType valueType = _clientDiagramModel.createNode(ORM_ValueType.class);
        ValueTypePresenter valueTypePresenter = new ValueTypePresenter(this, pos, valueType);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(valueTypePresenter);

        _clientDiagramModel.commit();
    }

    public void createUnaryPredicatePresenter(Point pos) {
        ORM_UnaryPredicate unaryPredicate = _clientDiagramModel.createNode(ORM_UnaryPredicate.class);
        UnaryPredicatePresenter unaryPredicatePresenter = new UnaryPredicatePresenter(this, pos, unaryPredicate);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(unaryPredicatePresenter);
        registerPresenter(unaryPredicatePresenter.getRole());
        _clientDiagramModel.commit();
    }

    public void createBinaryPredicatePresenter(Point pos) {

        ORM_BinaryPredicate binaryPredicate = _clientDiagramModel.createNode(ORM_BinaryPredicate.class);
        BinaryPredicatePresenter binaryPredicatePresenter = new BinaryPredicatePresenter(this, pos, binaryPredicate);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(binaryPredicatePresenter);
        registerPresenter(binaryPredicatePresenter.getRole());
        registerPresenter(binaryPredicatePresenter.getInverseRole());
        _clientDiagramModel.commit();
    }

    public void createExclusionConstraintPresenter(Point pos) {

        ORM_ExclusionConstraint exclusionConstraint = _clientDiagramModel.createNode(ORM_ExclusionConstraint.class);
        ExclusionConstraintPresenter exclusionConstraintPresenter = new ExclusionConstraintPresenter(this, pos, exclusionConstraint);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(exclusionConstraintPresenter);
        _clientDiagramModel.commit();
    }

    public void createInclusiveOrConstraintPresenter(Point pos) {

        ORM_InclusiveOrConstraint inclusiveOrConstraint = _clientDiagramModel.createNode(ORM_InclusiveOrConstraint.class);
        InclusiveOrConstraintPresenter inclusiveOrConstraintPresenter = new InclusiveOrConstraintPresenter(this, pos, inclusiveOrConstraint);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(inclusiveOrConstraintPresenter);
        _clientDiagramModel.commit();
    }

    public void createExclusiveOrConstraintPresenter(Point pos) {

        ORM_ExclusionOrConstraint exclusionOrConstraint = _clientDiagramModel.createNode(ORM_ExclusionOrConstraint.class);
        ExclusiveOrConstraintPresenter exclusiveOrConstraintPresenter = new ExclusiveOrConstraintPresenter(this, pos, exclusionOrConstraint);

        //зарегитреруем призентор и его соответсвующего mxCell
        registerPresenter(exclusiveOrConstraintPresenter);
        _clientDiagramModel.commit();
    }

    public void deleteElementPresenter(mxCell cell) {
        DiagramElement diagramElement = getORM_Element(cell);
        if (getPresenter(cell) != null) {
            List<DiagramElement> diagramElements = _clientDiagramModel.removeElement(diagramElement).collect(Collectors.toList());
            for (DiagramElement element : diagramElements) {
                ElementPresenter presenter = getElementPresenter(element);
                unregisterPresenter(presenter);
                presenter.destroy();
            }
            _clientDiagramModel.commit();
        }
    }

    public void startCreatingEdgeBy_mxGraph(int edgeType) {
        _edgeType = edgeType;
        if (edgeType != 3)
            _graphComponent.setConnectable(true);
        _graph.setCellsMovable(false);
    }

    public void endCreatingEdgeBy_mxGraph() {
        _edgeType = -1;
        _graphComponent.setConnectable(false);
        _graph.setCellsMovable(true);
    }

    public String canEdgeConnected(mxCell edge, mxCell source, mxCell target) {
        String edgeType = "";
        boolean result = false;
        ElementPresenter sourceEle = getPresenter(source);
        ElementPresenter targetEle = getPresenter(target);

        DiagramElement edgeElement = getORM_Element(edge);
        if (_edgeType == 1 || edgeElement instanceof ORM_RoleAssociation) {
            result = _clientDiagramModel.canConnectBy(sourceEle.getORM_Element(), targetEle.getORM_Element(), ORM_RoleAssociation.class, ValidationLevel.Strong);
            if (sourceEle.getORM_Element() instanceof ORM_Role) {
                result = _clientDiagramModel.canConnectBy(targetEle.getORM_Element(), sourceEle.getORM_Element(), ORM_RoleAssociation.class, ValidationLevel.Strong);
            }
        } else if (_edgeType == 2 || edgeElement instanceof ORM_Subtyping) {
            result = _clientDiagramModel.canConnectBy(sourceEle.getORM_Element(), targetEle.getORM_Element(), ORM_Subtyping.class, ValidationLevel.Strong);
        } else if (edgeElement instanceof ORM_ConstraintAssociation) {
            result = _clientDiagramModel.canConnectBy(sourceEle.getORM_Element(), targetEle.getORM_Element(), ORM_ConstraintAssociation.class, ValidationLevel.Strong);
        }
        if (!result) {
            edgeType = "It is not possible to connect nodes " + sourceEle.get_mxCell().getValue() +
                    " and " + targetEle.get_mxCell().getValue();
        }
        return edgeType;
    }

    public void changeName(mxCell cell, String newLabel) {
        ElementPresenter element = getPresenter(cell);
        if (element instanceof EntityTypePresenter) {
            ((EntityTypePresenter) element).setName(newLabel);
        } else if (element instanceof ValueTypePresenter) {
            ((ValueTypePresenter) element).setName(newLabel);
        } else if (element instanceof RolePresenter) {
            ((RolePresenter) element).setName(newLabel);
        }
        _clientDiagramModel.commit();
    }

    // класс контролирует создание дуги (Role Association/Subtyping/ConstraintAssociation) с помощью GUI
    private class edgeConnectedListener implements ORM_EventListener {
        mxCell source;

        @Override
        public void edgeConnected(ORM_EventObject e) {
            if (_edgeType == 1) {
                createRoleAssociationPresenter(e.get_mxCell());
            } else if (_edgeType == 2) {
                createSubtypingPresenter(e.get_mxCell());
            }
        }

        @Override
        public void processOfCreatingConstrainAssociation(ORM_EventObject e) {
            if (_edgeType == 3) {
                if (source == null) {
                    source = e.get_mxCell();
                } else {
                    mxCell target = e.get_mxCell();

                    if (!(getORM_Element(source) instanceof ORM_SubtypingConstraint)) {
                        target = source;
                        source = e.get_mxCell();
                    }

                    boolean result = _clientDiagramModel.canConnectBy(getORM_Element(source), getORM_Element(target), ORM_ConstraintAssociation.class, ValidationLevel.Strong);

                    if (result) {
                        createConstrainAssociation(source, target);

                    } else {
                        JOptionPane.showMessageDialog(null, "невозможно соединить узлы");
                        endCreatingEdgeBy_mxGraph();
                    }
                    source = null;
                }
            } else {
                source = null;
            }
        }

        private void createSubtypingPresenter(mxCell cell) {
            ORM_EntityType source = (ORM_EntityType) getORM_Element((mxCell) cell.getSource());
            ORM_EntityType target = (ORM_EntityType) getORM_Element((mxCell) cell.getTarget());

            ORM_Subtyping subtyping = _clientDiagramModel.connectBy(source, target, ORM_Subtyping.class);
            SubtypingPresenter subtypePresenter = new SubtypingPresenter(GraphPresenter.this, cell, subtyping);

            registerPresenter(subtypePresenter);
            endCreatingEdgeBy_mxGraph();

            _clientDiagramModel.commit();
        }

        private void createRoleAssociationPresenter(mxCell cell) {
            DiagramElement source = getORM_Element((mxCell) cell.getSource());
            DiagramElement target = getORM_Element((mxCell) cell.getTarget());

            if (source instanceof ORM_Role) {
                source = target;
                target = getORM_Element((mxCell) cell.getSource());
            }
            ORM_RoleAssociation roleAssociation = _clientDiagramModel.connectBy(source, target, ORM_RoleAssociation.class);
            RoleAssociationPresenter roleAssociationPresenter = new RoleAssociationPresenter(GraphPresenter.this, cell, roleAssociation);

            registerPresenter(roleAssociationPresenter);
            endCreatingEdgeBy_mxGraph();

            _clientDiagramModel.commit();
        }

        private void createConstrainAssociation(mxCell mxSource, mxCell mxTarget) {
            DiagramElement source = getORM_Element(mxSource);
            DiagramElement target = getORM_Element(mxTarget);

            ORM_ConstraintAssociation constraintAssociation = _clientDiagramModel.connectBy(source, target, ORM_ConstraintAssociation.class);
            ConstrainAssociationPresenter constrainAssociationPresenter = new ConstrainAssociationPresenter(GraphPresenter.this, mxSource, mxTarget, constraintAssociation);

            registerPresenter(constrainAssociationPresenter);
            endCreatingEdgeBy_mxGraph();

            _clientDiagramModel.commit();
        }
    }

    // изменяет внешний вид презенторов
    public void changeState(DiagramElement element, ValidateStatus validateStatus) {
        if (validateStatus == ValidateStatus.Acceptable) {
            if (element instanceof ORM_EntityType) {
                EntityTypePresenter entityTypePresenter = (EntityTypePresenter) getElementPresenter(element);
                entityTypePresenter.success();
            } else if (element instanceof ORM_ValueType) {
                ValueTypePresenter valueTypePresenter = (ValueTypePresenter) getElementPresenter(element);
                valueTypePresenter.success();
            } else if (element instanceof ORM_UnaryPredicate) {
                UnaryPredicatePresenter unaryPredicatePresenter = (UnaryPredicatePresenter) getElementPresenter(element);
                unaryPredicatePresenter.success();
            } else if (element instanceof ORM_BinaryPredicate) {
                BinaryPredicatePresenter binaryPredicatePresenter = (BinaryPredicatePresenter) getElementPresenter(element);
                binaryPredicatePresenter.success();
            } else if (element instanceof ORM_InclusiveOrConstraint) {
                InclusiveOrConstraintPresenter inclusiveOrConstraintPresenter = (InclusiveOrConstraintPresenter) getElementPresenter(element);
                inclusiveOrConstraintPresenter.success();
            } else if (element instanceof ORM_ExclusionOrConstraint) {
                ExclusiveOrConstraintPresenter exclusiveOrConstraintPresenter = (ExclusiveOrConstraintPresenter) getElementPresenter(element);
                exclusiveOrConstraintPresenter.success();
            } else if (element instanceof ORM_ExclusionConstraint) {
                ExclusionConstraintPresenter exclusionConstraintPresenter = (ExclusionConstraintPresenter) getElementPresenter(element);
                exclusionConstraintPresenter.success();
            }
        } else if (validateStatus == ValidateStatus.Invalid) {
            if (element instanceof ORM_EntityType) {
                EntityTypePresenter entityTypePresenter = (EntityTypePresenter) getElementPresenter(element);
                entityTypePresenter.fail();
            } else if (element instanceof ORM_ValueType) {
                ValueTypePresenter entityTypePresenter = (ValueTypePresenter) getElementPresenter(element);
                entityTypePresenter.fail();
            } else if (element instanceof ORM_UnaryPredicate) {
                UnaryPredicatePresenter unaryPredicatePresenter = (UnaryPredicatePresenter) getElementPresenter(element);
                unaryPredicatePresenter.fail();
            } else if (element instanceof ORM_BinaryPredicate) {
                BinaryPredicatePresenter binaryPredicatePresenter = (BinaryPredicatePresenter) getElementPresenter(element);
                binaryPredicatePresenter.fail();
            } else if (element instanceof ORM_InclusiveOrConstraint) {
                InclusiveOrConstraintPresenter inclusiveOrConstraintPresenter = (InclusiveOrConstraintPresenter) getElementPresenter(element);
                inclusiveOrConstraintPresenter.fail();
            } else if (element instanceof ORM_ExclusionOrConstraint) {
                ExclusiveOrConstraintPresenter exclusiveOrConstraintPresenter = (ExclusiveOrConstraintPresenter) getElementPresenter(element);
                exclusiveOrConstraintPresenter.fail();
            } else if (element instanceof ORM_ExclusionConstraint) {
                ExclusionConstraintPresenter exclusionConstraintPresenter = (ExclusionConstraintPresenter) getElementPresenter(element);
                exclusionConstraintPresenter.fail();
            }
        }
    }
}
