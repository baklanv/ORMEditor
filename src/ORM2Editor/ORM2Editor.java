package ORM2Editor;

import ORM_Event.Event1;
import ORM_Event.EventRecorderListener;
import ORM_Presenter.GraphPresenter;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import org.vstu.nodelinkdiagram.*;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_DiagramFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class ORM2Editor extends JFrame implements DiagramClient{

    ORM2Editor_mxGraph _graph;
    ClientDiagramModel _clientDiagramModel;
    GraphPresenter _graphPresenter;
    mxGraphComponent _graphComponent;

    // значение по умольчанию -1
    int _activeAction = -1;
    JToolBar _tb;

    private final int ENTITY_TYPE = 0, VALUE_TYPE = 1, UNARY_FACT = 2,
            BINARY_FACT = 3, EXCLUSION_CONSTRAINT = 4, INCLUSIVE_OR_CONSTRAINT = 5, EXCLUSIVE_OR_CONSTRAINT = 6,
            ROLE_ASSOC = 7, SUBTYPING = 8, SUBTYPING_CONSTRAIN = 9, DELETE = 10;

    String[] _toolbarItemName = {"EntityType", "ValueType", "UnaryFactType", "BinaryFactType", "ExclusionConstraint", "Inclusive Or Oonstraint",
            "ExclusiveOrConstraint", "RoleConnector", "SubtypeConnector", "SubtypingConstrain", "Delete"};

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

        _tb = createToolbar();
        add(_tb, "West");

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
                        ((JToggleButton) _tb.getComponent(INCLUSIVE_OR_CONSTRAINT)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case ROLE_ASSOC:
                        ((JToggleButton) _tb.getComponent(ROLE_ASSOC)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case SUBTYPING:
                        ((JToggleButton) _tb.getComponent(SUBTYPING)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case SUBTYPING_CONSTRAIN:
                        ((JToggleButton) _tb.getComponent(SUBTYPING_CONSTRAIN)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case DELETE:
                        mxCell deleteCell = (mxCell) _graph.getSelectionCell();
                        //_graphPresenter.de
                        ((JToggleButton) _tb.getComponent(DELETE)).setSelected(false);
                        _activeAction = -1;
                        break;
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setVisible(true);
    }

    private JToolBar createToolbar() {

        JToolBar toolbar = new JToolBar();

        JToggleButton entityType = new JToggleButton(new ImageIcon("images/EntityType.jpg"));
        entityType.setText("Entity Type");
        entityType.addItemListener(new toolbarController());
        entityType.setName(_toolbarItemName[0]);

        JToggleButton valueType = new JToggleButton(new ImageIcon("images/ValueType.jpg"));
        valueType.setText("Value Type");
        valueType.addItemListener(new toolbarController());
        valueType.setName(_toolbarItemName[1]);

        JToggleButton unaryFact = new JToggleButton(new ImageIcon("images/UnarnyFactType.jpg"));
        unaryFact.addItemListener(new toolbarController());
        unaryFact.setName(_toolbarItemName[2]);
        unaryFact.setText("Unary Role");

        JToggleButton binaryFact = new JToggleButton(new ImageIcon("images/BinaryFactType.jpg"));
        binaryFact.addItemListener(new toolbarController());
        binaryFact.setName(_toolbarItemName[3]);
        binaryFact.setText("Binary Role");

        JToggleButton constraintExclusion = new JToggleButton(new ImageIcon("images/ExclusionConstraint.jpg"));
        constraintExclusion.addItemListener(new toolbarController());
        constraintExclusion.setName(_toolbarItemName[4]);
        constraintExclusion.setText("Exclusion Constraint");

        JToggleButton inclusiveOr = new JToggleButton(new ImageIcon("images/Inclusive‐orConstraint.jpg"));
        inclusiveOr.addItemListener(new toolbarController());
        inclusiveOr.setName(_toolbarItemName[5]);
        inclusiveOr.setText("Inclusive Or Constraint");

        JToggleButton exclusiveOr = new JToggleButton(new ImageIcon("images/Exclusive‐orConstraint.jpg"));
        exclusiveOr.addItemListener(new toolbarController());
        exclusiveOr.setName(_toolbarItemName[6]);
        exclusiveOr.setText("Exclusive Or Constraint");

        JToggleButton roleConnector = new JToggleButton(new ImageIcon("images/RoleConnector.jpg"));
        roleConnector.addItemListener(new toolbarController());
        roleConnector.setName(_toolbarItemName[7]);
        roleConnector.setText("Role Association");

        JToggleButton subtyping = new JToggleButton(new ImageIcon("images/SubtypeConnector.jpg"));
        subtyping.setText("Subtyping");
        subtyping.addItemListener(new toolbarController());
        subtyping.setName(_toolbarItemName[8]);

        JToggleButton subtypingConstraint = new JToggleButton(new ImageIcon("images/ConstraintConnector.jpg"));
        subtypingConstraint.addItemListener(new toolbarController());
        subtypingConstraint.setName(_toolbarItemName[9]);
        subtypingConstraint.setText("Subtyping Constraint");

        JToggleButton delete = new JToggleButton(new ImageIcon("images/Delete.jpg"));
        delete.setText("Delete");
        delete.addItemListener(new toolbarController());
        delete.setName(_toolbarItemName[10]);

        JButton zoomIn = new JButton(new ImageIcon("images/zoomIn.jpg"));
        zoomIn.setText("Zoom In");
        zoomIn.addActionListener(e -> _graphComponent.zoomIn());

        JButton zoomOut = new JButton(new ImageIcon("images/zoomOut.jpg"));
        zoomOut.setText("Zoom Out");
        zoomOut.addActionListener(e -> _graphComponent.zoomOut());

        JButton zoomActual = new JButton(new ImageIcon("images/zoomActual.jpg"));
        zoomActual.setText("Zoom Actual");
        zoomActual.addActionListener(e -> _graphComponent.zoomActual());

        toolbar.add(entityType, BorderLayout.EAST);
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
                        }
                    }
                    deactivateAllButtonsExcept(_activeAction);
                }
            }
        }
    }

    private void deactivateAllButtonsExcept(int index) {

        for (int i = 0; i < _tb.getComponentCount(); i++) {

            if (_tb.getComponent(i) instanceof JToggleButton) {
                if (i != index) {
                    ((JToggleButton) _tb.getComponent(i)).setSelected(false);
                }
            }
        }
    }

    private final Set<EventRecorderListener> _listeners = new HashSet<>();

    public void addListener(EventRecorderListener l) {
        _listeners.add(l);
    }

    public void removeListener(EventRecorderListener l) {
        _listeners.remove(l);
    }

    private void fireEdit(Event1 e) {

        for (EventRecorderListener listener : _listeners) {
            listener.edit(e);
        }
    }

    private void fireDelete(Event1 e) {

        for (EventRecorderListener listener : _listeners) {
            listener.delete(e);
        }
    }

    public static void main(String[] args) {

        ORM2Editor frame = new ORM2Editor();
    }
}
