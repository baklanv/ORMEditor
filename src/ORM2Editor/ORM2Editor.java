package ORM2Editor;

import ORM_Event.ORM_EventObject;
import ORM_Presenter.GraphPresenter;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxSwingConstants;
import com.mxgraph.util.*;
import com.mxgraph.view.mxGraphSelectionModel;
import com.mxgraph.view.mxStylesheet;
import org.vstu.nodelinkdiagram.*;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ORM2Editor extends JFrame implements DiagramClient {

    ORM2Editor_mxGraph _graph;
    ClientDiagramModel _clientDiagramModel;
    GraphPresenter _graphPresenter;
    mxGraphComponent _graphComponent;

    // значение по умольчанию -1
    int _activeAction = -1;
    JToolBar _tb;

    JScrollPane _scrollBar;
    JToolBar _tbSb;

    private final int ENTITY_TYPE = 0, VALUE_TYPE = 1, UNARY_FACT = 2,
            BINARY_FACT = 3, EXCLUSION_CONSTRAINT = 4, INCLUSIVE_OR_CONSTRAINT = 5, EXCLUSIVE_OR_CONSTRAINT = 6,
            ROLE_ASSOC = 7, SUBTYPING = 8, CONSTRAIN_ASSOCIATION = 9, DELETE = 10;

    private final String[] _toolbarItemName = {"EntityType", "ValueType", "UnaryFactType", "BinaryFactType", "ExclusionConstraint", "Inclusive Or Oonstraint",
            "ExclusiveOrConstraint", "RoleConnector", "SubtypeConnector", "ConstrainAssociation", "Delete"};

    public ORM2Editor() {
        super("ORM2Editor");


        MainDiagramModel mainModel = new MainDiagramModel(new ORM_DiagramFactory());
        _clientDiagramModel = mainModel.registerClient(new DiagramClient() {
        });
        _graph = new ORM2Editor_mxGraph(_graphPresenter);
        _graphComponent = new mxGraphComponent(_graph);
        _graphPresenter = new GraphPresenter(_graph, _clientDiagramModel, _graphComponent);
        _clientDiagramModel.addListener(new ModelUpdateListener());

        mxSwingConstants.VERTEX_SELECTION_STROKE = new BasicStroke(1.0F, 0, 0, 10.0F, new float[]{7.0F, 7.0F}, 0.0F);
        mxSwingConstants.EDGE_SELECTION_STROKE = new BasicStroke(1.0F, 0, 0, 10.0F, new float[]{7.0F, 7.0F}, 0.0F);

        ORM_mxMultiplicity[] _mxMultiplicity = new ORM_mxMultiplicity[1];
        _mxMultiplicity[0] = new ORM_mxMultiplicity(_graphPresenter);
        _graph.setMultiplicities(_mxMultiplicity);

        _graph.setAllowDanglingEdges(false); // запрещаем висячие дуги
        _graph.setAllowLoops(false);

        _graphComponent.setConnectable(false);
        _graphComponent.setEnterStopsCellEditing(true);
        _graphComponent.setBackground(Color.WHITE);
        setBackground(Color.WHITE);

        mxStylesheet _styleSheet = _graph.getStylesheet();
        Map<String, Object> _defaultEdgeStyle = _styleSheet.getDefaultEdgeStyle();
        Map<String, Object> _defaultVertexStyle = _styleSheet.getDefaultVertexStyle();
        _defaultEdgeStyle.put("endArrow", " ");
        _defaultEdgeStyle.put("strokeColor", "black");
        _defaultVertexStyle.put("fontSize", 12); //ORM2 default size is 7, in here it is so small
        _defaultVertexStyle.put("fontFamily", "Times New Roman");
        _defaultVertexStyle.put("fillColor", "white");
        _defaultVertexStyle.put("strokeColor", "black");
        _defaultVertexStyle.put("spacing", 10);
        _defaultVertexStyle.put("spacingTop", 3);

        getContentPane().add(_graphComponent);

        getContentPane().setBackground(Color.white);
        _graph.addListener(mxEvent.CELL_CONNECTED, new mxEventSource.mxIEventListener() {
            @Override
            public void invoke(Object source, mxEventObject evt) {
                Map<String, Object> properties = evt.getProperties();
                Object edge = properties.get("edge");
                if (((mxCell) edge).getSource() != null
                        && ((mxCell) edge).getTarget() != null) {

                    ORM_EventObject ormEventObject = new ORM_EventObject(this);
                    ormEventObject.set_mxCell((mxCell) edge);
                    _graph.fireConnectEdge(ormEventObject);

                    _graphComponent.setConnectable(false);
                }
            }
        });

        _graph.getSelectionModel().addListener(mxEvent.CHANGE, (sender, evt) -> {
            mxGraphSelectionModel sm = (mxGraphSelectionModel) sender;
            mxCell cell = (mxCell) sm.getCell();
            if (cell != null) {
                ORM_EventObject ormEventObject = new ORM_EventObject(this);
                ormEventObject.set_mxCell(cell);
                _graph.fireProcessOfCreatingConstrainAssociation(ormEventObject);

                _graphComponent.setConnectable(false);
            }
        });

        _graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                _clientDiagramModel.beginUpdate();
                switch (_activeAction) {
                    case ENTITY_TYPE:
                        _graphPresenter.createEntityTypePresenter(new Point(e.getX(), e.getY()));
                        ((JToggleButton) _tb.getComponent(ENTITY_TYPE)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case VALUE_TYPE:
                        _graphPresenter.createValueTypePresenter(new Point(e.getX(), e.getY()));
                        ((JToggleButton) _tb.getComponent(VALUE_TYPE)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case UNARY_FACT:
                        _graphPresenter.createUnaryPredicatePresenter(new Point(e.getX(), e.getY()));
                        ((JToggleButton) _tb.getComponent(UNARY_FACT)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case BINARY_FACT:
                        _graphPresenter.createBinaryPredicatePresenter(new Point(e.getX(), e.getY()));
                        ((JToggleButton) _tb.getComponent(BINARY_FACT)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case EXCLUSION_CONSTRAINT:
                        _graphPresenter.createExclusionConstraintPresenter(new Point(e.getX(), e.getY()));
                        ((JToggleButton) _tb.getComponent(EXCLUSION_CONSTRAINT)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case INCLUSIVE_OR_CONSTRAINT:
                        _graphPresenter.createInclusiveOrConstraintPresenter(new Point(e.getX(), e.getY()));
                        ((JToggleButton) _tb.getComponent(INCLUSIVE_OR_CONSTRAINT)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case EXCLUSIVE_OR_CONSTRAINT:
                        _graphPresenter.createExclusiveOrConstraintPresenter(new Point(e.getX(), e.getY()));
                        ((JToggleButton) _tb.getComponent(EXCLUSIVE_OR_CONSTRAINT)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case ROLE_ASSOC:
                        ((JToggleButton) _tb.getComponent(ROLE_ASSOC)).setSelected(false);
                        _activeAction = -1;
                        _graphPresenter.endCreatingEdgeBy_mxGraph();
                        break;
                    case SUBTYPING:
                        ((JToggleButton) _tb.getComponent(SUBTYPING)).setSelected(false);
                        _activeAction = -1;
                        _graphPresenter.endCreatingEdgeBy_mxGraph();
                        break;
                    case CONSTRAIN_ASSOCIATION:
                        ((JToggleButton) _tb.getComponent(CONSTRAIN_ASSOCIATION)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case DELETE:
                        mxCell deleteCell = (mxCell) _graph.getSelectionCell();
                        if (deleteCell != null) {
                            _graphPresenter.deleteElementPresenter(deleteCell);
                        }
                        ((JToggleButton) _tb.getComponent(DELETE)).setSelected(false);
                        _activeAction = -1;
                        break;
                }
            }
        });

        // панель инструментов
        _tb = createToolbar();
        add(_tb, "West");
        _tb.setVisible(false);
        // панель ошибок
        _scrollBar = createScroll();
        add(_scrollBar, "South");
        _scrollBar.setVisible(false);
        // Создание строки главного меню
        JMenuBar menuBar = createMenuBar();
        // Подключаем меню к интерфейсу приложения
        setJMenuBar(menuBar);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Добавление в главное меню выпадающих пунктов меню
        JToggleButton button1 = new JToggleButton("Панель инструментов");
        button1.setBackground(Color.white);
        JToggleButton button2 = new JToggleButton("Панель ошибок");
        button2.setBackground(Color.white);
        menuBar.add(button1);
        menuBar.add(button2);

        button1.addItemListener(new MenuBarController());
        button2.addItemListener(new MenuBarController());

        button1.setSelected(true);
        button2.setSelected(true);

        return menuBar;
    }

    public void addMessage(String message) {
        JLabel mess = new JLabel(message);
        mess.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        mess.setForeground(Color.BLACK);
        _tbSb.add(mess);

        _scrollBar.revalidate();
        _scrollBar.repaint();

    }

    private JScrollPane createScroll() {
        _tbSb = createToolbarForError();
        JScrollPane jScrollPane = _scrollBar = new JScrollPane(_tbSb);

        _scrollBar.setPreferredSize(new Dimension(100, 115));
        _scrollBar.setMaximumSize(new Dimension(100, 155));
        return jScrollPane;
    }

    private JToolBar createToolbarForError() {
        JToolBar toolbar = new JToolBar();
        toolbar.setName("Список ошибок");
        toolbar.setBackground(Color.white);
        toolbar.setOrientation(1);
        toolbar.setFloatable(false);

        return toolbar;
    }

    private JToolBar createToolbar() {

        JToolBar toolbar = new JToolBar();
        JToggleButton entityType = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/EntityType.jpg")));
        entityType.setText("Entity Type");
        entityType.addItemListener(new toolbarController());
        entityType.setName(_toolbarItemName[0]);

        JToggleButton valueType = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/ValueType.jpg")));
        valueType.setText("Value Type");
        valueType.addItemListener(new toolbarController());
        valueType.setName(_toolbarItemName[1]);

        JToggleButton unaryFact = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/UnarnyFactType.jpg")));
        unaryFact.addItemListener(new toolbarController());
        unaryFact.setName(_toolbarItemName[2]);
        unaryFact.setText("Unary Role");

        JToggleButton binaryFact = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/BinaryFactType.jpg")));
        binaryFact.addItemListener(new toolbarController());
        binaryFact.setName(_toolbarItemName[3]);
        binaryFact.setText("Binary Role");

        JToggleButton constraintExclusion = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/ExclusionConstraint.jpg")));
        constraintExclusion.addItemListener(new toolbarController());
        constraintExclusion.setName(_toolbarItemName[4]);
        constraintExclusion.setText("Exclusion Constraint");

        JToggleButton inclusiveOr = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/Inclusive_orConstraint.jpg")));
        inclusiveOr.addItemListener(new toolbarController());
        inclusiveOr.setName(_toolbarItemName[5]);
        inclusiveOr.setText("Inclusive Or Constraint");

        JToggleButton exclusiveOr = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/Exclusive_orConstraint.jpg")));
        exclusiveOr.addItemListener(new toolbarController());
        exclusiveOr.setName(_toolbarItemName[6]);
        exclusiveOr.setText("Exclusive Or Constraint");

        JToggleButton roleConnector = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/RoleConnector.jpg")));
        roleConnector.addItemListener(new toolbarController());
        roleConnector.setName(_toolbarItemName[7]);
        roleConnector.setText("Role Association");

        JToggleButton subtyping = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/SubtypeConnector.jpg")));
        subtyping.setText("Subtyping");
        subtyping.addItemListener(new toolbarController());
        subtyping.setName(_toolbarItemName[8]);

        JToggleButton subtypingConstraint = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/ConstraintConnector.jpg")));
        subtypingConstraint.addItemListener(new toolbarController());
        subtypingConstraint.setName(_toolbarItemName[9]);
        subtypingConstraint.setText("Subtyping Constraint");

        JToggleButton delete = new JToggleButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/Delete.jpg")));
        delete.setText("Delete");
        delete.addItemListener(new toolbarController());
        delete.setName(_toolbarItemName[10]);

        JButton zoomIn = new JButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/zoomIn.jpg")));
        zoomIn.setText("Zoom In");
        zoomIn.addActionListener(e -> _graphComponent.zoomIn());

        JButton zoomOut = new JButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/zoomOut.jpg")));
        zoomOut.setText("Zoom Out");
        zoomOut.addActionListener(e -> _graphComponent.zoomOut());

        JButton zoomActual = new JButton(new ImageIcon(ORM2Editor.class.getResource("/resource/images/zoomActual.jpg")));
        zoomActual.setText("Zoom Actual");
        zoomActual.addActionListener(e -> _graphComponent.zoomActual());

        toolbar.add(entityType);
        toolbar.add(valueType);
        toolbar.add(unaryFact);
        toolbar.add(binaryFact);
        toolbar.add(constraintExclusion);
        toolbar.add(inclusiveOr);
        toolbar.add(exclusiveOr);
        toolbar.add(roleConnector);
        toolbar.add(subtyping);
        toolbar.add(subtypingConstraint);
        toolbar.add(delete);

        toolbar.add(zoomIn);
        toolbar.add(zoomOut);
        toolbar.add(zoomActual);
        toolbar.setBackground(Color.white);
        toolbar.setOrientation(1);
        toolbar.setFloatable(false);

        Dimension dimension = toolbar.getComponentAtIndex(6).getMaximumSize();
        for (int i = 0; i < toolbar.getComponentCount(); ++i) {
            toolbar.getComponentAtIndex(i).setMaximumSize(dimension);
        }
        return toolbar;
    }

    private class toolbarController implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() instanceof JToggleButton) {
                JToggleButton source = (JToggleButton) e.getSource();
                if (e.getStateChange() == 1) {
                    for (int i = 0; i < _toolbarItemName.length; i++) {
                        if (source.getName().equals(_toolbarItemName[i])) {
                            _activeAction = i;
                            _graphPresenter.endCreatingEdgeBy_mxGraph();
                        }
                    }
                    if (_activeAction == ROLE_ASSOC) {
                        _graphPresenter.startCreatingEdgeBy_mxGraph(1);  // Role Association 1

                    } else if (_activeAction == SUBTYPING) {
                        _graphPresenter.startCreatingEdgeBy_mxGraph(2);  // Subtyping 2
                    } else if (_activeAction == CONSTRAIN_ASSOCIATION) {
                        _graphPresenter.startCreatingEdgeBy_mxGraph(3);  // Constraint Association 2
                    }
                    deactivateAllButtonsExcept(_activeAction);
                }
            }
        }
    }

    private class MenuBarController implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            JToggleButton source = (JToggleButton) itemEvent.getSource();
            switch (source.getText()) {
                case "Панель инструментов":
                    _tb.setVisible(source.isSelected());
                    break;
                case "Панель ошибок":
                    _scrollBar.setVisible(source.isSelected());
                    break;
            }
            revalidate();
            repaint();
        }
    }

    private void deactivateAllButtonsExcept(int index) {
        for (int i = 0; i < _tb.getComponentCount(); i++) {
            if (_tb.getComponent(i) instanceof JToggleButton) {
                if (i != index) {
                    ((JToggleButton) _tb.getComponent(i)).setSelected(false);
                    if ((i == SUBTYPING && _activeAction != ROLE_ASSOC)
                            || (i == ROLE_ASSOC && _activeAction != SUBTYPING)) {
                        _graphComponent.setConnectable(false);
                    }
                }
            }
        }
    }

    private class ModelUpdateListener implements ClientDiagramModelListener {
        private List<String> _listOfDefects;

        @Override
        public void isUpdated(ModelUpdateEvent modelUpdateEvent) {
            _tbSb.removeAll();
            revalidate();
            repaint();
            _listOfDefects = new ArrayList<>();

            updateEntityTypePresenter();
            updateValueTypePresenter();
            updateUnaryPredicatePresenter();
            updateBinaryPredicatePresenter();
            updateInclusiveOrConstraintPresenter();
            updateExclusionConstraintPresenter();
            updateExclusiveOrConstraintPresenter();

            _listOfDefects = _listOfDefects.stream().distinct().collect(Collectors.toList());
            for (int i = 0; i < _listOfDefects.size(); i++) {
                addMessage((i + 1) + ". " + _listOfDefects.get(i));
            }
        }

        private void updateExclusionConstraintPresenter() {
            // РЕДАКТИРОВАНИЕ ORM_ExclusionConstraint
            List<ORM_ExclusionConstraint> ormExclusionConstraints = _clientDiagramModel.getElements(ORM_ExclusionConstraint.class).
                    filter(s -> s.getValidateStatus() == ValidateStatus.Intermediate).collect(Collectors.toList());
            for (ORM_ExclusionConstraint element : ormExclusionConstraints) {
                _graphPresenter.changeState(element, ValidateStatus.Invalid);
                _listOfDefects.addAll(element.getDefects());
            }
            List<ORM_ExclusionConstraint> exclusionConstraintList = _clientDiagramModel.getElements(ORM_ExclusionConstraint.class).
                    filter(e -> _graphPresenter.getElementPresenter(e).getValidateStatus() == ValidateStatus.Invalid
                            && e.getValidateStatus() == ValidateStatus.Acceptable).collect(Collectors.toList());
            for (ORM_ExclusionConstraint element : exclusionConstraintList) {
                _graphPresenter.changeState(element, ValidateStatus.Acceptable);
            }
        }

        private void updateExclusiveOrConstraintPresenter() {
            // РЕДАКТИРОВАНИЕ ORM_InclusiveOrConstraint
            List<ORM_ExclusionOrConstraint> ormExclusionOrConstraints = _clientDiagramModel.getElements(ORM_ExclusionOrConstraint.class).
                    filter(s -> s.getValidateStatus() == ValidateStatus.Intermediate).collect(Collectors.toList());
            for (ORM_ExclusionOrConstraint element : ormExclusionOrConstraints) {
                _graphPresenter.changeState(element, ValidateStatus.Invalid);
                _listOfDefects.addAll(element.getDefects());
            }
            List<ORM_ExclusionOrConstraint> exclusionOrConstraintList = _clientDiagramModel.getElements(ORM_ExclusionOrConstraint.class).
                    filter(e -> _graphPresenter.getElementPresenter(e).getValidateStatus() == ValidateStatus.Invalid
                            && e.getValidateStatus() == ValidateStatus.Acceptable).collect(Collectors.toList());
            for (ORM_ExclusionOrConstraint element : exclusionOrConstraintList) {
                _graphPresenter.changeState(element, ValidateStatus.Acceptable);
            }
        }

        private void updateBinaryPredicatePresenter() {
            // РЕДАКТИРОВАНИЕ ORM_UnaryPredicate
            List<ORM_BinaryPredicate> binaryPredicates = _clientDiagramModel.getElements(ORM_BinaryPredicate.class).
                    filter(s -> s.getValidateStatus() == ValidateStatus.Invalid).collect(Collectors.toList());
            for (ORM_BinaryPredicate element : binaryPredicates) {
                _graphPresenter.changeState(element, ValidateStatus.Invalid);
                _listOfDefects.addAll(element.getDefects());
            }
            List<ORM_BinaryPredicate> binaryPredicateList = _clientDiagramModel.getElements(ORM_BinaryPredicate.class).
                    filter(e -> _graphPresenter.getElementPresenter(e).getValidateStatus() == ValidateStatus.Invalid
                            && e.getValidateStatus() == ValidateStatus.Acceptable).collect(Collectors.toList());
            for (ORM_BinaryPredicate element : binaryPredicateList) {
                _graphPresenter.changeState(element, ValidateStatus.Acceptable);
            }
        }

        private void updateInclusiveOrConstraintPresenter() {
            // РЕДАКТИРОВАНИЕ ORM_InclusiveOrConstraint
            List<ORM_InclusiveOrConstraint> ormInclusiveOrConstraints = _clientDiagramModel.getElements(ORM_InclusiveOrConstraint.class).
                    filter(s -> s.getValidateStatus() == ValidateStatus.Intermediate).collect(Collectors.toList());
            for (ORM_InclusiveOrConstraint element : ormInclusiveOrConstraints) {
                _graphPresenter.changeState(element, ValidateStatus.Invalid);
                _listOfDefects.addAll(element.getDefects());
            }
            List<ORM_InclusiveOrConstraint> inclusiveOrConstraintList = _clientDiagramModel.getElements(ORM_InclusiveOrConstraint.class).
                    filter(e -> _graphPresenter.getElementPresenter(e).getValidateStatus() == ValidateStatus.Invalid
                            && e.getValidateStatus() == ValidateStatus.Acceptable).collect(Collectors.toList());
            for (ORM_InclusiveOrConstraint element : inclusiveOrConstraintList) {
                _graphPresenter.changeState(element, ValidateStatus.Acceptable);
            }
        }

        private void updateUnaryPredicatePresenter() {
            // РЕДАКТИРОВАНИЕ ORM_UnaryPredicate
            List<ORM_UnaryPredicate> unaryPredicates = _clientDiagramModel.getElements(ORM_UnaryPredicate.class).
                    filter(s -> s.getValidateStatus() == ValidateStatus.Invalid).collect(Collectors.toList());
            for (ORM_UnaryPredicate element : unaryPredicates) {
                _graphPresenter.changeState(element, ValidateStatus.Invalid);
                _listOfDefects.addAll(element.getDefects());
            }
            List<ORM_UnaryPredicate> unaryPredicateList = _clientDiagramModel.getElements(ORM_UnaryPredicate.class).
                    filter(e -> _graphPresenter.getElementPresenter(e).getValidateStatus() == ValidateStatus.Invalid
                            && e.getValidateStatus() == ValidateStatus.Acceptable).collect(Collectors.toList());
            for (ORM_UnaryPredicate element : unaryPredicateList) {
                _graphPresenter.changeState(element, ValidateStatus.Acceptable);
            }
        }

        private void updateValueTypePresenter() {
            // РЕДАКТИРОВАНИЕ ORM_ValueType
            List<ORM_ValueType> valueTypes = _clientDiagramModel.getElements(ORM_ValueType.class).
                    filter(s -> s.getValidateStatus() == ValidateStatus.Invalid).collect(Collectors.toList());
            for (ORM_ValueType element : valueTypes) {
                _graphPresenter.changeState(element, ValidateStatus.Invalid);
                _listOfDefects.addAll(element.getDefects());
            }
            List<ORM_ValueType> diagramElements2 = _clientDiagramModel.getElements(ORM_ValueType.class).
                    filter(e -> _graphPresenter.getElementPresenter(e).getValidateStatus() == ValidateStatus.Invalid
                            && e.getValidateStatus() == ValidateStatus.Acceptable).collect(Collectors.toList());
            for (ORM_ValueType element : diagramElements2) {
                _graphPresenter.changeState(element, ValidateStatus.Acceptable);
            }
        }

        private void updateEntityTypePresenter() {
            // РЕДАКТИРОВАНИЕ ORM_EntityType
            List<ORM_EntityType> entityTypes = _clientDiagramModel.getElements(ORM_EntityType.class).
                    filter(s -> s.getValidateStatus() == ValidateStatus.Invalid).collect(Collectors.toList());
            for (ORM_EntityType element : entityTypes) {
                _graphPresenter.changeState(element, ValidateStatus.Invalid);
                _listOfDefects.addAll(element.getDefects());
            }
            List<ORM_EntityType> diagramElements1 = _clientDiagramModel.getElements(ORM_EntityType.class).
                    filter(e -> _graphPresenter.getElementPresenter(e).getValidateStatus() == ValidateStatus.Invalid
                            && e.getValidateStatus() == ValidateStatus.Acceptable).collect(Collectors.toList());
            for (ORM_EntityType element : diagramElements1) {
                _graphPresenter.changeState(element, ValidateStatus.Acceptable);
            }
        }
    }

    public static void main(String[] args) {

        ORM2Editor frame = new ORM2Editor();
    }
}
