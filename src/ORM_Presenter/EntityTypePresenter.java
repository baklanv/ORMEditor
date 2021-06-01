package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.util.Point;
import org.vstu.orm2diagram.model.ORM_EntityType;

import java.util.List;

public class EntityTypePresenter extends ElementPresenter{
    static String _style = "spacing=10;verticalLabelPosition=middle;autosize=true;rounded=true;resizable=false;";

    public EntityTypePresenter(@NotNull GraphPresenter graphPresenter, @NotNull Point pos, @NotNull ORM_EntityType orm_entityType) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph()
                .insertVertex(graphPresenter.getMxGraph().getDefaultParent(), null,
                        generateName(), pos.getX(), pos.getY(), 80, 30,
                        _style);

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

    public static String canChangeName(GraphPresenter graphPresenter, String name){
        String result = "";

        if (name.isEmpty())
            return "Entity Type should not be empty";
        List<ElementPresenter> elements = graphPresenter.getCells(EntityTypePresenter.class);

        for (ElementPresenter ele : elements) {

            String anotherName = ele._mxCell.getValue().toString();
            if (anotherName.equals(name) ) {
                return "Entity Type with this name already exists";
            }
        }

        return result;
    }


    @Override
    public boolean equals(Object obj) {
        /*1. Проверьте*/
        if (obj == this) {
            /*и верните */
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        EntityTypePresenter guest = (EntityTypePresenter) obj;
        return this.get_mxCell().getId().equals(guest.get_mxCell().getId());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
