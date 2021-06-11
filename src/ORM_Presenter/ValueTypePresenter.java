package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.vstu.orm2diagram.model.ORM_ValueType;

public class ValueTypePresenter extends ElementPresenter{
    String _style = "strokeWidth=1.5;autosize=true;rounded=true;align=center;dashed=true;resizable=false;";
    public ValueTypePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos, @NotNull ORM_ValueType orm_valueType) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph()
                .insertVertex(graphPresenter.getMxGraph().getDefaultParent(), null,
                        generateName(), pos.getX(), pos.getY(), 80, 30, _style);

        //обновлять размер
        getGraphPresenter().getMxGraph().cellSizeUpdated(get_mxCell(), false);

        _diagramElement = orm_valueType;
        ((ORM_ValueType)_diagramElement).setName(getName());
        ((ORM_ValueType)_diagramElement).setPosition(getPosition());
        _validateStatus = ValidateStatus.Acceptable;
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
        ((ORM_ValueType) _diagramElement).setName(name);
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

    public Point getPosition() {
        //получить текущую геометрию
        mxGeometry cellGeo = _mxCell.getGeometry();

        return new Point((int) cellGeo.getX(), (int) cellGeo.getY());
    }

    public void fail() {
        _graphPresenter.getMxGraph().setCellStyle(_style + "strokeColor=red", new Object[]{_mxCell});
        _validateStatus = ValidateStatus.Invalid;
    }

    public void success(){
        _graphPresenter.getMxGraph().setCellStyle(_style, new Object[]{_mxCell});
        _validateStatus = ValidateStatus.Acceptable;
    }
}
