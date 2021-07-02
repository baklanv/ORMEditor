package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.sun.istack.internal.NotNull;
import org.vstu.nodelinkdiagram.statuses.ValidateStatus;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_EntityType;

public class EntityTypePresenter extends ElementPresenter {
    String _style = "spacing=10;verticalLabelPosition=middle;autosize=true;rounded=true;resizable=false;";

    public EntityTypePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos, @NotNull ORM_EntityType orm_entityType) {
        super(graphPresenter);


        _mxCell = (mxCell) graphPresenter.getMxGraph()
                .insertVertex(graphPresenter.getMxGraph().getDefaultParent(), null,
                        generateName(), pos.getX(), pos.getY(), 80, 30, _style);

        //обновлять размер
        getGraphPresenter().getMxGraph().cellSizeUpdated(get_mxCell(), false);

        _diagramElement = orm_entityType;
        ((ORM_EntityType) _diagramElement).setName(getName());
        ((ORM_EntityType) _diagramElement).setPosition(getPosition());
        _validateStatus = ValidateStatus.Acceptable;
    }

    // ------------ Генерация типового представления для Entity Type -------------
    private static int entityTypeCounter = 0;

    private String generateName() {

        return "EntityType" + entityTypeCounter++;
    }

    // -------------- Характеристики представления для Entity Type -----------
    public void setName(String name) {
        if (name.isEmpty()) {
            name = generateName();
        }
        _mxCell.setValue(name);
        ((ORM_EntityType) _diagramElement).setName(name);
    }

    public String getName() {
        return (String) _mxCell.getValue();
    }

    public void setPosition(Point p) {
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
