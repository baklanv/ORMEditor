package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxDomUtils;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.vstu.orm2diagram.model.ORM_EntityType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class SubtypingPresenter extends ElementPresenter{
    public SubtypingPresenter(@NotNull GraphPresenter graphPresenter, mxCell edge) {
        super(graphPresenter);

        _mxCell = edge;
        _mxCell.setStyle("strokeWidth=2;endArrow=classic;strokeColor=purple;");
        //_diagramElement = diagramElement;
    }

    public void setSource(EntityTypePresenter entity) {
        get_mxCell().setSource(entity._mxCell);
    }

    public EntityTypePresenter getSource() {
        mxICell source = get_mxCell().getSource();
        return (EntityTypePresenter)getGraphPresenter().getPresenter((mxCell) source);
    }

    public void setTarget(EntityTypePresenter entity) {
        get_mxCell().setTarget(entity._mxCell);
    }

    public EntityTypePresenter getTarget() {
        mxICell target = get_mxCell().getTarget();
        return (EntityTypePresenter)getGraphPresenter().getPresenter((mxCell) target);
    }

    public static String canConnect(GraphPresenter graphPresenter, ElementPresenter source, ElementPresenter target) { /// !!! доделать надо

        String result = "";

        List<ElementPresenter> elements = graphPresenter.getCells(SubtypingPresenter.class);

        if (!(source instanceof EntityTypePresenter) || !(target instanceof EntityTypePresenter))
            return "These elements cannot be connected";

        for (ElementPresenter ele : elements) {

            ElementPresenter sourceEle = ((SubtypingPresenter) ele).getSource();
            ElementPresenter targetEle = ((SubtypingPresenter) ele).getTarget();
            // проверка на уже существовании такой Subtyping с задаными узлами
            if (source.equals(sourceEle) && target.equals(targetEle) || source.equals(targetEle) && target.equals(sourceEle)) {

                return "These elements are already connected";
            }
        }

        return result;
    }
}
