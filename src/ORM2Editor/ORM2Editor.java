package ORM2Editor;

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

public class ORM2Editor extends JFrame {

    ORM2Editor_mxGraph _graph;
    ClientDiagramModel _clientDiagramModel;
    GraphPresenter _graphPresenter;
    mxGraphComponent _graphComponent;

    // значение по умольчанию -1
    int _activeAction = -1;
    JToolBar _tb;

    private final int ENTITY_TYPE = 0, VALUE_TYPE = 1, SYBTYPING = 2;

    String[] _toolbarItemName = {"EntityType", "ValueType", "Sybtyping"};

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
                //clientModel.beginUpdate();
                switch (_activeAction) {

                    case ENTITY_TYPE:
                        //Test_BaseNode test_baseNode = clientModel.createNode(Test_BaseNode.class);
                        //test_baseNode.setPosition(new Point(e.getX(), e.getY()));
                        //clientModel.commit();
                        ((JToggleButton) _tb.getComponent(ENTITY_TYPE)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case VALUE_TYPE:
                        mxCell deleteCell = (mxCell) _graph.getSelectionCell();

                        if (deleteCell != null) {

                            DiagramElement deleteElement = _graphPresenter.getORM_Element(deleteCell);
                            //clientModel.removeElement(deleteElement);
                            //_graphPresenter.delete(_graphPresenter.getPresenter(deleteCell));
                            //clientModel.commit();
                        }
                        ((JToggleButton) _tb.getComponent(VALUE_TYPE)).setSelected(false);
                        _activeAction = -1;
                        break;
                    case SYBTYPING:
                        ((JToggleButton) _tb.getComponent(SYBTYPING)).setSelected(false);
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

        JToggleButton sybtyping = new JToggleButton(new ImageIcon("images/SubtypeConnector.jpg"));
        sybtyping.setText("Sybtyping");
        sybtyping.addItemListener(new toolbarController());
        sybtyping.setName(_toolbarItemName[2]);

        toolbar.add(entityType);
        toolbar.add(valueType);
        toolbar.add(sybtyping);
        toolbar.setBackground(Color.white);
        toolbar.setOrientation(1);
        toolbar.setFloatable(false);

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
                    deactiveAllButtonsExcept(_activeAction);
                }
            }
        }
    }

    private void deactiveAllButtonsExcept(int index) {

        for (int i = 0; i < _tb.getComponentCount(); i++) {

            if (_tb.getComponent(i) instanceof JToggleButton) {
                if (i != index) {
                    ((JToggleButton) _tb.getComponent(i)).setSelected(false);
                }
            }
        }
    }

    public static void main(String[] args) {

        ORM2Editor frame = new ORM2Editor();
    }
}
