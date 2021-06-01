package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.vstu.orm2diagram.model.ORM_ValueType;

import java.util.List;

public class ValueTypePresenter extends ElementPresenter{
    public ValueTypePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos
    ) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph()
                .insertVertex(graphPresenter.getMxGraph().getDefaultParent(), null,
                        generateName(), pos.getX(), pos.getY(), 80, 30,
                        "strokeWidth=1.5;autosize=true;rounded=true;align=center;dashed=true;resizable=false");

        //_diagramElement = orm_valueType;
        //((ORM_ValueType)_diagramElement).setName(getName());
        //((ORM_ValueType)_diagramElement).setPosition(getPosition());
    }

    // ------------ Генерация типового представления для Value Type -------------
    private static int valueTypeCounter = 0;

    private String generateName() {

        return "ValueType" + valueTypeCounter++;
    }

    // -------------- Характеристики представления для Value Type -----------
    public void setName(String name) {
        if (name.isEmpty()) {
            name = generateName();
        }
        _mxCell.setValue(name);
    }

    public String getName() {
        return (String) _mxCell.getValue();
    }

    public void setPosition(java.awt.Point p) {
        //получить текущую геометрию
        mxGeometry oldGeo = _mxCell.getGeometry();
        //обновлять координат позиции
        oldGeo.setX(p.getX());
        oldGeo.setY(p.getY());

        _mxCell.setGeometry(oldGeo);
    }

    public java.awt.Point getPosition() {
        //получить текущую геометрию
        mxGeometry cellGeo = _mxCell.getGeometry();

        return new java.awt.Point((int) cellGeo.getX(), (int) cellGeo.getY());
    }

    public static String canChangeName(GraphPresenter graphPresenter, String name){
        String result = "";

        List<ElementPresenter> elements = graphPresenter.getCells(ValueTypePresenter.class);

        for (ElementPresenter ele : elements) {

            String anotherName = ele._mxCell.getValue().toString();
            if (anotherName.equals(name) ) {
                return "Value Type with this name already exists";
            }
        }

        return result;
    }
}
