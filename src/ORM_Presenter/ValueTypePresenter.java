package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.util.Point;

public class ValueTypePresenter extends ElementPresenter{
    public ValueTypePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph()
                .insertVertex(graphPresenter.getMxGraph().getDefaultParent(), null,
                        generateName(), pos.getX(), pos.getY(), 80, 30,
                        "spacing=10;verticalLabelPosition=middle;autosize=true;rounded=true;resizable=false");
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
}
