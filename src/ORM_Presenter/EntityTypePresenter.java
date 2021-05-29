package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_EntityType;

public class EntityTypePresenter extends ElementPresenter{
    public EntityTypePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos, @NotNull ORM_EntityType orm_entityType) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph()
                .insertVertex(graphPresenter.getMxGraph().getDefaultParent(), null,
                        generateName(), pos.getX(), pos.getY(), 80, 30,
                        "spacing=10;verticalLabelPosition=middle;autosize=true;rounded=true;resizable=false");

        _diagramElement = orm_entityType;
        ((ORM_EntityType)_diagramElement).setName(getName());
        ((ORM_EntityType)_diagramElement).setPosition(getPosition());
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
}
