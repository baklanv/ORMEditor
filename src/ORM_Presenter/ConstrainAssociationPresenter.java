package ORM_Presenter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConstrainAssociationPresenter extends ElementPresenter{
    public ConstrainAssociationPresenter(@NotNull GraphPresenter graphPresenter, mxCell m1, mxCell m2) {
        super(graphPresenter);

        _mxCell = (mxCell) graphPresenter.getMxGraph().insertEdge(graphPresenter.getMxGraph().getDefaultParent(),
                null, "", m1, m2,
                "strokeWidth=2;dashed=1;strokeColor=purple");
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

        List<ElementPresenter> elements = graphPresenter.getCells(ConstrainAssociationPresenter.class);

        if (!(((source instanceof SubtypingPresenter) && (target instanceof ExclusionConstraintPresenter || target instanceof ExclusiveOrConstraintPresenter || target instanceof InclusiveOrConstraintPresenter)) ||
                ((target instanceof SubtypingPresenter) && (source instanceof ExclusionConstraintPresenter || source instanceof ExclusiveOrConstraintPresenter || source instanceof InclusiveOrConstraintPresenter)))) {
            return "These elements cannot be connected";
        }

        for (ElementPresenter ele : elements) {

            ElementPresenter sourceEle = ((ConstrainAssociationPresenter) ele).getSource();
            ElementPresenter targetEle = ((ConstrainAssociationPresenter) ele).getTarget();
            // проверка на уже существовании такой Subtyping с задаными узлами
            if (source.equals(sourceEle) && target.equals(targetEle) || source.equals(targetEle) && target.equals(sourceEle)) {

                return "These elements are already connected";
            }
        }

        return result;
    }
}
