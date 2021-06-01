package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxDomUtils;
import org.jetbrains.annotations.NotNull;
import org.vstu.nodelinkdiagram.DiagramElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class RoleAssociationPresenter extends ElementPresenter{
    public RoleAssociationPresenter(@NotNull GraphPresenter graphPresenter, mxCell edge) {
        super(graphPresenter);

        _mxCell = edge;
        _mxCell.setStyle("strokeWidth=1.5;endArrow=\"\";strokeColor=black;");
    }


    public void setSource(ElementPresenter entity) {
        get_mxCell().setSource(entity._mxCell);
    }

    public ElementPresenter getSource() {
        mxICell source = get_mxCell().getSource();
        return getGraphPresenter().getPresenter((mxCell) source);
    }

    public void setTarget(ElementPresenter entity) {
        get_mxCell().setTarget(entity._mxCell);
    }

    public ElementPresenter getTarget() {
        mxICell target = get_mxCell().getTarget();
        return getGraphPresenter().getPresenter((mxCell) target);
    }

    public static String canConnect(GraphPresenter graphPresenter, ElementPresenter source, ElementPresenter target) { /// !!! доделать надо

        String result = "";

        if (target instanceof InclusiveOrConstraintPresenter || target instanceof ExclusionConstraintPresenter || target instanceof ExclusiveOrConstraintPresenter
        || source instanceof InclusiveOrConstraintPresenter || source instanceof ExclusionConstraintPresenter || source instanceof ExclusiveOrConstraintPresenter)
            return "These elements should not be connected";
        List<ElementPresenter> elements = graphPresenter.getCells(RoleAssociationPresenter.class);

        if (source != null &&  target != null) {
            for (ElementPresenter ele : elements) {

                ElementPresenter sourceEle = ((RoleAssociationPresenter) ele).getSource();
                ElementPresenter targetEle = ((RoleAssociationPresenter) ele).getTarget();
                // проверка на уже существовании такой Subtyping с задаными узлами
                if (source.equals(sourceEle) && target.equals(targetEle) || source.equals(targetEle) && target.equals(sourceEle)) {

                    return "These elements are already connected";
                }


            }
        }
        return result;
    }
}
